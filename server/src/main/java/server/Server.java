package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONObject;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.Face;
import protocol.ConnectedMessage;
import protocol.MessageType;
import protocol.NameMessage;

/**
 * 
 * @author tp2dfa Class being used as interface between the hosted game and the
 *         player connected and accepted in the game
 *
 */
public class Server {

	private ArrayList<ClientManager> acceptedConnection;
	private SocketIOServer server;
	private GameManager game;
	private int nbPartieAJouer;
	private ServerListener listener;
	private boolean modeLog;
	private final int MAXPLAYER = 4;

	/**
	 * Create the server with the default server port ServerSocket default port is
	 * 12777
	 * 
	 * @throws Exception caused by new ServerSocket other socket related method
	 */
	public Server() throws Exception {
		acceptedConnection = new ArrayList<ClientManager>();
		this.modeLog = setModeLog();
		game = new GameManager(this);
		Configuration config = new Configuration();
		config.setHostname("127.0.0.1");
		config.setPort(12777);
		server = new SocketIOServer(config);
		
	}

	/**
	 * Create the server with a specified port
	 * 
	 * @param args nombre de partie à réaliser par le serveur
	 * @param port to specify the port which to open to host the game
	 * @throws Exception caused by new ServerSocket and other socket related method
	 */
	public Server(int port, int args) throws Exception {
		acceptedConnection = new ArrayList<ClientManager>();
		this.nbPartieAJouer = args;
		this.modeLog = setModeLog();
		game = new GameManager(this);
		Configuration config = new Configuration();
		config.setHostname("127.0.0.1");
		config.setPort(port);
		server = new SocketIOServer(config);
		
		
	}

	public Server(int port) throws Exception {
		acceptedConnection = new ArrayList<ClientManager>();
		this.modeLog = setModeLog();
		game = new GameManager(this);
		Configuration config = new Configuration();
		config.setHostname("127.0.0.1");
		config.setPort(port);
		server = new SocketIOServer(config);
	}

	public void startServer() {
		server.start();

		listener = new ServerListener(this);
		listener.init();
		server.addConnectListener(listener);
	}

	/**
	 * Check condition if the server is able to accept the connection depending on
	 * client already connected and the state of the game
	 * 
	 * @param client, le socket du client qui veut être accepter dans la partie
	 * @throws IOException caused by ServerSocket.accept() method
	 */
	protected synchronized void acceptConnection(SocketIOClient client) throws IOException {
		if (acceptedConnection.size() < 4 && game.getState() == GameManager.LOBBY) {

			// On envoie au client la liste des joueurs déjà connectés
			sendClientPLayerNameAlreadyPresent(client);

			// On l'ajoute dans la liste des connexions du serveur
			acceptedConnection.add(new ClientManager(this, client));
			client.sendEvent(MessageType.connected.name(), new ConnectedMessage());
			System.out.println("Client accepted");

		} else {
			System.out.println("Client refused, disconnecting the client...");
			client.disconnect();
		}
	}

	/**
	 * Changer le nom du ClientManager par le nom que le socket a communiqué,
	 * l'ajoute dans le plateau et envoie son nom aux autres joueurs
	 * 
	 * @param client socket du client qui a communiqué son nom
	 * @param name   le nom que le socket a communiqué
	 */
	protected synchronized void addName(SocketIOClient client, String name) {
		ClientManager cl = findClientManager(client);
		if (isNameAlreadyTaken(name)) {
			name = name + UUID.randomUUID().toString();
			client.sendEvent(MessageType.name.name(), new NameMessage(name, true));
		}

		// Ajoute le nom du joueur, l'ajoute dans le plateau et indique aux joueurs déjà
		// arriver le nom du nouveau joueur
		cl.setNom(name);
		getGameManager().getPlateau().addPlayer(cl);
		getSocket().getBroadcastOperations().sendEvent(MessageType.name.name(), new NameMessage(name, false));

		launchTheParty();
	}

	/**
	 * Trouve le client manager qui détient cette socket de client
	 * 
	 * @param client la socket qu'on recherche
	 * @return le ClientManager détenant le socket
	 */
	public ClientManager findClientManager(SocketIOClient client) {
		for (int i = 0; i < acceptedConnection.size(); i++) {
			if (client.getSessionId().equals(acceptedConnection.get(i).getSocket().getSessionId())) {
				return acceptedConnection.get(i);
			}

		}

		// Car si il est pas dans la liste on en veut po
		client.disconnect();
		return null;

	}

	/**
	 * Send to the client the name of the player already connected and who told
	 * their name to the server
	 * 
	 * @param client to send the names
	 */
	protected void sendClientPLayerNameAlreadyPresent(SocketIOClient client) {
		ObjectMapper om = new ObjectMapper();
		for (ClientManager cm : getAcceptedConnection()) {
			if (cm.getNom() != null) {
				NameMessage nm = new NameMessage(cm.getNom(), false);
				try {
					client.sendEvent(MessageType.name.name(), om.writeValueAsString(nm));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Send a notification to the player that the game is starting and initalize the
	 * game if all player have a name (means they are initliazed)
	 */
	protected void launchTheParty() {
		if (allHaveName() && game.getState() == GameManager.LOBBY && getAcceptedConnection().size() == 4) {
			System.out.println("Tous les joueurs sont prêt...\nLancement de la partie");
			server.getBroadcastOperations().sendEvent(MessageType.start.name(), new JSONObject());

			game.newGame();
		}
	}

	public SocketIOServer getSocket() {
		return server;
	}

	public ArrayList<ClientManager> getAcceptedConnection() {
		return acceptedConnection;
	}

	public void stop() {
		server.stop();
	}

	public boolean isNameAlreadyTaken(String nom) {
		for (int i = 0; i < getAcceptedConnection().size(); i++) {

			if (acceptedConnection.get(i) != null) {
				if (acceptedConnection.get(i) != null) {
					if (nom.equals(acceptedConnection.get(i).getNom())) {

						return true;
					}
//					else
//						System.out.println(nom+" est différent de "+acceptedConnection.get(i).getJoueur().getNom());
				}
			}
		}
		return false;
	}

	public boolean allHaveName() {
		for (ClientManager cm : getAcceptedConnection()) {
			if (cm.getNom() == null)
				return false;
		}
		return true;
	}

	public ClientManager getPlayerByName(String name) {
		for (int i = 0; i < acceptedConnection.size(); i++) {
			if (acceptedConnection.get(i) != null) {
				if (acceptedConnection.get(i) != null) {
					if (acceptedConnection.get(i).getNom().equals(name)) {
						return acceptedConnection.get(i);
					}
				}
			}
		}
		return null;
	}

	public GameManager getGameManager() {
		return game;
	}

	public ServerListener getServerListener() {
		return listener;
	}

	public int getNbPartieAJouer() {
		return nbPartieAJouer;
	}

	public void setNbPartieAJouer(int nbPartieAJouer) {
		this.nbPartieAJouer = nbPartieAJouer;
	}
	
	public boolean setModeLog() {
		if (getNbPartieAJouer() == 1) {
			return true;
		}
		return false;
	}
	
	public boolean isModeLog() {
		return modeLog;
	}

	public static void main(String args[]) {
		Server server;
		if (args.length == 0) {
			try {
				server = new Server(12777);
				server.startServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (args.length == 1) {
			try {
				server = new Server(Integer.parseInt(args[0]));
				server.startServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Not enough args");
		}
	}
}
