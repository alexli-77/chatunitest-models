package org.apache.pdfbox.pdmodel.graphics.form;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PDFormXObjectRockyTest {

    @Test public void testGetResources() throws IOException { PDDocument document = Loader.loadPDF("simpleforms"); assertNotNull(document); List allPages = document.getPageTree().getAllKids(); assertEquals(allPages.size(), 1); Page page = ((PDPage)allPages.get(0)); ContentScanner scanner = new ContentScanner((List<?>)page.getContent()); while (!scanner.isEOF()) { int opCode = scanner.readOperator(null); switch (opCode){ case Operator.CONCAT:{ float[] values = scanner.nextOperandWordsAsFloatArray(false, true); break; }case COSWriter.OPERATOR_GET_FORM_DICTIONARY:{ String nextWord = scanner.getStringValue(!true); if ("Do".equalsIgnoreCase(nextWord)){ Object o = scanner.scanNextToken(); try{ PDFormXObject xobj = (PDFormXObject)(o instanceof PRIndirectReference?document.getResourceStore().checkAdditionalObjects():o).getCOSObject(); assertNotNull(xobj); PDResources res = xobj.getResources(); assertTrue(res == null || !"resourceDictRef".equals(xobj.getName())); break;}catch (IOException e){ fail("Could not retrieve or parse dictionary entry."); }}break; default :fail("Unexpected operator code '" + Operator.lookup[opCode] + "'!"); break;}}} } }

}
