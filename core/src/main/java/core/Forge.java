package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant la forge du jeu
 * Elle continet les différentes catégories de la forge
 */
public class Forge {
	
	private ArrayList<ForgeCategory> forgeCategories;
	
	/**
	 * Create the default forge with the init method
	 */
	public Forge() {
		initForge();
	}
	
	/**
	 * Create a custom forge with custom categories
	 * @param categories, custom categories that will be put inside the forge
	 */
	public Forge(ArrayList<ForgeCategory> categories) {
		this.forgeCategories = categories;
	}
	
	/**
	 * Init the default forge with the default categories and face
	 */
	public void initForge() {
		//init
		forgeCategories = new ArrayList<ForgeCategory>();
		
		//Categorie cost 2 gold
		HashMap<Ressource, Integer> costCat1 = new HashMap<Ressource, Integer>();
		costCat1.put(Ressource.gold, 2);
		ArrayList<Face> faceCat1 = new ArrayList<Face>();
		for(int i=0;i<4;i++)
			faceCat1.add(new Face(Ressource.gold, false, 3));
		for(int i=0;i<4;i++)
			faceCat1.add(new Face(Ressource.lunaryStone, false, 1));
		ForgeCategory cat1 = new ForgeCategory(costCat1, faceCat1);
		forgeCategories.add(cat1);
		
		//Categorie cost 3 gold
		HashMap<Ressource, Integer> costCat2 = new HashMap<Ressource, Integer>();
		costCat2.put(Ressource.gold, 3);
		ArrayList<Face> faceCat2 = new ArrayList<Face>();
		for(int i=0;i<4;i++)
			faceCat2.add(new Face(Ressource.gold, false, 4));
		for(int i=0;i<4;i++)
			faceCat2.add(new Face(Ressource.solaryStone, false, 1));
		ForgeCategory cat2 = new ForgeCategory(costCat2, faceCat2);
		forgeCategories.add(cat2);
		
		//Categorie cost 4 gold
		HashMap<Ressource, Integer> costCat3 = new HashMap<Ressource, Integer>();
		costCat3.put(Ressource.gold, 4);
		ArrayList<Face> faceCat3 = new ArrayList<Face>();
		faceCat3.add(new Face(Ressource.gold, false, 6));
		HashMap<Ressource, Integer> granted = new HashMap<Ressource, Integer>();
		granted.put(Ressource.gold, 2);
		granted.put(Ressource.lunaryStone, 1);
		faceCat3.add(new Face(granted, false));
		granted = new HashMap<Ressource, Integer>();
		granted.put(Ressource.victoryPoint, 1);
		granted.put(Ressource.solaryStone, 1);
		faceCat3.add(new Face(granted, false));
		granted = new HashMap<Ressource, Integer>();
		granted.put(Ressource.gold, 1);
		granted.put(Ressource.solaryStone, 1);
		granted.put(Ressource.lunaryStone, 1);
		faceCat3.add(new Face(granted, true));
		ForgeCategory cat3 = new ForgeCategory(costCat3, faceCat3);
		forgeCategories.add(cat3);
		
		//Categorie cost 5 gold
		HashMap<Ressource, Integer> costCat4 = new HashMap<Ressource, Integer>();
		costCat4.put(Ressource.gold, 5);
		ArrayList<Face> faceCat4 = new ArrayList<Face>();
		granted = new HashMap<Ressource, Integer>();
		granted.put(Ressource.gold, 3);
		granted.put(Ressource.victoryPoint, 2);
		for(int i=0;i<4;i++)
			faceCat4.add(new Face(granted, true));
		ForgeCategory cat4 = new ForgeCategory(costCat4, faceCat4);
		forgeCategories.add(cat4);
		
		//Categorie cost 6 gold
		HashMap<Ressource, Integer> costCat5 = new HashMap<Ressource, Integer>();
		costCat5.put(Ressource.gold, 6);
		ArrayList<Face> faceCat5 = new ArrayList<Face>();
		for(int i=0;i<4;i++)
			faceCat5.add(new Face(Ressource.lunaryStone, false, 2));
		ForgeCategory cat5 = new ForgeCategory(costCat5, faceCat5);
		forgeCategories.add(cat5);
		
		//Categorie cost 8 gold
		HashMap<Ressource, Integer> costCat6 = new HashMap<Ressource, Integer>();
		costCat6.put(Ressource.gold, 8);
		ArrayList<Face> faceCat6 = new ArrayList<Face>();
		for(int i=0;i<4;i++)
			faceCat6.add(new Face(Ressource.solaryStone, false, 2));
		for(int i=0;i<4;i++)
			faceCat6.add(new Face(Ressource.victoryPoint, false, 3));
		ForgeCategory cat6 = new ForgeCategory(costCat6, faceCat6);
		forgeCategories.add(cat6);
		
		//Categorie cost 12 gold
		HashMap<Ressource, Integer> costCat7 = new HashMap<Ressource, Integer>();
		costCat7.put(Ressource.gold, 12);
		ArrayList<Face> faceCat7 = new ArrayList<Face>();
		faceCat7.add(new Face(Ressource.victoryPoint, false, 4));
		granted = new HashMap<Ressource, Integer>();
		granted.put(Ressource.solaryStone, 1);
		granted.put(Ressource.lunaryStone, 1);
		granted.put(Ressource.victoryPoint, 1);
		granted.put(Ressource.gold, 1);
		faceCat7.add(new Face(granted, false));
		granted = new HashMap<Ressource, Integer>();
		granted.put(Ressource.victoryPoint, 2);
		granted.put(Ressource.lunaryStone, 2);
		faceCat7.add(new Face(granted, false));
		granted = new HashMap<Ressource, Integer>();
		granted.put(Ressource.lunaryStone, 2);
		granted.put(Ressource.solaryStone, 2);
		granted.put(Ressource.gold, 2);
		faceCat7.add(new Face(granted, true));
		ForgeCategory cat7 = new ForgeCategory(costCat7, faceCat7);
		forgeCategories.add(cat7);
	}
	
	/**
	 * Get the forgeCategory
	 * @return forgeCategories, all forge categories
	 */
	public ArrayList<ForgeCategory> getForgeCategories() {
		return forgeCategories;
	}
	
	/**
	 * Get the forgeCategory by cost (get the first one)
	 * @param cost, the cost of the category to get
	 * @return forgeCategory that cost the argument
	 */
	public ForgeCategory getCategoryByCost(int cost) {
		for(ForgeCategory fg : forgeCategories) {
			if(fg.getCost(Ressource.gold)==cost) {
				return fg;
			}
		}
		return null;
	}	
}
