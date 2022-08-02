package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Face;
import core.ForgeCategory;
import core.Ressource;
import core.cards.Card;
import protocol.CarteMessage;
import protocol.FaceMessage;
import protocol.HuntMessage;
import protocol.MessageType;
/**
 * 
 * Classe qui représente le Bot random qui fera des actions aléatoires
 *
 */
public class RandomBot implements Action {

	private Client client;
	private Random rdm;

	/**
	 * Constructeur du bot
	 * @param client, le client auquel le bot est relier
	 */
	public RandomBot(Client client) {
		this.client = client;
		rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	/* 
	 * Méthode permettant de savoir si le bot va faire une autre action ou non
	 * @return si le bot peux faire une autre action
	 */
	@Override
	public boolean newAction() {
		if (client.getMyself().getInventaire().getSolaryStone() >= 2) {
			if (rdm.nextBoolean())
				return true;
			else
				return false;
		} else
			return false;
	}

	/* 
	 * Methode qui dis si le bot veut exploiter ou non
	 * @return l'action du bot s'il peux acheter une carte ou non
	 */
	@Override
	public boolean isExploiting() {
		if (client.getMyself().getInventaire().getLunaryStone() > 0
				|| client.getMyself().getInventaire().getSolaryStone() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/* 
	 * Methode qui dis si le bot veut forger ou non
	 * @return l'action du bot s'il peux acheter une face ou non
	 */
	@Override
	public boolean isForging() {
		if (client.getMyself().getInventaire().getGold() > 2) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Methode d'exploi du client
	 * @return un faceMessage en fonction des différente action du bot
	 */
	@Override
	public FaceMessage forge() {
		Random rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());
		boolean isBuying = true;
		FaceMessage faceMessage = null;

		while (isBuying) {
			int nbCat = rdm.nextInt(client.getMyself().getInventaire().getGold());
			// System.out.println("NB GOLD DE " + client.getMyself().getNom() + " AVANT MAJ
			// : "
			// + client.getMyself().getInventaire().getGold());

			// Random pour savoir quelle categorie prendre en fonction du nombre de gold du
			// joueur
			// Variable pour éviter le bloquage
			int rollTry = 0;
			
			//TODO @Mozorate nettoie moi ça
			while (client.getPlateau().getForge().getCategoryByCost(nbCat) == null
					|| client.getPlateau().getForge().getCategoryByCost(nbCat).getFaces().isEmpty()) {
				
				nbCat = rdm.nextInt(client.getMyself().getInventaire().getGold());
				rollTry++;
				if (rollTry > 100) {
					if (faceMessage == null)
						return null;
					else
						return faceMessage;
				}
			}

			// Choix de la categorie ainsi que set du random sur le nb de faces dans la cat
			ForgeCategory forge = client.getPlateau().getForge().getCategoryByCost(nbCat);

			// Random pour le choix de la face
			if (forge == null || forge.getFaces().size() <= 0)
				break;

			int nbFace = rdm.nextInt(forge.getFaces().size());
			Face faceAchete = forge.buyFace(nbFace);

			// enlever le montant de gold
			client.getMyself().getInventaire().retirerGold(nbCat);
			// System.out.println("NB GOLD DE " + client.getMyself().getNom() + " APRES MAJ
			// : "
			// + client.getMyself().getInventaire().getGold());

			// test echange de faces AVANT le changement
			String de1 = "" + client.getMyself().getNom() + " ";
			String de2 = "" + client.getMyself().getNom() + " ";
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 5; j++) {
					if (i == 0) {
						de1 += client.getMyself().getInventaire().getListDeJoueur().get(i).getFaces()[j].toString()
								+ " ";
					}
					if (i == 1) {
						de2 += client.getMyself().getInventaire().getListDeJoueur().get(i).getFaces()[j].toString()
								+ " ";
					}
				}
			}

			// System.out.println("AVANT MAJ DE 1 : " + de1);
			// System.out.println("AVANT MAJ DE 2 : " + de2);

			// Echange des faces
			client.getMyself().getInventaire().getListDeJoueur().get(rdm.nextInt(1)).changeFace(rdm.nextInt(5),
					faceAchete);

			// test echange de faces AVANT le changement
			de1 = "" + client.getMyself().getNom() + " ";
			de2 = "" + client.getMyself().getNom() + " ";
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 5; j++) {
					if (i == 0) {
						de1 += client.getMyself().getInventaire().getListDeJoueur().get(i).getFaces()[j].toString()
								+ " ";
					}
					if (i == 1) {
						de2 += client.getMyself().getInventaire().getListDeJoueur().get(i).getFaces()[j].toString()
								+ " ";
					}
				}
			}

			// System.out.println("APRES MAJ DE 1 : " + de1);
			// System.out.println("APRES MAJ DE 2 : " + de2);

			if (faceMessage == null) {
				// Premiere face achetee, on créer le message à partir de rien
				ArrayList<Face> faceBuffer = new ArrayList<Face>();
				faceBuffer.add(faceAchete);
				faceMessage = new FaceMessage(client.getMyself().getNom(), faceBuffer, forge.getCost(),
						client.getMyself().getInventaire().getListDeJoueur());
			} else {
				// Pas la premiere achetee, on créer le message avec le message déjà existant
				// Ajout de la face aux faces déjà achetees
				ArrayList<Face> faceBuffer = new ArrayList<Face>(faceMessage.getFaces());
				faceBuffer.add(faceAchete);
				// Ajout du cout
				HashMap<Ressource, Integer> costBuffer = faceMessage.getCost();
				int goldRequired = costBuffer.get(Ressource.gold);
				goldRequired += forge.getCost(Ressource.gold);
				costBuffer = new HashMap<Ressource, Integer>();
				costBuffer.put(Ressource.gold, goldRequired);
				// Reconstruction du message
				faceMessage = new FaceMessage(client.getMyself().getNom(), faceBuffer, costBuffer,
						client.getMyself().getInventaire().getListDeJoueur());
			}

			// Evaluation si le bot veut continuer d'acheter ou pas
			isBuying = false;
			if (client.getMyself().getInventaire().getGold() >= 2)
				isBuying = rdm.nextBoolean();
		}
		return faceMessage;
	}

	/**
	 * Methode qui vérifie si le cout en solary de la face est null ou non
	 * @param ile, index de l'ile 
	 * @param nbStack, index du StackCard dans l'ile
	 * @return si la cart du StackCard est payable en lunary ou non
	 */
	public boolean checkIsNullLunary(int ile, int nbStack) {
		return client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard().getCost().get(Ressource.lunaryStone) == null;
	}

	/**
	 * Methode qui vérifie si le cout en lunary de la face est null ou non
	 * @param ile, index de l'ile
	 * @param nbStack, index du stackCard dans l'ile
	 * @return si la carte du StackCard est payable en solary ou non
	 */
	public boolean checkIsNullSolary(int ile, int nbStack) {
		return client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard().getCost().get(Ressource.solaryStone) == null;
	}
	
	/**
	 * Methode qui renvoit le cout en lunary de la carte
	 * @param ile, index de l'ile
	 * @param nbStack, index du stackCard dans l'ile
	 * @return le cout en lunary de la carte
	 */
	public int getCostLunary(int ile, int nbStack) {
		return client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard().getCost().get(Ressource.lunaryStone);
	}
	
	/**
	 * Methode qui renvoit le cout en solary de la carte
	 * @param ile, index de l'ile
	 * @param nbStack, index du stackCard dans l'ile
	 * @return le cout en solary de la carte
	 */	
	public int getCostSolary(int ile, int nbStack) {
		return client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard().getCost().get(Ressource.solaryStone);
	}
	
	/* 
	 * Methode qui renvoit un carteMassage en fonction des action du bot
	 * @return un cartemessage en fonction des action du bot
	 */
	@Override
	public CarteMessage exploit() {
		Random rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());
		int ile = rdm.nextInt(client.getPlateau().getIsles().size());
		int nbStack = rdm.nextInt(client.getPlateau().getIsles().get(ile).getListeStackCard().size());

		//TODO @Mozorate arrange moi ta merde
		while ((checkIsNullLunary(ile, nbStack)	|| getCostLunary(ile, nbStack) > client.getMyself().getInventaire().getLunaryStone())
				&& (checkIsNullSolary(ile, nbStack)	|| getCostSolary(ile, nbStack) > client.getMyself().getInventaire().getSolaryStone())) {
			
			ile = rdm.nextInt(client.getPlateau().getIsles().size());
			nbStack = rdm.nextInt(client.getPlateau().getIsles().get(ile).getListeStackCard().size());
		}
		

		int lunaryCost = 0;
		int solaryCost = 0;
		
		// check si il y a que des solary stone en cout
		if (checkIsNullLunary(ile,nbStack) && !checkIsNullSolary(ile, nbStack)) {
			solaryCost = getCostSolary(ile, nbStack);
		}
		// checkk si il y a que des lunary stone en cout
		else if (!checkIsNullLunary(ile,nbStack) && checkIsNullSolary(ile, nbStack) ) {
			lunaryCost = getCostLunary(ile, nbStack);
		}
		// check si il y a des solary et des lunary en cout
		else if (!checkIsNullLunary(ile,nbStack) && !checkIsNullSolary(ile, nbStack)) {
			solaryCost = getCostSolary(ile, nbStack);
			lunaryCost = getCostLunary(ile,nbStack);
		}

		Card carte = client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard();

		// Place la carte dans l'inventaire du joueur
		client.getMyself().getInventaire().getListedecartes().add(carte);

		// Enlever la carte de la pile et placer le joueur sur l'ile
		String nomJoueurChasse = client.getPlateau().getIsles().get(ile).getJoueur();
		client.getPlateau().getIsles().get(ile).prendreCarte(
				client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard().getNom(),
				client.getMyself());
		client.getPlateau().getIsles().get(ile).positionnerJoueur(client.getMyself());

		if (nomJoueurChasse != client.getMyself().getNom() && !nomJoueurChasse.equals("")) {
			HuntMessage hunt = new HuntMessage(nomJoueurChasse, ile);
			ObjectMapper map = new ObjectMapper();
			try {
				String envoisChasse = map.writeValueAsString(hunt);
				client.getSocket().emit(MessageType.hunt.name(), envoisChasse);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

		}

		// Enleve le cout de la carte dans l'inventaire
		client.getMyself().getInventaire().retirerLunary(lunaryCost);
		client.getMyself().getInventaire().retirerSolary(solaryCost);

		// Creation carteMessage pour l'envoi
		CarteMessage carteMessage = new CarteMessage(client.getMyself().getNom(), carte, lunaryCost, solaryCost, ile);
		ObjectMapper mapping = new ObjectMapper();
		return carteMessage;
	}

	/* 
	 * Methode qui renvoit si le joueur active les effet des cartes durant son tour 
	 * @return si le joueur active les carte ou non
	 */
	public boolean activateCard(String nameCard) {
		Random rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());

		switch (nameCard) {
		case Card.ELDER:
			// Check si les conditions requises pour activer la carte sont atteintes
			if (client.getMyself().getInventaire().getGold() >= 3)
				return rdm.nextBoolean();
			else
				return false;
		case Card.GUARDIANOWLS:
			// Check si les conditions requises pour activer la carte sont atteintes
			return rdm.nextBoolean();
		default:
			//System.err.println("La carte n'a pas été trouvé ou n'est pas activable [" + nameCard + "]");
			return false;
		}
	}

	/* 
	 * Methode qui renvoit dans  une arraylist de carte la face qui convient le mieux au joueur
	 * @return la face qui convient le mieux au joueur
	 */
	public Face chooseFace(ArrayList<Face> facesAvailable) {
		if(facesAvailable==null)
			return null;
		
		Random rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());

		int index = rdm.nextInt(facesAvailable.size() - 1);
		
		if(facesAvailable.size()==0)
			return null;
		
		return facesAvailable.get(index);
	}
	
	/*
	 * Methode qui renvoit l'index du de choisi
	 * @return l'index du dé choisi
	 */
	public int chooseDe() {
		return rdm.nextInt(2);
	}
	
	/*
	 * Methode qui renvoit dans  une arraylist de carte la face qui convient le mieux au joueur
	 * @return la face qui convient le mieux au joueur
	 */
	@Override
	public int mirror(ArrayList<Face> facesAvailable) {
		Random rdm = new Random();
		rdm.setSeed(System.currentTimeMillis());
		return rdm.nextInt(facesAvailable.size()-1);
	}

}
