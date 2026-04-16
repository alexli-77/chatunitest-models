package org.apache.fontbox.ttf;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HorizontalMetricsTableRockyTest {

    @Test public void testGetLeftSideBearing() throws Exception { assertEquals(-3, ttfReader.hmt.getLeftSideBearing(2)); assertEquals(-1, ttfReader.hmt.

}
