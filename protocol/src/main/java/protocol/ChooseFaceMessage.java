package protocol;

import java.util.ArrayList;
import java.util.List;

import core.Face;

public class ChooseFaceMessage {

	private ArrayList<Face> facesAvailable;
	private Face faceSelected;
	private String nom;
	
	public ChooseFaceMessage() {
		
	}
	
	public ChooseFaceMessage(String nom, ArrayList<Face> facesAvailable) {
		this.nom = nom;
		this.facesAvailable = facesAvailable;
		faceSelected = null;
	}
	
	public ChooseFaceMessage(ChooseFaceMessage cfm, Face faceSelected) {
		this.nom = cfm.getNom();
		this.facesAvailable = new ArrayList<Face>(cfm.getFacesAvailable());
		this.faceSelected = faceSelected;
	}
	
	public ChooseFaceMessage(String nom, ArrayList<Face> facesAvailable, Face faceSelected) {
		this.nom = nom;
		this.facesAvailable = facesAvailable;
		this.faceSelected = faceSelected;
	}
	
	public void setFacesAvailable(List<Face> facesAvailable) {
		this.facesAvailable = new ArrayList<Face>(facesAvailable);
	}
	
	public List<Face> getFacesAvailable() {
		return facesAvailable;
	}
	
	public void setFaceSelected(Face faceSelected) {
		this.faceSelected = faceSelected;
	}
	
	public Face getFaceSelected() {
		return faceSelected;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
}
