package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

@Test public void testGetLength () throws IOException { assertEquals("Should have returned correct value.", 14264l, cosDoc.streamToSave.getLength()); }   static final String SCHEMA; static final int VERSION; }
Runtime fact:
{int invocationIndex(); boolean returnsValue (); Object[] parameterArrayForMethodCallOnMockObject(); Class<?> declaringClassOfParameterWithGivenPosition(); Method methodBeingRewrittenByThisRule(); Annotation[] annotationsOnDeclaredThrowablesAnnotatedAsThrownByTheMethodUnderTest(); List<Annotation> declaredCheckedExceptionsAndUncheckedErrorsOrNullIfNotAvailable(); Type genericReturnType(); Annotation[] genericExceptionTypes(); Runtime runtimeInformationAboutCurrentlyExecutingCodeLocation(); InvocationContext contextInformationAboutCurrentExecutionPoint(); Matcher matcherThatCanMatchArgumentsAgainstExpectedPatterns(); ArgumentCaptor argumentCaptorForArgumentNumberN(); Object actualArgumentPassedWhenMethodIsCalled(); boolean mockHasAlreadyReceivedACallToVerifyThisInteractionBeforeAnyMoreActualCallsAreAllowed(); int numberOfTimesAFinalizableAnswerProvidedAResultWhichCannotChangeBetweenSubsequentRequestsByConsumers(); Iterator iteratorOverAllPossibleFinalResultsSuppliedByANotifyingAnswerUntilOneSpecifiesItWillNoLongerGenerateNewResults(); Answer answerProvidingOnlyOncePerCallABasicResponse(); Result resultProducedAfterLastTimeAMatchingAnswerRespondedTrue(); MockitoCore core;}
Checker Framework version: 9.0.1

### JUnit Test:

@Test public void testGetLength () throws IOException { verify(this.cosDocument).setSize(eq((long)14264)); when(this.cosWriter.writeContainerEntry(any())).thenReturn(new ByteBufferBackedOutputStream(), null); assertFalse("There should not yet exist a byte buffer for writing to the container entry body section of this object.", ((ByteBufferBackedOutputStream)(this.testee)).hasBytes()); this.byteBufferOutputSteamWrapper.close(); verify(this.cosWriter).endIndirectObjectsSectionBodyEntries(); verifyZeroInteractions(this.cosDictionary

}