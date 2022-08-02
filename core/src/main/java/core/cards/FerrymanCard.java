package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte Ferryman
 */
public class FerrymanCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public FerrymanCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.FERRYMAN, 12, false, false);
	}

}
