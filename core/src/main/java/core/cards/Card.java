package core.cards;

import java.util.HashMap;

import core.BuyableItem;
import core.Ressource;

/**
 * Classe représentant une carte du jeu 
 */
public class Card extends BuyableItem{
	
	public static final String BLACKSMITHHAMMER = "BlacksmithsHammerCard";
	public static final String WILDSPIRIT = "WildSpiritsCard";
	public static final String SPHINX = "SphinxCard";
	public static final String CANCER = "CancerCard";
	public static final String BLACKSMITHCHEST = "BlacksmithsChestCard";
	public static final String ELDER = "ElderCard";
	public static final String GUARDIANOWLS = "GuardiansOwlCard";
	public static final String INVISIBILITYHELMET = "HelmetInvisibilityCard";
	public static final String ABYSSALMIRROR = "MirrorAbyssCard";
	public static final String MINOTAUR = "MinotaurCard";
	public static final String SATYRS = "SatyrsCard";
	public static final String SILVERHIND = "SilverHindCard";
	public static final String FERRYMAN = "FerrymanCard";
	public static final String GORGON = "GorgonCard";
	public static final String HYDRA = "HydraCard";
	
	private String nom;
	private int victoryPoints;
	private boolean eachTurn;
	private boolean hasBuyEffect;

	/**
	 * Constructeur d'une carte
	 * @param ressourceCost, la/les ressources nécessaires pour obtenir la carte
	 * @param nom, le nom de la carte
	 * @param victoryPoints, les victory point que donne la carte
	 * @param eachTurn, sert à savoir si son effet est activable à chaque tour ou non
	 * @param hasBuyEffect, si la carte s'active juste à l'achat
	 */
	public Card(HashMap<Ressource, Integer> ressourceCost, String nom, int victoryPoints, boolean eachTurn, boolean hasBuyEffect) {
		super(ressourceCost);
		this.nom = nom;
		this.victoryPoints = victoryPoints;
		this.eachTurn = eachTurn;
		this.hasBuyEffect = hasBuyEffect;
	}
	
	/**
	 * Constructeur vide
	 */
	public Card() {
		super();
	}
	
	/**
	 * Méthode permettant de récupérer le nom de la carte
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Méthode permettant de récupérer les victory point 
	 * @return victoryPoints
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * Méthode permettant de modifier le nom de la carte
	 * @param nom, le nouveau nom de la carte
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Méthode permettant de modifier le nombre de victory point de la carte
	 * @param victoryPoints, le nouveau nombre de victory point
	 */
	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	/**
	 * Méthode permettant de savoir si la carte s'active à chaque tour
	 * @return eachturn, true ou false si l'effet s'active à chaque tour
	 */
	public boolean isEachTurn() {
		return eachTurn;
	}

	/**
	 * Méthode permettant de modifier si l'effet de la carte s'active à chaque tour
	 * @param eachTurn, true ou false
	 */
	public void setEachTurn(boolean eachTurn) {
		this.eachTurn = eachTurn;
	}

	/**
	 * Méthode permettant de savoir si la carte à un effect immédiat
	 * @return hasBuyEffect, true ou false
	 */
	public boolean getHasBuyEffect() {
		return hasBuyEffect;
	}

	/**
	 * Méthode permettant de modifier si la carte a un effet ou non
	 * @param hasEffect, true ou false
	 */
	public void setHasBuyEffect(boolean hasEffect) {
		this.hasBuyEffect = hasEffect;
	}
	
}