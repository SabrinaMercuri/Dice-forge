package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.Face;
import core.Forge;
import core.ForgeCategory;
import core.Inventaire;
import core.Joueur;
import core.Ressource;
import core.cards.BlacksmithsHammerCard;
import core.cards.Card;
import protocol.AskForgingMessage;
import protocol.CardEffectMessage;
import protocol.CarteMessage;
import protocol.ChooseFaceMessage;
import protocol.FaceMessage;
import protocol.HelmetMessage;
import protocol.Listener;
import protocol.MarteauAcheteMessage;
import protocol.MarteauMessage;
import protocol.MessageType;
import protocol.NameMessage;
import protocol.NewActionMessage;
import protocol.NewGameMessage;
import protocol.RetraitSolaryMessage;
import protocol.RollMessage;
import protocol.SatyrsMessage;
import protocol.SilverHindMessage;
import protocol.SphinxMessage;
import protocol.TurnMessage;
import protocol.UpdateDeMessage;
import protocol.UpdateRessourceMessage;
import io.socket.emitter.Emitter;
import protocol.ChestMessage;

public class ClientListener implements Listener {

	private Client client;
	private Action action;
	private boolean isMySecondAction;

	public ClientListener(Client client, Action action) {
		this.client = client;
		this.action = action;
	}

	@Override
	public void init() {
		isMySecondAction = false;

		client.getSocket().on(MessageType.connected.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {

				client.nomClientMessage();
			}
		});

		client.getSocket().on(MessageType.turn.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {

				ObjectMapper om = new ObjectMapper();
				TurnMessage tm = new TurnMessage();
				try {
					tm = om.readValue(args[0].toString(), TurnMessage.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (tm.getPlayerToPlay().equals(client.getMyself().getNom())) {
					playEffect();
					getClient().getSocket().emit(MessageType.effectEnd.name(), "");
				}
			}
		});

		client.getSocket().on(MessageType.newAction.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				// Le serveur me demande si je souhaite rejouer ou pas;
				isMySecondAction = true;
				boolean isReplaying = action.newAction();
				ObjectMapper om = new ObjectMapper();
				NewActionMessage nam;
				try {
					nam = om.readValue(args[0].toString(), NewActionMessage.class);
					if (nam.isNeedResponse()) {
						if (client.getMyself().getInventaire().getSolaryStone() < 2 || isReplaying == false) {
							nam = new NewActionMessage(false, client.getMyself().getNom(), false);
							try {
								client.getSocket().emit(MessageType.newAction.name(), om.writeValueAsString(nam));
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
							client.getSocket().emit(MessageType.endTurn.name(), new TurnMessage());
						} else if (isReplaying) {

							// Je souhaite rejouer
							nam = new NewActionMessage(true, client.getMyself().getNom(), false);
							try {
								client.getSocket().emit(MessageType.newAction.name(), om.writeValueAsString(nam));
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
							playTurn();
						} else {

							// Je ne souhaite juste pas rejouer
							nam = new NewActionMessage(false, client.getMyself().getNom(), false);
							try {
								client.getSocket().emit(MessageType.newAction.name(), om.writeValueAsString(nam));
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
							client.getSocket().emit(MessageType.endTurn.name(), new TurnMessage());
						}
					} else {
						// needResponse == false -> Sa veut dire que cest un broadcast du serveur d'une
						// nouvelle action joueur donc on enleve 2 cailloux
						client.getPlateau().getPlayerByName(nam.getNomJoueur()).getInventaire().retirerSolary(2);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Message de nom
		client.getSocket().on(MessageType.name.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {

				ObjectMapper om = new ObjectMapper();
				NameMessage nm = new NameMessage();

				try {
					nm = om.readValue(args[0].toString(), NameMessage.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (nm.getIsModified()) {
					// Le serveur a modifié mon nom car le nom était déjà pris

					client.getMyself().setNom(nm.getName());
				} else {
					if (!nm.getName().equals(client.getMyself().getNom())) {
						// C'est un joueur différent de moi meme je l'ajoute dans la liste des joueurs
						// dans le plateau
						client.getPlateau().getPlayers().add(new Joueur(nm.getName()));
					}
				}
			}
		});

		// Message de Type Start
		client.getSocket().on(MessageType.start.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {

			}
		});

		// Message de Victory
		client.getSocket().on(MessageType.victory.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {

			}
		});

		// Message de new Round
		client.getSocket().on(MessageType.newRound.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {

			}
		});

		// Message de End Round
		client.getSocket().on(MessageType.endRound.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {

			}
		});

		// Message End Turn
		client.getSocket().on(MessageType.endTurn.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {

			}
		});

		// Message de RollResult, le joueur doit choisir les ressources qu'il souhaite
		// s'il le peut (mirror | face OU)
		client.getSocket().on(MessageType.rollResult.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {

				ObjectMapper om = new ObjectMapper();
				RollMessage rollMessage = new RollMessage();
				try {
					rollMessage = om.readValue(objects[0].toString(), RollMessage.class);
					HashMap<String, ArrayList<Face>> decision = processDicesResult(rollMessage.getRollResults());
					rollMessage = new RollMessage(decision);
					String data = om.writeValueAsString(rollMessage);
					getClient().getSocket().emit(MessageType.rollChoice.name(), data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		// Message de RollChoice le serveur a fini de recevoir tous les choix des
		// joueurs on attribue les ressources mtn
		client.getSocket().on(MessageType.rollChoice.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rollMessage = new RollMessage();
				try {
					rollMessage = om.readValue(args[0].toString(), RollMessage.class);
					// Attribution des ressources au joueur
					for (String namePlayer : rollMessage.getRollResults().keySet()) {
						Joueur joueur = getClient().getPlateau().getPlayerByName(namePlayer);
						joueur.ajouteRessource(rollMessage.getRollResults().get(namePlayer));
					}
					// On envoie au serveur qu'on est OK
					getClient().getSocket().emit(MessageType.rollEnd.name(), "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Le serveur a fini d'appliquer et de recevoir les effets, le joueur peut faire
		// ces actions
		client.getSocket().on(MessageType.effectEnd.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				TurnMessage tm = new TurnMessage();
				try {
					tm = om.readValue(args[0].toString(), TurnMessage.class);
					if (tm.getPlayerToPlay().equals(client.getMyself().getNom())) {
						playTurn();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		// Message de type FaceAchete
		client.getSocket().on(MessageType.faceAchete.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {
				ObjectMapper om = new ObjectMapper();
				FaceMessage fm = new FaceMessage();
				try {
					fm = om.readValue(objects[0].toString(), FaceMessage.class);

					if (!client.getMyself().getNom().equals(fm.getNamePlayer())) {
						// update les cartes du joueurs ayant acheté la face
						client.getPlateau().getPlayerByName(fm.getNamePlayer()).getInventaire()
								.setListDeJoueur(fm.getUpdatedDe());

						// enlever la face sur le plateau
						ArrayList<ForgeCategory> fgCat = client.getPlateau().getForge().getForgeCategories();

						for (Face f : fm.getFaces()) {
							for (ForgeCategory fg : fgCat) {
								if (fg.contains(f)) {

									fg.buyFace(f);

									break;
								}
							}
						}

						// Retrait des golds dans l'inventaire du joueur acheteur
						client.getPlateau().getPlayerByName(fm.getNamePlayer()).getInventaire()
								.retirerGold(fm.getCost().get(Ressource.gold));

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		client.getSocket().on(MessageType.carteAchete.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {
				ObjectMapper om = new ObjectMapper();
				CarteMessage cm = new CarteMessage();
				try {
					cm = om.readValue(objects[0].toString(), CarteMessage.class);

					if (!client.getMyself().getNom().equals(cm.getNamePlayer())) {

						// Ajoute une carte dans l'inventaire du joueur l'ayant acheté
						client.getPlateau().getPlayerByName(cm.getNamePlayer()).getInventaire().getListedecartes()
								.add(cm.getCarte());

						// Enlever la carte de la pile et placer le joueur sur l'ile
						client.getPlateau().getIsles().get(cm.getIle()).prendreCarte(cm.getCarte().getNom(),
								client.getPlateau().getPlayerByName(cm.getNamePlayer()));

						client.getPlateau().getIsles().get(cm.getIle())
								.positionnerJoueur(client.getPlateau().getPlayerByName(cm.getNamePlayer()));

						// Enleve le cout de la carte dans l'inventaire
						client.getPlateau().getPlayerByName(cm.getNamePlayer()).getInventaire()
								.retirerLunary(cm.getLunaryCost());
						client.getPlateau().getPlayerByName(cm.getNamePlayer()).getInventaire()
								.retirerSolary(cm.getSolaryCost());

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		// Reception Message de retrait de solary lors de la 2e action
		client.getSocket().on(MessageType.retraitSolary.name(), new Emitter.Listener() {
			@Override
			public void call(Object... objects) {
				ObjectMapper om = new ObjectMapper();
				RetraitSolaryMessage cm = new RetraitSolaryMessage();

				try {
					cm = om.readValue(objects[0].toString(), RetraitSolaryMessage.class);
					client.getPlateau().getPlayerByName(cm.getNom()).getInventaire().retirerSolary(cm.getNbRetrait());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		client.getSocket().on(MessageType.effect.name(), new Emitter.Listener() {
			@Override
			public void call(Object... args) {
			}
		});

		client.getSocket().on(MessageType.updateDe.name(), new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				UpdateDeMessage cem = new UpdateDeMessage();
				try {
					cem = om.readValue(args[0].toString(), UpdateDeMessage.class);
					client.getPlateau().getPlayerByName(cem.getNamePlayer()).getInventaire()
							.setListDeJoueur(cem.getUpdatedDe());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		client.getSocket().on(MessageType.askForge.name(), new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				AskForgingMessage forgingMessage = new AskForgingMessage();

				try {
					forgingMessage = om.readValue(args[0].toString(), AskForgingMessage.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Création des random
				Random rdmDe = new Random();
				rdmDe.setSeed(System.currentTimeMillis());

				Random rdmFace = new Random();
				rdmFace.setSeed(System.currentTimeMillis());

				int deChoisis = rdmDe.nextInt(1);
				int faceChoisie = rdmFace.nextInt(5);

				if (client.getMyself().getNom().equals(forgingMessage.getNomJoueur())) {
					// Je forge ma face sur mon dé
					client.getMyself().getInventaire().getListDeJoueur().get(deChoisis)
							.setFace(forgingMessage.getFacePortal(), faceChoisie);

					// Envoie au serveur pour actualiser de son côté

					client.getSocket().emit(MessageType.askForge.name(),
							new AskForgingMessage(client.getMyself().getInventaire().getListDeJoueur(),
									forgingMessage.getFacePortal(), client.getMyself().getNom()));
				} else {
					// Je met à jour mon plateau pour actualiser le dés de l'autre joueur
					client.getPlateau().getPlayerByName(forgingMessage.getNomJoueur()).getInventaire().getListDeJoueur()
							.get(deChoisis).setFace(forgingMessage.getFacePortal(), faceChoisie);
				}

			}
		});

		/**
		 * Reçoit le message pour mettre à jour les ressources d'un joueur args[0] =
		 * UpdateRessourceMessage
		 */
		client.getSocket().on(MessageType.updateRessource.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				UpdateRessourceMessage urm = new UpdateRessourceMessage();
				try {
					urm = om.readValue(args[0].toString(), UpdateRessourceMessage.class);
					Joueur joueurToUpdate = client.getPlateau().getPlayerByName(urm.getNamePlayer());
					joueurToUpdate.getInventaire()
							.updateRessource(new HashMap<Ressource, Integer>(urm.getRessources()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		client.getSocket().on(MessageType.helmet.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				HelmetMessage cem = new HelmetMessage();
				Random rdm = new Random(); // a modifer via le comportement du bot quand on l'aura implementer
				try {
					cem = om.readValue(args[0].toString(), HelmetMessage.class);
					if (client.getMyself().getNom().equals(cem.getNamePlayer())) {
						Face mThree = cem.getMultiplyThree();
						int de = rdm.nextInt(client.getMyself().getInventaire().getListDeJoueur().size());
						int face = rdm.nextInt(
								client.getMyself().getInventaire().getListDeJoueur().get(de).getFaces().length);
						client.getMyself().getInventaire().getListDeJoueur().get(de).changeFace(face, mThree);
						UpdateDeMessage updateDe = new UpdateDeMessage(
								client.getMyself().getInventaire().getListDeJoueur(), cem.getNamePlayer());
						try {
							client.getSocket().emit(MessageType.updateDe.name(), om.writeValueAsString(updateDe));
						} catch (JsonProcessingException e) {
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		client.getSocket().on(MessageType.chest.name(), new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				ChestMessage cem = new ChestMessage();
				try {
					cem = om.readValue(args[0].toString(), ChestMessage.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (client.getMyself().getNom().equals(cem.getNamePlayer())) {
					client.getMyself().getInventaire().setLimitGold(cem.getUpdGold());
					client.getMyself().getInventaire().setLimitLunary(cem.getUpdLun());
					client.getMyself().getInventaire().setLimitSolary(cem.getUpdSol());
				} else {
					client.getPlateau().getPlayerByName(cem.getNamePlayer()).getInventaire()
							.setLimitGold(cem.getUpdGold());
					client.getPlateau().getPlayerByName(cem.getNamePlayer()).getInventaire()
							.setLimitLunary(cem.getUpdLun());
					client.getPlateau().getPlayerByName(cem.getNamePlayer()).getInventaire()
							.setLimitSolary(cem.getUpdSol());
				}
			}
		});

		client.getSocket().on(MessageType.marteau.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {

				ObjectMapper om = new ObjectMapper();
				MarteauMessage mt = new MarteauMessage();
				try {
					mt = om.readValue(args[0].toString(), MarteauMessage.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				for (Joueur joueur : client.getPlateau().getPlayers()) {
					if (joueur.getNom().equals(mt.getNamePlayer())) {
						for (int i = 0; i < joueur.getInventaire().getListedecartes().size(); i++) {
							if (joueur.getInventaire().getListedecartes().get(i).getNom()
									.equals("BlacksmithsHammerCard")) {
								BlacksmithsHammerCard carte = (BlacksmithsHammerCard) joueur.getInventaire()
										.getListedecartes().get(i);
								joueur.getInventaire().getListedecartes().remove(i);
								joueur.getInventaire().getListedecartes().add(mt.getMarteau());
								joueur.getInventaire().ajouterPoint(mt.getVictoryPoint());
							}
						}
					}
				}
			}
		});

		client.getSocket().on(MessageType.marteauAchat.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {

				ObjectMapper om = new ObjectMapper();
				MarteauAcheteMessage mt = new MarteauAcheteMessage();
				try {
					mt = om.readValue(args[0].toString(), MarteauAcheteMessage.class);

					// Si la carte piocher est un marteau
					if (mt.getMarteau().getNom().equals("BlacksmithsHammerCard")) {
						// Ajoute une carte dans l'inventaire du joueur l'ayant acheté
						client.getPlateau().getPlayerByName(mt.getNamePlayer()).getInventaire().getListedecartes()
								.add(new BlacksmithsHammerCard(mt.getMarteau().getCost()));

						// Enlever la carte de la pile et placer le joueur sur l'ile
						client.getPlateau().getIsles().get(mt.getIle()).prendreCarte(mt.getMarteau().getNom(),
								client.getPlateau().getPlayerByName(mt.getNamePlayer()));

						client.getPlateau().getIsles().get(mt.getIle())
								.positionnerJoueur(client.getPlateau().getPlayerByName(mt.getNamePlayer()));

						// Enleve le cout de la carte dans l'inventaire
						client.getPlateau().getPlayerByName(mt.getNamePlayer()).getInventaire()
								.retirerLunary(mt.getLunaryCost());
						client.getPlateau().getPlayerByName(mt.getNamePlayer()).getInventaire()
								.retirerSolary(mt.getSolaryCost());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		// Recoit une demande du serveur pour demander au client concerné les dés qu'il
		// souhaite roll
		// renvoie au serveur le de qu'il souhaite roll
		// le serveur lui renverra avec sphinx2 le rollResult pour le traitement
		client.getSocket().on(MessageType.sphinx.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				SphinxMessage sm = new SphinxMessage();

				try {
					sm = om.readValue(args[0].toString(), SphinxMessage.class);
					if (sm.getName().equals(getClient().getMyself().getNom())) {
						int index = action.chooseDe();
						sm.setDeToRoll(getClient().getMyself().getInventaire().getListDeJoueur().get(index));
						getClient().getSocket().emit(MessageType.sphinx.name(), om.writeValueAsString(sm));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		// Traitement du rollresult puis le serveur renverra un sphinx3 pour ajouter les
		// ressources
		client.getSocket().on(MessageType.sphinx2.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();

				try {
					rm = om.readValue(args[0].toString(), RollMessage.class);
					if (rm.getRollResults().containsKey(getClient().getMyself().getNom())) {
						HashMap<String, ArrayList<Face>> decision = new HashMap<String, ArrayList<Face>>();
						decision = processDicesResult(rm.getRollResults());
						rm = new RollMessage(decision);
						getClient().getSocket().emit(MessageType.sphinx2.name(), om.writeValueAsString(rm));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Ajout des ressources au joueur concerné
		client.getSocket().on(MessageType.sphinx3.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();

				try {
					rm = om.readValue(args[0].toString(), RollMessage.class);
					for (String s : rm.getRollResults().keySet()) {
						Joueur joueur = getClient().getPlateau().getPlayerByName(s);
						joueur.ajouteRessource(rm.getRollResults().get(s));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Recoit une demande du serveur pour demander au client concerné les dés qu'il
		// souhaite roll
		// renvoie au serveur le de qu'il souhaite roll
		// le serveur lui renverra avec silverhind2 le rollResult pour le traitement
		client.getSocket().on(MessageType.silverhind.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				SilverHindMessage shm = new SilverHindMessage();

				try {
					shm = om.readValue(args[0].toString(), SilverHindMessage.class);
					if (shm.getName().equals(getClient().getMyself().getNom())) {
						int index = action.chooseDe();
						shm.setDeToRoll(getClient().getMyself().getInventaire().getListDeJoueur().get(index));
						getClient().getSocket().emit(MessageType.silverhind.name(), om.writeValueAsString(shm));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		// Traitement du rollresult puis le serveur renverra un sphinx3 pour ajouter les
		// ressources
		client.getSocket().on(MessageType.silverhind2.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();

				try {
					rm = om.readValue(args[0].toString(), RollMessage.class);
					if (rm.getRollResults().containsKey(getClient().getMyself().getNom())) {
						HashMap<String, ArrayList<Face>> decision = new HashMap<String, ArrayList<Face>>();
						decision = processDicesResult(rm.getRollResults());
						rm = new RollMessage(decision);
						getClient().getSocket().emit(MessageType.silverhind2.name(), om.writeValueAsString(rm));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Ajout des ressources au joueur concerné
		client.getSocket().on(MessageType.silverhind3.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();

				try {
					rm = om.readValue(args[0].toString(), RollMessage.class);
					for (String s : rm.getRollResults().keySet()) {
						Joueur joueur = getClient().getPlateau().getPlayerByName(s);
						joueur.ajouteRessource(rm.getRollResults().get(s));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Le client recoit le satyrsMessage du serveur il doit choisir dans les faces
		// et renvoyer satyrs avec son choix
		// Le serveur lui renverra satyrs 2 pour l'ajout de ressource
		client.getSocket().on(MessageType.satyrs.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				SatyrsMessage sm = new SatyrsMessage();

				try {
					sm = om.readValue(args[0].toString(), SatyrsMessage.class);
					if (sm.getNamePlayer().equals(getClient().getMyself().getNom())) {
						// Choisie 2 faces
						ArrayList<Face> faceChoosen = new ArrayList<Face>();
						while (faceChoosen.size() < 2 && sm.getRolls()!=null) {
							Face f = action.chooseFace(sm.getRolls());
							sm.getRolls().remove(f);
							faceChoosen.add(f);
						}
						// Process the face (Face OR)
						HashMap<String, ArrayList<Face>> results = new HashMap<String, ArrayList<Face>>();
						results.put(getClient().getMyself().getNom(), faceChoosen);
						results = processDicesResult(results);
						// Send message to server
						sm = new SatyrsMessage(getClient().getMyself().getNom(),
								results.get(getClient().getMyself().getNom()));
						getClient().getSocket().emit(MessageType.satyrs.name(), om.writeValueAsString(sm));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		// Le client recoit le 2eme satyrMessage pour attribuer les ressources au joueur
		// correspondant
		client.getSocket().on(MessageType.satyrs2.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				SatyrsMessage sm = new SatyrsMessage();

				try {
					sm = om.readValue(args[0].toString(), SatyrsMessage.class);
					// attribue les ressources
					Joueur joueur = getClient().getPlateau().getPlayerByName(sm.getNamePlayer());
					joueur.ajouteRessource(sm.getRolls());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Le client choisi parmis les 3 faces laquelle il souhaite et renvoie un
		// guardianowls messagetype au serveur
		// Qui lui renverra apres un guardianowls2 pour ajouter les ressources à
		// l'inventaire
		client.getSocket().on(MessageType.guardianowls.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				ChooseFaceMessage cfm = new ChooseFaceMessage();

				try {
					cfm = om.readValue(args[0].toString(), ChooseFaceMessage.class);
					if (cfm.getNom().equals(getClient().getMyself().getNom())) {
						// Choisie entre les faces et renvoie le guardianowls message au serveur avec
						// son choix
						Face faceChoosen = action.chooseFace(new ArrayList<Face>(cfm.getFacesAvailable()));
						cfm.setFaceSelected(faceChoosen);
						getClient().getSocket().emit(MessageType.guardianowls.name(), om.writeValueAsString(cfm));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Ajoute les ressources au joueur concerné
		client.getSocket().on(MessageType.guardianowls2.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				ChooseFaceMessage cfm = new ChooseFaceMessage();

				try {
					cfm = om.readValue(args[0].toString(), ChooseFaceMessage.class);
					Joueur joueur = getClient().getPlateau().getPlayerByName(cfm.getNom());
					ArrayList<Face> ressource = new ArrayList<Face>();
					ressource.add(cfm.getFaceSelected());
					joueur.ajouteRessource(ressource);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Le serveur demande au joueur de process ces faces puis lui envoie un
		// updateRessurce messagetype pour mettre à jour les ressources si besoin
		client.getSocket().on(MessageType.minotaur.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();

				try {
					rm = om.readValue(args[0].toString(), RollMessage.class);
					if (rm.getRollResults().containsKey(getClient().getMyself().getNom())) {
						HashMap<String, ArrayList<Face>> decision = new HashMap<String, ArrayList<Face>>();
						decision = processDicesResult(rm.getRollResults());
						rm = new RollMessage(decision);
						getClient().getSocket().emit(MessageType.minotaur.name(), om.writeValueAsString(rm));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// process les rollresults et renvoie au serveur qui renverra cancer2 pour
		// mettre à jour les ressources
		client.getSocket().on(MessageType.cancer.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();

				try {
					rm = om.readValue(args[0].toString(), RollMessage.class);
					if (rm.getRollResults().containsKey(getClient().getMyself().getNom())) {
						HashMap<String, ArrayList<Face>> decision = new HashMap<String, ArrayList<Face>>();
						decision = processDicesResult(rm.getRollResults());
						rm = new RollMessage(decision);
						getClient().getSocket().emit(MessageType.cancer.name(), om.writeValueAsString(rm));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// ajoute les ressources au joueur concerné
		client.getSocket().on(MessageType.cancer2.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();

				try {
					rm = om.readValue(args[0].toString(), RollMessage.class);
					for (String s : rm.getRollResults().keySet()) {
						Joueur joueur = getClient().getPlateau().getPlayerByName(s);
						joueur.ajouteRessource(rm.getRollResults().get(s));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Message New Game
		client.getSocket().on(MessageType.newgame.name(), new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				ObjectMapper om = new ObjectMapper();
				NewGameMessage message = new NewGameMessage();

				// réinitialiser le plateau sans virer les joueurs
				getClient().getPlateau().setNbRound(0);
				getClient().getPlateau().setForge(new Forge());
				getClient().getPlateau().initIsle();

				for(Joueur joueur : getClient().getPlateau().getPlayers()) {
					joueur.setInventaire(new Inventaire());
				}
				
				getClient().getMyself().setInventaire(new Inventaire());
				getClient().getSocket().emit(MessageType.newgame.name(), new NewGameMessage());
			}
		});

		// Message hunt 1 process les results
		client.getSocket().on(MessageType.hunt.name(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();

				try {
					rm = om.readValue(args[0].toString(), RollMessage.class);
					if (rm.getRollResults().containsKey(getClient().getMyself().getNom())) {
						HashMap<String, ArrayList<Face>> decision = new HashMap<String, ArrayList<Face>>();
						decision = processDicesResult(rm.getRollResults());
						rm = new RollMessage(decision);
						getClient().getSocket().emit(MessageType.hunt2.name(), om.writeValueAsString(rm));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//Message hunt 2 ajouter les resultats dans l'inventaire
		client.getSocket().on(MessageType.hunt2.name(), new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				ObjectMapper om = new ObjectMapper();
				RollMessage rm = new RollMessage();

				try {
					rm = om.readValue(args[0].toString(), RollMessage.class);
					for (String s : rm.getRollResults().keySet()) {
						Joueur joueur = getClient().getPlateau().getPlayerByName(s);
						joueur.ajouteRessource(rm.getRollResults().get(s));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Fonction qui éxécute les méthodes de l'interface fonction pour que le joueur
	 * puisse jouer en communiquant ses actions au serveur
	 */
	public void playTurn() {
		ObjectMapper om = new ObjectMapper();

		FaceMessage fm = null;
		CarteMessage cm = null;
		boolean forging = action.isForging();
		boolean exploiting = action.isExploiting();
		// Le serveur m'a indiqué que c'est mon tour je joue
		if (forging && exploiting) {
			// On choisit en priorité le forging
			fm = action.forge();
		} else if (forging && !exploiting) {
			// On forge
			fm = action.forge();
		} else if (!forging && exploiting) {
			// On exploite
			cm = action.exploit();
		} else
			; // On fait rien

		if (fm != null) {
			// On achete une ou des faces on le dit au serveur
			try {
				client.getSocket().emit(MessageType.faceAchete.name(), om.writeValueAsString(fm));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else if (cm != null && !cm.getCarte().getNom().equals("BlacksmithsHammerCard")) {
			// On achete une carte on le dit au serveur
			try {
				client.getSocket().emit(MessageType.carteAchete.name(), om.writeValueAsString(cm));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else if (cm != null && cm.getCarte().getNom().equals("BlacksmithsHammerCard")) {
			// On achete une carte on le dit au serveur
			try {
				HashMap<Ressource, Integer> cost = new HashMap<Ressource, Integer>();
				cost.put(Ressource.lunaryStone, 1);
				MarteauAcheteMessage marteau = new MarteauAcheteMessage(cm.getNamePlayer(),
						new BlacksmithsHammerCard(cost), cm.getLunaryCost(), cm.getSolaryCost(), cm.getIle());
				client.getSocket().emit(MessageType.marteauAchat.name(), om.writeValueAsString(marteau));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else
			;

		if ((cm == null && fm == null) || isMySecondAction) {
			client.getSocket().emit(MessageType.endTurn.name(), new TurnMessage());
			isMySecondAction = false;
		}

	}

	public void playEffect() {
		ObjectMapper om = new ObjectMapper();
		// Check pour chaque carte activable à chaque tour s'il décide de l'activer
		for (Card c : client.getMyself().getInventaire().getListedecartes()) {
			if (c.isEachTurn()) {
				boolean activate = action.activateCard(c.getNom());
				if (activate) {
					CardEffectMessage cem = new CardEffectMessage(client.getMyself().getNom(), c.getNom());
					try {
						client.getSocket().emit(MessageType.effect.name(), om.writeValueAsString(cem));
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public boolean isMySecondAction() {
		return isMySecondAction;
	}

	public void setMySecondAction(boolean isMySecondAction) {
		this.isMySecondAction = isMySecondAction;
	}

	public boolean isFaceEquals(Face f1, Face f2) {
		return f1.getRessourceGranted().equals(f2.getRessourceGranted());
	}

	/**
	 * Traite le resultat brut des dés donnés par le serveur, change les portails en
	 * face que le client choisi, et choisi la ressource dans les faces OU
	 * 
	 * @param rollResults Result des dés que le serveur a lancé
	 * @return retourne les faces que le client a choisi dans ces faces apres avoir
	 *         traiter les mirroir et les faces OU
	 */
	public HashMap<String, ArrayList<Face>> processDicesResult(HashMap<String, ArrayList<Face>> rollResults) {
		ArrayList<Face> myResults = rollResults.get(client.getMyself().getNom());

		int howManyPortal = 0;
		int howManyOrFace = 0;

		// Traite les portails en premier
		for (Face f : myResults) {
			if (f.getRessourceGranted().containsKey(Ressource.portal))
				howManyPortal++;
		}

		if (howManyPortal > 1) {
			for (int i = 0; i < myResults.size(); i++) {
				if (myResults.get(i).getRessourceGranted().containsKey(Ressource.portal))
					myResults.set(i, chooseMirror(rollResults));
			}
		}

		// Traite les face OU en deuxieme
		for (Face f : myResults) {
			if (f.getHasChoice())
				howManyOrFace++;
		}

		if (howManyOrFace > 0) {
			for (int i = 0; i < myResults.size(); i++) {
				if (myResults.get(i).getHasChoice())
					myResults.set(i, chooseRessource(myResults.get(i)));
			}
		}

		HashMap<String, ArrayList<Face>> myDecision = new HashMap<String, ArrayList<Face>>();
		myDecision.put(client.getMyself().getNom(), myResults);
		return myDecision;
	}

	public Face chooseMirror(HashMap<String, ArrayList<Face>> rollResults) {
		ArrayList<Face> listFaces = new ArrayList<Face>();
		// Toutes les faces qui ne m'appartiennent pas
		for (String s : rollResults.keySet()) {
			if (!s.equals(getClient().getMyself().getNom())) {
				for (Face f : rollResults.get(s))
					listFaces.add(f);
			}
		}

		return getAction().chooseFace(listFaces);
	}

	public Face chooseRessource(Face face) {
		ArrayList<Face> faceList = face.toRessourceToFaces();
		return getAction().chooseFace(faceList);
	}

}
