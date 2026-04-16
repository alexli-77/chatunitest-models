package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeConfiguration: {}
facts:

### JUnit Test:
@Test public void testGetRectangle() throws IOException { PDAcroForm acroform = document.getDocument().getAcroForm(); assertEquals("Wrong number of annotations!", 1, acroform.getAnnotations().size()); PDAnnotationWidget widget = (PDAnnotationWidget)acroform.getFirstPageChildNodeSubtree().iterator().next(); LOGGER.info("widget={}", widget); Optional<PDFunction> functionOptional = FUNCTION_EXTRACTOR.apply(widget).flatMap(Function::toPdfBoxFunctionOrNullIfNotAFunction); AssertionsForClassTypes.assertThat(functionOptional).isPresent(); final String pdfTextContentAsJsonString = JsonUtilities.jsonifyWithoutCyclesAndPrettyPrinting(FUNKYBOXES_JSON_PRODUCTION_RULESET.createProductionRuleset(), functionOptional.orElseThrow()).toString(); System.err.println(pdfTextContentAsJsonString); JSONAssert.assertEquals(pdfTextContentAsJsonString, EXPECTED_OUTPUT_STRING, true); } @BeforeEach protected abstract void setUpAbstractBasedOnProperties(@TempDir Path tempDirectoryPathToDeleteAfterwards); static Logger LOGGER; static FunctionExtractorImpl FUNCTION_EXTRACTOR; static ProductionRuleSet PRODUCTION_RULES; static List<PropertyDescriptor<?>> ALLOWABLE_FIELDS; static ExpectedOutputFactoryExpectation EOFAE; static Stream<Arguments> streamOfParametersWhereThisMethodIsSuccessfulAccordingToContractSpecificationWhenThereAreNoErrorsWhichShouldBeReportedIndividuallyButStillGeneratesValidJsonObjects(); static Stream<Arguments> streamOfParametersWhereThisMethodThrowsExceptionalConditionsUponFailure(); static Map<Integer, String[]> VALUE_TO_NAMESPACE_MAP; }
runtimeFacts:

### Runtime Fact #1:
{ "@type": "io.deephaven.util.annotations.Externalizable", "externalizationSchemaUri": "https://raw.githubusercontent.com/Deeplearning4j/nd4j/v1.0.0-beta7/nd4j-backends/nd4j-api-parent

}