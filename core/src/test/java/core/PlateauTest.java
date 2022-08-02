package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlateauTest  {
	
	protected Plateau plateau;

	@BeforeEach
	protected void setUp() throws Exception{
		plateau = new Plateau();
		plateau.setForge(new Forge());
	}
	
	@AfterEach
	protected void tearDown() throws Exception{
		
	}
	
	@Test
	public void testAddPlayer() throws Exception{
		/**
		 * Test d'ajout d'un joueur au plateau
		 */
		plateau.addPlayer(new Joueur("Joueur 1"));
		assertEquals(1, plateau.getPlayers().size());
		System.out.println("Ajout de 1 joueur avec succes");
	}
	
	@Test
	public void testTooMuchPlayer() throws Exception {
		/**
		 * Test si le plateau n'accepte que 4 joueurs au maximum
		 */
		assertEquals(0, plateau.getPlayers().size());
		plateau.addPlayer(new Joueur("Joueur 1"));
		plateau.addPlayer(new Joueur("Joueur 1"));
		plateau.addPlayer(new Joueur("Joueur 1"));
		plateau.addPlayer(new Joueur("Joueur 1"));
		assertEquals(4, plateau.getPlayers().size());
		plateau.addPlayer(new Joueur("Joueur 1"));
		assertEquals(4, plateau.getPlayers().size());
		System.out.println("Le plateau a bien 4 joueurs au maximal");
	}
	
	@Test
	public void testGetIsles() throws Exception {
		assertEquals(7, plateau.getIsles().size());
	}
	
	@Test
	public void testGetForge() throws Exception {
		assertEquals(7, plateau.getForge().getForgeCategories().size());
	}
	
	@Test
	public void testGetPlayerName() throws Exception {
		Joueur j = new Joueur("bob");
		assertEquals(0, plateau.getPlayers().size());
		plateau.addPlayer(j);
		assertNull(plateau.getPlayerByName(""));
		assertEquals(j, plateau.getPlayerByName("bob"));
	}
	
	@Test
	public void testNbRound() throws Exception {
		assertEquals(0, plateau.getNbRound());
		plateau.setNbRound(2);
		assertEquals(2, plateau.getNbRound());
	}
	
	@Test
	public void testIsleByCost() throws Exception {
		HashMap<Ressource, Integer> hm = new HashMap<Ressource, Integer>();
		hm.put(Ressource.lunaryStone, 2);
		assertNotNull(plateau.getIsleByCost(hm));
		hm.put(Ressource.lunaryStone, 7);
		assertNull(plateau.getIsleByCost(hm));
	}
	
}
