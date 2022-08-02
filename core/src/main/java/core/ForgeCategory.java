package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe représentant les différentes catégories de la forge
 *
 */
public class ForgeCategory extends BuyableItem{
	
	private ArrayList<Face> faceAvailable;
	
	/**
	 * Constructeur de la ForgeCategory
	 * @param cost, le côut de la catégorie
	 * @param faces, les différentes faces dans la catégorie
	 */
	public ForgeCategory(HashMap<Ressource, Integer> cost, ArrayList<Face> faces) {
		super(cost);
		this.faceAvailable = faces;
	}
	
	/**
	 * Méthode permettant de savoir si la catégorie est vide
	 * @return true, si elle est vide sinon false
	 */
	public boolean isEmpty() {
		if(faceAvailable.size()==0)
			return true;
		else
			return false;
	}
	
	/**
	 * Méthode permettant d'acheter une face dans une catégorie de la forge selon son emplacement
	 * @param index, index de la face choisi
	 * @return null si l'index n'existe pas (rien ou plus grand que la taille de la catégorie)
	 * ou faceBuffer, la face achetée correpond à l'index donné
	 */
	public Face buyFace(int index) {
		if(index<0 || index>faceAvailable.size())
			return null;
		
		Face faceBuffer = faceAvailable.get(index);
		faceAvailable.remove(index);
		return faceBuffer;
	}
	
	/**
	 * Méthode permettant d'acheter une face selon une face donnée
	 * @param face, la face recherchée
	 * @return faceBuffer, la face achetée correpond à l'index donné
	 * ou null, si la face n'existe pas 
	 */
	public Face buyFace(Face face) {
		for(int i=0;i<faceAvailable.size();i++) {
			if(face.equals(faceAvailable.get(i))) {
				Face faceBuffer = faceAvailable.get(i);
				faceAvailable.remove(i);
				return faceBuffer;
			}
		}
		return null;
	}
	
	/**
	 * Méthode permettant de retourner l'ensemble des faces dans la catégorie
	 * @return faceAvailable, la liste des faces dans la catérie
	 */
	public ArrayList<Face> getFaces(){
		return faceAvailable;
	}
	
	
	
	/**
	 * Méthode permettant de récupérer la face à l'index voulu
	 * @param index, place de la face dans la catégorie
	 * @return la face à l'index indiqué 
	 */
	public Face getFace(int index) {
		return faceAvailable.get(index);
	}
	
	/**
	 * Méthode permettant de savoir si la catégorie contient une certaine face ou non
	 * @param face, face recherchée
	 * @return true, si la face est dans la catégorie, sinon false
	 */
	public boolean contains(Face face) {
		for(Face f : faceAvailable) {
			if(face.equals(f))
				return true;
		}
		return false;
	}
}
