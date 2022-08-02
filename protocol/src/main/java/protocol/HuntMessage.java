package protocol;

public class HuntMessage {

	private String nomJoueurChasse;
	private int ile;
	
	public HuntMessage() {
	}
	
	public HuntMessage(String nom, int ile) {
		nomJoueurChasse = nom;
		this.ile = ile;
	}
	
	public void setNom(String nom) {
		nomJoueurChasse =nom;
	}
	
	public String getNom() {
		return nomJoueurChasse;
	}

	public int getIle() {
		return ile;
	}

	public void setIle(int ile) {
		this.ile = ile;
	}
}
