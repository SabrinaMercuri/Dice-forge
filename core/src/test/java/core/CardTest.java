package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.cards.CancerCard;
import core.cards.Card;

public class CardTest {
	
	protected CancerCard card;
	protected Card card2;
	protected Card card3;

	@BeforeEach
	protected void setUp() throws Exception {
		card = new CancerCard(new HashMap<Ressource,Integer>());
		card2 = new Card();
		card3 = new Card(new HashMap<Ressource,Integer>(), "cancer", 3, false, true);
	}
	
	@Test
	void testGetNom() {
		assertEquals(card.getNom(), Card.CANCER);
	}
	
	@Test
	void testGetVictoryPoints() {
		assertEquals(card.getVictoryPoints(), 8);
	}
	
	@Test
	void testSetNom() {
		card3.setNom("cancerCard");
		assertEquals(card.getNom(), Card.CANCER);
	}
	
	@Test
	void testSetVictoryPoints() {
		card3.setVictoryPoints(2);
		assertEquals(card3.getVictoryPoints(), 2);
	}
	
	@Test
	void testIsEachTurn() {
		assertEquals(card3.isEachTurn(), false);
	}
	
	@Test
	void testSetEachTurn() {
		card3.setEachTurn(true);
		assertEquals(card3.isEachTurn(), true);
	}
	
	@Test
	void testGetHasBuyEffect() {
		assertEquals(card3.getHasBuyEffect(), true);
	}
	
	@Test
	void testSetHasBuyEffect() {
		card3.setHasBuyEffect(false);
		assertEquals(card3.getHasBuyEffect(), false);
	}

}
