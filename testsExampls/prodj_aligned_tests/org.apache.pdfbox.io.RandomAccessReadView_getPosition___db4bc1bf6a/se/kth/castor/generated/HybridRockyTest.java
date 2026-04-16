package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeAssertions: {}

### JUnit Test:
@Test public void testGetPosition() throws IOException { RandomAccessFile raf = new MockRandomAccess(new byte[0]); try (InputStream is = Channels.newChannel(raf).inputStream()) { PdfBoxResourceLoader resourceLoaderMock = mock(PdfBoxResourceLoader.class); whenNew(PDFParser.class) .withArguments("test") .thenThrow(NullPointerException.class); PDFDocument pdfdoc = new PDFDocument(is, null, resourceLoaderMock); assertEquals(-1l, pdfdoc.currentPos()); } catch (IOException e) { fail("Should not throw exception"); } } @SuppressWarnings({"unchecked", "rawtypes"}) static class MockRandomAccess extends RandomAccessFile { private final ByteBuffer buf; protected MockRandomAccess(byte[] data) { this.buf = wrapByteArrayToBuf(data); super((String)"a file name"); setLength(this.buf.capacity()); } @Override int read(@NotNull byte[] b, int off, int len) throws IOException { if (!canReadBytes(len)) { return -1; }; for (int i = 0;i < len && available > 0;++i){b[off + i]=(byte)(buf.get() & 0xFF); --available;} return len; } boolean canReadBytes(final int bytesNeeded); @Override void seek(long pos); @Override String toString(); @Override FileDescriptor getFD(); @Override int write(@NotNull byte[] b, int offset, int length); @Override long skipBytes(long n); @Override synchronized native FileLock lock(long position, long size, boolean shared); @Override synchronized native FileLock tryLock(long position, long size, boolean shared); @Override native void release(FileLock flock); @Override int readInt(); @Override float readFloat(); @Override double readDouble(); @Override short readShort(); @Override char readChar(); @Override boolean readBoolean(); @Override int readUnsignedByte(); @Override int readUTFLen(int len); @Override String readLine(); @Override int readUnsignedShort(); @Override long readLong(); @Override Object clone(); @Override InputStream getChannelAsStream(); @Override long transferTo(OutputStream outstream, long count, long timeoutMilliseconds);

}