package protocol;

public enum MessageType {
	connected ("Connected"),
	rollResult ("Roll Result"),
	rollChoice ("rollChoice"),
	rollEnd ("rollEnd"),
	effectEnd ("effectEnd"),
	name ("Name"),
	turn ("Turn"),
	forge ("Forge"),
	exploit ("Exploit"),
	newAction ("New Action"),
	newRound ("New Round"),
	endRound ("End Round"),
	endTurn ("End Turn"),
	start ("Start"),
	carteAchete("CarteAchete"),
	newgame("newGame"),
	faceAchete("FaceAchete"),
	retraitSolary("RetraitSolary"),
	hunt("Chasse"),
	hunt2("Chasse2"),
	victory("Victory"),
	effect("effect"),
	updateDe("updateDe"),
	updateRessource("updateRessource"),
	helmet("helmet"),
	chest("chest"),
	marteau("marteau"),
	marteauAchat("marteauAchat"),
	askForge("askForging"),
	satyrs("satyrs"),
	satyrs2("satyrs2"),
	guardianowls("guardianeffect"),
	guardianowls2("guardian2effect"),
	sphinx("sphinx"),
	sphinx2("sphinx2"),
	sphinx3("sphinx3"),
	silverhind("silverhind"),
	silverhind2("silverhind2"),
	silverhind3("silverhind3"),
	minotaur("minotaur"),
	cancer("cancer"),
	cancer2("cancer2");
	
	
	private String nom;
    private MessageType(String nom) {
        this.nom = nom;
    }
    
    @Override
    public String toString(){
        return nom;
    }
}
