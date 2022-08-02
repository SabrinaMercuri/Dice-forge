package protocol;

import core.cards.BlacksmithsHammerCard;
import core.cards.Card;

public class MarteauAcheteMessage {

	private String namePlayer;
	private BlacksmithsHammerCard marteau;
	private int lunaryCost;
	private int solaryCost;
	private int ile;

	public MarteauAcheteMessage(String namePlayer, BlacksmithsHammerCard marteau, int lunaryCost, int solaryCost, int ile) {
			super();
			this.namePlayer = namePlayer;
			this.marteau = marteau;
			this.lunaryCost = lunaryCost;
			this.solaryCost = solaryCost;
			this.ile = ile;
		}

	public MarteauAcheteMessage() {

		}

	public String getNamePlayer() {
		return namePlayer;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}

	public BlacksmithsHammerCard getMarteau() {
		return marteau;
	}

	public void setmarteau(BlacksmithsHammerCard marteau) {
		this.marteau = marteau;
	}

	public int getLunaryCost() {
		return lunaryCost;
	}

	public void setLunaryCost(int lunaryCost) {
		this.lunaryCost = lunaryCost;
	}

	public int getSolaryCost() {
		return solaryCost;
	}

	public void setSolaryCost(int solaryCost) {
		this.solaryCost = solaryCost;
	}

	public int getIle() {
		return ile;
	}

	public void setIle(int ile) {
		this.ile = ile;
	}

}
