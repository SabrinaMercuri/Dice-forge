package protocol;

public class CardEffectMessage {
	
	private String namePlayer;
	private String cardName;
	
	public CardEffectMessage() {
		
	}
	
	public CardEffectMessage(String namePlayer, String cardName) {
		this.namePlayer = namePlayer;
		this.cardName = cardName;
	}
	
	public String getNamePlayer() {
		return namePlayer;
	}
	
	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	
}
