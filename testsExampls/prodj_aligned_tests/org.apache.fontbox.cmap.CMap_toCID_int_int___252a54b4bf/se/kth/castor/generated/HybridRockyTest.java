package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

@Test public void testWithIntParameterAndTwoByteValueShouldBeCorrectForCode0x0020() throws Exception { assertEquals("Wrong result.", Integer.valueOf(3), new TestClass().testMethod()); } static final class TestClass extends BasePdfBoxTestCase { private int testMethod() throws IOException { CMap cm = createCM(); Pair pair = new Pair(new Character('a'), 0x0020); int[] bytes = new int[pair.getValue().toString().length()]; ArrayUtils.fillArray(bytes, ' ', charset.encodeCharactersAsBytes(charset)); int key = charset.convertCharacterToEncoding(pair.getKey(), bytes); return cm.toCID(key, -1); } protected CMap createCM();  }   runtimeFacts: {}

### JUnit Test:

}