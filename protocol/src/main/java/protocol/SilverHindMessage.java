package protocol;

import core.De;

public class SilverHindMessage {

	private String namePlayer;
	private boolean request;
	private De deToRoll;
	
	public SilverHindMessage() {
	}
	
	public SilverHindMessage(String namePlayer) {
		this.namePlayer=namePlayer;
		setRequest(false);
		setDeToRoll(null);
	}
	
	public SilverHindMessage(String namePlayer, boolean request) {
		this.namePlayer=namePlayer;
		this.setRequest(request);
		setDeToRoll(null);
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

	public boolean isRequest() {
		return request;
	}

	public void setRequest(boolean request) {
		this.request = request;
	}
}
