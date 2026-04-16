package org.apache.pdfbox.pdmodel.graphics.color;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PDColorRockyTest {

    @Test public void testGetComponentsWithNullCS() throws IOException { PDDocument doc = new PDDocument(); InputStream input = TestUtils.openResource("PDFBox-data/outputRawContents.pdf"); OutputStream output = Files.newOutputStream(tempFile); Transformer transformer = TransformUtility.createTransformerForOutputFormat("application/vnd.cups-raster"); transformer.transform(input, output, new COSObjectKey[]{}); PDRenderingSettings settings = new PDRenderingSettings(); PDCoverPage coverpage = new PDCoverPage(doc); CoverPageRenderer renderer = new CoverPageRenderer(coverpage, settings, 300f); StringWriter stringwriter = new StringWriter(); PrintWriter printwriter = new PrintWriter(stringwriter); boolean ok = renderer.renderToText(printwriter); assertTrue(ok); assertEquals("\uFEFF[4 0 obj <</Length 1>> stream QQQQ endstream endobj ]%%EOF\u000A ", stringwriter.toString().replaceAll("%\\d+ beginfont.*endfont %%EOF", "%beginFont%endFont %%%EOF")); } static final class MockFactory implements InvokedMethodListener { @SuppressWarnings({"unchecked", "rawtypes"}) @Override Object onInvoke(@NotNull Method method, @NotNull Class<?> declaringClass, @NotNull List<ParameterDescription> parameterDescriptions, @NotNull Throwable throwable) { switch (method.getName()) { default: break; } return null; }   } }
    runtimeFacts:
    
    ### JUnit Test:

}
