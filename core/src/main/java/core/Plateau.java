package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant le plateau du jeu
 * Contenant les joueurs, les iles, la forge et le nombre de round
 */
public class Plateau {

	private ArrayList<Joueur> players;
	private ArrayList<Isle> isles;
	private Forge forge;
	private int nbRound;

	/**
	 * Construteur du plateau contenant des joueurs, des iles, une forge et des manches
	 */
	public Plateau() {
		players = new ArrayList<Joueur>();
		isles = new ArrayList<Isle>();
		forge = new Forge();
		initIsle();
		nbRound = 0;
	}

	/**
	 * Méthode permettant de récupérer les joueurs présent sur le plateau
	 * @return la liste des joueurs
	 */
	public ArrayList<Joueur> getPlayers() {
		return players;
	}

	/**
	 * Méthode permettant de récupérer les iles sur le plateau
	 * @return la liste des iles
	 */
	public ArrayList<Isle> getIsles() {
		return isles;
	}

	/**
	 * Méthode permettant de retourner la Force du plateau
	 * @return la forge
	 */
	public Forge getForge() {
		return forge;
	}

	/**
	 * @param joueur, le joueur ajouter la partie
	 * 
	 *                Ajoute un joueur la partie, la partie n'accepte au maximum que
	 *                4 joueurs, si on essaie d'en rajouter la partie les rejettent
	 */
	public void addPlayer(Joueur joueur) {
		if (players.size() >= 4)
			return;
		else
			players.add(joueur);
	}

	/**
	 * Méthode permettant d'initialiser l'ile
	 */
	public void initIsle() {

		// Initialisation des iles
		Isle i1 = new Isle();
		i1.setStackCard("BlacksmithsHammerCard", players.size());
		i1.setStackCard("BlacksmithsChestCard", players.size());
		isles.add(i1);

		Isle i2 = new Isle();
		i2.setStackCard("SilverHindCard", players.size());
		i2.setStackCard("SatyrsCard", players.size());
		isles.add(i2);

		Isle i3 = new Isle();
		i3.setStackCard("FerrymanCard", players.size());
		i3.setStackCard("HelmetInvisibilityCard", players.size());
		isles.add(i3);

		Isle i4 = new Isle();
		i4.setStackCard("CancerCard", players.size());
		i4.setStackCard("HydraCard", players.size());
		i4.setStackCard("SphinxCard", players.size());
		isles.add(i4);

		Isle i5 = new Isle();
		i5.setStackCard("MirrorAbyssCard", players.size());
		i5.setStackCard("GorgonCard", players.size());
		isles.add(i5);

		Isle i6 = new Isle();
		i6.setStackCard("MinotaurCard", players.size());
		i6.setStackCard("GuardiansOwlCard", players.size());
		isles.add(i6);

		Isle i7 = new Isle();
		i7.setStackCard("WildSpiritsCard", players.size());
		i7.setStackCard("ElderCard", players.size());
		isles.add(i7);

	}

	/**
	 * Méthode permettant de récupérer un joueur par son nom
	 * @param nom du joueur voulu
	 * @return le joueur correspondant au nom ou null si aucun joueur n'a ce nom
	 */
	public Joueur getPlayerByName(String nom) {
		for (Joueur p : players) {
			if (p.getNom().equals(nom)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Méthode permetant de récupérer une ile en fonction de la ressource choisie et de son cout
	 * @param ressource choisie pour récupérer l'ile correspondante
	 * @return l'ile si une ile a la ressource voulue ou rien si elle n'exite pas
	 */
	public Isle getIsleByCost(HashMap<Ressource, Integer> ressource) {
		for (Isle i : isles) {
			for (StackCard sc : i.getListeStackCard()) {
				if (sc.getCard().getCost().equals(ressource)) {
					return i;
				}
			}
		}
		return null;
	}

	/**
	 * Méthode permettant de récupérer le nombre de tour
	 * @return le nombre de tour 
	 */
	public int getNbRound() {
		return nbRound;
	}

	/**
	 * Méthode permettant de changer le nombre de round
	 * @param nbRound nombre de round à mettre
	 */
	public void setNbRound(int nbRound) {
		this.nbRound = nbRound;
	}

	/**
	 * Méthode permettant de modifier la forge
	 * @param forge, la nouvelle forge qui remplacera l'ancienne
	 */
	public void setForge(Forge forge) {
		this.forge = forge;
	}


}
