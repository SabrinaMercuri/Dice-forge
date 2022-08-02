package server;

import java.util.ArrayList;
import java.util.HashMap;

import core.Face;
import core.Ressource;

public class Statistique {

	private ArrayList<StatJoueur> joueurs;

	public Statistique() {
		joueurs = new ArrayList<StatJoueur>();
	}

	public void addJoueur(String nom) {
		joueurs.add(new StatJoueur(nom));
	}

	public ArrayList<StatJoueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Retourne l'instance StatJoueur contenant le nom du joueur en parametre
	 * 
	 * @param name du joueur dont on recherche l'instance de StatJoueur
	 * @return l'instance de StatJoueur du joueur
	 */
	public StatJoueur getStatJoueur(String name) {
		for (StatJoueur sj : joueurs) {
			if (sj.getName().equals(name))
				return sj;
		}
		return null;
	}

	/**
	 * Mets à jours les ressources gagnés par le joueur dont le nom correspond à
	 * celui dans le premiere argument
	 * 
	 * @param name       nom du joueur à mettre les stats à jour
	 * @param ressources ressource à ajouter dans les ressources gagnés du joueur
	 */
	public void updateStat(String name, HashMap<Ressource, Integer> ressources) {
		StatJoueur sj = getStatJoueur(name);
		for (Ressource r : ressources.keySet()) {
			switch (r) {
			case victoryPoint:
				sj.ajouteNbVictoryPoint(ressources.get(r));
				break;
			case gold:
				sj.ajouterOrGagne(ressources.get(r));
				break;
			case solaryStone:
				sj.ajouterSolaryGagne(ressources.get(r));
				break;
			case lunaryStone:
				sj.ajouterLunaryGagne(ressources.get(r));
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Mets à jours les ressources gagnés par le joueur dont le nom correspond à
	 * celui dans le premiere argument
	 * 
	 * @param name  nom du joueur à mettre les stats à jour
	 * @param faces contenant les ressources à rajouter dans les stats du joueur
	 */
	public void updateStat(String name, ArrayList<Face> faces) {
		if(faces==null)
			return;
		
		int x3 = 0;
		HashMap<Ressource, Integer> ressources = new HashMap<Ressource, Integer>();

		// ArrayList to hashmap
		for (Face f : faces) {
			for (Ressource r : f.getRessourceGranted().keySet()) {
				if (ressources.containsKey(r))
					ressources.replace(r, ressources.get(r) + f.getNumberOfRessourceGranted(r));
				else
					ressources.put(r, f.getNumberOfRessourceGranted(r));
			}
		}

		// Multiply by three
		if (x3 > 0) {
			for (Ressource r : ressources.keySet()) {
				ressources.replace(r, ressources.get(r) * 3);
			}
		}

		// Base method
		updateStat(name, ressources);
	}

	public String toString() {
		String stat = "\n\n";
		for (StatJoueur joueur : joueurs) {
			stat += joueur.toString() + "\n\n";
		}
		return stat;
	}
}
