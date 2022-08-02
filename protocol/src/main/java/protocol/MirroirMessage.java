package protocol;

import java.util.ArrayList;

import core.Face;

public class MirroirMessage {


	private ArrayList<Face> listFace;
	private Face faceSelected ;



	public MirroirMessage() {}


	public MirroirMessage(ArrayList<Face> listFace, Face face) {
		super();
		this.listFace = listFace;
		this.faceSelected= face;
	}

	public ArrayList<Face> getListFace() {
		return listFace;
	}

	public void setListFace(ArrayList<Face> listFace) {
		this.listFace = listFace;
	}


	public Face getFaceSelected() {
		return faceSelected;
	}


	public void setFaceSelected(Face face) {
		this.faceSelected = face;
	}




}