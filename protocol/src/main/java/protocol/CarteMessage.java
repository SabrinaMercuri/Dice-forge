package protocol;

import core.cards.Card;

public class CarteMessage {

	private String namePlayer;
	private Card carte;
	private int lunaryCost;
	private int solaryCost;
	private int ile;

	public CarteMessage(String namePlayer, Card carte, int lunaryCost, int solaryCost, int ile) {
		super();
		this.namePlayer = namePlayer;
		this.carte = carte;
		this.lunaryCost = lunaryCost;
		this.solaryCost = solaryCost;
		this.ile = ile;
	}

	public CarteMessage() {

	}

	public String getNamePlayer() {
		return namePlayer;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}

	public Card getCarte() {
		return carte;
	}

	public void setCarte(Card carte) {
		this.carte = carte;
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
