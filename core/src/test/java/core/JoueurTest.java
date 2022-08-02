package core;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JoueurTest   {
	protected Joueur joueur;
	
	@BeforeEach
	protected void setUp() throws Exception {
		joueur = new Joueur("Jean");
		
	}

	@AfterEach
	protected void tearDown() throws Exception {
		joueur = null;
	}

	@Test
	public void testAjoutRessourceSimple() {
		System.out.println("testAjoutRessourceSimple");
		Face face = new Face(Ressource.gold, false, 3);
		ArrayList<Face> listFace = new ArrayList<Face>();
		listFace.add(face);
		int goldBefore = joueur.getInventaire().getGold();
		joueur.ajouteRessource(listFace);
		assertEquals(goldBefore+3, joueur.getInventaire().getGold());
	}
	
	@Test
	public void testAjoutRessourceMultiply() {
		System.out.println("testAjoutRessourceMultiply");
		Face face1 = new Face(Ressource.gold, false, 3);
		Face face2 = new Face(Ressource.multiplyThree, false, 0);
		ArrayList<Face> listFace = new ArrayList<Face>();
		listFace.add(face1);
		listFace.add(face2);
		int goldBefore = joueur.getInventaire().getGold();
		System.out.println(joueur.getInventaire().getGold());
		joueur.ajouteRessource(listFace);
		System.out.println(joueur.getInventaire().getGold());
		assertEquals(goldBefore+9, joueur.getInventaire().getGold());
	}
	
	@Test 
	public void testAjoutRessourceMultiple() { ///test gold et lunary
		System.out.println("testAjoutRessourceMultiple");
		Face face1 = new Face(Ressource.gold, false, 3);
		Face face2 = new Face(Ressource.lunaryStone, false, 1);
		ArrayList<Face> listFace = new ArrayList<Face>();
		listFace.add(face1);
		listFace.add(face2);
		System.out.println(joueur.getInventaire().getGold());
		int goldBefore = joueur.getInventaire().getGold();
		int lunaryBefore = joueur.getInventaire().getLunaryStone();
		joueur.ajouteRessource(listFace);
		assertEquals(goldBefore+3, joueur.getInventaire().getGold());
		System.out.println(joueur.getInventaire().getGold());
		assertEquals(lunaryBefore+1, joueur.getInventaire().getLunaryStone());
	}
	
	@Test 
    public void testAjoutRessourceMultiple2() {  ///test solary et voctoryPoint
        System.out.println("testAjoutRessourceMultiple");
        Face face1 = new Face(Ressource.victoryPoint, false, 3);
        Face face2 = new Face(Ressource.solaryStone, false, 1);
        ArrayList<Face> listFace = new ArrayList<Face>();
        listFace.add(face1);
        listFace.add(face2);
        int victoryBefore = joueur.getInventaire().getVictoryPoint();
        int solaryBefore = joueur.getInventaire().getSolaryStone();
        joueur.ajouteRessource(listFace);
        assertEquals(victoryBefore+3, joueur.getInventaire().getVictoryPoint());
        assertEquals(solaryBefore+1, joueur.getInventaire().getSolaryStone());    

        Face face3 = new Face(Ressource.portal, false, 1);
        listFace.add(face3);
        joueur.ajouteRessource(listFace);
    }
    
    @Test
    public void testAjoutRessourceMultiple3() {  ///test solary et voctoryPoint
        System.out.println("testAjoutRessourceMultiple");
        Face face1 = new Face(Ressource.portal, false, 3);
        ArrayList<Face> listFace = new ArrayList<Face>();
        listFace.add(face1);
        joueur.ajouteRessource(listFace);
    }
	
	@Test
	public void testLancerDieuMineur() {
		Face faceResult = joueur.lancerDeMineur(1);
		assertNotEquals(null, faceResult);
	}
	
	@Test
	public void testLancerDieu() {
		ArrayList<Face> faceResult = joueur.lancerDeDieu();
		for(Face f : faceResult) {
			assertNotEquals(null, f);
		}
	}
	
	@Test
	public void testLancerDieuAddRessource() {
		ArrayList<Face> faceResult = joueur.lancerDeDieu();
		for(Face f : faceResult) {
			assertNotEquals(null, f);
		}
		ArrayList<Face> faceResult2 = joueur.lancerDeDieu();
		for(Face f : faceResult2) {
			assertNotEquals(null, f);
		}
	}
	
	@Test
	public void testGetNom() {
		assertEquals("Jean", joueur.getNom());
	}
	
	@Test
	public void testSetNom() {
		joueur.setNom("bob");
		assertEquals("bob", joueur.getNom());
	}
	
}


