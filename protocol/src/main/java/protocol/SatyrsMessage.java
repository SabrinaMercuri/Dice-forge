package protocol;

import java.util.ArrayList;

import core.Face;

public class SatyrsMessage {
	
	private String namePlayer;
	private ArrayList<Face> rolls;

	public SatyrsMessage() {
	}
	
	/**
	 * Message send to client or server to notice the faces rolls by other players, information provided are in the parma
	 * @param namePlayer Name of the player who bought the satyrs card
	 * @param rolls faces of rolls's players
	 */
	public SatyrsMessage(String namePlayer, ArrayList<Face> rolls) {
		this.namePlayer = namePlayer;
	}
			
	public String getNamePlayer() {
		return namePlayer;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}
	
	public ArrayList<Face> getRolls() {
		return this.rolls;
	}
	
	public void setRolls(ArrayList<Face> rolls) {
		this.rolls = rolls;
	}
}
