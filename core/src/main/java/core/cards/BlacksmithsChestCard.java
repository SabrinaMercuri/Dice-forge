package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte BlacksmithsChest
 */
public class BlacksmithsChestCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public BlacksmithsChestCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.BLACKSMITHCHEST, 2, false, true);
	}

}
