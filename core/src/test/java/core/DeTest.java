package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeTest {
    protected De de;

    @BeforeEach
    protected void setUp() throws Exception {
        de = new De();

    }
    
    @AfterEach
    protected void tearDown() throws Exception {
    }
    
    @Test
    public void testGetFaces() throws Exception {        
        assertEquals(6, de.getFaces().length);
    }
    
    @Test
    public void testChangeFace() throws Exception {
        for(int i=0;i<6;i++) {
            de.changeFace(i, new Face(Ressource.gold, false, 1));
        }
        Face f1 = new Face(Ressource.gold, false, 3);
        de.changeFace(2, f1);
        assertEquals(f1, de.getFaces()[2]);
    } 
    
    @Test
    public void testRoll() throws Exception {  
    	for(int i=0;i<6;i++) {
            de.changeFace(i, new Face(Ressource.gold, false, 1));
        }
        assertNotNull(de.roll());
        Face r1 = de.getFaces()[1]; 
        assertEquals(r1,de.getFaces()[1]);
    }
    
    
    @Test
    public void testSetFaces() throws Exception {   
        De de2 = new De();
        Face[] faces = new Face[6];
        for(int i=0;i<6;i++) {
            faces[i] = new Face(Ressource.gold, false, 1);
        }
        de2.setFaces(faces);
        for(int i=0; i<6; i++) {
            assertEquals(faces[i].getRessourceGranted(), de2.getFaces()[i].getRessourceGranted());
        }
    }
    
    @Test
    public void testSetFace() throws Exception {  
    	De de3 = new De();
        Face[] faces = new Face[6];
        for(int i=0;i<6;i++) {
            faces[i] = new Face(Ressource.gold, false, 1);
        }
        de3.setFace(new Face(Ressource.gold, false, 3), 0);
        assertEquals((Integer)3, de3.getFaces()[0].getRessourceGranted().get(Ressource.gold));
        de3.setFace(new Face(Ressource.gold, false, 4), -2);
        assertEquals((Integer)3, de3.getFaces()[0].getRessourceGranted().get(Ressource.gold));
        de3.setFace(new Face(Ressource.gold, false, 4), 6);
        assertEquals((Integer)3, de3.getFaces()[0].getRessourceGranted().get(Ressource.gold));
    }
    
}
