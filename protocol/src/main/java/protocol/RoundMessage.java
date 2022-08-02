package protocol;

public class RoundMessage {
	
	private int roundNumber;
	
	public RoundMessage(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	
	public RoundMessage() {
		
	}
	
	public int getRoundNumber() {
		return roundNumber;
	}
	
	public void setRoundNumber(int number) {
		this.roundNumber = number;
	}
	
}
