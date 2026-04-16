package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeTests:
{}
facts:
{}
jUnitTests:
@Test public void testEquals() throws Exception { COSDocument document = new COSDocument(); PDDestination dest = PDPageFitWidthDestination.create("Hello"); assertTrue(!dest.getCOSObject().equals("")); } @BeforeEach protected void setUp() {} @AfterAll static void tearDownClass() {} @BeforeEach void beforeEachMethod() {} @Test public final void testEquals() throws Exception; }
runtimeFacts:
@SuppressWarnings({"unused", "WeakerAccess"}) class Test { private int aPrivateIntFieldWithoutGetterOrSetter; String[] anArrayOfStrings; List<String> aListOfStrings; Map<Integer, Integer> someMap; Set<Double> someSet; Vector vector; byte bytes[]; long getALong(); short getASignedShort(); float aFloatValue(); double aDoubleValue(); char myCharValue(); Boolean boolValue(); Date date; BigDecimal bigDecimals; ByteBuffer buffer; Calendar calendar; Class<?> clazz; Collection collection; Object object; Iterator iterator; InputStream inputStream; OutputStream outputStream; Reader reader; Writer writer; Throwable throwables; Thread thread; Future future; Enum enumConstant; InetAddress inetAdress; Locale locale; TimeZone timeZone; ZoneOffset zoneOffsets; Rectangle rectangle; Point point; Matrix matrix; Dimension dimension; Color color; Pattern pattern; AffineTransform transform; Font font; URI uri; URL url; Path path; Package packge; Method method; Constructor constructor; Field field; Serializable serializedObj; StackTraceElement stacktraceElements; Annotation annotation; AccessibleObject accessibleObjects; Invokable invoker; Event event; Type type; GenericDeclaration genericDeclartions; Element element; Document doc; Node node; X509Certificate certificate; KeyStore keyStores; PrivateKey privatesKeys; Certificate certificationChain; PublicKey publikKey; JAXBContext jaxbContext; SOAPMessage soapMessages; XMLGregorianCalendar gregorianCalendars; UUID uuid; AtomicBoolean atomicBooleans; AtomicByte atomicBytes; AtomicCharacter atomicChars; AtomicDouble atomicDouble; AtomicFloat atomic

}