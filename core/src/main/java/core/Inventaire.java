package core;

import java.util.ArrayList;
import java.util.HashMap;

import core.cards.Card;

/**
 * Classe représentant l'inventaire d'un joueur
 * Comprenant différentes ressources, ses dés, ses cartes
 */
public class Inventaire {

	private int victoryPoint;
	private int gold;
	private int solaryStone;
	private int lunaryStone;
	private ArrayList<De> listDeJoueur = new ArrayList<De>();
	private ArrayList<Card> listedecartes = new ArrayList<Card>();
	private ArrayList<Face> listedefaceNONEQUIPE = new ArrayList<Face>();

	private int limitGold;
	private int limitSolary;
	private int limitLunary;

	/**
	 * Constructeur de la classe Inventaire qui initialise les variables de la
	 * classe avec des valeurs par défauts 0 pour toutes les ressources et 12 pour
	 * la limite de Gold et 6 pour la limite de SolaryStone et LunaryStone
	 */
	public Inventaire() {

		this.listDeJoueur.add(new De());
		this.listDeJoueur.add(new De());

		this.listDeJoueur.get(0)
				.setFaces(new Face[] { new Face(Ressource.gold, false, 1), new Face(Ressource.gold, false, 1),
						new Face(Ressource.gold, false, 1), new Face(Ressource.gold, false, 1),
						new Face(Ressource.gold, false, 1), new Face(Ressource.gold, false, 1) });
		this.listDeJoueur.get(1)
				.setFaces(new Face[] { new Face(Ressource.gold, false, 1), new Face(Ressource.gold, false, 1),
						new Face(Ressource.gold, false, 1), new Face(Ressource.gold, false, 1),
						new Face(Ressource.victoryPoint, false, 2), new Face(Ressource.gold, false, 1) });

		this.victoryPoint = 0;
		this.gold = 0;
		this.solaryStone = 0;
		this.lunaryStone = 0;

		this.limitGold = 12;
		this.limitSolary = 6;
		this.limitLunary = 6;

	}

	// Méthode d'ajout

	/**
	 * Méthode qui permet d'ajouter de l'or à l'inventaire La valeur de l'ajout est
	 * obligatoirement supérieur à 0 Si la somme de l'ajout et de l'or en stock
	 * dépasse la limite alors notre stock en or prend comme valeur la limite Sinon
	 * on ajoute l'or à notre stock
	 * 
	 * @param gold to add to the inventory
	 */
	public void ajouterGold(int gold) {
		if (gold <= 0)
			return;
		if ((this.gold + gold) >= this.limitGold)
			this.gold = this.limitGold;
		else
			this.gold += gold;
	}

	/**
	 * Méthode qui permet d'ajouter des Victory point à l'inventaire La valeur de
	 * l'ajout est obligatoirement supérieur à 0 Si la somme de l'ajout et des
	 * Victory point en stock dépasse la limite alors notre stock en Victory point
	 * prend comme valeur la limite Sinon on ajoute les Victory point à notre stock
	 * 
	 * @param pt pt à ajouter au joueur
	 */
	public void ajouterPoint(int pt) {
		if (pt <= 0)
			return;
		this.victoryPoint += pt;
	}

	/**
	 * Méthode qui permet d'ajouter des Solary stone à l'inventaire La valeur de
	 * l'ajout est obligatoirement supérieur à 0 Si la somme de l'ajout et des
	 * Solary ston en stock dépasse la limite alors notre stock en Solary stone
	 * prend comme valeur la limite Sinon on ajoute les Solary stone à notre stock
	 * 
	 * @param solary solaryStone à ajouter au joueur
	 */
	public void ajouterSolary(int solary) {
		if (solary <= 0)
			return;

		if ((this.solaryStone + solary) >= this.limitSolary)
			this.solaryStone = this.limitSolary;
		else
			this.solaryStone += solary;
	}

	/**
	 * Méthode qui permet d'ajouter des Lunary stone à l'inventaire La valeur de
	 * l'ajout est obligatoirement supérieur à 0 Si la somme de l'ajout et des
	 * Solary Lunary stone en stock dépasse la limite alors notre stock en Lunary
	 * stone prend comme valeur la limite Sinon on ajoute les Lunary stone à notre
	 * stock
	 * 
	 * @param lunary lunaryStone à ajouter au joueur
	 */
	public void ajouterLunary(int lunary) {
		if (lunary <= 0)
			return;

		if ((this.lunaryStone + lunary) >= this.limitLunary)
			this.lunaryStone = this.limitLunary;
		else
			this.lunaryStone += lunary;
	}

	// Méthodes de retrait
	/**
	 * Méthode qui permet de retirer des gold à l'inventaire La valeur de retrait
	 * est obligatoirement supérieur à 0 Si la différence entre la valeur de
	 * l'inventaire en gold et la valeur de retrait est inférieur ou égalle à zéro
	 * alors la valeur de l'inventaire en gold vaut 0 Sinon on retire autant de gold
	 * que la valeur de retrait
	 * 
	 * @param gold gold à ajouter au joueur
	 */
	public void retirerGold(int gold) {
		if (gold <= 0)
			return;
		if ((this.gold - gold) <= 0)
			this.gold = 0;
		else
			this.gold -= gold;
	}

	/**
	 * Méthode qui permet de retirer des Victory point à l'inventaire La valeur de
	 * retrait est obligatoirement supérieur à 0 Si la différence entre la valeur de
	 * l'inventaire en Victory point et la valeur de retrait est inférieur ou égalle
	 * à zéro alors la valeur de l'inventaire en Victory point vaut 0 Sinon on
	 * retire autant de Victory point que la valeur de retrait
	 * 
	 * @param pt point à retirer au joueur
	 */
	public void retirerPoint(int pt) {
		if (pt <= 0)
			return;
		if ((this.victoryPoint - pt) <= 0)
			this.victoryPoint = 0;
		else
			this.victoryPoint -= pt;
	}

	/**
	 * Méthode qui permet de retirer des Solary stone à l'inventaire La valeur de
	 * retrait est obligatoirement supérieur à 0 Si la différence entre la valeur de
	 * l'inventaire en Solary stone et la valeur de retrait est inférieur ou égalle
	 * à zéro alors la valeur de l'inventaire en Solary stone vaut 0 Sinon on retire
	 * autant de Solary stone que la valeur de retrait
	 * 
	 * @param solary solary à retirer au joueur
	 */
	public void retirerSolary(int solary) {
		if (solary <= 0)
			return;
		if ((this.solaryStone - solary) <= 0)
			this.solaryStone = 0;
		else
			this.solaryStone -= solary;
	}

	/**
	 * Méthode qui permet de retirer des Lunary stone à l'inventaire La valeur de
	 * retrait est obligatoirement supérieur à 0 Si la différence entre la valeur de
	 * l'inventaire en Lunary stone et la valeur de retrait est inférieur ou égalle
	 * à zéro alors la valeur de l'inventaire en Lunary stone vaut 0 Sinon on retire
	 * autant de Lunary stone que la valeur de retrait
	 * 
	 * @param lunary lunary à retirer à l'inventaire
	 */
	public void retirerLunary(int lunary) {
		if (lunary <= 0)
			return;
		if ((this.lunaryStone - lunary) <= 0)
			this.lunaryStone = 0;
		else
			this.lunaryStone -= lunary;
	}

	/**
	 * Met à jour les ressources dans l'inventaire en fonction de la HashMap de
	 * ressource passer en parametre de la fonction
	 * 
	 * @param ressources la clef est la ressource, l'integer le nombre de ressource
	 */
	public void updateRessource(HashMap<Ressource, Integer> ressources) {
		for (Ressource r : ressources.keySet()) {
			switch (r) {
			case gold:
				if (ressources.get(r) > 0)
					ajouterGold(ressources.get(r));
				else
					retirerGold(ressources.get(r) * -1);
				break;
			case lunaryStone:
				if (ressources.get(r) > 0)
					ajouterLunary(ressources.get(r));
				else
					retirerLunary(ressources.get(r) * -1);
				break;
			case solaryStone:
				if (ressources.get(r) > 0)
					ajouterSolary(ressources.get(r));
				else
					retirerSolary(ressources.get(r) * -1);
				break;
			case victoryPoint:
				if (ressources.get(r) > 0)
					ajouterPoint(ressources.get(r));
				else
					retirerPoint(ressources.get(r) * -1);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Méthode permettant de récupérer le nombre de victory point de l'inventaire
	 * @return victoryPoint, le nombre de victory point
	 */
	public int getVictoryPoint() {
		return victoryPoint;
	}

	/**
	 * Méthode permettant de récupérer le nombre de gold de l'inventaire
	 * @return gold, le nombre de gold
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * Méthode permettant de récupérer le nombre de solary stone de l'inventaire
	 * @return solaryStone, le nombre de solary stone
	 */
	public int getSolaryStone() {
		return solaryStone;
	}

	/**
	 * Méthode permettant de récupérer le nombre de lunary stone de l'inventaire
	 * @return lunaryStone, le nombre de lunary stone
	 */
	public int getLunaryStone() {
		return lunaryStone;
	}

	/**
	 * Méthode permettant de récupérer les dés du joueur
	 * @return listDeJoueur, la liste des dés du joueur
	 */
	public ArrayList<De> getListDeJoueur() {
		return listDeJoueur;
	}

	/**
	 * Méthode permettant de récupérer le nombre max de gold de l'inventaire
	 * @return limitGold, le nombre maximum de gold
	 */
	public int getLimitGold() {
		return limitGold;
	}

	/**
	 * Méthode permettant de récupérer le nombre max de solary de l'inventaire
	 * @return limitSolary, le nombre maximum de solary
	 */
	public int getLimitSolary() {
		return limitSolary;
	}

	/**
	 * Méthode permettant de récupérer le nombre max de lunary de l'inventaire
	 * @return limitLunary, le nombre maximum de lunary
	 */
	public int getLimitLunary() {
		return limitLunary;
	}

	/**
	 * Méthode permettant de précupérer la liste des cartes dans l'inventaire du joueur
	 * @return listedecartes, la liste des cartes
	 */
	public ArrayList<Card> getListedecartes() {
		return listedecartes;
	}

	/**
	 * Méthode permettant de précupérer la liste des faces non equipes
	 * @return la liste des faces non equipes
	 */
	public ArrayList<Face> getListedefaceNONEQUIPE() {
		return listedefaceNONEQUIPE;
	}

	/**
	 * Méthode permettant de mdifier les dés de l'inventaire
	 * @param listDeJoueur, les nouveaux dés du joueur
	 */
	public void setListDeJoueur(ArrayList<De> listDeJoueur) {
		this.listDeJoueur = listDeJoueur;
	}

	/**
	 * Méthode permettant de mofidier le nombre de solary
	 * @param solaryStone, le nouveau nombre de solary
	 */
	public void setSolaryStone(int solaryStone) {
		this.solaryStone = solaryStone;
	}

	/**
	 * Méthode permettant de mofidier le nombre de lunary
	 * @param lunaryStone, le nouveau nombre de lunary
	 */
	public void setLunaryStone(int lunaryStone) {
		this.lunaryStone = lunaryStone;
	}

	/**
	 * Méthode permettant d'afficher le contenu de l'inventaire
	 */
	@Override
	public String toString() {
		return "Inventaire [victoryPoint=" + victoryPoint + ", gold=" + gold + ", solaryStone=" + solaryStone
				+ ", lunaryStone=" + lunaryStone + ", limitGold=" + limitGold + ", limitSolary=" + limitSolary
				+ ", limitLunary=" + limitLunary + ", listDeJoueur=" + listDeJoueur.toString() + "]";
	}

	/**
	 * Méthode permettant de mofidier le nombre de victory point
	 * @param victoryPoint, le nouveau nombre de victory point
	 */
	public void setVictoryPoint(int victoryPoint) {
		this.victoryPoint = victoryPoint;
	}

	/**
	 * Méthode permettant de mofidier le nombre max de gold
	 * @param augment, le nouveau maximum
	 */
	public void setLimitGold(int augment) {
		this.limitGold += augment;
	}

	/**
	 * Méthode permettant de mofidier le nombre max de lunary
	 * @param augment, le nouveau maximum
	 */
	public void setLimitLunary(int augment) {
		this.limitLunary += augment;
	}

	/**
	 * Méthode permettant de mofidier le nombre max de solary
	 * @param augment, le nouveau maximum
	 */
	public void setLimitSolary(int augment) {
		this.limitSolary += augment;
	}

}
