package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte Elder
 */
public class ElderCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public ElderCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.ELDER, 0, true, false);
	}

}
