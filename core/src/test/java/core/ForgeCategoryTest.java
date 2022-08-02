package core;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ForgeCategoryTest {
	
	ForgeCategory fc;
	ForgeCategory fc2;
	Face face1Example;
	Face face2Example;
	
	@BeforeEach
	public void setUp() {
		HashMap<Ressource, Integer> cost = new HashMap<Ressource, Integer>();
		cost.put(Ressource.gold, 3);
		ArrayList<Face> faceList = new ArrayList<Face>();
		face1Example = new Face(Ressource.gold, false, 3);
		face2Example = new Face(Ressource.lunaryStone, false, 1);
		faceList.add(face1Example);
		fc = new ForgeCategory(cost, faceList);
		fc2 = new ForgeCategory(cost, new ArrayList<Face>());
	}
	
	@AfterEach
	public void tearDown() {
		
	}
	
	@Test
	public void testCost() {
		assertEquals(3, fc.getCost(Ressource.gold));
	}
	
	@Test
	public void testSizeCategory() {
		assertEquals(1, fc.getFaces().size());
	}
	
	@Test
	public void testCategoryContain() {
		assertEquals(true, fc.contains(face1Example));
		assertEquals(false, fc.contains(face2Example));
	}
	
	@Test
	public void testBuyWithFace() {
		assertEquals(face1Example, fc.buyFace(face1Example));
		assertEquals(0, fc.getFaces().size());
	}
	
	@Test
	public void testBuyWithIndex() {
		assertEquals(face1Example, fc.buyFace(0));
		assertEquals(0, fc.getFaces().size());
	}
	
	@Test
	public void testBuyReturNull() {
		assertEquals(null, fc.buyFace(face2Example));
		assertNull(fc2.buyFace(1));
		assertNull(fc2.buyFace(-1));
	}
	
	@Test
	public void testIsEmpty() {
		assertEquals(false, fc.isEmpty());
		assertEquals(true, fc2.isEmpty());
	}
	
	@Test
	public void testGetFace() {
		assertEquals(face1Example, fc.getFace(0));
	}

}
