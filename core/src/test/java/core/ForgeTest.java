package core;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ForgeTest {
	
	Forge forge;
	Forge forge2;
	
	@BeforeEach
	public void setUp() {
		forge = new Forge();
		forge2 = new Forge(forge.getForgeCategories());
	}
	
	@AfterEach
	public void tearDown() {
		
	}
	
	@Test
	void testGetCategoryByCost() {
		assertNotEquals(null, forge.getCategoryByCost(4));
	}
	
	@Test
	void testGetCategoryByCostNull() {
		assertEquals(null, forge2.getCategoryByCost(9));
	}
	
	@Test
	void testNbCategory() {
		assertEquals(7, forge.getForgeCategories().size());
	}

}
