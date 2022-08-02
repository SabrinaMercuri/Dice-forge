package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte WildSpirits
 */
public class WildSpiritsCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public WildSpiritsCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.WILDSPIRIT, 2, false, true);
	}

}
