package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeFactsAfterAnalysis:

### JUnit Test:
@Disabled("test disabled") public void testGetBoolean() throws IOException { PDDocument doc = new PDDocument(); PDFont font = PDType1Font.TIMES_ROMAN; String text = "Hello World"; float widthOfText = font.getStringWidth(text)/font.getUnitsPerEm(); Assertions.assertEquals((float)(widthOfText*doc.getNumberOfPages()), doc.calculatePageSize().height - doc.calculateMediaBoxHeight()); } static class GetMethods implements IMethodSelector{ @Override public List<IMethodDescription> selectMethodsToRecord(List<Class<?>> classes) { ArrayList<String> selectedClasses = new ArrayList<>(); for( Class clazz : classes){if (!selectedClasses.contains(clazz.getName())) {selectedClasses.add(clazz.getName()); }} System.out .println("\rSelected Methods"); int i = 0 ;for( Class clazz : classes ){System.out .print('\u001B'); ++i % 10==0 ? System.out .printf("%d/%d ",i+1,classes.size()) : System.out .print('.'); assertTrue(!selectedClasses.isEmpty(),"No methods were recorded."); } System.out .println(' ' + classes.size()+" classes analyzed." ); return Collections.<IMethodDescription>emptyList(); } @SuppressWarnings({"unchecked","rawtypes"}) @Override Collection<Class> selectInterceptorsForPackage(Collection packageNameSet) { throw new UnsupportedOperationException("Not supported yet."); }   }  }

### Runtime Facts Before Analysis:
[ ]

### Results After Analysis:
[{"type":"BEFORE"},{"lineNum":3,"classDesc":{"name":"PDFontUtilsTest","className":"com.adobe.acrobat.pdfxrefviewer.utilities.tests.PDFontUtilsTest"},"signature":"boolean getBoolean(@Nonnull final Map map,final Object[] parameters), final Boolean result) { super(map,parameters); this.result = result;} },{"type":"AFTER"},{"lineNum":15,"classDesc":{"name":"PDFontUtilsTest","className":"com.adobe.acrobat.pdfxrefviewer

}