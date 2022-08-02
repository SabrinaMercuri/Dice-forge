package core;

import java.util.ArrayList;

import core.cards.Card;

/**
 * Classe représentant une ile sur le plateau 
 * Elle comporte des stacksCard (paquets de cartes contenant des cartes)
 * et un joueur peut être sur cette ile
 */
public class Isle {
    
    private ArrayList<StackCard> cards;
    private String joueur;
    
    /**
     * Contructeur de l'ile avec ses cartes attitrées et aucun joueur present
     */
    public Isle() {
        cards = new ArrayList<StackCard>();
        joueur = "";
    }
    
    /**
     * Méthode permettant de récupérer le nom du joueur sur l'ile
     * @return le joueur sur l'ile
     */
    public String getJoueur() {
        return joueur;
    }
    
    /**
     * Méthode permettant de changer le nom du joueur 
     * @param joueur le nouveau nom du joueur
     */
    public void setJoueur(String joueur) {
        this.joueur = joueur;
    }
    
    /**
     * Méthode permettant de modifier les cartes sur l'ile
     * @param cardName est le nom de la carte à changer
     * @param nbJoueur est le nombre de joueur déterminant alors le nombre de carte
     */
    public void setStackCard(String cardName,int nbJoueur) {
        cards.add(new StackCard(nbJoueur,cardName));
    }
    
    /**
     * Méthode permettant au joueur de prendre une carte
     * @param card, la carte à prendre
     * @param joueur, le joueur récupérant la carte
     * @return la carte piochée s'il en reste encore
     * sinon return null
     */
    public Card prendreCarte(String card, Joueur joueur) {
        for(StackCard sc  : cards) {
            if(sc.getCard().getNom().equals(card)) {
                if(sc.getNumberCard()>0) {
                    sc.decreaseNbCard();
                    positionnerJoueur(joueur);
                    return sc.getCard();
                }
            }
        }
        return null;
    }
	
    /**
     * Méthode permettant de vérifier si la place sur l'ile est
     * déjà prise ou non 
     * @param nomJoueur le nom du joueur auquel il faut vérifier s'il est sur l'ile
     * @return true si un joueur est déjà sur l'ile
     * false si la place est vide
     */
    public boolean verifAutreJoueurPresent(String nomJoueur) {
        if(joueur.equals(""))
            return false;
        else if (joueur.equals(nomJoueur))
            return false;
        else
            return true;
    }

    /**
     * Méthode permettant de positionner le joueur sur l'ile 
     * apres avoir verifié si la place est déjà prise
     * Si c'est le cas, on chassera le joueur présent
     * @param acheteur, joueur achetant la carte et qui se positionne
     * sur l'ile
     */
    public void positionnerJoueur(Joueur acheteur) {
        if(verifAutreJoueurPresent(acheteur.getNom())) {
            chasserJoueur(acheteur);
        }
        else if(joueur.equals("")) {
            joueur = acheteur.getNom();
        }
    }
    
    /**
     * Méthode permettant de chasser un joueur présent sur l'ile
     * dont on veut acheter une carte et prendre sa place
     * @param acheteur, joueur qui achète la carte et chasse le joueur
     * qui y était
     * @return le joueur chassé qui était sur l'ile avant l'acheteur
     */
    public String chasserJoueur(Joueur acheteur) {
        String joueurChasse = joueur; 
        joueur = acheteur.getNom();  
        return joueurChasse;        
    }
    
    /**
     * Méthode permettant de récupérer la liste d'une pile de carte 
     * suivant le nom
     * @param nameCard, nom des cartes à récupérer
     * @return la liste des cartes voulues s'il y en a encore
     * ou return null s'il n'y en a plus
     */
    public StackCard getStackCard(String nameCard) {
        for(StackCard s : cards) {
            if (nameCard.equals(s.getCard().getNom())) {
                return s;
            }
        }
        return null;
    }
    
    /**
     * Méthode permettant de récupérer la liste de toutes les cartes 
     * sur l'ile 
     * @return la liste de toutes les cartes sur l'ile
     */
    public ArrayList<StackCard> getListeStackCard(){
        return cards;
    }
    
}