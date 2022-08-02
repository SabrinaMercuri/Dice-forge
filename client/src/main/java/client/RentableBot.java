package client;

import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.De;
import core.Face;
import core.ForgeCategory;
import core.Isle;
import core.Ressource;
import core.StackCard;
import core.cards.Card;
import protocol.CarteMessage;
import protocol.FaceMessage;
import protocol.HuntMessage;
import protocol.MessageType;

/**
 * 
 * Classe qui représente le Bot rentable qui fera des action qui lui rapport le
 * plus de ressource a chaque fois
 *
 */
public class RentableBot implements Action {

	private Client client;

	/**
	 * Constructeur du bot
	 * 
	 * @param client, le client auquel le bot est relier
	 */
	public RentableBot(Client client) {
		this.setClient(client);
	}

	/*
	 * Méthode permettant de savoir si le bot va faire une autre action ou non
	 * 
	 * @return si le bot peux faire une autre action
	 */
	@Override
	public boolean newAction() {
		if (checkEnoughtSolary()) {
			return true;
		}
		return false;
	}

	/**
	 * Methode qui vérifie que le joueur a assez de solary dans son inventaire
	 * 
	 * @return vrai ou faux selon les solaryStone du joueur
	 */
	private boolean checkEnoughtSolary() {
		return client.getMyself().getInventaire().getLimitSolary()
				- client.getMyself().getInventaire().getSolaryStone() <= 2;
	}

	/*
	 * Methode qui dis si le bot veut exploiter ou non
	 * 
	 * @return l'action du bot s'il peux acheter une carte ou non
	 */
	@Override
	public boolean isExploiting() {
		if (client.getMyself().getInventaire().getLunaryStone() > 3
				|| client.getMyself().getInventaire().getSolaryStone() > 3) {
			return true;
		}
		return false;
	}

	/*
	 * Methode qui dis si le bot veut forger ou non
	 * 
	 * @return l'action du bot s'il peux acheter une face ou non
	 */
	@Override
	public boolean isForging() {
		if (client.getMyself().getInventaire().getGold() >= 4) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Methode d'exploi du client
	 * 
	 * @return un faceMessage en fonction des différente action du bot
	 */
	@Override
	public FaceMessage forge() {
		boolean isBuying = true;
		FaceMessage faceMessage = null;

		while (isBuying) {
			int nbCat = trouveCat(client.getMyself().getInventaire().getGold());
			// System.out.println(nbCat);
			if (nbCat == -1) {
				return faceMessage;
			}

			/*
			 * System.out.println("NB GOLD DE " + client.getMyself().getNom() +
			 * " AVANT MAJ : " + client.getMyself().getInventaire().getGold());
			 */

			// Choix de la categorie ainsi que set du random sur le nb de faces dans la cat
			ForgeCategory forge = client.getPlateau().getForge().getCategoryByCost(nbCat + 1);

			int nbFace = findFace(forge);
			Face faceAchete = forge.buyFace(nbFace);

			// enlever le montant de gold
			client.getMyself().getInventaire().retirerGold(nbCat);
			/*
			 * System.out.println("NB GOLD DE " + client.getMyself().getNom() +
			 * " APRES MAJ : " + client.getMyself().getInventaire().getGold());
			 */

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

			/*
			 * System.out.println("AVANT MAJ DE 1 : " + de1);
			 * System.out.println("AVANT MAJ DE 2 : " + de2);
			 */

			// Echange des faces
			int indexDe = chooseDe();
			client.getMyself().getInventaire().getListDeJoueur().get(indexDe).changeFace(getFace(indexDe), faceAchete);

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

			/*
			 * System.out.println("APRES MAJ DE 1 : " + de1);
			 * System.out.println("APRES MAJ DE 2 : " + de2);
			 */

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
		}
		return faceMessage;
	}

	/**
	 * Methode qui renvoit la catégarie de forge ou le joueur va acheter sa face
	 * 
	 * @param nbGold, nombre de gold du joueur
	 * @return la catégorie de forge
	 */
	public int trouveCat(int nbGold) {
		int taille = client.getPlateau().getForge().getForgeCategories().size();
		int index = nbGold;
		for (int i = taille; i >= 0; i--) {
			if (!checkCategoryNull(index) && !checkEmpty(index)) {
				return index - 1;
			}
			index--;
		}
		return -1;
	}

	/**
	 * Methode qui vérifie que la catégorie choisi n'est pas vide de face
	 * 
	 * @param nbCat, l'index de la catégorie
	 * @return si la catégorie de forge est vide ou pas
	 */
	private boolean checkEmpty(int nbCat) {
		if (!checkCategoryNull(nbCat)) {
			if (client.getPlateau().getForge().getCategoryByCost(nbCat).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Methode qui vérifie si la catégorie existe
	 * 
	 * @param nbCat, l'index de la catégorie
	 * @return si la catégorie de forge existe ou pas
	 */
	public boolean checkCategoryNull(int nbCat) {
		if (client.getPlateau().getForge().getCategoryByCost(nbCat) == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Methode qui revoit l'index de la face que le joueur achete
	 * 
	 * @param f, la forgecatégorie ou la face va etre acheter
	 * @return la face choisie dans la forge categorie
	 */
	public int findFace(ForgeCategory f) {
		if (f.getFaces().size() == 1)
			return 0;
		else {
			return f.getFaces().size() - 1;
		}
	}

	/*
	 * Methode qui renvoit l'index du de choisi
	 * 
	 * @return l'index du dé choisi
	 */
	@Override
	public int chooseDe() {
		int maxVal = 0;
		int index = 0;
		for (De d : client.getMyself().getInventaire().getListDeJoueur()) {
			if (calculMaxVal(d) > maxVal) {
				maxVal = calculMaxVal(d);
				index = client.getMyself().getInventaire().getListDeJoueur().indexOf(d);
			}
		}
		if (index == 0)
			return 1;
		else
			return 0;
	}

	/**
	 * Methode qui renvoit la valeur maximale d'une ressource d'un joueur
	 * 
	 * @param de, De ou l'on veut connaitre la valeur maximale
	 * @return la valeur maximal donné par les faces d'un dé
	 */
	public int calculMaxVal(De de) {
		int val = 0;
		HashMap<Ressource, Integer> ress = new HashMap<Ressource, Integer>();
		for (Face f : de.getFaces()) {
			for (Ressource r : f.getRessourceGranted().keySet()) {
				switch (r) {
				case portal:
					break;
				case multiplyThree:
					break;
				default:
					ress.put(r, f.getNumberOfRessourceGranted(r));
					break;
				}
			}
		}
		for (Ressource r : ress.keySet()) {
			if (val < ress.get(r)) {
				val = ress.get(r);
			}
		}
		return val;
	}

	/**
	 * Methode qui renvoit l'index de la face sélectionné pour etre remplacé
	 * 
	 * @param de, index du dé ou l'on veut changer la face
	 * @return la face qui raporte le moins de ressource
	 */
	public int getFace(int de) {
		int maxVal = 0;
		int index = 0;
		int indexFace = 0;
		De deTo = client.getMyself().getInventaire().getListDeJoueur().get(de);
		for (Face f : deTo.getFaces()) {
			for (Ressource r : f.getRessourceGranted().keySet()) {
				switch (r) {
				case portal:
					index++;
					break;
				case multiplyThree:
					index++;
					break;
				default:
					if (f.getNumberOfRessourceGranted(r) < maxVal) {
						maxVal = f.getNumberOfRessourceGranted(r);
						index = indexFace;
					}
					break;
				}
			}
			indexFace++;
		}
		return index;
	}

	/**
	 * Methode qui vérifie si le cout en solary de la face est null ou non
	 * 
	 * @param ile, index de l'ile
	 * @param nbStack, index du StackCard dans l'ile
	 * @return si la cart du StackCard est payable en lunary ou non
	 */
	public boolean checkIsNullLunary(int ile, int nbStack) {
		return client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard().getCost()
				.get(Ressource.lunaryStone) == null;
	}

	/**
	 * Methode qui vérifie si le cout en lunary de la face est null ou non
	 * 
	 * @param ile, index de l'ile
	 * @param nbStack, index du stackCard dans l'ile
	 * @return si la carte du StackCard est payable en solary ou non
	 */
	public boolean checkIsNullSolary(int ile, int nbStack) {
		return client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard().getCost()
				.get(Ressource.solaryStone) == null;
	}

	/**
	 * Methode qui renvoit le cout en lunary de la carte
	 * 
	 * @param ile, index de l'ile
	 * @param nbStack, index du stackCard dans l'ile
	 * @return le cout en lunary de la carte
	 */
	public int getCostLunary(int ile, int nbStack) {
		return client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard().getCost()
				.get(Ressource.lunaryStone);
	}

	/**
	 * Methode qui renvoit le cout en solary de la carte
	 * 
	 * @param ile, index de l'ile
	 * @param nbStack, index du stackCard dans l'ile
	 * @return le cout en solary de la carte
	 */
	public int getCostSolary(int ile, int nbStack) {
		return client.getPlateau().getIsles().get(ile).getListeStackCard().get(nbStack).getCard().getCost()
				.get(Ressource.solaryStone);
	}

	/*
	 * Methode qui renvoit un carteMassage en fonction des action du bot
	 * 
	 * @return un cartemessage en fonction des action du bot
	 */
	@Override
	public CarteMessage exploit() {
		int ile = getIndexIle();
		int nbStack = getNbStack(ile);

		int lunaryCost = 0;
		int solaryCost = 0;

		// check si il y a que des solary stone en cout
		if (checkIsNullLunary(ile, nbStack) && !checkIsNullSolary(ile, nbStack)) {
			solaryCost = getCostSolary(ile, nbStack);
		}
		// checkk si il y a que des lunary stone en cout
		else if (!checkIsNullLunary(ile, nbStack) && checkIsNullSolary(ile, nbStack)) {
			lunaryCost = getCostLunary(ile, nbStack);
		}
		// check si il y a des solary et des lunary en cout
		else if (!checkIsNullLunary(ile, nbStack) && !checkIsNullSolary(ile, nbStack)) {
			solaryCost = getCostSolary(ile, nbStack);
			lunaryCost = getCostLunary(ile, nbStack);
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
		return carteMessage;
	}

	/**
	 * Mathode qui renvoit l'index du stackCard si le bot veut acheter dedant
	 * 
	 * @param ile, index de l'ile
	 * @return l'index du stacCard
	 */
	private int getNbStack(int ile) {
		int index = 0;
		if (acheterHydre()) {
			return 1;
		}
		for (StackCard sc : client.getPlateau().getIsles().get(ile).getListeStackCard()) {
			if (conditionLunary(sc) || conditionSolary(sc)) {
				return index;
			}
			index++;
		}
		return index;
	}

	/**
	 * Mathode qui renvoit l'index de l'ile si le bot veut acheter dedant
	 * 
	 * @return l'index de l'ile
	 */
	private int getIndexIle() {
		int index = 0;
		if (acheterHydre()) {
			return 3;
		}
		for (Isle ile : client.getPlateau().getIsles()) {
			for (StackCard sc : ile.getListeStackCard()) {
				if (conditionLunary(sc) || conditionSolary(sc)) {
					return index;
				}
			}
			index++;
		}
		return index;
	}

	/**
	 * Methode qui renvoit si le joueur peut acheter la carte hydre ou non
	 * 
	 * @return si le joueur peux acheter l'hydre ou non
	 */
	private boolean acheterHydre() {
		int coupCarteSol = getCostSolary(3, 1);
		int coupCarteLun = getCostSolary(3, 1);
		int solaryClient = client.getMyself().getInventaire().getSolaryStone();
		int lunaryClient = client.getMyself().getInventaire().getLunaryStone();
		if (coupCarteSol <= solaryClient && coupCarteLun <= lunaryClient)
			return true;
		return false;
	}

	/**
	 * Methode qui vérifie si le joueur à assez de lunary pour payer
	 * 
	 * @param sc, stackCard ou l'on veut savoir si on peut acheter
	 * @return si le joueur peux acheter la carte en fonction de ces lunary
	 */
	private boolean conditionLunary(StackCard sc) {
		if (sc.getCard().getCost().get(Ressource.lunaryStone) == null) {
			return false;
		}
		return (client.getMyself().getInventaire().getLunaryStone() - sc.getCard().getCost(Ressource.lunaryStone)) == 0;
	}

	/**
	 * Methode qui vérifie si le joueur à assez de solary pour payer
	 * 
	 * @param sc, stackCard ou l'on veut savoir si on peut acheter
	 * @return si le joueur peux acheter la carte en fonction de ces solary
	 */
	private boolean conditionSolary(StackCard sc) {
		if (sc.getCard().getCost().get(Ressource.solaryStone) == null) {
			return false;
		}
		return (client.getMyself().getInventaire().getSolaryStone() - sc.getCard().getCost(Ressource.solaryStone)) == 0;
	}

	/*
	 * Methode qui renvoit si le joueur active les effet des cartes durant son tour
	 * 
	 * @return si le joueur active les carte ou non
	 */
	@Override
	public boolean activateCard(String nameCard) {
		switch (nameCard) {
		case Card.ELDER:
			// Check si les conditions requises pour activer la carte sont atteintes
			if (client.getMyself().getInventaire().getGold() >= 6)
				return true;
			else
				return false;
		case Card.GUARDIANOWLS:
			return true;
		default:
			//System.err.println("La carte n'a pas été trouvé ou n'est pas activable [" + nameCard + "]");
			return false;
		}
	}

	/*
	 * Methode qui renvoit dans une arraylist de carte la face qui convient le mieux
	 * au joueur
	 * 
	 * @return la face qui convient le mieux au joueur
	 */
	@Override
	public Face chooseFace(ArrayList<Face> facesAvailable) {
		int maxVal = 0;
		int index = 0;
		int indexFace = 0;
		for (Face f : facesAvailable) {
			for (Ressource r : f.getRessourceGranted().keySet()) {
				switch (r) {
				case portal:
					index++;
					break;
				case multiplyThree:
					index++;
					break;
				default:
					if (f.getNumberOfRessourceGranted(r) > maxVal) {
						maxVal = f.getNumberOfRessourceGranted(r);
						index = indexFace;
					}
					break;
				}
			}
			indexFace++;
		}
		return facesAvailable.get(index);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	/*
	 * Methode qui renvoit dans une arraylist de carte la face qui convient le mieux
	 * au joueur
	 * 
	 * @return la face qui convient le mieux au joueur
	 */
	@Override
	public int mirror(ArrayList<Face> facesAvailable) {
		Face f = chooseFace(facesAvailable);
		return facesAvailable.indexOf(f);
	}

}
