package protocol;

import core.Face;
import core.Ressource;

public class HelmetMessage {
	private String namePlayer;
	private Face multiplythree;
	
	public HelmetMessage() {
		
	}
	
	/**
	 * 
	 * @param namePlayer Name of the player who bought the face
	 * @param multiplythree face qui sera ajouté sur le dé du joueur
	 */
	public HelmetMessage(String namePlayer, Face multiplythree) {
		this.namePlayer = namePlayer;
		this.multiplythree = multiplythree;
	}
			
	public String getNamePlayer() {
		return namePlayer;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}
	
	public Face getMultiplyThree() {
		return this.multiplythree;
	}
	
	public void setMultiplyThree(Face multiplythree) {
		this.multiplythree = multiplythree;
	}
}
