package client;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import core.Face;
import core.Joueur;
import core.Plateau;
import protocol.MessageType;
import protocol.NameMessage;
import protocol.RollMessage;

import io.socket.client.IO;
import io.socket.client.Socket;

public class Client {

	private Socket socket;
	private Plateau plateau;
	private ClientListener listener;
	private Action action;

	public Client(String nom) throws JSONException {
		action = new RandomBot(this);
		initPlateau(nom);
		try {
			socket = IO.socket("http://127.0.0.1:12777");
			socket.connect();
			startClient();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connect to http://127.0.0.1:specified port
	 * 
	 * @param nom  nom du client (nous)
	 * @param port to connect
	 * @throws JSONException quand on démarre le client dans le cas ou il arrive pas
	 *                       à parser ou deparser un message
	 */
	public Client(String nom, int port) throws JSONException {
		action = new RandomBot(this);
		initPlateau(nom);
		try {
			socket = IO.socket("http://127.0.0.1:" + port);
			socket.connect();
			startClient();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connect to the specified host and port
dans hel	 * 
	 * @param nom  nom du joueur[0] (nous)
	 * @param host ip adress
	 * @param port number
	 * @throws JSONException jsonexception quand on démarre le client dans le cas ou
	 *                       il arrive pas à parser ou déparse du json
	 */
	public Client(String nom, String host, int port) throws JSONException {
		action = new RandomBot(this);
		initPlateau(nom);
		try {
			socket = IO.socket("http://" + host + ":" + port);
			socket.connect();
			startClient();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public Client(String nom, String uri) throws JSONException {
		action = new RandomBot(this);
		initPlateau(nom);
		try {
			socket = IO.socket(uri);
			socket.connect();
			startClient();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void startClient() throws JSONException {
		// Réceptions des messages côté client
		// Message de type Name
		listener = new ClientListener(this, action);
		listener.init();
	}

	/**
	 * Initialise le plateau et ajoute le joueur dans le plateau en tant que
	 * joueur[0]
	 * 
	 * @param nom nom du joueur[0] (le client)
	 */
	public void initPlateau(String nom) {
		plateau = new Plateau();
		plateau.getPlayers().add(new Joueur(nom));
	}

	/**
	 * @return le joueur répresentant le client
	 */
	public Joueur getMyself() {
		return plateau.getPlayers().get(0);
	}

	public void nomClientMessage() {
		NameMessage nameMessage = new NameMessage(getMyself().getNom(), false);
		getSocket().emit(MessageType.name.name(), new JSONObject(nameMessage));
	}

	/**
	 * Attribue les ressources tirées par le serveur pour le joueur au joueur
	 * concerné
	 * 
	 * @param rollMessage rollMessage reçu par le client envoyé par le serveur
	 */
	public void attributionRessourceJoueur(RollMessage rollMessage) {
		HashMap<String, ArrayList<Face>> ressources = rollMessage.getRollResults();
		
		for(String name : ressources.keySet()) {
			getPlateau().getPlayerByName(name).ajouteRessource(ressources.get(name));
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public ClientListener getListener() {
		return listener;
	}

	public void setListener(ClientListener listener) {
		this.listener = listener;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	public Action getAction() {
		return action;
	}

	public static void main(String args[]) {
		if (args.length == 0) {
			System.err.println("Not enough args");
			System.exit(1);
		} else if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")
				|| args[0].equalsIgnoreCase("?"))) {
			System.err.println(
					"Args syntax are \njava -jar Client.jar playerName\nOr java -jar Client.jar playerName serverPort\nOr java -jar Client.jar playerName hostServer portServer");
			System.exit(1);
			;
		}

		Client client;
		try {
			if (args.length == 1) {
				// connect 127.0.0.1 12777 with specified name
				client = new Client(args[0]);
			} else if (args.length == 2) {
				// connect 127.0.0.1 specified port with specified name
				client = new Client(args[0], Integer.parseInt(args[1]));
			} else {
				// connect specified host specified port with specified name
				client = new Client(args[0], args[1], Integer.parseInt(args[2]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
