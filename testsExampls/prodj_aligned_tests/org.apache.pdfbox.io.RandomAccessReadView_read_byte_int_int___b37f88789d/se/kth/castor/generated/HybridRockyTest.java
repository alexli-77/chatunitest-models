package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeTests:
{}
facts: {}
coverageByProject: {}
coverageSummaryPerClass: {}
buildResult: SUCCESSFUL
durationMilliseconds: 90
testDurationSeconds: 0.00652
testsFailed: true
failureMessage: null
runtimeInformation: Runtime Information for Testing Method org.apache.pdfbox.io.RandomAccessReadView#read(byte[],int,int): @SuppressWarnings("resource") public final void testPDFBoxIssue246_RaiseExceptionIfTryToWriteAfterClose(@Mocked RandomAccessFile mockRAF, byte[] dataArray, int startOffset, int lengthOfDataBlock, long expectedStreamSize){ new Expectations(){{ one(mockRAF).getChannel(); result = mockChannel; }}; final ByteBuffer bufferForInputStream = ByteBuffer.wrap(dataArray); final MockUp<ByteBuffer> mockBuf = new MockUp<ByteBuffer>(bufferForInputStream) {}; assertThatThrownBy(() -> rafv.write(startOffset + lengthOfDataBlock, dataArray)).as("writing to closed file").hasRootCauseInstanceOf(IllegalStateException.class).withFailMessageContaining("This PDFBoxRandomAccessReader was already closed"); } runtimeAssertions: [] compileTimeErrorMessages: [] staticAnalysisErrors: []

### JUnit Test:
@Test public void testPDFBoxIssue246_RaiseExceptionIfTryToWriteAfterClose(@Mocked RandomAccessFile mockRAF, byte[] dataArray, int startOffset, int lengthOfDataBlock, long expectedStreamSize){ new NonStrictExpectations(){{ one(mockRAF).close(); times = 1;} }; new StrictExpectations(){{ one(rafv).length(); returns((long)expectedStreamSize); one(mockRAF).seek(anyLong); times = 1; one(randomAccessRead).available(); returns(-1L); one(mockRAF).close(); times = 1; one(randomAccessRead).setEof(); times = 1; one(randomAccessRead).markIsSet(false); }}; try { rafv.write(startOffset + lengthOfDataBlock, dataArray); fail("should throw IllegalStateException!"); } catch (final

}