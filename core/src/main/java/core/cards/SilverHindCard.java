package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte SilverHind
 */
public class SilverHindCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public SilverHindCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.SILVERHIND, 2, false, true);
	}

}
