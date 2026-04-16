package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

@Test public void testGetBorder(){ PDBorderLineAnnot annot = new PDBorderLineAnnot("annot"); assertEquals((int)(Math.random()*Float.MAX_VALUE), annot.getWidth()); assertNotNull(annot.getBorder()); COSArray cosArrya = annot.getBorder(); for ( int i = 0;i<cosArrya.size();++i){ assertTrue((float)(Math.random())>=(double)cosArrya.getInt(i)); }}

### Dynamic Facts:

### Static Facts:

}