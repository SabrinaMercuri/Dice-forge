package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte HelmetInvisibility
 */
public class HelmetInvisibilityCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public HelmetInvisibilityCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.INVISIBILITYHELMET, 4, false, true);
	}

}
