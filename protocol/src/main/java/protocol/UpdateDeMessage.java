package protocol;

import java.util.ArrayList;
import java.util.List;

import core.De;

public class UpdateDeMessage {

	private String namePlayer;
	private ArrayList<De> updatedDe;
	
	public UpdateDeMessage() {
		
	}
	
	/**
	 * Message send to client or server to notice a face bought by a player, information provided are in the parma
	 * @param namePlayer Name of the player who bought the face
	 * @param updatedDe Update de of the player with the new face
	 */
	public UpdateDeMessage( ArrayList<De> updatedDe, String namePlayer) {
		this.updatedDe = updatedDe;
		this.namePlayer = namePlayer;
	}
			
	public String getNamePlayer() {
		return namePlayer;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}
	
	public ArrayList<De> getUpdatedDe() {
		return this.updatedDe;
	}
	
	public void setUpdatedDe(List<De> updatedDe) {
		this.updatedDe = new ArrayList<De>(updatedDe);
	}

}
