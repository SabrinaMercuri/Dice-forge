package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte Sphinx
 */
public class BlacksmithsHammerCard extends Card {
	private int nbGold;
	private boolean status1eEtape;
	private boolean status;

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public BlacksmithsHammerCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.BLACKSMITHHAMMER, 0, false, false);
		nbGold = 0;
		status1eEtape = true;
		status = true;
	}
	
	/**
	 * Constructeur vide
	 */
	public BlacksmithsHammerCard() {
		
	}

	/**
	 * Méthode permettant de récupérer le nombre de gold
	 * @return nbGold, le nombre de gold
	 */
	public int getNbGold() {
		return nbGold;
	}

	/**
	 * Méthode permettant de modifier le nombre de gold
	 * @param nbGold, nouveau nombre de gold
	 */
	public void setNbGold(int nbGold) {
		this.nbGold = nbGold;
	}

	/**
	 * Méthode permettant de modifier le statut de la 1ere étape
	 * @param status1eEtape, true ou false
	 */
	public void setStatus1eEtape(boolean status1eEtape) {
		this.status1eEtape = status1eEtape;
	}

	/**
	 * Méthode permettant de changer le statut
	 * @param status, true ou false
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Méthode permettant de savoir si on est à la première étape
	 * @return status1eEtape, true ou false
	 */
	public boolean isStatus1eEtape() {
		return status1eEtape;
	}

	/**
	 * Méthode permettant de connaitre le status 
	 * @return status, true ou false
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * Méthode permettant d'ajouter des golds pour le marteau
	 * @param goldAjoute, nombre de gold à ajouter
	 */
	public void ajouteGoldMarteau(int goldAjoute) {
		if (!isStatus()) {

		} else {
			if (getNbGold() < 10) {
				nbGold += goldAjoute;
				System.out.println("JE RAJOUTE " + goldAjoute + " AU MARTEAU POUR LA PREMIERE PARTIE");
			} else if (getNbGold() > 10 || getNbGold() < 20) {
				nbGold += goldAjoute;
				System.out.println("JE RAJOUTE " + goldAjoute + " AU MARTEAU POUR LA DEUXIEME PARTIE");
			} else if (getNbGold() >= 20) {
				status = false;
				System.out.println("NBGOLD PLUS GRAND QUE 20");
			}
		}

	}

	/**
	 * Méthode permettant de connaitre le palier 
	 * @return 10, pour la première étape finie, 15 pour la derniere
	 * et 0 si la première étape n'est pas finie
	 */
	public int palierMarteau() {
		if (getNbGold() >= 10 && getNbGold() < 20 && status1eEtape) {
			status1eEtape = false;
			return 10;
		} else if (getNbGold() >= 20 && status && !status1eEtape) {
			status = false;
			return 15;
		}
		return 0;
	}
	
	
	
}
