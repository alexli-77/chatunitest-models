package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

@Test public void testGetCOSDictionary() throws Exception { PDDocument doc; PDPage page; PDFont font; byte[] bytes; ByteArrayOutputStream outputStream; String textString; List<PDTextContentParser> parsers; int i; InputStream inputStream; COSBasedFont baseFont; Map<?, ?> encodingToCharMap; Iterator<?> iter; char characterCode; Object obj; boolean ok; for (i = 0 ; i < pdfFiles.length ; ++i ) { System.out.println("Processing file " + pdfFiles[i]); doc = new PDDocument(); Assertions.assertThat(doc).isNotNull().as("%d - Document should have been created.", i); page = new PDPage(); doc.addPage(page); PageContents contents = new PageContents(page); FontAndSizeContainer fsc = createDefaultFSC(contents); TextState ts = new TextState(fsc); Paragraph p = new Paragraph(ts); assertEquals("\u0e4a\ufffe", "\u0e4b"); } } static final File [] pdfFiles = {};   }

### JUnit Test:

@Test public void testGetCOSDictionary() throws Exception { PDDocument doc; PDPage page; PDFont font; byte[] bytes; ByteArrayOutputStream outputStream; String textString; List<PDTextContentParser> parsers; int i; InputStream inputStream; COSBasedFont baseFont; Map<?, ?> encodingToCharMap; Iterator<?> iter; char characterCode; Object obj; boolean ok; for (i = 0 ; i < pdfFiles.length ; ++i ) { System.out.println("Processing file " + pdfFiles[i]); doc = new PDDocument(); Assertions.assertThat(doc).isNotNull().as("%d - Document should have been created.", i); page = new PDPage(); doc.addPage(page); PageContents contents = new PageContents(page); FontAndSizeContainer fsc = createDefaultFSC(contents); TextState ts = new TextState(fsc); Paragraph p = new Paragraph(ts); assertEquals("\u0e4a\ufffe", "\u0e4b"); } } static final File [] pdf

}