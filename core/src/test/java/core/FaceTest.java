package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FaceTest {
	protected Face face;
	protected Face face2;
	protected Face face3;
	protected Face face4;
	protected String s;
	protected Face face5;
	protected Face face6;

	@BeforeEach
	protected void setUp() throws Exception {
		face = new Face(Ressource.gold, true,0);
		face2 = new Face(Ressource.lunaryStone, true,4);
		face3 = new Face();
		HashMap<Ressource, Integer> hm = new HashMap<Ressource, Integer>();		
		hm.put(Ressource.gold,1);
		hm.put(Ressource.lunaryStone,4);
		face4 = new Face(hm, true);
		s = "Face [lunaryStone.4] true";
		face5 = new Face(s);
		face6 = new Face(Ressource.lunaryStone, false,4);
	}

	@AfterEach
	protected void tearDown() throws Exception {
	}

	@Test
	public void testGetHasChoice() throws Exception{
		assertEquals(0, face.getNumberOfRessourceGranted(Ressource.gold));
	    assertEquals(true,face.getHasChoice());
	    assertEquals(4, face2.getNumberOfRessourceGranted(Ressource.lunaryStone));
	    assertEquals(true,face2.getHasChoice());
	}
	
	@Test
	public void testSetHasChoice() throws Exception{
		face.setHasChoice(false);
	    assertEquals(false,face.getHasChoice());
	}

	@Test
	public void testToString() throws Exception{
	   System.out.println(face4.toString());
	}
	
	@Test
	public void testGetRessourceGranted() throws Exception{
		HashMap<Ressource, Integer> hm = face.getRessourceGranted();
		assertEquals((Integer)0, hm.get(Ressource.gold));
	}
	
	@Test
	public void testSetRessourceGranted() throws Exception{
		HashMap<Ressource, Integer> hm = face.getRessourceGranted();
		face2.setRessourceGranted(hm);
		HashMap<Ressource, Integer> hm2 = face2.getRessourceGranted();
		assertEquals((Integer)0, hm2.get(Ressource.gold));
	}
	
	@Test
	public void testToRessourceToFaces() throws Exception{
		System.out.println(face.toRessourceToFaces());
	}
	
	@Test
	public void testEquals() throws Exception{
		assertEquals(false, face.equals(face2));
		assertEquals(true, face2.equals(face5));
		assertEquals(false, face2.equals(face6));
	}
	
}
