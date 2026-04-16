package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeTests: {}
factsAboutMethods:
### JUnit Test Case:
@Test public void testGetSubtableForWindowsSymbolSetBmpUcs2BE() throws Exception { CMAPTable table; InputStream input = new FileInputStream("src/test/resources/fonts/DejaVuSerifCondensed.otf"); OpenFont font = FontLoader.load(input); table = font.getTable(true).getCMaps(); assertEquals("\ufeff" + "\ufffd", StringUtilities.fromBytes(table.getStringAsByteArray((short)(0xffff), 0))); } static final byte[] BMPSTRING = Base64.decodeBase64ToTypedArray("AQAAEw=="); @SuppressWarnings({"unused"}) private List<byte[]> createStringListWithRepeatedCharactersAndNullTerminator(@NonNls CharSequence string) { StringBuilder sb = new StringBuilder(); char ch = ' '; boolean firstCharacter = true; while (string != null && !string.isEmpty()) { if (!firstCharacter || Character.isHighSurrogate(ch) ) { sb.append('\0'); } else { sb.setLength(sb.length()); } sb.append('\\').appendCodePoint(Character.toChars(Character.codePointBefore(string, Math.min(string.length(), MAXCHARPERLINE)))[0]); firstCharacter = false; } return Arrays.asList(Base64.encodeToString(sb.toString()).replaceAll(",","").getBytes(StandardCharsets.US_ASCII)); }  }

### Assertions:
### Facts:
### Runtime Tested Methods Coverage Results: .*

}