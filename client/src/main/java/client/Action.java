package client;

import java.util.ArrayList;

import core.Face;
import protocol.CarteMessage;
import protocol.FaceMessage;

public interface Action {
	public boolean newAction();
	public boolean isExploiting();
	public boolean isForging();
	public FaceMessage forge();
	public CarteMessage exploit();
	public boolean activateCard(String nameCard);
	public Face chooseFace(ArrayList<Face> facesAvailable);
	public int chooseDe();
	public int mirror(ArrayList<Face> facesAvailable);
}
