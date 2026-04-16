package org.apache.pdfbox.pdmodel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PDAppearanceContentStreamRockyTest {

    @Test public void testSetStrokeWithoutColorShouldNotFail(@Mock final PDCircle circle, @Capturing final COSDictionary cosObject ) throws Exception { doNothing().when(cosObject).writeTo(any(COSBaseOutputSubstream.class)); whenNew(COSDictionary.class ).withNoArguments().thenAnswer((final InvocationOnMock invocation) -> { return new COSDictionary(){}; }); final PDRadialGradient gradient = mock(PDRadialGradient.class); when(circle.getGraphicsState()).thenThrow(new IllegalArgumentException("error")); assertFalse(appearenceCS.setStrokingColorOnDemand(null)); } @BeforeEach public void setup() { MockitoAnnotations .initMocks(this ); appearenceCS = spy(new PDAppearanceContentStream(document, page, appearanceResources, true)); document = mock(PDFunctionDocument.class); page = mock(PDPage.class); when(page.findResourceCacheValue( anyString())). thenReturn(null); when(page.createRetrievalInputStream(any(), anyInt())) .thenReturn(null); resources = mock(PDResources.class); when(resources.add(isA(COSDictionary.class))) .thenReturn(true); when(page.getResources()).thenReturn(resources); when(page.appendRawCommands(anyList())).thenAnswer((i)->{return i.<Collection<String>>getArgument(0).toString();}); when(page.hasContents()).thenReturn(false); appStreamDict = new COSDictionary(); when(page.getLastContainedStream()).thenReturn(appStreamDict); when(page.beginVariableText()).thenReturn(textPositioningContext); when(page.endVariableText()).thenReturn(textRenderInfo); when(page.closeVariableText()).thenReturn(textLayoutEngineResult); when(page.drawFormattedImage(anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyBoolean(), eq(false), anyFloat(), anyFloat())); when(page.drawImage(eq(image))); when(page.insertAndDrawTransparencyGroup(anyFloat(), anyFloat(), anyFloat(), anyFloat(), any(), any(), anyMap

}
