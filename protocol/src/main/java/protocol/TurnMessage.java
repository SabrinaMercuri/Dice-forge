package protocol;

public class TurnMessage {
	
	private String playerToPlay;

	public TurnMessage(String playerToPlay) {
		this.playerToPlay = playerToPlay;
	}
	
	public TurnMessage() {
		
	}
	
	public String getPlayerToPlay() {
		return this.playerToPlay;
	}
	
	public void setPlayerToPlay(String name) {
		this.playerToPlay = name;
	}
	
}
