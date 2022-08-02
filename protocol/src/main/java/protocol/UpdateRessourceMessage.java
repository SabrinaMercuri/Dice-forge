package protocol;

import java.util.HashMap;
import java.util.Map;

import core.Ressource;

public class UpdateRessourceMessage {
	
	private String namePlayer;
	private HashMap<Ressource, Integer> ressources;
	
	public UpdateRessourceMessage(String namePlayer, HashMap<Ressource, Integer> ressources) {
		this.namePlayer = namePlayer;
		this.ressources = ressources;
	}
	
	public UpdateRessourceMessage() {
		
	}
	
	public String getNamePlayer() {
		return namePlayer;
	}
	
	public Map<Ressource, Integer> getRessources(){
		return ressources;
	}
	
	public void setNamePlayer(String namePlayer) {
		this.namePlayer = namePlayer;
	}
	
	public void setRessources(Map<Ressource, Integer> ressources) {
		this.ressources = new HashMap<Ressource, Integer>(ressources);
	}
	
}
