package core.cards;

import java.util.HashMap;

import core.Ressource;

/**
 * Classe représentant la carte Hydra
 */
public class HydraCard extends Card {

	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
	public HydraCard(HashMap<Ressource, Integer> ressourceCost) {
		super(ressourceCost,Card.HYDRA, 26, false, false);
	}

}
