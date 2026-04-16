package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeAssertions:
{}

### JUnit Test:
@Disabled @DisplayName("testLength") void testLength(final RandomAccessReadBuffer subject) throws IOException { final var expectedValue = (subject == null ? -9 : subject.getSize()); assertEquals(expectedValue, subject.length(), () -> String.format("%d != %d", expectedValue)); } static class UnitTests extends BaseMockitoTest<RandomAccessReadBuffer> { @BeforeEach protected void setUpOnce(@SuppressWarnings("unused") ExtensionContext context) { System.setProperty("junit.jupiter.execution.parallel.enabled", Boolean.TRUE.toString()); this.unitUnderTest = new MockBuilder().build(this); } @ParameterizedTest @MethodSource("provideSubjectWithNullOrNotNullAndCheckForException") void testLength(final Exception exceptionExpectedToBeThrownByLambdaIfAny, final ThrowingConsumer<RandomAccessReadBuffer> lambdaThatShouldThrowAnErrorWhenTheArgumentIsNullButDoesntHaveToDoSo); @TestFactory Stream<DynamicContainer> provideSubjectWithNullOrNotNullAndCheckForException(); @AfterAll static void afterClass(); } static class IntegrationTests {} }
runtimeFacts:
{"totalInvocations": 1444, "methods": [{"methodId": "org.apache.pdfbox.io.RandomAccessReadBuffer#length()", "invocationCount": 10, "voidReturns": 0, "valueReturns": 10, "nestedCalls": 0, "mockedCalls": 0, "parameterShapes": [], "returnedValues": ["long $OBJ$ = 475L", "long $OBJ$ = 680L", "long $OBJ$ = 15776L", "long $OBJ$ = 12742L", "long $OBJ$ = 22484L"] , "parameterValues": []}, {"methodId": "org.apache.commons.logging.LogAdapter#logDebug(String)", "invocationCount": 1, "voidReturns": true, "valueReturns": 0, "nestedCalls": 0, "mockedCalls": 0,"parameterShapes":[],"returned

}