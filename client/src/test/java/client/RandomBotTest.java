package client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Face;
import core.Ressource;
import core.cards.Card;
import protocol.CarteMessage;
import protocol.FaceMessage;

class RandomBotTest {
	
	Client client;
	
	@BeforeEach
	void setUp() {
		try {
			client = new Client("Francis");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testForge() {
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
	}
	
	@Test
	void testExploit() {
		client.getMyself().getInventaire().ajouterLunary(12);
		client.getMyself().getInventaire().ajouterSolary(12);
		CarteMessage cm = client.getAction().exploit();
		assertNotEquals(cm.getCarte(), null);
	}
	
	@Test
	void testActiveCard() {
		assertFalse(client.getAction().activateCard(Card.ELDER));
		client.getMyself().getInventaire().ajouterGold(12);
		assertNotNull(client.getAction().activateCard(Card.ELDER));
		assertNotNull(client.getAction().activateCard(Card.GUARDIANOWLS));
		assertFalse(client.getAction().activateCard(Card.CANCER));
		Ressource r = Ressource.valueOf("gold");
		System.out.println(r);
	}
	
	@Test
	void testChooseFace() {
		ArrayList<Face> faces = new ArrayList<Face>();
		faces.add(new Face());
		faces.add(new Face());
		assertNotNull(client.getAction().chooseFace(faces));
	}
	
	@Test
	void testChoixDe() {
		int index = client.getAction().chooseDe();
		boolean b = (index<2 && index>=0);
		assertTrue(b);
	}
	
	@Test
	void mirrorTest() {
		ArrayList<Face> faces = new ArrayList<Face>();
		faces.add(new Face(Ressource.victoryPoint,false,3));
		faces.add(new Face(Ressource.gold,false,3));
		faces.add(new Face(Ressource.solaryStone,false,3));		
		assertNotNull(client.getAction().mirror(faces));
	}
}
