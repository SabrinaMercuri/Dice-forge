package server;

import java.io.IOException;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.protocol.Packet;
import core.Joueur;

/**
 * 
 * @author tp2dfa
 * Class used to manage the connection to a client
 *
 */
public class ClientManager extends Joueur{

	private Server server;
	private SocketIOClient socketToClient;
	
	public ClientManager(Server server, SocketIOClient client) throws IOException {
		super(null);
		this.server = server;
		this.socketToClient = client;	
	}
	
	public void setNom(String nom) {
		super.setNom(nom);
	}
	
	public SocketIOClient getSocket() {
		return this.socketToClient;
	}
}