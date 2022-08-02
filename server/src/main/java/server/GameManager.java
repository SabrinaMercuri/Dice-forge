package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.De;
import core.Face;
import core.Forge;
import core.Joueur;
import core.Plateau;
import core.Ressource;
import core.cards.BlacksmithsHammerCard;
import core.cards.Card;
import protocol.ChooseFaceMessage;
import protocol.ChooseRessourceMessage;
import protocol.MarteauMessage;
import protocol.MessageType;
import protocol.MirroirMessage;
import protocol.NewGameMessage;
import protocol.RollMessage;
import protocol.RoundMessage;
import protocol.TurnMessage;
import protocol.UpdateRessourceMessage;
import protocol.VictoryMessage;

/**
 * 
 * @author tp2dfa Manage the game hosted by the server
 *
 */
public class GameManager {

	public static String LOBBY = "lobby";
	public static String INGAME = "ingame";

	private String state;

	private Server server;
	private Plateau board;
	private Statistique stat;

	private int partieAJouer;
	private int nbpartie;
	private int indexJoueur = 0;
	private ObjectMapper om;

	public GameManager(Server server) {
		om = new ObjectMapper();
		state = LOBBY;

		stat = new Statistique();

		this.server = server;

		this.partieAJouer = server.getNbPartieAJouer();
		this.nbpartie = 0;

		board = new Plateau();
	}

	public Plateau getPlateau() {
		return board;
	}

	public int getNbpartie() {
		return nbpartie;
	}

	public void setBoard(Plateau board) {
		this.board = board;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Statistique getStat() {
		return stat;
	}

	public void newGame() {
		// Init des statistiques
		for (ClientManager cm : server.getAcceptedConnection())
			stat.addJoueur(cm.getNom());

		// Debut de game
		this.setState(INGAME);

		// Init de choice
		server.getServerListener().initChoice();

		newRound();
	}

	/**
	 * Si le nombre de manche est inférieur à 10 alors on fait jouer les joueurs
	 * Sinon on met fin à la partie et on calcul les points de chacun et on envoie
	 * le vainqueur
	 */
	public void newRound() {
		// Tant qu'on a pas jouer 10 manche, on joue
		if (nbpartie < server.getNbPartieAJouer()) {

			this.getPlateau().setNbRound(this.getPlateau().getNbRound() + 1);
			if (this.getPlateau().getNbRound() < 10) {
				// On continue la meme partie
				if (server.isModeLog()) {
					System.out.println("\n\nNouvelle manche n°" + this.getPlateau().getNbRound() + "\n\n");
				}
				turn();
			} else {
				System.out.println("Nouvelle partie n°" + nbpartie);
				// On créer une nouvelle partie
				String gagnant = getGagnant();
				for (StatJoueur joueur : getStat().getJoueurs()) {
					if (joueur.getName().equals(gagnant)) {
						joueur.ajouteVictoire();
					}
				}
				// Envoit du message de victoire au client
				server.getSocket().getBroadcastOperations().sendEvent(MessageType.victory.name(),
						new VictoryMessage(getGagnant()));
				if (server.isModeLog()) {
					System.out.println("Le gagnant est " + getGagnant());
				}
				// On réinitialise le plateau sans virer les joueurs
				if (nbpartie + 1 < partieAJouer) {

					board.setNbRound(0);
					board.setForge(new Forge());
					board.initIsle();

					server.getSocket().getBroadcastOperations().sendEvent(MessageType.newgame.name(),
							new NewGameMessage());
				}
				nbpartie++;
			}
		} else {
			System.out.println("\n\nIts the end !");
			if (!server.isModeLog()) {
				System.out.println(getStat().toString());
			}
			// Fin du programme on arrête tout
			for (ClientManager cm : server.getAcceptedConnection())
				cm.getSocket().disconnect();
			server.stop();
		}

	}

	/*
	 * public void initialiserNouvellePartie() { //si on a pas jouer le nombre de
	 * partie à jouer if (nbpartie+1 < server.getNbPartieAJouer()) {
	 * 
	 * //On réinitialise le plateau sans virer les joueurs board.setNbRound(0);
	 * board.setForge(new Forge()); board.initIsle();
	 * 
	 * server.getSocket().getBroadcastOperations().sendEvent(MessageType.newgame.
	 * name(), new NewGameMessage()); nbpartie++; } else { nbpartie++; }
	 * 
	 * }
	 * 
	 * 
	 * public void finDeManche() {
	 * 
	 * 
	 * 
	 * String gagnant = getGagnant();
	 * 
	 * for (StatJoueur joueur : getStat().getJoueurs()) { if
	 * (joueur.getName().equals(gagnant)) { joueur.ajouteVictoire(); } }
	 * 
	 * //Envoit du message de victoire au client
	 * server.getSocket().getBroadcastOperations().sendEvent(MessageType.victory.
	 * name(), new VictoryMessage(getGagnant()));
	 * 
	 * //On regarde si on lance une nouvelle partie initialiserNouvellePartie();
	 * 
	 * }
	 */

	public void calculerPointDesJoueurs() {
		int pointActuel = 0;
		int pointAddition = 0;

		// Pour chaque joueur
		for (ClientManager j : server.getAcceptedConnection()) {
			pointActuel = j.getInventaire().getVictoryPoint();

			// Pour chacune de ses cartes
			for (Card c : j.getInventaire().getListedecartes()) {

				// On calcul ses points
				pointAddition += c.getVictoryPoints();

				int somme = pointActuel + pointAddition;
				j.getInventaire().setVictoryPoint(somme);

			}
		}
	}

	private String getGagnant() {
		String gagnant = null;
		int max = 0;

		// On regarde le max de point
		for (int i = 0; i < 3; i++) {
			// Si le joueur i a un nb de point supérieur au joueur i+1
			if (server.getAcceptedConnection().get(i).getInventaire().getVictoryPoint() > server.getAcceptedConnection()
					.get(i + 1).getInventaire().getVictoryPoint()) {

				// Le max prend la valeur du nb de VictoryPoint du joueur i
				max = server.getAcceptedConnection().get(i).getInventaire().getVictoryPoint();
			}

			else {
				//// Le max prend la valeur du nb de VictoryPoint du joueur i + 1
				max = server.getAcceptedConnection().get(i + 1).getInventaire().getVictoryPoint();
			}
		}

		// On regarde qui a le nb max de point et on le retourne
		for (ClientManager j : server.getAcceptedConnection()) {
			if (j.getInventaire().getVictoryPoint() == max) {
				gagnant = j.getNom();
				return gagnant;
			} else {

			}
		}
		return gagnant;

	}

	public void turn() {
		// Roll des des de tous les joueurs
		HashMap<String, ArrayList<Face>> rollPlayer = rolls();

		// Envoie les messages des rolls aux joueurs
		sendRollResult(rollPlayer);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mozorate la JAVADOC
	 * 
	 * @param listFace une liste de face???
	 */
	public void ajoutGoldMarteau(ArrayList<Face> listFace) {
		for (int i = 0; i < board.getPlayers().size(); i++) {
			for (Card carte : board.getPlayers().get(i).getInventaire().getListedecartes()) {
				if (carte.getNom().equals(Card.BLACKSMITHHAMMER)) {

					// Regarde si le marteau est toujours actif
					if (((BlacksmithsHammerCard) carte).isStatus()) {
						int multiplicateur = 1;
						int nbGold = 0;
						// regarde les ressources sur les faces pour voir si il y a de l'or
						for (Face face : listFace) {
							for (Ressource r : face.getRessourceGranted().keySet()) {
								if (r == Ressource.gold) {

									nbGold += face.getNumberOfRessourceGranted(Ressource.gold);
									// ajoute l'or au marteau
									((BlacksmithsHammerCard) carte).ajouteGoldMarteau(nbGold * multiplicateur);
									String marteaumessage;
									try {
										marteaumessage = om.writeValueAsString(new MarteauMessage(
												((BlacksmithsHammerCard) carte), board.getPlayers().get(i).getNom(),
												((BlacksmithsHammerCard) carte).palierMarteau()));
										server.getSocket().getBroadcastOperations()
												.sendEvent(MessageType.marteau.name(), marteaumessage);
									} catch (JsonProcessingException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * String is player's name and the ArrayList of Face is their face results
	 * 
	 * @return an HashMap with for each player their rolls
	 */
	public HashMap<String, ArrayList<Face>> rolls() {
		HashMap<String, ArrayList<Face>> results = new HashMap<String, ArrayList<Face>>();

		for (ClientManager cm : server.getAcceptedConnection())
			results.put(cm.getNom(), cm.lancerDeDieu());

		return results;
	}

	/**
	 * 
	 * @param playerName roll les dés du joueur possédant ce nom
	 * @return une map avec le nom du joueur et une arraylist des faces qu'il a
	 *         obtenu
	 */
	public HashMap<String, ArrayList<Face>> rolls(String playerName) {
		HashMap<String, ArrayList<Face>> results = new HashMap<String, ArrayList<Face>>();
		ClientManager cm = server.getPlayerByName(playerName);
		results.put(cm.getNom(), cm.lancerDeDieu());
		return results;
	}

	/**
	 * Envoie en broadcast les resultats du lancer de dés
	 * 
	 * @param rollResults resultats du lancer de dés
	 */
	public void sendRollResult(HashMap<String, ArrayList<Face>> rollResults) {
		ObjectMapper om = new ObjectMapper();
		RollMessage rm = new RollMessage(rollResults);

		try {
			String data = om.writeValueAsString(rm);
			this.server.getSocket().getBroadcastOperations().sendEvent(MessageType.rollResult.name(), data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setIndexJoueur(int indexJoueur) {
		if (indexJoueur < 0 || indexJoueur > server.getAcceptedConnection().size())
			return;
		this.indexJoueur = indexJoueur;
	}

	/**
	 * Incremente indexJoueur de 1, si index joueur supérieur ou égal au nombre de joueurs
	 * connectés on revient à 0 (car on a fait un tour)
	 */
	public void incrementIndexJoueur() {
		this.indexJoueur++;
		if (this.indexJoueur >= server.getAcceptedConnection().size())
			this.indexJoueur = 0;
	}

	public int getIndexJoueur() {
		return this.indexJoueur;
	}
}