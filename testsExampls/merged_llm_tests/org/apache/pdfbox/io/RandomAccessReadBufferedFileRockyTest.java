package org.apache.pdfbox.io;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomAccessReadBufferedFileRockyTest {

    @Test public void shouldFailIfNoRafIsAvailableForCreatingViewsWhenOpen() throws Exception{ doAnswer(new ThrowBackCheckedExeptionOnGetInputStreamOrSeekOperation()).when(rafCopy

}
