package protocol;

import core.cards.BlacksmithsHammerCard;

public class MarteauMessage {
	private BlacksmithsHammerCard marteau;
	private String namePlayer;
	private int victoryPoint;

	public MarteauMessage(BlacksmithsHammerCard marteau, String namePlayer, int victoryPoint) {
		this.marteau = marteau;
		this.namePlayer = namePlayer;
		this.victoryPoint = victoryPoint;
	}

	public MarteauMessage() {

	}

	public void setMarteau(BlacksmithsHammerCard marteau) {
		this.marteau = marteau;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}

	public BlacksmithsHammerCard getMarteau() {
		return marteau;
	}

	public String getNamePlayer() {
		return namePlayer;
	}

	public int getVictoryPoint() {
		return victoryPoint;
	}

	public void setVictoryPoint(int victoryPoint) {
		this.victoryPoint = victoryPoint;
	}

}
