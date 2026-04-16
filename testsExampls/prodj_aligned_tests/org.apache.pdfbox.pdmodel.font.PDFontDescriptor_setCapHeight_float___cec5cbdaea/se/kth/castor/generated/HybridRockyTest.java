package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeAssertions: {}
coverageResults: {"classes": {}, "packages": {"org.apache.pdfbox.util.awt": .95, "org.apache.pdfbox.pdmodel.common.PDRectangle": 1, "java.io.InputStreamReader": 1}, "testClasses": {}, "classLoadTimes": {}}, runtimeTests: []
buildResult: SUCCESS
runtimeEnvironment: JDK_ANDROIDX_UNITTESTED
runtimeDeps: pdfbox, junit

### JUnit Test:
@Test public void testSetFontBBox() throws IOException { PDFontDescriptor fd = new PDFontDescriptor(); assertNull("default value should be null",fd.getFontBoundingBox()); PDRectangle rect = new PDRectangle(-1f,-1f, -1f, -1f ); fd.setFontBoundingBox(rect); assertEquals(new Rectangle2D.Float((int)-1,(int)-1 , (int)-1, (int)-1), fd.getFontBoundingBox().toGeneralPath().getBounds2D(), DELTA); }   static final float EPS; static { if ("true".equalsIgnoreCase(System.getProperty("awt.headless"))) { System.out.println("Running in headless environment"); EPS = Float.NaN;} else{EPS = EPSILON*Math.max(Float.MIN_VALUE, Math.ulp(Float.MAX_VALUE)); }} private boolean isHeadlessEnv(); @BeforeEach void setup(){ } @AfterAll static void tearDownClass()throws Exception{} @Test void testGetPostScriptEncoding() throws IOException; @Test void testIsSymbolicTrue(); @Test void testIsEmbeddedFalse(); @Test void testHasKernPairsFalse(); @SuppressWarnings({"unused","deprecation"}) @Deprecated @Test void testCreateDefaultResourceCacheKeyForInvalidCharacters(); @Test void testCreateDefaultResourceCacheKeyWithSingleCharacterString(); @Test void testCreateDefaultResourceCacheKeyWithMultipleCharacterStrings(); @Test void testSetItalicAngleNegativeValue(); @Test void testSetStemVZero(); @Test void testToStringEmpty(); @Test void testToUnicodeArrayNoMappings(); @Test void testParseUnknown

}