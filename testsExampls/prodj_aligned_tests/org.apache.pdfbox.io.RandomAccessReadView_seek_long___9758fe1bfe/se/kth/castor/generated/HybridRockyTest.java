package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeTests:
{}
compileTimeFacts: {}

### JUnit Test:
@Test public void testSeek() throws IOException{ final RandomAccessRead mockRAF = Mockito.spy(rafMock()); rafReaderUnderTest.setSourceStream(mockRAF); try{rafReaderUnderTest.seek(-5l); fail("should have thrown exception"); }catch(IllegalArgumentException e){ assertEquals("Invalid position -5",e.getMessage()); verifyZeroInteractions(mockRAF);}try{rafReaderUnderTest.seek((int)-1);fail("Should have caught an illegal argument.");} catch( IllegalArgumentException ex ) { Assertions.expectThrows(ex,"Wrong offset value.",IllegalArgumentException::new );} try{rafReaderUnderTest.seek(Integer.MAX_VALUE+1l);fail("Should have caught an illegal argument.");} catch( IllegalArgumentException ex ){Assertions.expectThrows(ex,"Wrong offset value.",IllegalArgumentException::new );} } @BeforeEach protected void setUp(){ this.rafReaderUnderTest = new RandomAccessFileReaderImpl(); } private static org.junit.jupiter.api.extension.ExtensionContext.Store getCurrentStore(){ return null; }  }
runtimeFacts:

### JUnit Test:
@Test public void testGetEOFPositiveValue()throws Exception { byte[] buffer = new byte[2]; int lengthOfBuffer = 2; rafReaderUnderTest.read(buffer, 0 ,lengthOfBuffer); rafReaderUnderTest.getEnd(); rafReaderUnderTest.close(); }  @AfterAll static void tearDownClass(){ System.out.println("@AfterAll method of "+TestRandomAccessFileReader.class+" running!"); }  }
runtimeFacts:

### JUnit Test:
@Nested class GetBytes extends AbstractTestCaseWithRaf { byte [] readArray ; @Test void shouldThrowWhenNullByteArrayPassedAsParameterToMethod(@TempDir Path tempDirectory) throws IOException { ByteArrayOutputStream baos = new ByteArrayOutputStream(); File file = Files.createTempFile(tempDirectory,null,".tmp").toAbsolutePath().normalize().toFile(); OutputStream os = new BufferedOutputStream(baos); IOUtils.copyLarge

}