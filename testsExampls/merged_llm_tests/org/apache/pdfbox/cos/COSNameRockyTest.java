package org.apache.pdfbox.cos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class COSNameRockyTest {

    @Test public void testGetNameForUnicodeCharsAsciiRange() throws Exception { COSDocument doc = createSimpleDoc(); assertEquals("Hello World!", ((COSDictionary)(doc.getPage(0).getResources().getDictionaryObject(COSName.FONT)) .getItem(COSName.font0)).getName()); } private static final int[] BYTE_RANGE; static {BYTE_RANGE = new int[2]; BYTE_RANGE[0] = -128 ; BYTE_RANGE[1]= +127;} }

}
