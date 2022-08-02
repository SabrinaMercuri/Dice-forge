package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte Satyrs
 */
public class SatyrsCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public SatyrsCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.SATYRS, 6, false, true);
	}

}
