package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.cards.Card;

public class InventaireTest {
	protected Inventaire inventaire; 

	@BeforeEach
	protected void setUp() throws Exception {
		inventaire = new Inventaire();
	}

	@AfterEach
	protected void tearDown() throws Exception {
	}
	
	@Test
	public void testGetGold() throws Exception{
		assertEquals(0, inventaire.getGold());
	}
	
	@Test
	public void testGetSolaryStone() throws Exception{
		assertEquals(0, inventaire.getSolaryStone());
	}	
	
	@Test
	public void testGetLunaryStone() throws Exception{
		assertEquals(0, inventaire.getLunaryStone());
	}	
	
	@Test
	public void testGetVictoryPoint() throws Exception{
		assertEquals(0, inventaire.getVictoryPoint());
	}	
	
	@Test
	public void testAjouterGold() throws Exception{
		inventaire.ajouterGold(3);
		assertEquals(3, inventaire.getGold());
	}
	
	@Test
	public void testAjouterLunary() throws Exception{
		inventaire.ajouterLunary(3);
		assertEquals(3, inventaire.getLunaryStone());
	}
	
	@Test
	public void testAjouterSolary() throws Exception{
		inventaire.ajouterSolary(3);
		assertEquals(3, inventaire.getSolaryStone());
	}
	
	@Test
	public void testAjouterPoint() throws Exception{
		inventaire.ajouterPoint(3);
		assertEquals(3, inventaire.getVictoryPoint());
	}
	
	@Test
	public void testRetirerGold() throws Exception{
		inventaire.ajouterGold(3);
		inventaire.retirerGold(-3);
		assertEquals(3, inventaire.getGold());
		inventaire.retirerGold(2);
		assertEquals(1, inventaire.getGold());
		inventaire.retirerGold(1);
		assertEquals(0, inventaire.getGold());
		
	}
	
	@Test
	public void testRetirerLunary() throws Exception{
		inventaire.ajouterLunary(3);
		inventaire.retirerLunary(-3);
		assertEquals(3, inventaire.getLunaryStone());
		inventaire.retirerLunary(2);
		assertEquals(1, inventaire.getLunaryStone());
		inventaire.retirerLunary(1);
		assertEquals(0, inventaire.getLunaryStone());
	}
	
	@Test
	public void testRetirerSolary() throws Exception{
		inventaire.ajouterSolary(3);
		inventaire.retirerSolary(-3);
		assertEquals(3, inventaire.getSolaryStone());
		inventaire.retirerSolary(2);
		assertEquals(1, inventaire.getSolaryStone());
		inventaire.retirerSolary(1);
		assertEquals(0, inventaire.getSolaryStone());
	}
	
	@Test
	public void testRetirerPoint() throws Exception{
		inventaire.ajouterPoint(3);
		inventaire.retirerPoint(-3);
		assertEquals(3, inventaire.getVictoryPoint());
		inventaire.retirerPoint(2);
		assertEquals(1, inventaire.getVictoryPoint());
		inventaire.retirerPoint(1);
		assertEquals(0, inventaire.getVictoryPoint());
	}
	
	@Test
	public void testAjoutRessource() throws Exception { 
		//on test la limite haute
		inventaire.ajouterGold(13);
		inventaire.ajouterLunary(7); 
		inventaire.ajouterSolary(7);
		inventaire.ajouterPoint(24);
		assertEquals(12, inventaire.getGold());
		assertEquals(6, inventaire.getLunaryStone());
		assertEquals(24, inventaire.getVictoryPoint());
		assertEquals(6, inventaire.getSolaryStone());
		//demander pour ajouter
		inventaire.ajouterGold(-3);
		assertEquals(12,inventaire.getGold());
		inventaire.ajouterPoint(-9);
		assertEquals(24,inventaire.getVictoryPoint());
		inventaire.ajouterLunary(-3);
		assertEquals(6,inventaire.getLunaryStone());
		inventaire.ajouterSolary(-3);
		assertEquals(6,inventaire.getSolaryStone());
		//On test la limite inferieur
		inventaire.retirerGold(14);
		inventaire.retirerLunary(8);
		inventaire.retirerSolary(8);
		inventaire.retirerPoint(25);
		assertEquals(0, inventaire.getGold());
		assertEquals(0, inventaire.getLunaryStone());
		assertEquals(0, inventaire.getVictoryPoint());
	}
	
	@Test
	public void testSetLimit() throws Exception { 
		// on test le set des limites
		inventaire.setLimitGold(4);
		inventaire.setLimitLunary(3);
		inventaire.setLimitSolary(3);
		assertEquals(16, inventaire.getLimitGold());
		assertEquals(9, inventaire.getLimitLunary());
		assertEquals(9, inventaire.getLimitSolary());
	}
	
	@Test
	public void testUpdateRessource() throws Exception { 	
		inventaire.ajouterGold(1);
		inventaire.ajouterLunary(1);
		inventaire.ajouterSolary(1);
		inventaire.ajouterPoint(1);
		// valeur positive
		HashMap<Ressource, Integer> res = new HashMap<Ressource, Integer>();
		res.put(Ressource.gold,3);
		res.put(Ressource.lunaryStone,3);
		res.put(Ressource.solaryStone,3);
		res.put(Ressource.victoryPoint,3);
		inventaire.updateRessource(res);
		assertEquals(4,inventaire.getGold());
		assertEquals(4, inventaire.getSolaryStone());
		assertEquals(4, inventaire.getLunaryStone());
		assertEquals(4, inventaire.getVictoryPoint());
		// valeur négative
		res.put(Ressource.gold,-3);
		res.put(Ressource.lunaryStone,-3);
		res.put(Ressource.solaryStone,-3);
		res.put(Ressource.victoryPoint,-3);
		inventaire.updateRessource(res);
		assertEquals(1,inventaire.getGold());
		assertEquals(1, inventaire.getSolaryStone());
		assertEquals(1, inventaire.getLunaryStone());
		assertEquals(1, inventaire.getVictoryPoint());
		// pas un type
		res.put(Ressource.multiplyThree,2);
		inventaire.updateRessource(res);
	}

	@Test
	public void testSetSolaryStone() throws Exception { 
		inventaire.ajouterSolary(1);
		inventaire.setSolaryStone(2);
		assertEquals(2, inventaire.getSolaryStone());
	}
	
	@Test
	public void testSetLunaryStone() throws Exception { 
		inventaire.ajouterLunary(1);
		inventaire.setLunaryStone(2);
		assertEquals(2, inventaire.getLunaryStone());
	}
	
	@Test
	public void testSetVictoryPoint() throws Exception { 
		inventaire.ajouterPoint(1);
		inventaire.setVictoryPoint(2);
		assertEquals(2, inventaire.getVictoryPoint());
	}
	
	@Test
	public void testSetListeJoueur() throws Exception { 
		ArrayList<De> de = new ArrayList<De>();
		inventaire.setListDeJoueur(de);
		assertEquals(de,inventaire.getListDeJoueur());
	}
	
	@Test
	public void testGetListeCartes() throws Exception { 	
		ArrayList<Card> card = new ArrayList<Card>();
		assertEquals(new ArrayList<Card>(), inventaire.getListedecartes());
	}
	
	@Test
	public void testGetListeFace() throws Exception { 	
		ArrayList<Card> card = new ArrayList<Card>();
		assertEquals(new ArrayList<Face>(), inventaire.getListedefaceNONEQUIPE());
	}
	
	@Test
	public void testToString() throws Exception { 	
		assertNotEquals("invetaire du joueur",inventaire.toString()); // a vérifier 
	}
	
}
