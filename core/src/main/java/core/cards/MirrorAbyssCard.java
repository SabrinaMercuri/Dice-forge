package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte MirrorAbyss
 */
public class MirrorAbyssCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public MirrorAbyssCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.ABYSSALMIRROR, 10, false, true);
	}

}
