package protocol;

import java.util.ArrayList;
import java.util.List;

import core.De;
import core.Face;

public class AskForgingMessage {

	private ArrayList<De> listDeJoueur;
	private Face facePortal ;
	private String nomJoueur;



	public AskForgingMessage() {}


	public AskForgingMessage(ArrayList<De> listFace, Face face, String nomjoueur) {
		super();
		this.nomJoueur = nomjoueur;
		this.listDeJoueur = listFace;
		this.facePortal= face;
	}

	

	public List<De> getListDeJoueur() {
		return listDeJoueur;
	}


	public void setListDeJoueur(List<De> listDeJoueur) {
		this.listDeJoueur = new ArrayList<De>(listDeJoueur);
	}


	public void setFacePortal(Face face) {
		this.facePortal = face;
	}


	public String getNomJoueur() {
		return nomJoueur;
	}


	public void setNomJoueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
	}


	public Face getFacePortal() {
		return facePortal;
	}
	
	
}
