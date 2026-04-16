package org.apache.pdfbox.cos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class COSArrayRockyTest {

    @Test public void testGetObjectIndexOutOfBoundsShouldBeZeroOnEmptyAndOneOnNonempty() throws Exception { assertEquals((short) 0, cosarray.getShort(-1)); assertEquals((byte) 5, cosarray.getByte(-1)); assertFalse(cosarray.isEmpty()); assertTrue(!cosarray.containsKey('a')); assertEquals(new Float(null), new Float(cosarray.getFloat(-1))); assertEquals(new Integer(null), new Long(cosarray.getLong(-1))); assertEquals(false, cosarray.getBoolean(-1)); assertEquals((short) 0, cosarray.getInt(-1)); assertEquals((long) 0L, cosarray.getLong(-1)); assertEquals((double) 0d, cosarray.getDouble(-1), .0f); assertEquals(true, cosarray.getDictionary(-1).isEmpty()); assertEquals((char) 0, cosarray.getString(-1).charAt(0)); assertEquals((String) "", cosarray.getName(-1)); assertEquals((float[]) null, cosarray.toFloatArray()); try { cosarray.addAll(Collections.<org.apache.pdfbox.pdmodel.PDDocument> emptySet()); fail("should have caught ArrayStoreException"); } catch (ArrayStoreException e) { /* ignore */ } }   static final String TENBYTESTRING; private CosArrayFactory factory; protected COSArray cosarray; List

}
