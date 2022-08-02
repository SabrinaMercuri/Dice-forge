package core.cards;

import java.util.HashMap;
import core.Ressource;

/**
 * Classe représentant la carte Cancer
 */
public class CancerCard extends Card {
	
	/**
	 * Constructeur de la carte
	 * @param ressourceCost, les ressources dont à besoin le joueur pour obtenir la carte
	 */
    public CancerCard(HashMap<Ressource, Integer> ressourceCost) {
        super(ressourceCost, Card.CANCER, 8, false, true);
    }
}
