package protocol;

public class RetraitSolaryMessage {

	private String nom;
	private int nbRetrait;

	public RetraitSolaryMessage(String nom, int nbRetrait) {

		this.nom = nom;
		this.nbRetrait = nbRetrait;
	}

	public RetraitSolaryMessage() {

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbRetrait() {
		return nbRetrait;
	}

	public void setNbRetrait(int nbRetrait) {
		this.nbRetrait = nbRetrait;
	}
	
	

}
