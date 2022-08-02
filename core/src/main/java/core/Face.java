package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe représentant une face d'un dé
 * Caractérisée par la ressource de la face 
 * et si la face a été choisie par le joueur ou non
 */
public class Face {

	private HashMap<Ressource, Integer> ressourceGranted;
	private boolean hasChoice;

	/**
	 * Constructeur de la classe Face
	 * @param ressource, de la face
	 * @param hasChoice, si la face est choisie ou non
	 * true si la face est choisie sinon false
	 * @param numberOfRessourceGranted, nombre de ressource de la face
	 */
	public Face(Ressource ressource, boolean hasChoice, int numberOfRessourceGranted) {
		ressourceGranted = new HashMap<Ressource, Integer>();
		ressourceGranted.put(ressource, numberOfRessourceGranted);
		this.hasChoice = hasChoice;
	}
	
	/**
	 * Constructeur de la classe Face
	 * @param ressourceGranted, ressource et nb de ressource de la face
	 * @param hasChoice, si la face est choisie ou non
	 * true si la face est choisie sinon false
	 */
	public Face(Map<Ressource, Integer> ressourceGranted, boolean hasChoice) {
		this.ressourceGranted = new HashMap<Ressource, Integer>(ressourceGranted);
		this.hasChoice = hasChoice;
	}
	
	/**
	 * Constructeur vide
	 */
	public Face() {
	}

	/**
	 * Constructeur de la classe Face
	 * @param face face à partir de laquelle on construit la nouvelle face
	 */
	public Face(String face) {
		ressourceGranted = new HashMap<Ressource, Integer>();
		String keys[] = face.split(" ");
		String ressource = keys[1].replace("[", "");
		ressource = ressource.replace("]", "");
		String ressources[] = ressource.split(";");
		for (String r : ressources) {
			Ressource res = Ressource.valueOf(r.split("\\.")[0]);
			int nbRes = Integer.parseInt(r.split("\\.")[1]);
			ressourceGranted.put(res, nbRes);
		}
		hasChoice = Boolean.parseBoolean(keys[2]);
	}
	
	/**
	 * Méthode permettrant de récupérer la ressource de la face
	 * @return ressourceGranted, la ressource
	 */
	public HashMap<Ressource, Integer> getRessourceGranted(){
		return ressourceGranted;
	}

	/**
	 * Méthode permettant de savoir si la face a été choisie
	 * @return hasChoice, si la face est choisie true sinon false
	 */
	public boolean getHasChoice() {
		return hasChoice;
	}
	
	/**
	 * Méthode permettant de changer le fait que la face a été choisie ou non
	 * @param hasChoice, le choix a changer (true ou false)
	 */
	public void setHasChoice(boolean hasChoice) {
		this.hasChoice = hasChoice;
	}
	
	/**
	 * Méthode permettant de changer la ressourcea de la face
	 * @param ressourceGranted, la ressource à changer 
	 */
	public void setRessourceGranted(Map<Ressource, Integer> ressourceGranted) {
		this.ressourceGranted = new HashMap<Ressource, Integer>(ressourceGranted);
	}

	/**
	 * Méthode permettant de récupérer le nombre de ressource sur une face
	 * @param ressource, la ressource a remplacer
	 * @return ressource, la nouvelle ressource
	 */
	public int getNumberOfRessourceGranted(Ressource ressource) {
		return ressourceGranted.get(ressource);
	}
	
	/**
	 * Méthode permettant de récupérer la liste de Face correspondant à chaque ressource
	 * @return faces, la liste de faces 
	 */
	public ArrayList<Face> toRessourceToFaces(){
		ArrayList<Face> faces = new ArrayList<Face>();
		for(Ressource r : ressourceGranted.keySet()) 
			faces.add(new Face(r, false, ressourceGranted.get(r)));
		return faces;
	}

	/**
	 * Méthode permettant d'afficher une string donnant la ressource d'une face 
	 * et si elle a été choisie
	 */
	public String toString() {
		String ressource = "[";
		for (Ressource r : ressourceGranted.keySet()) {
			ressource += r.name() + "." + ressourceGranted.get(r) + ";";
		}
		ressource = ressource.substring(0, ressource.length()-1);
		ressource += "]";
		return "Face " + ressource + " " + hasChoice;
	}

	/**
	 * @param face, la face avec laquelle on test l'égalité
	 * Méthode permettant de face si une face est la même que celle donnée
	 * @return true si les deux faces sont les mêmes sinon false
	 */
	public boolean equals(Face face) {
		if (this.getRessourceGranted().equals(face.getRessourceGranted()) && hasChoice == face.getHasChoice())
			return true;
		else
			return false;
	}
}