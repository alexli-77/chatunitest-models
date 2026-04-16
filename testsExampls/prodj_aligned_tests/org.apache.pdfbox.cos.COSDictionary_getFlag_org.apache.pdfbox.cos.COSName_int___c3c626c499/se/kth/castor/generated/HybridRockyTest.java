package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

@Test public void testGetBoolean() throws IOException { Map<String, String> map = new HashMap<>(); map.put("testFile", PDFBOX_TESTING_FILE + "helloWorldWithAnnotations.pdf"); for (Map.Entry entry : map.entrySet()) { PdfDocument pdfDoc; try (PDDocument pdPdfDoc = Loader.loadPDF((String) entry.getValue())) { pdfDoc = new PdfDocument(pdPdfDoc); Assert.assertEquals("wrong type returned", Boolean.TRUE, ((DictionaryWrapper) pdfDoc).getObject().getItem(new PdfName("IsValid")).getAsBool()); } } }  static final String PDFBOX_TESTING_DIR; private static File createTempDir(); static synchronized InputStream openResource(final Class<?> clazz, final String resourceFileName); static final char[] BASELINE_CHARSET; static final String UTF8; static final Charset CHARSET; static final byte[] EMPTY_BYTEARRAY; static final RandomAccessReadSource RAWSOURCE; static final long MAXIMUM_SIZE; static final List<Integer> MULTIREADER_SIGNS; static final float DEFAULT_FLOAT; static final double DEFAULT_DOUBLE; static final Integer ZERO_INT; static final Long ZERO_LONG; static final Double NEGATIVE_ONE_DOUBLE; static final Float NaN_VALUE; static final ByteArrayOutputStream DATASTREAM_OUTPUT; static final OutputStream DELETEABLE_DATASTREAM_OUTPUT; static final String FILENAME_FORBIDEN_CHARACTERS; static final Set<Character> FORBIDDEN_

}