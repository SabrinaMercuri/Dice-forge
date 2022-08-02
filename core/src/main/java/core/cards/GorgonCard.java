package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte Gorgon
 */
public class GorgonCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public GorgonCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost,Card.GORGON, 14, false, false);
	}

}
