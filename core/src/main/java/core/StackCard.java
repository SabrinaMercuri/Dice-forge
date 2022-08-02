package core;

import java.util.HashMap;

import core.cards.BlacksmithsChestCard;
import core.cards.BlacksmithsHammerCard;
import core.cards.CancerCard;
import core.cards.Card;
import core.cards.ElderCard;
import core.cards.FerrymanCard;
import core.cards.GorgonCard;
import core.cards.GuardiansOwlCard;
import core.cards.HelmetInvisibilityCard;
import core.cards.HydraCard;
import core.cards.MinotaurCard;
import core.cards.MirrorAbyssCard;
import core.cards.SatyrsCard;
import core.cards.SilverHindCard;
import core.cards.SphinxCard;
import core.cards.WildSpiritsCard;

/**
 * Classe représentant les différents types de carte sur une ile 
 * Caractérisée par son nom (le type) et son nombre 
 */
public class StackCard {

	private Card card;
	private int numberOfCard;

	/**
	 * Constructeur de la Stack Card
	 * @param nbCard, nombre de carte dans la stack
	 * @param nom, le type de carte 
	 */
	public StackCard(int nbCard, String nom) {
		this.card = typeCard(nom);
		numberOfCard = nbCard;
	}

	/**
	 * Méthode permettant de créer une carte en fonction du nom
	 * @param nom, type de la carte souhaitée
	 * @return la carte et son prix
	 */
	private Card typeCard(String nom) {
		HashMap<Ressource, Integer> cost = new HashMap<Ressource, Integer>();
		switch (nom) {
		case Card.BLACKSMITHHAMMER:
			cost.put(Ressource.lunaryStone, 1);
			return new BlacksmithsHammerCard(cost);
		case Card.BLACKSMITHCHEST:
			cost.put(Ressource.lunaryStone, 1);
			return new BlacksmithsChestCard(cost);
		case Card.SILVERHIND:
			cost.put(Ressource.lunaryStone, 2);
			return new SilverHindCard(cost);
		case Card.SATYRS:
			cost.put(Ressource.lunaryStone, 3);
			return new SatyrsCard(cost);
		case Card.FERRYMAN:
			cost.put(Ressource.lunaryStone, 4);
			return new FerrymanCard(cost);
		case Card.INVISIBILITYHELMET:
			cost.put(Ressource.lunaryStone, 5);
			return new HelmetInvisibilityCard(cost);
		case Card.CANCER:
			cost.put(Ressource.lunaryStone, 6);
			return new CancerCard(cost);
		case Card.HYDRA:
			cost.put(Ressource.lunaryStone, 5);
			cost.put(Ressource.solaryStone, 5);
			return new HydraCard(cost);
		case Card.SPHINX:
			cost.put(Ressource.solaryStone, 6);
			return new SphinxCard(cost);
		case Card.ABYSSALMIRROR:
			cost.put(Ressource.solaryStone, 5);
			return new MirrorAbyssCard(cost);
		case Card.GORGON:
			cost.put(Ressource.solaryStone, 4);
			return new GorgonCard(cost);
		case Card.MINOTAUR:
			cost.put(Ressource.solaryStone, 3);
			return new MinotaurCard(cost);
		case Card.GUARDIANOWLS:
			cost.put(Ressource.solaryStone, 2);
			return new GuardiansOwlCard(cost);
		case Card.WILDSPIRIT:
			cost.put(Ressource.solaryStone, 1);
			return new WildSpiritsCard(cost);
		case Card.ELDER:
			cost.put(Ressource.solaryStone, 1);
			return new ElderCard(cost);
		default:
			return null;
		}
	}
	
	/**
	 * Méthode permettant de récupéter une carte dans la stack
	 * @return card, la carte souhaitée
	 */
	public Card getCard(){
		return card;
	}

	/**
	 * Méthode permettant de récupérer le nombre de carte dans la stack
	 * @return numberOfCard, le nombre de cartes
	 */
	public int getNumberCard() {
		return numberOfCard;
	}

	/**
	 * Méthode permettant d'enlever une carte du paquet/ de la stack
	 */
	public void decreaseNbCard() {
		if (numberOfCard != 0) {
			numberOfCard--;
		}
	}
}
