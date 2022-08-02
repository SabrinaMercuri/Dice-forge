package protocol;

import core.De;

public class SphinxMessage {

	private String namePlayer;
	private De deToRoll;
	
	public SphinxMessage() {
	}
	
	public SphinxMessage(String namePlayer) {
		this.namePlayer=namePlayer;
		this.deToRoll=null;
	}
	
	public void setName(String namePlayer) {
		this.namePlayer=namePlayer;
	}
	
	public String getName() {
		return namePlayer;
	}

	public De getDeToRoll() {
		return deToRoll;
	}

	public void setDeToRoll(De deToRoll) {
		this.deToRoll = deToRoll;
	}
}
