package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

@Test public void testGetLong() throws IOException { try (PDDocument doc = new PDDocument()) { byte[] bytes = Files.readAllBytes(new File("target/test-data/helloWorldDocWithRefXrefs.pdf").toPath()); ByteBuffer buffer = java.nio.ByteBuffer.wrap(bytes); PDFParser parser = new DefaultPDFParser().parse(buffer, doc); assertEquals(-1l, doc.getPages().get(1).findResourcesOfPage((int)(doc.getFirstNonBlankPageNum())) .getExtGState("/GS").asDictionary().getIntOrDefault("ca", 0)); } } private   HelloWorldITest(); }

}