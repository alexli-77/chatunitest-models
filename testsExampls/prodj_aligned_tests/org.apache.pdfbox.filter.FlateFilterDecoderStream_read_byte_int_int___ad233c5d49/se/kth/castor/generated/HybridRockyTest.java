package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeTests: {}
unitTests: { "testReadChunkWithLessThanOneByteLeftOverAndNoMoreToFetch": {"expectedValue": 1}, "testReadSingleBlock": {}, "testResetWhenNotWrittenBeforeOrAfterAWrite": {}, "testGetUnderlyingInputStreamDoesntCrashOnEmptyBuffer": {}, "testSkipAllButLastBitIsZero": {"expectedExceptionClass": org.junit.AssertionError.class }, "testDecompressMultipleTimesThenSeekBackward": {}, "testFullyConsumeInputAndCloseItProperly": {}, "testReadFullLength": {}, "testAvailableShouldBeGreaterEqualTotalSizeMinusPositionPlusAmountAlreadyPushedOut": {}, "testCorrectlyPushoutBitsForCurrentState": {"expectedResult": true}, "testSetUpInitializesStuffAsExpected": {}, "testEndingCaseHasRightNumberOfUnconsummedBits": {}, "testDecompressionCanHandleGarbageAtBeginning": {}, "testMarkSupported": {}, "testCreateInstanceUsingNewInstantiationPolicyWorks": {}, "testResetWillReinitializeVariablesThatAreUsedByNextMethod": {}, "testCompressedFileContainsValidContent": {}, "testReadPartialAndAdvanceOffsetSuccessfully": {}, "testResetResetsEverythingLikeItsMeantTo": {}, "testConstructorUsesDefaultParameters": {}, "testReadSomeWhereThereArentAnyExtraBytesAdditionallyToTheRequested": {}, "testChecksumVerification": {}, "testSimpleDecompressionSuceeds": {}, "testCloneCreatesAnIdenticalObject": {}, "testReadOffsetsAreOkayWhenWeHaveADifferentWindowStartPoint": {}, "testDoNothingIfOutputLimitAlreadyMet": {}, "testTwoConsecutiveSequentialReadCallsShouldProduceOnlyOneChunkPerIteration": {}, "testAvailabilityCalculatedAccordingly": {}, "testReadJustEnoughDataSoTheNecessaryInformationAboutTheRemainingDataCanBeFound": {}, "testDecompressingSmallFiles": {}, "testRewindAfterFinished": {}, "testFillTheArrayCompletely": {}, "testReadSomewhereInMiddle": {}, "testLargeRandomAccessIsPossible": {}, "testMarkCannotGoBelowZero": {}, "testReadWhole

}