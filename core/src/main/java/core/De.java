package core;

import java.util.Random;

/**
 * Classe représentant un dé lancé par le joueur
 * Il comporte 6 faces et elles sont aléatoires
 *
 */
public class De {

	private Face[] faces;
	private Random rdm;
	
	/**
	 * Constructeur de la classe De
	 */
	public De () {
		faces = new Face[6];
		rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());
	}
	
	/**
	 * Méthode permettant de faire rouler un dé 
	 * @return la face obtenue après le rolls
	 */
	public Face roll () {
		int index = rdm.nextInt(6);
		return faces[index];
	}
	
	/**
	 * Méthode permettant de changer la face du dé
	 * @param index de la face à changer
	 * @param face à retourner à la place de la précédente
	 */
	public void changeFace (int index, Face face) {
		faces[index]=face;
	}

	/**
	 * Méthode permettant de récupérer les différentes faces d'un dé
	 * @return les différentes faces (tableau de faces)
	 */
	public Face[] getFaces() {
		return faces;
	}

	/**
	 * Méthode permettant de changer les faces d'un dé
	 * @param faces qui est un tableau contenant les faces du dé
	 */
	public void setFaces(Face[] faces) {
		this.faces = faces;
	}
	
	/**
	 * Méthode permettant de changer un face du dé selon un index
	 * @param face choisie pour en remplacer une autre
	 * @param index choisie de la face à remplacer
	 */
	public void setFace(Face face, int index) {
		if(index<0 || index>=faces.length) return;
		faces[index] = face;
	}
	
	@Override
	public String toString() {
		String display = "De : ";
		for(int i=0;i<faces.length;i++)
			display += faces[i]+" ";
		return display;
	}
}
