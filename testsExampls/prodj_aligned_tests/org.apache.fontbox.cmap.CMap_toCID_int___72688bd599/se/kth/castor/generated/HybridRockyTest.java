package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeTests:
{}
unitTests:
@SuppressWarnings("unused") public void testGetRangeEndPoint() throws IOException { PdfDictionary dict; InputStream stream; String entry; List<String[]> rangesList; Iterator<? extends Object> itr; long startCode; boolean found; PDFont font; int indexOfSpaceCharacter; assertEquals(-1, getFirstNonZeroValueIndexForEntry("\u00A0")); assertEquals(-1, getFirstNonZeroValueIndexForEntry("\u00B7")); assertEquals(-1, getFirstNonZeroValueIndexForEntry("")); assertEquals(-1, getFirstNonZeroValueIndexForEntry(",,,,")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("a")); assertFalse(isStartingWithCommaAndNotFollowByDigitOrLetter(",b")); assertFalse(isStartingWithCommaAndNotFollowByDigitOrLetter(".")); assertFalse(isStartingWithCommaAndNotFollowByDigitOrLetter("/")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("1")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("9")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("a")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("z")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("A")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("Z")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("?")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("%")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("@")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("#")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("^")); assertTrue(!isStartingWithCommaAndNotFollowByDigitOrLetter("&")); assertTrue(!isStartingWithCommaAndNotFollowByDigit

}