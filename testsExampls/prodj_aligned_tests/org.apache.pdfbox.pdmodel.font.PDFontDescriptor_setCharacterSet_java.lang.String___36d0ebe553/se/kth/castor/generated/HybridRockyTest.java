package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeTests: {}

### JUnit Test:
@Test public final void testGetFontFile() throws Exception { PDFontDescriptor fd = new PDSimpleFont(); assertNull("Should be no file.",fd.getFontFile()); } static final String[] methods; static final int candidateIndex; static final boolean returnsValue; static final Object returnedObject; static final Throwable thrownException; }

### JUnit Test:
@Test public final void testGetAscentLineGapDescentXHeightCapHeightItalicAngleStemvStemhMissingWidthFlagIsFixedPitchEncodingSchemeMacExpertCharactersBoundingBoxFamilyClassFlagsWeightFallbackToPanoseAndFullnameAdditionalEntries() throws Exception { Assertions.fail("Not yet implemented"); } static final String[] methods; static final int candidateIndex; static final boolean returnsValue; static final Object returnedObject; static final Throwable thrownException; }

### Runtime Facts:
@Test public final void getFontFile() { this.testMethodStart(this.getClass(), FontInfoDictionary.class, "getFontFile", java.io.InputStream[].class); try { ((BooleanSupplier & Serializable) () -> { return pdfDoc == null ? null : pdfDoc.getPageByNumber((int) MathUtils.nextInt(MathUtils.TWO)) .findResources().getResourceCache().checkEmbeddedFilesForSubtype("/Filter /Flatedecode").values().stream() .filter($ -> $.getNameAsString() .equalsIgnoreCase(".ttf")) .map($ -> ($ instanceof COSStream && !isTrue($) || isFalse($())) ? new TTFParser().parse((COSStream)$).toType1Font() : null ) .collect(Collectors.toList()) ; }).expectResult(null); }) .accept(() -> { FontInfoDictionary fi = new FontInfoDictionary(); fi.addEntry("foo", new Integer[]{}); }); failuresSoFar++; throwIfAssertionsFailed(); } catch (Throwable t) { exceptionThrownOnLastFailure = t; } finally { this.testFinished(); } } static final String[] methods; static final int candidateIndex; static final boolean returnsValue; static final Object returnedObject; static final Throwable thrownException; }

### JUnit Test:

}