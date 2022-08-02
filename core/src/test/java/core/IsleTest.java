package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IsleTest {

	protected Isle isle;
	
	@BeforeEach
	protected void setUp() throws Exception {
		isle = new Isle();
		isle.setStackCard("CancerCard", 2);
	}
	
	@Test
	void testPositionnerJoueur() {
		isle.positionnerJoueur(new Joueur(""));
	}
	
	@Test
	void testGetJoueur() {
		isle.positionnerJoueur(new Joueur(""));
		assertEquals(isle.getJoueur(), "");
	}
	
	@Test
	void testVerifJoueurPresent() {
		isle.positionnerJoueur(new Joueur(""));
		assertEquals(isle.getJoueur(), "");
		assertFalse(isle.verifAutreJoueurPresent("bob"));
	}	
	
	@Test
	void testChasserJoueur() {
		isle.positionnerJoueur(new Joueur(""));
		assertEquals(isle.getJoueur(), "");
		isle.setJoueur("bob");
		assertEquals(isle.getJoueur(), "bob");
		assertFalse(isle.verifAutreJoueurPresent("bob"));
		isle.prendreCarte("CancerCard", new Joueur("kevin"));
		assertEquals(isle.getStackCard("CancerCard").getNumberCard(),1);
		assertTrue(isle.verifAutreJoueurPresent("bob"));
		assertTrue(isle.getJoueur().equals("kevin"));
	}	
	
	@Test
	void testSetJoueur() {
		isle.setJoueur("bob");
		assertEquals(isle.getJoueur(), "bob");
		assertFalse(isle.verifAutreJoueurPresent("bob"));
	}
	
	@Test
	void testPrendreCarte() {
		isle.prendreCarte("CancerCard", new Joueur("kevin"));
		assertEquals(isle.getStackCard("CancerCard").getNumberCard(),1);
		assertTrue(isle.verifAutreJoueurPresent("bob"));
		assertTrue(isle.getJoueur().equals("kevin"));
	}
	
	@Test
	void testGetStackCard() {
		StackCard s =new StackCard(2,"ElderCard");
        isle.getListeStackCard().add(s);
        
	}    
	@Test
	void testGetListStackCard() {
		StackCard s =new StackCard(2,"ElderCard");
		isle.getListeStackCard().add(s);
		isle.prendreCarte("CancerCard", new Joueur("kevin"));
        ArrayList<StackCard> scTest = new ArrayList<StackCard>();
        scTest.add(isle.getStackCard("CancerCard"));
        scTest.add(s);
        assertEquals(isle.getListeStackCard(),scTest);
        isle.prendreCarte("", new Joueur("kevin"));
        isle.prendreCarte("CancerCard", new Joueur("kevin"));
        assertEquals(isle.getStackCard("CancerCard").getNumberCard(),0);
        isle.prendreCarte("CancerCard", new Joueur("kevin"));
        assertNull(isle.getStackCard("SilverHindCard"));
	}

}
