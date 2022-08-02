package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant tout ce qui peut être acheté dans le jeu (carte ou face)
 * caratérisé par une ou plusieurs ressources et leur côut (nombre de ressource)
 *
 */
public abstract class BuyableItem {

	private HashMap<Ressource, Integer> cost;
	
	/**
	 * Constructeur de BuyAbleItem
	 * @param ressourceCost, la ressource et le coût de l'item acheté
	 */
	public BuyableItem(HashMap<Ressource, Integer> ressourceCost) {
		this.cost = ressourceCost;
	}
	
	/**
	 * Constructeur vide
	 */
	public BuyableItem() {	
	}

	/**
	 * Méthode permettant de récupérer les ressources de l'item
	 * @return returnValue, la ressource 
	 */
	public ArrayList<Ressource> getRessources(){
		ArrayList<Ressource> returnValue = new ArrayList<Ressource>();
		for(Ressource r : returnValue)
			returnValue.add(r);
		return returnValue;
	}
	
	/**
	 * Méthode permettant de récupérer le côut de de la ressource 
	 * @param ressource, la ressource de l'item
	 * @return le coût de l'item (le nb de ressource)
	 */
	public int getCost(Ressource ressource) {
		return cost.get(ressource);
	}
	
	/**
	 * Méthode permettant de récupérer le coût de l'item
	 * @return le coup de l'item, soit la ressource 
	 * et le nombre de fois qu'elle doit être dépensé pour avoir l'item
	 */
	public HashMap<Ressource, Integer> getCost(){
		return cost;
	}
	
	/**
	 * Méthode permettant de changer le coût de l'item
	 * @param cost, le coût de l'item, soit la ressource demandée et combien
	 */
	public void setCost(Map<Ressource, Integer> cost) {
		this.cost = new HashMap<Ressource, Integer>(cost);
	}
}
