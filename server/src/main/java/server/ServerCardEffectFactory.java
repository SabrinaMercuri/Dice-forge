package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.De;
import core.Face;
import core.Joueur;
import core.Ressource;
import core.cards.Card;
import core.cards.CardEffectFactory;
import protocol.AskForgingMessage;
import protocol.ChestMessage;
import protocol.ChooseFaceMessage;
import protocol.FaceMessage;
import protocol.MessageType;
import protocol.RollMessage;
import protocol.SatyrsMessage;
import protocol.SilverHindMessage;
import protocol.SphinxMessage;
import protocol.UpdateDeMessage;
import protocol.UpdateRessourceMessage;
import protocol.HelmetMessage;

public class ServerCardEffectFactory extends CardEffectFactory {

	private ServerListener serverListener;
	private Server server;

	public ServerCardEffectFactory(ServerListener serverListener) {
		this.serverListener = serverListener;
		this.server = serverListener.getServer();
	}

	@Override
	public void blacksmithChestEffect(String namePlayer) {
		ObjectMapper om = new ObjectMapper();
		String chestMessage;
		try {
			chestMessage = om.writeValueAsString(new ChestMessage(namePlayer));
			server.getSocket().getBroadcastOperations().sendEvent(MessageType.chest.name(), chestMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void blacksmithHammerEffect(String namePlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void abyssalMirrorEffect(String namePlayer) {
		ObjectMapper om = new ObjectMapper();


		// Création du message AskForging pour forger un portal sur un dé

		ArrayList<De> listeDe = null;
		Face portal = new Face(Ressource.portal, false, 0);

		// Envoie aux joueurs
		server.getSocket().getBroadcastOperations().sendEvent(MessageType.askForge.name(),
				new AskForgingMessage(listeDe, portal, namePlayer));

	}

	@Override
	public void cancerEffect(String namePlayer) {
		//init de l'arraylist
		HashMap<String, ArrayList<Face>> roll = new HashMap<String, ArrayList<Face>>();
		roll.put(namePlayer, new ArrayList<Face>());
		ClientManager acheteur = server.getPlayerByName(namePlayer);
		ObjectMapper om = new ObjectMapper();
		String rollResultString;
		//2 roll de dieu
		for (int i = 0; i < 2; i++) {
			for(Face f : acheteur.lancerDeDieu())
				roll.get(namePlayer).add(f);
		}
		try {
			rollResultString = om.writeValueAsString(new RollMessage(roll));
			acheteur.getSocket().sendEvent(MessageType.cancer.name(), rollResultString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void wildSpiritEffect(String namePlayer) {
		HashMap<Ressource, Integer> ressources = new HashMap<Ressource, Integer>();
		ressources.put(Ressource.gold, 3);
		ressources.put(Ressource.lunaryStone, 3);
		UpdateRessourceMessage urm = new UpdateRessourceMessage(namePlayer, ressources);
		ObjectMapper om = new ObjectMapper();
		try {
			server.getSocket().getBroadcastOperations().sendEvent(MessageType.updateRessource.name(),
					om.writeValueAsString(urm));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sphinxEffect(String namePlayer) {
		server.getSocket().getBroadcastOperations().sendEvent(MessageType.sphinx.name(), new SphinxMessage(namePlayer));
	}

	
	
	@Override
	public void elderEffect(String namePlayer) {
		// Retire 3 gold et fait gagner 4 victory point au joueurs
		ClientManager player = server.getPlayerByName(namePlayer);

		// Créer la hashmap de ressources à update
		HashMap<Ressource, Integer> ressources = new HashMap<Ressource, Integer>();
		ressources.put(Ressource.gold, -3);
		ressources.put(Ressource.victoryPoint, 4);

		// Update les ressources du joueur
		player.getInventaire().updateRessource(ressources);

		// Envoie l'update en broadcast aux joueurs
		UpdateRessourceMessage urm = new UpdateRessourceMessage(namePlayer, ressources);
		ObjectMapper om = new ObjectMapper();
		try {
			server.getSocket().getBroadcastOperations().sendEvent(MessageType.updateRessource.name(),
					om.writeValueAsString(urm));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void guardianOwlsEffect(String namePlayer) {
		// Le joueur doit choisir entre 3 faces, 1 lunary 1 solary 1 gold
		ArrayList<Face> faces = new ArrayList<Face>();
		faces.add(new Face(Ressource.gold, false, 1));
		faces.add(new Face(Ressource.lunaryStone, false, 1));
		faces.add(new Face(Ressource.solaryStone, false, 1));

		ChooseFaceMessage cfm = new ChooseFaceMessage(namePlayer, faces);

		ClientManager cm = server.getPlayerByName(namePlayer);
		ObjectMapper om = new ObjectMapper();
		try {
			cm.getSocket().sendEvent(MessageType.guardianowls.name(), om.writeValueAsString(cfm));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void invisibilityHelmetEffect(String namePlayer) {
		HashMap<Ressource, Integer> res = new HashMap<Ressource, Integer>();
		res.put(Ressource.multiplyThree, 1);
		Face mThree = new Face(res, false);
		ObjectMapper om = new ObjectMapper();
		String helmetMessage;
		try {
			helmetMessage = om.writeValueAsString(new HelmetMessage(namePlayer, mThree));
			server.getPlayerByName(namePlayer).getSocket().sendEvent(MessageType.helmet.name(), helmetMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void minotaurEffect(String namePlayer) {
		ObjectMapper om = new ObjectMapper();

		//Roll des dés et on enleve le joueur qui a acheté la carte (on va pas lui retirer des points)
		HashMap<String, ArrayList<Face>> results = server.getGameManager().rolls();
		results.remove(namePlayer);
		
		server.getServerListener().initMinotaurBuffer(namePlayer);
		try {
			//Broadcast des machins
			String data = om.writeValueAsString(new RollMessage(results));
			server.getSocket().getBroadcastOperations().sendEvent(MessageType.minotaur.name(), data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void satyrsEffect(String namePlayer) {
		/// arrayList de faces qui va contenir les rolls des joueurs
		ArrayList<Face> rolls = new ArrayList<Face>();
		ObjectMapper om = new ObjectMapper();
		String otherRolls;
		/// boucle sur les autres joueurs pour récupérer leurs dés
		for (Joueur player : server.getGameManager().getPlateau().getPlayers()) {
			if (!player.getNom().equals(namePlayer)) {
				for(Face f : player.lancerDeDieu()) {
					//Filtre les portails
					if(!f.getRessourceGranted().containsKey(Ressource.portal))
						rolls.add(f);
				}
			}
		}
		/// envoi d'un message au joueur contenant le nom du joueur qui va choisir parmi
		/// les faces et l'arraylist de face
		try {
			otherRolls = om.writeValueAsString(new SatyrsMessage(namePlayer, rolls));
			server.getPlayerByName(namePlayer).getSocket().sendEvent(MessageType.satyrs.name(), otherRolls);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void silverHindEffect(String namePlayer) {
		ClientManager cm = server.getPlayerByName(namePlayer);
		cm.getSocket().sendEvent(MessageType.silverhind.name(), new SphinxMessage(namePlayer));
	}
	
}
