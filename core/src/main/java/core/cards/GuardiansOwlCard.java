package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte GuardiansOwl
 */
public class GuardiansOwlCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public GuardiansOwlCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost, Card.GUARDIANOWLS, 4, true, false);
	}

}
