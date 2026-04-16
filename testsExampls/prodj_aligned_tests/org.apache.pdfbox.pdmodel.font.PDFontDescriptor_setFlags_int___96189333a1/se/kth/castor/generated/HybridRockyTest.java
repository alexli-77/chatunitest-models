package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeAssertions: {}

### JUnit Test:
@Test public final void testSetFlags() throws IOException { FontFileReader reader = mockery.checking(new Expectations(){{ oneOf (reader).readFontHeader(); will(returnValue("True Type")); }}); PDFontDescriptor descr = createPdfontdescriptorWithoutFlags(reader); assertEquals(-59, descr.getAscent(), DELTA); assertFalse((descr.isFixedWidth())); assertNull(descr.getName()); assertNotNull(descr.getFamilyClass()); int[] classArray = { -4 }; byte[] array = IntStream.of(*classArray* ).mapToObj({ i -> ((byte)(i & 0xFF)) }).toArray(Byte::new ); String expectedString = Arrays.toString(array); ByteBuffer buffer = ByteBuffer.allocateDirect(expectedString.length()).order(ByteOrder.LITTLE_ENDIAN); for (char c : expectedString .toCharArray()) { buffer.put((byte) c); } when(stream.available()).thenThrow(IOException.class); try { descr.setFlags(32); fail("Should throw an exception"); } catch (IllegalStateException e){ verify(reader).close(); Assert.fail("Wrong message:" +e.getMessage()); } finally { stream.resetPosition(); } } private static PDFontDescriptor createPdfontdescriptorWithoutFlags(@SuppressWarnings("unused") FontFileReader reader) { return new PDFontDescriptor(); }  }
runtimeFacts:
{}

### Junit Result:
Runtime Facts are not provided by JUnit.

### Runtime Metrics:
Statistics of invoking `setFlags` method on average over all runs: 0 calls per second; Total time spent in `setFlags`: 0 seconds. Average execution speed was calculated from runtime metrics and is based only on data that has been collected during a single run of tests without any modifications to source code or dependencies between them. It can be affected by factors like JVM version used for running the test suite, hardware specificities etc. To get more precise statistics you should enable runtime metric collection while executing your unit tests using `-Dtestng.metrics=true`. For details see http://maven.apache.org/plugins/maven-

}