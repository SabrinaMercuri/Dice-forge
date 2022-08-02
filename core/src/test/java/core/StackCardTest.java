package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.cards.BlacksmithsChestCard;
import core.cards.BlacksmithsHammerCard;
import core.cards.CancerCard;
import core.cards.ElderCard;
import core.cards.FerrymanCard;
import core.cards.GorgonCard;
import core.cards.GuardiansOwlCard;
import core.cards.HelmetInvisibilityCard;
import core.cards.HydraCard;
import core.cards.MinotaurCard;
import core.cards.MirrorAbyssCard;
import core.cards.SatyrsCard;
import core.cards.SilverHindCard;
import core.cards.SphinxCard;
import core.cards.WildSpiritsCard;

class StackCardTest {

	protected StackCard stack;
	
	@BeforeEach
	protected void setUp() throws Exception {
		stack = new StackCard(4, "CancerCard");
	}
	
	@Test
	void testGetCard() {
		 assertTrue(stack.getCard() instanceof CancerCard);
	}
	
	@Test
	void testSatyrsCard() {
		 stack = new StackCard(4, "SatyrsCard");
		 assertTrue(stack.getCard() instanceof SatyrsCard);
	}
	
	@Test
	void testGetNumberCard() {
		 assertEquals(stack.getNumberCard(), 4);
	}
	
	@Test
	void testDecreaseNbCard() {
		 stack.decreaseNbCard();
		 assertEquals(stack.getNumberCard(), 3);
		 stack.decreaseNbCard();
		 stack.decreaseNbCard();
		 stack.decreaseNbCard();
		 stack.decreaseNbCard();
		 stack.decreaseNbCard();
		 assertEquals(stack.getNumberCard(), 0);
	}
	
	@Test
	void testSilverHindCard() {
		 stack = new StackCard(4, "SilverHindCard");
		 assertTrue(stack.getCard() instanceof SilverHindCard);
	}
	
	@Test
	void testFerrymanCard() {
		 stack = new StackCard(4, "FerrymanCard");
		 assertTrue(stack.getCard() instanceof FerrymanCard);
		 
	}
	
	@Test
	void testHydraCard() {
		 stack = new StackCard(4, "HydraCard");
		 assertTrue(stack.getCard() instanceof HydraCard);
	}
	
	@Test
	void testSphinxCard() {
		 stack = new StackCard(4, "SphinxCard");
		 assertTrue(stack.getCard() instanceof SphinxCard);
	}
	
	@Test
	void testGorgonCard() {
		 stack = new StackCard(4, "GorgonCard");
		 assertTrue(stack.getCard() instanceof GorgonCard);
	}
	
	@Test
	void testMinotaurCard() {
		 stack = new StackCard(4, "MinotaurCard");
		 assertTrue(stack.getCard() instanceof MinotaurCard);
	}
	
	@Test
	void testElderCard() {
		 stack = new StackCard(4, "ElderCard");
		 assertTrue(stack.getCard() instanceof ElderCard);
	}
	
	@Test
	void testWildSpiritCard() {
		 stack = new StackCard(4, "WildSpiritsCard");
		 assertTrue(stack.getCard() instanceof WildSpiritsCard);
	}
	
	@Test
	void testGuardiansOwlCard() {
		 stack = new StackCard(4, "GuardiansOwlCard");
		 assertTrue(stack.getCard() instanceof GuardiansOwlCard);
	}
	
	@Test
	void testHelmetInvisibilityCard() {
		 stack = new StackCard(4, "HelmetInvisibilityCard");
		 assertTrue(stack.getCard() instanceof HelmetInvisibilityCard);
	}
	
	@Test
	void testMirrorAbyssCard() {
		 stack = new StackCard(4, "MirrorAbyssCard");
		 assertTrue(stack.getCard() instanceof MirrorAbyssCard);
	}
	
	@Test
	void testBlacksmithsHammerCard() {
		 stack = new StackCard(4, "BlacksmithsHammerCard");
		 assertTrue(stack.getCard() instanceof BlacksmithsHammerCard);
	}
	
	@Test
	void testBlacksmithsChestCard() {
		 stack = new StackCard(4, "BlacksmithsChestCard");
		 assertTrue(stack.getCard() instanceof BlacksmithsChestCard);
	}
	
	@Test
	void testDefaultCard() {
		 stack = new StackCard(4, "");
		 assertNull(stack.getCard());
	}
	

}
