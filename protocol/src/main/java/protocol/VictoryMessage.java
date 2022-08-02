package protocol;


public class VictoryMessage{

	private String joueurGagnant;
	
	public VictoryMessage(String joueurGagnant) {
		this.joueurGagnant = joueurGagnant;
	}
	
	public VictoryMessage() {
		
	}
	
	public String getGagnant() {
		return joueurGagnant;
	}
	
	public void setGagnant(String name) {
		this.joueurGagnant = name;
	}
	
}
