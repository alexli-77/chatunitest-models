package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeFactsAboutThisMethod: {"startLine": -1, "lineRangeLength": 15}, runtimeCheckingResultsForThisMethod: {}

### JUnit Test:
@Disabled("WIP") public void testParse() throws Exception { String filepath = getFile("/fonts/TrueType/ArialUnicodeMS.ttf").getAbsolutePath().replace('\\', '/'); InputStream inputstream = Files.newInputStream( Paths.get(filepath), StandardOpenOption.READ ); RandomAccessRead rar = new RandomAccessBuffer(inputstream); byte[] bytes = IOUtils.toByteArray(rar); assertEquals("\uFDFE\r\n%!PS-AdobeFont-   Type1 ", new String(bytes, 0, Math.min(bytes.length, 25))); ByteProvider provider = new ArrayByteProvider(bytes); PDFont font = PDDocument.loadNonSeq(provider).getPage(0).findDescendantOfClass(PDFont.class); List<COSBase> tokens = COSArrayList.asList(PDResources.createDefault(), font); for (COSBase base : tokens) { System.out.println(base + ": " + base.toString()); } } static class FakeRandomAccessReader extends DefaultRandomAccessSource implements SeekableResource { private final long length; int position; protected FakeRandomAccessReader(byte[] bb, boolean readOnly, long len) throws FileNotFoundException { super(bb, readOnly ? -1L : len); this.position = 0; this.length = len; setPosition(len / 2); } @Override public synchronized long getCurrentPos() throws IOException { throw new UnsupportedOperationException(); } @SuppressFBWarnings({"RV_RETURN_VALUE_IGNORED_BUT_SUBCLASS_TESTS_IT","RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE"}) @Override public synchronized long seek(long pos) throws IOException { if (!readonly && pos < size()) { position = (int)(pos % bufferSize == 0? pos / bufferSize * bufferSize: pos); return position; } throw new RuntimeException("Seek not implemented"); } @Override long getModificationDate(); @Override long getLastModifiedDate

}