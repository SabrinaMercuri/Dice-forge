package protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.De;
import core.Face;
import core.Ressource;

public class FaceMessage {

	private String namePlayer;
	private ArrayList<Face> faces;
	private HashMap<Ressource, Integer> cost;
	private ArrayList<De> updatedDe;
	
	public FaceMessage() {
		
	}
	
	/**
	 * Message send to client or server to notice a face bought by a player, information provided are in the parma
	 * @param namePlayer Name of the player who bought the face
	 * @param faces Face bought by the player
	 * @param cost How many the player bought the face for
	 * @param updatedDe Update de of the player with the new face
	 */
	public FaceMessage(String namePlayer, ArrayList<Face> faces, HashMap<Ressource, Integer> cost, ArrayList<De> updatedDe) {
		this.namePlayer = namePlayer;
		this.faces = faces;
		this.cost = cost;
		this.updatedDe = updatedDe;
	}

	public String getNamePlayer() {
		return namePlayer;
	}
	
	public HashMap<Ressource, Integer> getCost(){
		return cost;
	}
	
	public List<Face> getFaces() {
		return faces;
	}

	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}
	
	public void setFace(List<Face> faces) {
		this.faces = new ArrayList<Face>(faces);
	}
	
	public void setCost(Map<Ressource, Integer> cost) {
		this.cost = new HashMap<Ressource, Integer>(cost);
	}
	
	public ArrayList<De> getUpdatedDe() {
		return this.updatedDe;
	}
	
	public void setUpdatedDe(List<De> updatedDe) {
		this.updatedDe = new ArrayList<De>(updatedDe);
	}

}
