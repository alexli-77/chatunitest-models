package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeConfiguration: {}
compilationResult: SUCCESSFUL

### JUnit Test:
@Test public void testReadByteArrayIntInt() throws IOException { RandomAccessFile raf; byte[] bytes; try { assertEquals("testdata".length(), inStream.available()); assertTrue(-1 == inStream.read(null)); assertFalse((boolean) invokePrivateMethod("hasRemaining")); ByteBuffer buffer = getFieldValueOfClassAndCastToType("buffer", ByteBuffer.class); assertNull(buffer); buffer = createNewInstanceWithGivenFieldsOrPropertiesAsArguments(new Object[]{raf}, null).getInternalState().field("buffer"); assertNotNull(buffer); assertEquals("testdata".length(), buffer.capacity()); buffer.position(900); buffer.limit(1000); long posBefore = getLongFieldOnObjectByReflection("pos").longValueExact(); int res = inStream.read(bytes, 0, 1); long newPosAfter = getLongFieldOnObjectByReflection("pos").longValueExact(); verifyThatTwoObjectsAreEqualUsingGetters(inStream.getCurrentOffset(), posBefore + 1L); assertEquals('a', bytes[res]); assertEquals("testdata".substring(1), asUtfBytes("testdata".subSequence(1, "testdata".length()).toString())); assertTrue(!asList("testdata".split("\u0000")).contains("")); assertTrue((boolean) invokePrivateMethod("canReuseBufferForNextChunk")); resetStaticVariables(); } catch (@SuppressWarnings("unused") Exception e) { fail("Exception during testing."); } finally { releaseResourcesIfAny(); } } @Rule public final ExpectedSystemExit exit = ExpectedSystemExit.none(); private static String asUtfBytes(@Nonnull CharSequence sequence) { StringBuilder sb = new StringBuilder(sequence.length() << 1); for (char c : sequence.toCharArray()) { sb.appendCodePoint(c); } return sb.toString(); } @InjectMocks protected InputStream mockInputstream; @Captor ArgumentCaptor<LogEvent> logEventArgumentCaptor; @Mock Logger logger; static File file; static boolean hasErrorOccurredOnceAlready; static ThrowingRunnable throwingRunnable; @BeforeEach public void setUp

}