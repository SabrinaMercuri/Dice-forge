package server;

public class StatJoueur {

	private String name;
	private int nbVictoire;
	private int nbVictoryPoint;
	private int nbFacesAchete;
	private int nbCartesAchete;
	private int nbLancer;
	private int orGagne;
	private int orDepense;
	private int solaryGagne;
	private int solaryDepense;
	private int lunaryGagne;
	private int lunaryDepense;

	public StatJoueur(String name) {
		this.name = name;
		nbVictoire = 0;
		nbVictoryPoint = 0;
		nbFacesAchete = 0;
		nbCartesAchete = 0;
		nbLancer = 0;
		orGagne = 0;
		orDepense = 0;
		solaryGagne = 0;
		solaryDepense = 0;
		lunaryGagne = 0;
		lunaryDepense = 0;
	}

	// GETTER
	public String getName() {
		return name;
	}

	public int getNbVictoire() {
		return nbVictoire;
	}

	public int getNbVictoryPoint() {
		return nbVictoryPoint;
	}

	public int getNbFacesAchete() {
		return nbFacesAchete;
	}

	public int getNbCartesAchete() {
		return nbCartesAchete;
	}

	public int getNbLancer() {
		return nbLancer;
	}

	public int getOrGagne() {
		return orGagne;
	}

	public int getOrDepense() {
		return orDepense;
	}

	public void ajouteVictoire() {
		nbVictoire++;
	}

	public int getSolaryGagne() {
		return solaryGagne;
	}

	public int getSolaryDepense() {
		return solaryDepense;
	}

	public int getLunaryGagne() {
		return lunaryGagne;
	}

	public int getLunaryDepense() {
		return lunaryDepense;
	}

	// METHODES DE CALCUL DE STATISTIQUE
	public void ajouteNbVictoryPoint(int victoryPoint) {
		nbVictoryPoint += victoryPoint;
	}

	public void ajouteFacesAchete() {
		nbFacesAchete++;
	}

	public void ajouteCartesAchete() {
		nbCartesAchete++;
	}

	public void ajouteNbLancer() {
		nbLancer++;
	}

	public void ajouterOrGagne(int nbGold) {
		orGagne += nbGold;
	}

	public void retirerOrDepense(int nbGold) {
		orDepense += nbGold;
	}

	public void ajouterSolaryGagne(int nbSolary) {
		solaryGagne += nbSolary;
	}

	public void retirerSolaryDepense(int nbSolary) {
		solaryDepense += nbSolary;
	}

	public void ajouterLunaryGagne(int nbLunary) {
		lunaryGagne += nbLunary;
	}

	public void retirerLunaryDepense(int nbLunary) {
		lunaryDepense += nbLunary;
	}

	public String toString() {
		return "Joueur : " + getName() + 
				"\nNombres victoire :" + getNbVictoire() + 
				"\nNombre total de Victory Point "+ getNbVictoryPoint() + 
				"\nNombre de Cartes achetees " + getNbCartesAchete() + 
				"\nNombre de Faces achetees "+ getNbFacesAchete() + 
				"\nNombre de lance " + getNbLancer() +
				"\nNombre d'Or gagne "+ getOrGagne() +
				"\nNombre d'Or depense " + getOrDepense() +
				"\nNombre d'Solary gagne "+ getSolaryGagne() +
				"\nNombre d'Solary depense " + getSolaryDepense() +
				"\nNombre d'Lunary gagne "+ getLunaryGagne() +
				"\nNombre d'Lunary depense " + getLunaryDepense() + 
				""
				;
	}
}
