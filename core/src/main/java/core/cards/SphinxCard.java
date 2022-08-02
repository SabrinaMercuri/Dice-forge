package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte Sphinx
 */
public class SphinxCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public SphinxCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.SPHINX, 10, false, true);
	}
}
