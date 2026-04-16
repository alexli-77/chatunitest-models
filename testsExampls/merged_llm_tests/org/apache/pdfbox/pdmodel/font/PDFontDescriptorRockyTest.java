package org.apache.pdfbox.pdmodel.font;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PDFontDescriptorRockyTest {

    @Test public void testGetFlags() throws Exception { assertEquals("should be zero when not set in dictionary.", 0, descr.getFlags()); } staticMethodTests:

    @Test public void testSetFontBBox() throws IOException { PDFontDescriptor fd = new PDFontDescriptor(); assertNull("default value should be null",fd.getFontBoundingBox()); PDRectangle rect = new PDRectangle(-1f,-1f, -1f, -1f ); fd.setFontBoundingBox(rect); assertEquals(new Rectangle2D.Float((int)-1,(int)-1 , (int)-1, (int)-1), fd.getFontBoundingBox().toGeneralPath().getBounds2D(), DELTA); }   static final float EPS; static { if ("true".equalsIgnoreCase(System.getProperty("awt.headless"))) { System.out.println("Running in headless environment"); EPS = Float.NaN;} else{EPS = EPSILON*Math.max(Float.MIN_VALUE, Math.ulp(Float.MAX_VALUE)); }} private boolean isHeadlessEnv(); @BeforeEach void setup(){ } @AfterAll static void tearDownClass()throws Exception{}

    @Test void testHasKernPairsFalse(); @SuppressWarnings({"unused","deprecation"}) @Deprecated

}
