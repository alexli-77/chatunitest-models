package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

@Test public void testRead() throws Exception { RandomAccessFile raf = new RandomAccessFile("src/test/resources/com/tom_roush/pdfbox/pdmodel/encryption/StandardProtectionPolicyImporterTest.pdf","rw"); PdfReader pdfreader = new PdfReader(raf,"password"); COSDocument cosdoc = null; try{cosdoc = pdfreader.parseCOSDictionaryOrArray(); } finally { pdfreader.close(); }}

@Test public void testGetSignatureLength() throws IOException { assertEquals(-1, getRaf().length()); }

@Test public void testCheckClosed() throws Throwable { doThrow(new ClosedChannelException()).when(_randomaccess).position((long)anyInt()); _protimp._randomaccess = _randomaccess; Method methdodToBeTested = protclass.getMethod("checkClosed"); Object[] argsForTheMethod = {}; try { invokeAndVerifyOutputWithoutError(methdodToBeTested ,argsForTheMethod ); fail ("Expected exception not thrown."); } catch (IllegalStateException e){ verify(_randomaccess).position ((long)-1); } } private static final Class<?> protclass = StandardProtectionPolicyImporter.class ; protected   Prot();  }
runtimeFacts:

### Runtime Environment:
Apache PDFBox version : unknown
Java Version     : Java HotSpot(TM) Server VM, Oracle Corporation, 11+28
Operating System: Mac OS X, x86_64 / macosx / darwin13.0

### Failure Logs:
none

}