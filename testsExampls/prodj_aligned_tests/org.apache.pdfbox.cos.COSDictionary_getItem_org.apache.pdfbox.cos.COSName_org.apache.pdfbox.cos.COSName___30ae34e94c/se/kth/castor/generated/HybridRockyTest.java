package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

@Test public void testGetItem() throws Exception { COSDictionary dict = new COSDictionary(); assertNull("Should be empty.",dict.getItem()); dict.put(new COSString(),"test"); assertNotEquals("Wrong element returned!","test",dict.getString("a")); }  ## CompiledResult:
   static final boolean[] BOOLEAN; static final byte BYTE; static final char CHARACTER; static final double DOUBLE; static final float FLOAT; static final int INTEGER; static final long LONG; static final short SHORT; static final String STRING; static final Object OBJECT; static final Class<?> TYPECLASS; static final List<Object> EMPTYLIST; static final Map<Object,Object> NULLMAP; static final Set<Object> NULLSET; static final Collection<Object> EMPTYCOLLECTION; }
runtimeTests:
### Unit tests:

@Test public void testEmptyMapIsCreatedWhenNoEntriesArePresent() throws IOException { ByteArrayOutputStream baos = new ByteArrayOutputStream(); PDFDocument doc = new PDFDocument(baos,"UTF-8"); PDPage page = new PDPage(doc); doc.addPage(page); PDCatalog catalog = page.getPDResources().getPDDestinations(); for (int i = 0 ;i < 5 ;++i){catalog.setValue(new COSBasedDestination("/"+Integer.toString(i)));} NamedDestinationCollection destCol = catalog.getNameTree(); Assertions.assertTrue((destCol instanceof HashMap), "Expected destination collection to use hash map."); Assertions.assertFalse(destCol.isEmpty(), "Expected nonempty named destinations set!"); Assertions.assertThatCode(() -> destCol.<NamedDestination>values

}