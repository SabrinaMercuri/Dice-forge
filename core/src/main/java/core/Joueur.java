package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant un joueur Un joueur possède un nom, un inventaire et peut
 * lancer des dés
 */
public class Joueur {

	private String nom;
	private Inventaire inventaire;

	/**
	 * Constructeur de la classe Joueur
	 * 
	 * @param nom prend en paramètre le nom qu'aura le joueur
	 */
	public Joueur(String nom) {
		super();
		this.nom = nom;
		inventaire = new Inventaire();
	}

	/**
	 * Méthode permettant de récupérer le nom du joueur
	 * 
	 * @return nom, le nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Méthode permettant de récupérer l'inventaire du joueur
	 * 
	 * @return inventaire, l'inventaire du joueur
	 */
	public Inventaire getInventaire() {
		return inventaire;
	}

	/**
	 * Lance les dés et retourne l arraylist des faces obtenues
	 * 
	 * @return face obtenu
	 */
	public ArrayList<Face> lancerDeDieu() {
		ArrayList<De> des = inventaire.getListDeJoueur();

		Face face1 = lancerDe(des.get(0));
		Face face2 = lancerDe(des.get(1));

		ArrayList<Face> faces = new ArrayList<Face>();
		faces.add(face1);
		faces.add(face2);
		return faces;
	}

	/**
	 * Méthode permettant de faire un lancer de dé mineur (lancer 1 seul dé)
	 * 
	 * @param indexDeChoisis, le dé choisi pour être lancé
	 * @return ressource, la/les ressouces obetenues
	 */
	public Face lancerDeMineur(int indexDeChoisis) {

		ArrayList<De> des = inventaire.getListDeJoueur();

		Face ressource = lancerDe(des.get(indexDeChoisis));
		ArrayList<Face> faceList = new ArrayList<Face>();
		faceList.add(ressource);
		ajouteRessource(faceList);

		return ressource;
	}

	/**
	 * Méthode permettant d'ajouter les ressources à l'inventaire du joueur
	 * 
	 * @param faces, les faces obtenues par le joueur, indiquand les ressources
	 *               obtenues
	 */
	public void ajouteRessource(ArrayList<Face> faces) {
		if (faces == null)
			return;

		HashMap<Ressource, Integer> ressourcesList = new HashMap<Ressource, Integer>();
		int multiplicateur = 1;
		for (Face f : faces) {
			// Add ressource to a list
			for (Ressource r : f.getRessourceGranted().keySet()) {
				if (ressourcesList.containsKey(r)) {
					// Already exist add number ressource
					int newNumberRessource = ressourcesList.get(r) + f.getNumberOfRessourceGranted(r);
					ressourcesList.replace(r, newNumberRessource);
				} else {
					switch (r) {
					case portal:
						break;
					case multiplyThree:
						multiplicateur *= 3;
						break;
					default:
						ressourcesList.put(r, f.getNumberOfRessourceGranted(r));
						break;
					}
				}
			}

		}

		// Multiplie la liste par le multiplicateur
		for (Ressource r : ressourcesList.keySet()) {
			switch (r) {
			case gold:
				getInventaire().ajouterGold(ressourcesList.get(r) * multiplicateur);
				break;
			case victoryPoint:
				getInventaire().ajouterPoint(ressourcesList.get(r) * multiplicateur);
				break;
			case lunaryStone:
				getInventaire().ajouterLunary(ressourcesList.get(r) * multiplicateur);
				break;
			case solaryStone:
				getInventaire().ajouterSolary(ressourcesList.get(r) * multiplicateur);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Méthode permettant de faire un lancer de De simple
	 * 
	 * @param de, le dé à lancer
	 * @return la face obtenue
	 */
	public Face lancerDe(De de) {
		Face face = de.roll();
		return face;
	}

	/**
	 * Méthode permettant de changer le nom du joueur
	 * 
	 * @param nom, nouveau nom du joueur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Méthode permettant de changer l'inventaire du joueur
	 * 
	 * @param inventaire, nouvel inventaire du joueur
	 */
	public void setInventaire(Inventaire inventaire) {
		this.inventaire = inventaire;
	}

}
