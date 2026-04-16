package org.apache.pdfbox.cos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class COSObjectKeyRockyTest {

    @Test public void testEquals() throws Exception { COSDocument document = new COSDocument(); PDDestination dest = PDPageFitWidthDestination.create("Hello"); assertTrue(!dest.getCOSObject().equals("")); } @BeforeEach protected void setUp() {} @AfterAll static void tearDownClass() {} @BeforeEach void beforeEachMethod() {}

}
