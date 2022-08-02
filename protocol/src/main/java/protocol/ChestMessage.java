package protocol;

public class ChestMessage {

	private String namePlayer;
	private int updGold;
	private int updLun;
	private int updSol;
	
	public ChestMessage() {
		
	}
	
	/**
	 * Message send to client or server to notice a face bought by a player, information provided are in the parma
	 * @param namePlayer Name of the player who bought the face
	 */
	public ChestMessage(String namePlayer) {
		this.namePlayer = namePlayer;
		this.updGold = 4;
		this.updLun = 3;
		this.updSol = 3;
	}

	public String getNamePlayer() {
		return namePlayer;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}

	public int getUpdGold() {
		return updGold;
	}

	public void setUpdGold(int updGold) {
		this.updGold = updGold;
	}

	public int getUpdLun() {
		return updLun;
	}

	public void setUpdLun(int updLun) {
		this.updLun = updLun;
	}

	public int getUpdSol() {
		return updSol;
	}

	public void setUpdSol(int updSol) {
		this.updSol = updSol;
	}
	
	
}