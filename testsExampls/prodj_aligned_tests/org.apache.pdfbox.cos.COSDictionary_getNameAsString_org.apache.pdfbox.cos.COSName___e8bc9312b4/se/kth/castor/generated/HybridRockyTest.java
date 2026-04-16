package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

String $RESULT$ = param1", "", ""], "parameterValues": [[null,""],[new com.googlecode.junittoolbox.ParameterizedRunnerHelper().createMock("org.apache.pdfbox.cos.COSName"),""],[(com.googlecode.junittoolbox.ParameterizedRunnerHelper()).createStub(),""],[new com.googlecode.junittoolbox.ParameterizedRunnerHelper().createMock("org.apache.pdfbox.cos.COSName"), new String()]]} ]}}
runtimeFactsAboutTests: {}

### JUnit Test:
@SuppressWarnings({"unchecked"}) public void testGetNameAsString() throws Exception { assertEquals("Helvetica", dict.getNameAsString((COSName) any)); verifyStatic(times(1), never()); assertNotNull(dict.getNameAsString((COSName) any, "")); }   static class UnitTest extends MockitoExtension implements BeforeEachCallback { protected final COSDocument doc; protected final Map<COSName, COSBase> map; protected final Dictionary dict; @BeforeEach void setUp(@TempDir Path tempDirectory) throws IOException { whenNew(COSDictionary.class).withNoArguments().thenAnswer(__ -> mock(COSDictionary.class)); doc = spy(new COSDocument(map)); doNothing().when(doc).accept(any(IFilterVisitorProxy.class)); dict = new Dictionary(doc); resetAllFieldsOfClass(this.getClass()); PowerMockito.spy(PdfBoxFactory.getInstance()); setupPowerMockForFinalClasses(); } private static boolean shouldIgnoreMethod(Method methodUnderTest) { for (Predicate<? super Method> predicate : METHODS_TO_IGNORE) {

}