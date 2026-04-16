package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeFactsWithAssertionsEnabled:
{}
factsAboutMethodsOnClasspathOrRuntimeScopeForMethodBeingAnalyzed:
{@NotNull public static Point2D getTransform(@Nonnull Matrix m, @Nonnull Rectangle rect){ double[] values = m.getValues(); AffineTransform at = createAffineTransformation(values); if(!at.isIdentity())rect = applyToRect(at,new Double[]{rect.x,rect.y,rect.width+rect.x,rect.height+rect.y}); return new org.apache.commons.math3.geometry.euclidean.twod.Vector2D(rect.centerX(), rect.centerY()); }   private  Vector2D getOriginAfterRotationAndScalingApplied(@Nonnull final Line line); void setValue(final int index, final float value); boolean isSingularityPresent(); abstract boolean equals(Object o); int hashCode(); String toString(); Object clone();  }
runtimeFactsWithoutOptionalParameters: {}

### JUnit Test:
@Disabled("Needs test data") @ParameterizedTest @ArgumentsSource(NonZeroArgumentProvider.class) public void testGetScale(double scaleX, double scaleY, double expectedResultX, double expectedResultY) throws Exception { assertEquals(expectedResultX, actualInstanceUnderTest().scaleX(scaleX), DELTA); assertEquals(expectedResultY, actualInstanceUnderTest().scaleY(scaleY), DELTA); } @SuppressWarnings({"unused","WeakerAccess"}) class NonZeroArgumentProvider implements ArgumentsProvider { @Override public Stream<? extends Arguments> provideArguments(ExtensionContext context) { return Arrays .asList(arguments(-0.0d,-0.0f), arguments(Double.NaN, Float.MIN_VALUE), arguments(Double.POSITIVE_INFINITY, -Float.MAX_VALUE)).stream () .map(args -> args.toArray()). map(Objects::nonNull). filter(Boolean ::booleanValue ). flatMap(arg -> arg ? Collections.<Arguments>emptySet().stream() : Stream.of(arguments(0.0d, 0.0f))); } @SafeVarargs ArgumentCaptor<Arguments> argumentCaptorOf(BiFunction<Double,Float,Arguments

}