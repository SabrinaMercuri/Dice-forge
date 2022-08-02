package core;

/**
 * Enum listant les différents types de ressources disponibles dans le jeu
 *
 */
public enum Ressource {
	gold ("gold"),
	solaryStone ("solaryStone"),
	lunaryStone ("lunaryStone"),
	victoryPoint ("victoryPoint"),
	multiplyThree ("multiplyThree"),
	portal ("portal");
	
	private String nom;
	
    /**
     * Constructeur de la ressource
     * @param nom, nom de la ressource
     */
    private Ressource(String nom) {
        this.nom = nom;
    }
    
    /**
     * Méthode permettant d'afficher le nom de la ressource
     */
    @Override
    public String toString(){
        return nom;
    }
}
