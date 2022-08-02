package protocol;

import java.util.ArrayList;
import java.util.List;

import core.De;
import core.Face;
import core.Ressource;

public class ChooseRessourceMessage {

	private ArrayList<Face> ressourceAvailable;
	private Face faceSelected;

	public ChooseRessourceMessage() {
	}

	public ChooseRessourceMessage(ArrayList<Face> ressourceAvailable, Face faceSelected) {
		this.ressourceAvailable = ressourceAvailable;
		this.faceSelected = faceSelected;
	}
	
	public ChooseRessourceMessage(ArrayList<Face> ressourceAvailable) {
		this.ressourceAvailable = ressourceAvailable;
		this.faceSelected = null;
	}
	
	public ChooseRessourceMessage(ChooseRessourceMessage crm, Face faceSelected) {
		this.ressourceAvailable = new ArrayList<Face>(crm.getRessourceAvailable());
		this.faceSelected = faceSelected;
	}

	public void setFaceSelected(Face faceSelected) {
		this.faceSelected = faceSelected;
	}

	public Face getFaceSelected() {
		return faceSelected;
	}

	public List<Face> getRessourceAvailable() {
		return ressourceAvailable;
	}

	public void setRessourceAvailable(List<Face> ressourceAvailable) {
		this.ressourceAvailable = new ArrayList<Face>(ressourceAvailable);
	}
}
