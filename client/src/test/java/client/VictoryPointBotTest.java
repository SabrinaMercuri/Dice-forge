package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Face;
import core.ForgeCategory;
import core.Ressource;
import core.cards.Card;
import protocol.CarteMessage;
import protocol.FaceMessage;

public class VictoryPointBotTest {
	protected Client client;
	
	@BeforeEach
	void setUp() {
		try {
			client = new Client("Jackson");
			client.setAction(new VictoryPointBot(client));
		}catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void newActionTest() {
		client.getMyself().getInventaire().setSolaryStone(5);
		assertTrue(client.getAction().newAction());
		client.getMyself().getInventaire().setSolaryStone(3);
		assertFalse(client.getAction().newAction());
	}
	
	@Test
	void isExploitingTest() {
		// cas ou solary et lunary > 3 (valide la condition)
		client.getMyself().getInventaire().setSolaryStone(5);
		client.getMyself().getInventaire().setLunaryStone(5);
		assertTrue(client.getAction().isExploiting());
		
		// cas ou solary et lunary <= 3 (valide pas la condition)
		client.getMyself().getInventaire().setSolaryStone(3);
		client.getMyself().getInventaire().setLunaryStone(3);
		assertTrue(client.getAction().isExploiting());
		
		// cas ou lunary > 3 (valide la condition)
		client.getMyself().getInventaire().setSolaryStone(3);
		client.getMyself().getInventaire().setLunaryStone(5);
		assertTrue(client.getAction().isExploiting());
		
		// cas ou solary > 3 (valide la condition)
		client.getMyself().getInventaire().setSolaryStone(5);
		client.getMyself().getInventaire().setLunaryStone(3);
		assertTrue(client.getAction().isExploiting());		
	}
	
	@Test
	void isForgingTest() {
		// cas ou solary et lunary >= 4 (valide la condition)
		client.getMyself().getInventaire().ajouterGold(5);
		assertTrue(client.getAction().isForging());
		
		// cas ou solary et lunary < 4 (valide pas la condition)
		client.getMyself().getInventaire().retirerGold(2);;
		assertFalse(client.getAction().isForging());
	}
	
	@Test
	void forgeTest() {
		boolean oneFace = false;
		boolean multipleFace = false;
		
		client.getMyself().getInventaire().ajouterGold(12);
		FaceMessage fm1 = client.getAction().forge();
		client.getMyself().getInventaire().retirerGold(12);
		client.getMyself().getInventaire().ajouterGold(3);
		FaceMessage fm2 = client.getAction().forge();
		
		if(fm1.getFaces().size()>=1)
			multipleFace = true;
		if(fm2.getFaces().size()==1)
			oneFace = true;
		
		assertEquals(oneFace, true);
		assertEquals(multipleFace, true);
		
		
		// test si les catégorie sont vides
		videLaForge();
		client.getMyself().getInventaire().ajouterGold(12);
		FaceMessage fm3 = client.getAction().forge();
		assertNull(fm3);
		
		// test s'il n'y a plus de categories
		ArrayList<ForgeCategory> cat = client.getPlateau().getForge().getForgeCategories();
		client.getPlateau().getForge().getForgeCategories().removeAll(cat);
		client.getMyself().getInventaire().ajouterGold(12);
		FaceMessage fm4 = client.getAction().forge();
		assertNull(fm4);
		
	}
	
	private void videLaForge() {
		for(ForgeCategory fc : client.getPlateau().getForge().getForgeCategories()) {
			int index = client.getPlateau().getForge().getForgeCategories().indexOf(fc);
			ArrayList<Face> face = fc.getFaces();
			client.getPlateau().getForge().getForgeCategories().get(index).getFaces().removeAll(face);
		}
	}
	
	@Test
	void exploitTest() {
		client.getMyself().getInventaire().ajouterLunary(12);
		CarteMessage cm = client.getAction().exploit();
		assertEquals(cm.getCarte().getNom(), Card.CANCER);
		
		client.getMyself().getInventaire().retirerLunary(12);
		client.getMyself().getInventaire().ajouterSolary(12);
		CarteMessage cm2 = client.getAction().exploit();
		assertEquals(cm2.getCarte().getNom(), Card.SPHINX);
	}
	
	@Test
	void activateCardTest() {
		assertFalse(client.getAction().activateCard(Card.ELDER));
		client.getMyself().getInventaire().ajouterGold(6);
		assertTrue(client.getAction().activateCard(Card.ELDER));
		assertTrue(client.getAction().activateCard(Card.GUARDIANOWLS));
		assertFalse(client.getAction().activateCard(Card.CANCER));
	}
	
	@Test
	void testChooseFace() {
		ArrayList<Face> faces = new ArrayList<Face>();
		for(Face f : client.getMyself().getInventaire().getListDeJoueur().get(client.getAction().chooseDe()).getFaces()) {
			faces.add(f);
		}
		assertNotNull(client.getAction().chooseFace(faces));
	}
	
	@Test
	void mirrorTest() {
		ArrayList<Face> faces = new ArrayList<Face>();
		faces.add(new Face(Ressource.gold,false,3));
		faces.add(new Face(Ressource.victoryPoint,false,3));
		faces.add(new Face(Ressource.solaryStone,false,3));		
		assertEquals(1,client.getAction().mirror(faces));
	}
}
