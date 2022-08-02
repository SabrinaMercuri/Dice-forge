package protocol;

public class NewActionMessage {

	private boolean isRePlaying;
	private String nomJoueur;
	private boolean needResponse;
	
	public NewActionMessage() {
		
	}
	
	public NewActionMessage(boolean isRePlaying, String nom, boolean needResponse) {
		this.isRePlaying = isRePlaying;
		nomJoueur = nom;
		this.needResponse = needResponse;
	}

	public boolean isRePlaying() {
		return isRePlaying;
	}

	public void setRePlaying(boolean isRePlaying) {
		this.isRePlaying = isRePlaying;
	}

	public String getNomJoueur() {
		return nomJoueur;
	}

	public void setNomJoueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
	}

	public boolean isNeedResponse() {
		return needResponse;
	}

	public void setNeedResponse(boolean needResponse) {
		this.needResponse = needResponse;
	}
	
	
}
