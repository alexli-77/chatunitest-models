runtimeOptions: {}
checkerFrameworkVersion: 2.10.1

### JUnit Test:
@Disabled("Manual test") public class MatrixGetScalingFactorTests extends AbstractPDFBoxTest { private static final Logger LOGGER = LogManager.getLogger(); @BeforeEach void setUp(@TempDirectory Path tempDir) throws IOException { mFileOut = Files.createFile(tempDir.resolve("testOutput")); } @ParameterizedTest @MethodSource({"dataProviderForTestWithoutShearScaleAndZeroTranslateX"}) void testWithoutShearScaleAndZeroTranslateX(final String name, final boolean[] inputArray, final int indexOfExpectedValueToBeCorrectlyCalculated, final float expectedResult) { assertEquals((expectedResult), new org.apache.pdfbox.util.Matrix(inputArray).getScalingFactorY(), EPSILON); } @SuppressWarnings("unused") protected static Stream<Arguments> dataProviderForTestWithoutShearScaleAndZeroTranslateX() { return Stream.<Arguments>builder().add(arguments(new Object[]{true}, 4, .0f)).build(); } @AfterAll static void teardownClass() { System.setErr(ORIGINAL_STDERR); System.setOut(ORIGINAL_OUTPUT); } @Override Class<?> getInstanceClassUnderTest() { return null; }  }
runtimeFacts:
{}

### Checkstyle Report:

### FindBugs Report:
