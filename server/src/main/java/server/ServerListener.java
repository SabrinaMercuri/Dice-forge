package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.De;
import core.Face;
import core.Forge;
import core.Inventaire;
import core.Joueur;
import core.Ressource;
import protocol.AskForgingMessage;
import protocol.CardEffectMessage;
import protocol.CarteMessage;
import protocol.ChooseFaceMessage;
import protocol.FaceMessage;
import protocol.HuntMessage;
import protocol.Listener;
import protocol.MarteauAcheteMessage;
import protocol.MessageType;
import protocol.NameMessage;
import protocol.NewActionMessage;
import protocol.RollMessage;
import protocol.SatyrsMessage;
import protocol.SilverHindMessage;
import protocol.SphinxMessage;
import protocol.TurnMessage;
import protocol.UpdateDeMessage;
import protocol.UpdateRessourceMessage;

public class ServerListener implements Listener, ConnectListener {

	private Server server;
	private ServerCardEffectFactory effectFactory;
	private int nbActionJoueur = 0;

	private HashMap<String, ArrayList<Face>> choice;
	private HashMap<String, ArrayList<Face>> minotaurBuffer;

	/**
	 * Nombre de joueur aillant recu un message sert de buffer
	 */
	private int playerReceived = 0;

	public ServerListener(Server server) {
		this.server = server;
		this.effectFactory = new ServerCardEffectFactory(this);
	}

	/**
	 * Fonction permettant d'ajouter un nom au client
	 * 
	 * @param client Le client concerné
	 * @param data   Le nom que l'on doit lui attribuer
	 */
	public void ajouterNom(SocketIOClient client, NameMessage data) {
		server.addName(client, data.getName());
	}

	/**
	 * Fonction qui réinitialise l'inventaire et le plateau des joueur pour la
	 * nouvelle partie
	 */
	public void reinitialiserInventairePlateau() {

		// L'inventaire
		for (ClientManager c : server.getAcceptedConnection()) {
			c.setInventaire(new Inventaire());

		}

		// Le plateau
		server.getGameManager().getPlateau().setNbRound(0);
		server.getGameManager().getPlateau().setForge(new Forge());
		server.getGameManager().getPlateau().initIsle();
		server.launchTheParty();

	}

	/**
	 * Fonction qui forge les dés d'un joueur
	 * 
	 * @param client Le client concerné
	 * @param data   Contenu du message reçu en forme de string
	 */
	public void demandeDeForger(SocketIOClient client, String data) {
		ObjectMapper om = new ObjectMapper();
		AskForgingMessage forgingMessage = new AskForgingMessage();

		try {
			forgingMessage = om.readValue(data, AskForgingMessage.class);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		if (server.isModeLog()) {
			System.out.println(forgingMessage.getNomJoueur() + " choisie une face d'un autre joueur ");
		}
		ArrayList<De> de = new ArrayList<De>();
		de.addAll(forgingMessage.getListDeJoueur());
		server.findClientManager(client).getInventaire().setListDeJoueur(de);
	}

	/**
	 * Fonction qui permet l'achat du carte Marteau tout en actualisant les
	 * ressource de la personne qui l'achète
	 * 
	 * @param client Le client qui achète la carte Marteau
	 * @param data   Contenu du message reçu en forme de string
	 */
	public void achatCarteMarteau(SocketIOClient client, String data) {
		ObjectMapper mapping = new ObjectMapper();
		MarteauAcheteMessage marteauMessageAchat = new MarteauAcheteMessage();

		nbActionJoueur++;

		try {
			marteauMessageAchat = mapping.readValue(data, MarteauAcheteMessage.class);

			// Envoit en BroadCast & Mise à jour Locale
			server.getSocket().getBroadcastOperations().sendEvent(MessageType.marteauAchat.name(), data);

			for (int i = 0; i < server.getAcceptedConnection().size(); i++) {
				if (server.getAcceptedConnection().get(i) != null) {

					if (server.getAcceptedConnection().get(i).getSocket().equals(client)) {

						// MAJ de son nb de Lunary
						server.getAcceptedConnection().get(i).getInventaire()
								.retirerLunary(marteauMessageAchat.getLunaryCost());
						server.getGameManager().getPlateau().getPlayers().get(i).getInventaire()
								.retirerLunary(marteauMessageAchat.getLunaryCost());
						// MAJ de son nb de Solary
						server.getAcceptedConnection().get(i).getInventaire()
								.retirerSolary(marteauMessageAchat.getSolaryCost());
						server.getGameManager().getPlateau().getPlayers().get(i).getInventaire()
								.retirerSolary(marteauMessageAchat.getSolaryCost());

						// mise a jour de la carte et de l'ile sur le plateau
						server.getGameManager().getPlateau().getPlayers().get(i).getInventaire().getListedecartes()
								.add(marteauMessageAchat.getMarteau());
						server.getGameManager().getPlateau().getIsles().get(marteauMessageAchat.getIle()).prendreCarte(
								marteauMessageAchat.getMarteau().getNom(),
								server.getGameManager().getPlateau().getPlayers().get(i));
					}

				}
				if (server.isModeLog()) {
					System.out.println(marteauMessageAchat.getNamePlayer() + " a achete une carte marteau ");
				} else {
					// Met a jour les statistiques du joueur ayant achete la carte
					for (StatJoueur joueur : server.getGameManager().getStat().getJoueurs()) {
						if (joueur.getName().equals(marteauMessageAchat.getNamePlayer())) {

							joueur.retirerLunaryDepense(marteauMessageAchat.getLunaryCost());
							joueur.retirerSolaryDepense(marteauMessageAchat.getSolaryCost());
							joueur.ajouteCartesAchete();
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (nbActionJoueur <= 1) {
			if (server.isModeLog()) {
				System.out.println(marteauMessageAchat.getNamePlayer() + ", souhaites tu rejouer ?");
			}

			client.sendEvent(MessageType.newAction.name(), new NewActionMessage(false, null, true));
		} else
			nbActionJoueur = 0;
	}

	/**
	 * Fonction qui permet l'achat d'une carte tout en actualisant les ressource de
	 * la personne qui l'achète
	 * 
	 * @param client Le client qui achète la carte
	 * @param data   Contenu du message reçu en forme de string
	 */
	public void achatDeCarte(SocketIOClient client, String data) {
		ObjectMapper mapping = new ObjectMapper();
		CarteMessage cartemessage = new CarteMessage();
		ClientManager cm = server.findClientManager(client);

		nbActionJoueur++;

		try {
			cartemessage = mapping.readValue(data, CarteMessage.class);
			if (cartemessage.getCarte().getHasBuyEffect()) {
				// Met dans l effect factory la carte
				effectFactory.factory(cartemessage.getCarte().getNom(), cm.getNom());
			}
			// Envoit en BroadCast & Mise à jour Locale
			server.getSocket().getBroadcastOperations().sendEvent(MessageType.carteAchete.name(), data);
			// Mise a jour ressource du joueur
			for (int i = 0; i < server.getAcceptedConnection().size(); i++) {
				if (server.getAcceptedConnection().get(i) != null) {

					if (server.getAcceptedConnection().get(i).getSocket().equals(client)) {

						// MAJ de son nb de Lunary
						server.getAcceptedConnection().get(i).getInventaire()
								.retirerLunary(cartemessage.getLunaryCost());
						server.getGameManager().getPlateau().getPlayers().get(i).getInventaire()
								.retirerLunary(cartemessage.getLunaryCost());
						// MAJ de son nb de Solary
						server.getAcceptedConnection().get(i).getInventaire()
								.retirerSolary(cartemessage.getSolaryCost());
						server.getGameManager().getPlateau().getPlayers().get(i).getInventaire()
								.retirerSolary(cartemessage.getSolaryCost());

						// mise a jour de la carte et de l'ile sur le plateau
						server.getGameManager().getPlateau().getPlayers().get(i).getInventaire().getListedecartes()
								.add(cartemessage.getCarte());
						server.getGameManager().getPlateau().getIsles().get(cartemessage.getIle()).prendreCarte(
								cartemessage.getCarte().getNom(),
								server.getGameManager().getPlateau().getPlayers().get(i));

					}

				}
			}

			if (server.isModeLog()) {
				System.out.println(
						cartemessage.getNamePlayer() + " a achete une carte " + cartemessage.getCarte().getNom());
			} else {
				// Met a jour les statistiques du joueur apres achat de la carte
				for (StatJoueur joueur : server.getGameManager().getStat().getJoueurs()) {
					if (joueur.getName().equals(cartemessage.getNamePlayer())) {

						joueur.retirerLunaryDepense(cartemessage.getLunaryCost());
						joueur.retirerSolaryDepense(cartemessage.getSolaryCost());
						joueur.ajouteCartesAchete();

					}
				}
			}

			if (nbActionJoueur <= 1) {
				if (server.isModeLog()) {
					System.out.println(cartemessage.getNamePlayer() + ", souhaites tu rejouer ?");
				}
				client.sendEvent(MessageType.newAction.name(), new NewActionMessage(false, null, true));
			} else
				nbActionJoueur = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fonction qui permet l'achat d'une face tout en mettant à jour les dés et le
	 * nombre de gold du joueur concerné
	 * 
	 * @param client Le client qui achète la face
	 * @param data   Contenu du message reçu en forme de string
	 */
	public void achatDeFace(SocketIOClient client, String data) {
		ObjectMapper mapping = new ObjectMapper();
		FaceMessage facemessage = new FaceMessage();

		nbActionJoueur++;

		try {
			// Mise à jour Locale
			facemessage = mapping.readValue(data, FaceMessage.class);
			server.getSocket().getBroadcastOperations().sendEvent(MessageType.faceAchete.name(), data);
			// Mise a jour serveur
			for (int i = 0; i < server.getAcceptedConnection().size(); i++) {
				if (server.getAcceptedConnection().get(i) != null) {

					if (server.getAcceptedConnection().get(i).getSocket().equals(client)) {

					}

				}
			}
			for (ClientManager cm : server.getAcceptedConnection()) {
				if (cm.getNom().equals(facemessage.getNamePlayer())) {
					// Mise à jour des dés du joueur
					cm.getInventaire().setListDeJoueur(facemessage.getUpdatedDe());
					// MAJ de son nb de gold
					cm.getInventaire().retirerGold(facemessage.getCost().get(Ressource.gold));
				}
			}

			if (server.isModeLog()) {
				System.out.println(facemessage.getNamePlayer() + " a achete une face coutant "
						+ facemessage.getCost().get(Ressource.gold) + " de gold");
			} else {
				// Met a jour les statistiques du joueur apres achat de la face
				for (StatJoueur joueur : server.getGameManager().getStat().getJoueurs()) {
					if (joueur.getName().equals(facemessage.getNamePlayer())) {

						joueur.retirerOrDepense(facemessage.getCost().get(Ressource.gold));
						joueur.ajouteFacesAchete();

					}
				}
			}

			if (nbActionJoueur <= 1) {
				if (server.isModeLog()) {
					System.out.println(facemessage.getNamePlayer() + ", souhaites tu rejouer ?");
				}
				client.sendEvent(MessageType.newAction.name(), new NewActionMessage(false, null, true));
			} else
				nbActionJoueur = 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fonction qui permet de déterminer si le jouer rejoue (avec actualisation de
	 * ses ressource) ou non
	 * 
	 * @param client Le joueur qui souhaite rejouer ou non
	 * @param data   Le contenu du message reçu en forme de String
	 */
	public void nouvelleAction(SocketIOClient client, String data) {
		ObjectMapper mapping = new ObjectMapper();
		NewActionMessage message = new NewActionMessage();

		try {
			message = mapping.readValue(data, NewActionMessage.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String nameJoueur = message.getNomJoueur();

		if (message.isRePlaying()) {
			// Si le joueur rejoue, on lui retire 2 cailloux + message aux autres pour
			// qu'ils le fassent
			if (server.isModeLog()) {
				System.out.println(nameJoueur + " va rejouer");
			} else {
				// Met a jour les statistiques
				for (StatJoueur joueur : server.getGameManager().getStat().getJoueurs()) {
					if (joueur.getName().equals(nameJoueur)) {
						joueur.retirerSolaryDepense(2);
					}
				}
			}
			server.findClientManager(client).getInventaire().retirerSolary(2);
			server.getSocket().getBroadcastOperations().sendEvent(MessageType.newAction.name(), data);
		} else {
			if (server.isModeLog()) {
				System.out.println(nameJoueur + " ne va pas rejouer");
			}
		}
	}

	/**
	 * Fonction qui
	 * 
	 * @param data Le contenu du message reçu en forme de String
	 */
	public void effetCarte(String data) {
		ObjectMapper om = new ObjectMapper();
		CardEffectMessage cem = new CardEffectMessage();

		try {
			cem = om.readValue(data, CardEffectMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (server.isModeLog()) {
			System.out.println("----------- Effet de la carte " + cem.getCardName() + " pour " + cem.getNamePlayer()
					+ " -----------");
		}
		if (cem.getCardName().equals("SilverHindCard")) {
			server.getSocket().getBroadcastOperations().sendEvent(MessageType.silverhind.name(),
					new SilverHindMessage(cem.getNamePlayer(), true));
		} else {
			effectFactory.factory(cem.getCardName(), cem.getNamePlayer());
		}
	}

	/**
	 * Fonction qui permet de mettre à jour les dés du joueur lors d'un
	 * UpdateDeMessage
	 * 
	 * @param data Le contenu du message reçu en forme de String
	 */
	public void updateDe(String data) {
		ObjectMapper om = new ObjectMapper();
		UpdateDeMessage cem = new UpdateDeMessage();
		try {
			cem = om.readValue(data, UpdateDeMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (server.isModeLog()) {
			System.out.println(cem.getNamePlayer() + " met a jour ses des");
		}
		getServer().getPlayerByName(cem.getNamePlayer()).getInventaire().setListDeJoueur(cem.getUpdatedDe());
		server.getSocket().getBroadcastOperations().sendEvent(MessageType.updateDe.name(), data);
	}

	/**
	 * Fonction qui met à jour les ressources d'un joueur lors d'un
	 * UpdateressourceMessage
	 * 
	 * @param data Le contenu du message reçu en forme de String
	 */
	public void updateRessource(String data) {
		ObjectMapper om = new ObjectMapper();
		UpdateRessourceMessage cem = new UpdateRessourceMessage();
		try {
			cem = om.readValue(data, UpdateRessourceMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (server.isModeLog()) {
			System.out.println(cem.getNamePlayer() + " met a jour ses ressources");
		}
		server.getGameManager().getPlateau().getPlayerByName(cem.getNamePlayer()).getInventaire()
				.updateRessource(new HashMap<Ressource, Integer>(cem.getRessources()));
		server.getSocket().getBroadcastOperations().sendEvent(MessageType.updateRessource.name(), data);
	}

	@Override
	public void init() {
		// Reception de Message de type Name
		server.getSocket().addEventListener(MessageType.name.name(), NameMessage.class,
				new DataListener<NameMessage>() {

					@Override
					public void onData(SocketIOClient client, NameMessage data, AckRequest ackSender) {

						ajouterNom(client, data);

					}
				});

		server.getSocket().addEventListener(MessageType.newgame.name(), String.class, new DataListener<String>() {

			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {

				// On reinitialise le plateau et les inventaire de chacun

				reinitialiserInventairePlateau();
			}

		});

		// Reception de Message de type EndTurn au prochain
		server.getSocket().addEventListener(MessageType.endTurn.name(), TurnMessage.class,
				new DataListener<TurnMessage>() {
					@Override
					public void onData(SocketIOClient client, TurnMessage data, AckRequest ackSender) {
						if (server.isModeLog()) {
							System.out.println(server.findClientManager(client).getNom() + " a fini son tour.");
						}
						nbActionJoueur = 0;
						// On incremente indexJoueur
						server.getGameManager().incrementIndexJoueur();
						if (server.getGameManager().getIndexJoueur() == 0) {
							// Nouvelle manche
							server.getGameManager().newRound();
						} else {
							server.getGameManager().turn();
						}
					}
				});

		// Reception de Message de type askForging
		server.getSocket().addEventListener(MessageType.askForge.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) {

				demandeDeForger(client, data);
			}
		});

		// Reception de Message de type Carte Marteau
		server.getSocket().addEventListener(MessageType.marteauAchat.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) {

				achatCarteMarteau(client, data);
			}
		});

		// Reception de Message de type Carte
		server.getSocket().addEventListener(MessageType.carteAchete.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) {

				achatDeCarte(client, data);
			}
		});

		// Reception de Message de type Face & Mise à jour des faces diso
		server.getSocket().addEventListener(MessageType.faceAchete.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) {

				achatDeFace(client, data);
			}
		});

		server.getSocket().addEventListener(MessageType.hunt.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				processHuntMessage(data);
			}
		});

		server.getSocket().addEventListener(MessageType.hunt2.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				processHuntMessage2(data);
			}
		});

		// Reception de message de type Newaction
		server.getSocket().addEventListener(MessageType.newAction.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) {

				nouvelleAction(client, data);

			}
		});

		// Reception de message de type CardEffectMessage
		server.getSocket().addEventListener(MessageType.effect.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {

				effetCarte(data);
			}

		});

		// Reception de message de type UpdateDe
		server.getSocket().addEventListener(MessageType.updateDe.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				updateDe(data);
			}
		});

		// Reception de message de type UpdateRessource
		server.getSocket().addEventListener(MessageType.updateRessource.name(), String.class,
				new DataListener<String>() {
					@Override
					public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {

						updateRessource(data);
					}
				});

		server.getSocket().addEventListener(MessageType.sphinx.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				Joueur joueur = getServer().findClientManager(client);
				processSphinxMessage(joueur, data);
			}
		});

		server.getSocket().addEventListener(MessageType.sphinx2.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				Joueur joueur = getServer().findClientManager(client);
				processSphinx2Message(joueur, data);
			}
		});

		server.getSocket().addEventListener(MessageType.silverhind.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				Joueur joueur = getServer().findClientManager(client);
				processSilverHind(joueur, data);
			}
		});

		server.getSocket().addEventListener(MessageType.silverhind2.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				Joueur joueur = getServer().findClientManager(client);
				processSilverHind2(joueur, data);
			}
		});

		server.getSocket().addEventListener(MessageType.cancer.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				Joueur joueur = getServer().findClientManager(client);
				processCancerMessage(joueur, data);
			}
		});

		// Le joueur a fait son choix on stock son choix en attendant que tout le monde
		// nous renvoie ce qu'il a choisi on ajoute son choix au buffer
		server.getSocket().addEventListener(MessageType.rollChoice.name(), String.class, new DataListener<String>() {

			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();
				rm = om.readValue(data, RollMessage.class);
				// ajoute le choix du joueur au buffer
				String playerName = server.findClientManager(client).getNom();
				addChoiceVerifyMaybeSend(playerName, rm.getRollResults().get(playerName));
			}

		});

		// Le joueur nous a envoyé qu'il avait reçu les choices on le fait jouer
		server.getSocket().addEventListener(MessageType.rollEnd.name(), String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				if (hasReceived()) {
					timeToDoYourAction();
				}
			}

		});

		server.getSocket().addEventListener(MessageType.effectEnd.name(), String.class, new DataListener<String>() {

			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				// On lui renvoie effectEnd avec un turnMessage et son nom le client comprendra
				// que cest à lui de jouer
				String name = getServer().getAcceptedConnection().get(getServer().getGameManager().getIndexJoueur())
						.getNom();
				TurnMessage tm = new TurnMessage(name);
				getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.effectEnd.name(), tm);
			}

		});

		// Attribue les ressources et envoie aux autres clients le fait qu'on doit lui
		// rajouter des ressources
		server.getSocket().addEventListener(MessageType.satyrs.name(), String.class, new DataListener<String>() {

			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				ClientManager joueur = getServer().findClientManager(client);
				processSatyrsMessage(joueur, data);
			}

		});

		// Attribue les ressources et envoie aux autres clients le fait qu'on doit lui
		// rajouter des ressources
		server.getSocket().addEventListener(MessageType.guardianowls.name(), String.class, new DataListener<String>() {

			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				ClientManager joueur = getServer().findClientManager(client);
				processGuardianOwlsMessage(joueur, data);
			}

		});

		server.getSocket().addEventListener(MessageType.minotaur.name(), String.class, new DataListener<String>() {

			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				ObjectMapper om = new ObjectMapper();
				Joueur joueur = getServer().findClientManager(client);
				RollMessage rm = om.readValue(data, RollMessage.class);
				addMinotaurBufferVerifyMaybeSend(joueur.getNom(), rm.getRollResults().get(joueur.getNom()));
			}

		});
	}

	@Override
	public void onConnect(SocketIOClient client) {
		try {
			System.out.println("Received a connection demand ");
			server.acceptConnection(client);
		} catch (IOException e) {
			System.err.println("An error occured while accepting an incoming connection");
			e.printStackTrace();
		}
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public ServerCardEffectFactory getEffectFactory() {
		return this.effectFactory;
	}

	public void setEffectFactory(ServerCardEffectFactory effectFactory) {
		this.effectFactory = effectFactory;
	}

	public HashMap<String, ArrayList<Face>> getChoice() {
		return this.choice;
	}

	public void setChoice(HashMap<String, ArrayList<Face>> choice) {
		this.choice = choice;
	}

	/**
	 * Init choice avec les arraylist init à null
	 */
	public void initChoice() {
		this.setChoice(new HashMap<String, ArrayList<Face>>());
		for (ClientManager cm : getServer().getAcceptedConnection()) {
			getChoice().put(cm.getNom(), null);
		}
	}

	/**
	 * Reset le buffer choice en passant les arraylist à null
	 */
	public void resetChoice() {
		for (ClientManager cm : getServer().getAcceptedConnection()) {
			getChoice().replace(cm.getNom(), null);
		}
	}

	/**
	 * Ajoute le choix d'un joueur dans le buffer, fonction bloquante, un seul appel
	 * à la fois possible Si le buffer est rempli on envoie les resultats
	 * 
	 * @param playerName nom du joueur auquel on doit rajouter dans le buffer son choix
	 * @param choice choix des faces fait par le joueur
	 */
	public synchronized void addChoice(String playerName, ArrayList<Face> choice) {
		getChoice().replace(playerName, choice);
	}

	/**
	 * Regarde si tous les choix des joueurs ont été receptionné, fonction
	 * bloquante, un seul appel à la fois possible.
	 * 
	 * @return true si choix est rempli, false s'il ne l'est pas encore
	 */
	public synchronized boolean isChoiceFull() {
		boolean isReady = true;
		for (ClientManager cm : getServer().getAcceptedConnection()) {
			if (getChoice().get(cm.getNom()) == null) {
				isReady = false;
				break;
			}
		}
		return isReady;
	}

	/**
	 * Envoie un RollMessage à tous les clients pour leur donner le choix de tous
	 * les joueurs et qu'il puissent ajouter les ressources
	 */
	public void sendChoice() {
		ObjectMapper om = new ObjectMapper();
		RollMessage rm = new RollMessage(this.getChoice());
		try {
			getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.rollChoice.name(),
					om.writeValueAsString(rm));
			addStat(this.getChoice());
			ajouterRessource(this.getChoice());
			this.resetChoice();

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Un appel à la fois, synchronized, ajoute le choix, verifie si tous les
	 * joueurs ont rendu leurs choix, si cest le cas renvoie les choix
	 * 
	 * @param playerName nom du joueur qui a rendu son choix au serveur
	 * @param choice     faces qu'à choisi le joueur
	 */
	public synchronized void addChoiceVerifyMaybeSend(String playerName, ArrayList<Face> choice) {
		addChoice(playerName, choice);
		if (isChoiceFull())
			sendChoice();
	}

	public HashMap<String, ArrayList<Face>> getMinotaurBuffer() {
		return this.minotaurBuffer;
	}

	public void setMinotaurBuffer(HashMap<String, ArrayList<Face>> minotaurBuffer) {
		this.minotaurBuffer = minotaurBuffer;
	}

	/**
	 * Init choice avec les arraylist init à null
	 * @param namePlayer nom du joueur qui doit être exclu de l'effet minotaur
	 */
	public void initMinotaurBuffer(String namePlayer) {
		this.setMinotaurBuffer(new HashMap<String, ArrayList<Face>>());
		for (ClientManager cm : getServer().getAcceptedConnection()) {
			if (!cm.getNom().equals(namePlayer))
				getMinotaurBuffer().put(cm.getNom(), null);
		}
	}

	/**
	 * Reset le buffer choice en passant les arraylist à null
	 */
	public void resetMinotaurBuffer() {
		for (ClientManager cm : getServer().getAcceptedConnection()) {
			getMinotaurBuffer().replace(cm.getNom(), null);
		}
	}

	/**
	 * Ajoute le choix d'un joueur dans le buffer, fonction bloquante, un seul appel
	 * à la fois possible Si le buffer est rempli on envoie les resultats
	 * 
	 * @param playerName nom du joueur dont son choix doit etre rajouter dans le buffer
	 * @param choice choix des faces fait par le joueur
	 */
	public synchronized void addMinotaurBuffer(String playerName, ArrayList<Face> choice) {
		getMinotaurBuffer().replace(playerName, choice);
	}

	/**
	 * Regarde si tous les choix des joueurs ont été receptionné, fonction
	 * bloquante, un seul appel à la fois possible.
	 * 
	 * @return true si choix est rempli, false s'il ne l'est pas encore
	 */
	public synchronized boolean isMinotaurBufferFull() {
		boolean isReady = true;
		for(String name : getMinotaurBuffer().keySet()) {
			if(getMinotaurBuffer().get(name)==null) {
				isReady = false;
				break;
			}
		}
		return isReady;
	}

	/**
	 * Envoie un RollMessage à tous les clients pour leur donner le choix de tous
	 * les joueurs et qu'il puissent ajouter les ressources
	 */
	public void sendMinotaurBuffer() {
		ObjectMapper om = new ObjectMapper();
		
		try {
			for(String s : getMinotaurBuffer().keySet()) {
				ArrayList<Face> myFace = getMinotaurBuffer().get(s);
				HashMap<Ressource, Integer> ressource = new HashMap<Ressource, Integer>();
				for(int i=0; i<getMinotaurBuffer().get(s).size();i++) {
					for(Ressource r : getMinotaurBuffer().get(s).get(i).getRessourceGranted().keySet()) {
						if(r == Ressource.victoryPoint || r == Ressource.multiplyThree) {
							if(ressource.containsKey(r)) {
								//replace with adding
								ressource.replace(r, ressource.get(r)+getMinotaurBuffer().get(s).get(i).getRessourceGranted().get(r));
							}
							else {
								//put
								ressource.put(r, getMinotaurBuffer().get(s).get(i).getRessourceGranted().get(r));
							}
						}
					}
				}
				if(!ressource.containsKey(Ressource.victoryPoint))
					return;
				
				//applique les fait x3 et enleve la ressource
				if(ressource.containsKey(Ressource.multiplyThree)) {
					int count = ressource.get(Ressource.multiplyThree);
					ressource.replace(Ressource.victoryPoint, ressource.get(Ressource.victoryPoint)*3*count);
				}
				//passe les victory en negative
				ressource.replace(Ressource.victoryPoint, ressource.get(Ressource.victoryPoint)*-1);
				UpdateRessourceMessage urm = new UpdateRessourceMessage(s, ressource);
				String data = om.writeValueAsString(urm);
				getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.updateRessource.name(), data);
			}
			this.resetMinotaurBuffer();

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Un appel à la fois, synchronized, ajoute le choix, verifie si tous les
	 * joueurs ont rendu leurs choix, si cest le cas renvoie les choix
	 * 
	 * @param playerName nom du joueur qui a rendu son choix au serveur
	 * @param choice     faces qu'à choisi le joueur
	 */
	public synchronized void addMinotaurBufferVerifyMaybeSend(String playerName, ArrayList<Face> choice) {
		addMinotaurBuffer(playerName, choice);
		if (isMinotaurBufferFull())
			sendMinotaurBuffer();
	}

	/**
	 * ajoute aux statistiques les faces qui ont été tiré et choisies par les
	 * joueurs
	 * 
	 * @param rollChoice choix et face tirés par les joueurs
	 */
	public void addStat(HashMap<String, ArrayList<Face>> rollChoice) {
		for (String playerName : rollChoice.keySet())
			getServer().getGameManager().getStat().updateStat(playerName, rollChoice.get(playerName));
	}

	/**
	 * ajoute aux inventaires les faces qui ont été tiré et choisies par les joueurs
	 * 
	 * @param rollChoice rollchoice choix et face tirés par les joueurs
	 */
	public void ajouterRessource(HashMap<String, ArrayList<Face>> rollChoice) {
		for (String playerName : rollChoice.keySet())
			getServer().getPlayerByName(playerName).ajouteRessource(rollChoice.get(playerName));
	}

	public void setPlayerReceived(int nb) {
		this.playerReceived = nb;
	}

	public synchronized int getPlayerReceived() {
		return this.playerReceived;
	}

	/**
	 * Fonction bloquante un appel à la fois, incremente de playerReceived
	 */
	public synchronized void incrementPlayerReceived() {
		this.playerReceived++;
	}

	/**
	 * Fonction bloquante, un appel à la fois, incremente et regarde si tout le
	 * monde a recu
	 * 
	 * @return true si playerReceived = nombre de joueurs connectés et reset a 0
	 *         false sinon
	 */
	public synchronized boolean hasReceived() {
		this.incrementPlayerReceived();
		if (this.getPlayerReceived() == server.getAcceptedConnection().size()) {
			// Reset du compteur
			this.setPlayerReceived(0);
			return true;
		} else
			return false;
	}

	/**
	 * Envoie à tous les joueurs le nom du joueur qui doit maintenant faire son
	 * action
	 * 
	 */
	public void timeToDoYourAction() {
		String name = this.getServer().getAcceptedConnection().get(this.getServer().getGameManager().getIndexJoueur())
				.getNom();

		if (server.isModeLog()) {
			System.out.println(name + " commence a jouer.");
		}

		this.getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.turn.name(), new TurnMessage(name));
	}

	/**
	 * Ajoute à l'inventaire du joueur les ressources et envoie en broadcast en
	 * satyrs2 les ressources à ajouter au joueur
	 * 
	 * @param joueur l'instance joueur auquel on doit ajouter les ressources
	 * @param data   le string json envoyé par le client
	 */
	public void processSatyrsMessage(Joueur joueur, String data) {
		ObjectMapper om = new ObjectMapper();
		SatyrsMessage sm = new SatyrsMessage();
		try {
			sm = om.readValue(data, SatyrsMessage.class);
			// Ajoute les ressources à l'inventaire du joueur
			joueur.ajouteRessource(sm.getRolls());
			// Ajoute les ressources au stat
			getServer().getGameManager().getStat().updateStat(joueur.getNom(), sm.getRolls());
			// Envoie en broadcast aux autres joueurs pour qu'ils ajoutent les ressources
			getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.satyrs2.name(), data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute à l'inventaire du joueur les ressources en envoie en broadcast en
	 * guardianowls2 les ressources à ajouter au joueur
	 * 
	 * @param joueur l'instance joueur auquel on doit ajouter les ressources
	 * @param data   le string json envoyé par le client
	 */
	public void processGuardianOwlsMessage(Joueur joueur, String data) {
		ObjectMapper om = new ObjectMapper();
		ChooseFaceMessage cfm = new ChooseFaceMessage();
		try {
			cfm = om.readValue(data, ChooseFaceMessage.class);
			// Ajoute au ressource du joueur
			ArrayList<Face> face = new ArrayList<Face>();
			face.add(cfm.getFaceSelected());
			joueur.ajouteRessource(face);
			// Ajoute au stat du joueur
			getServer().getGameManager().getStat().updateStat(joueur.getNom(), face);
			// Envoie en broadcast aux autres joueurs
			getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.guardianowls2.name(), data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Recupere le dé que souhaite roll le joueur 4 fois et le roll, renvoie les
	 * resultats au client qu'ils puissent traiter les faces
	 * 
	 * @param joueur l'instance joueur auquel on doit ajouter les ressources
	 * @param data   le string json envoyé par le client
	 */
	public void processSphinxMessage(Joueur joueur, String data) {
		ObjectMapper om = new ObjectMapper();
		SphinxMessage sm = new SphinxMessage();
		RollMessage rm = new RollMessage();
		HashMap<String, ArrayList<Face>> rollResult = new HashMap<String, ArrayList<Face>>();
		rollResult.put(joueur.getNom(), new ArrayList<Face>());

		try {
			sm = om.readValue(data, SphinxMessage.class);
			// roll du dé 4 fois
			for (int i = 0; i < 4; i++) {
				Face f = sm.getDeToRoll().roll();
				// Filtre les mirroirs
				if (!f.getRessourceGranted().containsKey(Ressource.portal))
					rollResult.get(joueur.getNom()).add(f);
			}
			rm = new RollMessage(rollResult);
			String rmdata = om.writeValueAsString(rm);
			getServer().getPlayerByName(joueur.getNom()).getSocket().sendEvent(MessageType.sphinx2.name(), rmdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute les ressources et les renvoie aux autres joueurs afin qu'ils les
	 * ajoutent aussi
	 * 
	 * @param joueur l'instance joueur auquel on doit ajouter les ressources
	 * @param data   le string json envoyé par le client
	 */
	public void processSphinx2Message(Joueur joueur, String data) {
		ObjectMapper om = new ObjectMapper();
		RollMessage rm = new RollMessage();

		try {
			rm = om.readValue(data, RollMessage.class);
			for (String s : rm.getRollResults().keySet()) {
				// ajoute les ressources
				joueur.ajouteRessource(rm.getRollResults().get(s));
				// Ajoute les stats
				getServer().getGameManager().getStat().updateStat(joueur.getNom(), rm.getRollResults().get(s));
			}
			// Broadcast
			getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.sphinx3.name(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * roll le de choisit par le joueur et lui renvoie les resultats
	 * 
	 * @param joueur l'instance joueur auquel on doit ajouter les ressources
	 * @param data   le string json envoyé par le client
	 */
	public void processSilverHind(Joueur joueur, String data) {
		ObjectMapper om = new ObjectMapper();
		SilverHindMessage shm = new SilverHindMessage();
		HashMap<String, ArrayList<Face>> rollResult = new HashMap<String, ArrayList<Face>>();
		rollResult.put(joueur.getNom(), new ArrayList<Face>());
		try {
			shm = om.readValue(data, SilverHindMessage.class);
			Face f = shm.getDeToRoll().roll();
			if (!f.getRessourceGranted().containsKey(Ressource.portal))
				rollResult.get(joueur.getNom()).add(f);
			RollMessage rm = new RollMessage(rollResult);
			String rmdata = om.writeValueAsString(rm);
			getServer().getPlayerByName(joueur.getNom()).getSocket().sendEvent(MessageType.silverhind2.name(), rmdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ajoute les ressources et broadcast aux joueurs pour l'ajout au ressource
	 * 
	 * @param joueur l'instance joueur auquel on doit ajouter les ressources
	 * @param data   le string json envoyé par le client
	 */
	public void processSilverHind2(Joueur joueur, String data) {
		ObjectMapper om = new ObjectMapper();
		RollMessage rm = new RollMessage();

		try {
			rm = om.readValue(data, RollMessage.class);
			for (String s : rm.getRollResults().keySet()) {
				// ajoute au ressource
				joueur.ajouteRessource(rm.getRollResults().get(s));
				// Ajoute au stat
				getServer().getGameManager().getStat().updateStat(joueur.getNom(),
						rm.getRollResults().get(joueur.getNom()));
			}
			getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.silverhind3.name(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ajoute les ressources et broadcast aux joueurs pour l'ajout au ressource
	 * 
	 * @param joueur l'instance joueur auquel on doit ajouter les ressources
	 * @param data   le string json envoyé par le client
	 */
	public void processCancerMessage(Joueur joueur, String data) {
		ObjectMapper om = new ObjectMapper();
		RollMessage rm = new RollMessage();

		try {
			rm = om.readValue(data, RollMessage.class);
			for (String s : rm.getRollResults().keySet()) {
				// ajoute au ressource
				joueur.ajouteRessource(rm.getRollResults().get(s));
				// Ajoute au stat
				getServer().getGameManager().getStat().updateStat(joueur.getNom(),
						rm.getRollResults().get(joueur.getNom()));
			}
			getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.cancer2.name(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Roll les dés du joueur chassé et lui envoie pour qu'il process le résultat
	 * 
	 * @param data du huntMessage
	 */
	public void processHuntMessage(String data) {
		ObjectMapper om = new ObjectMapper();
		HuntMessage hm;
		HashMap<String, ArrayList<Face>> rollResult = new HashMap<String, ArrayList<Face>>();

		try {
			hm = om.readValue(data, HuntMessage.class);
			ClientManager cm = getServer().getPlayerByName(hm.getNom());
			rollResult.put(cm.getNom(), cm.lancerDeDieu());
			RollMessage rm = new RollMessage(rollResult);
			cm.getSocket().sendEvent(MessageType.hunt.name(), om.writeValueAsString(rm));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Roll les dés du joueur chassé et lui envoie pour qu'il process le résultat
	 * 
	 * @param data         du huntMessage
	 */
	public void processHuntMessage2(String data) {
		ObjectMapper om = new ObjectMapper();
		RollMessage rm = new RollMessage();

		try {
			rm = om.readValue(data, RollMessage.class);
			for (String s : rm.getRollResults().keySet()) {
				ClientManager joueur = getServer().getPlayerByName(s);
				// ajoute au ressource
				joueur.ajouteRessource(rm.getRollResults().get(s));
				// Ajoute au stat
				getServer().getGameManager().getStat().updateStat(joueur.getNom(),
						rm.getRollResults().get(joueur.getNom()));
			}
			getServer().getSocket().getBroadcastOperations().sendEvent(MessageType.hunt2.name(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
