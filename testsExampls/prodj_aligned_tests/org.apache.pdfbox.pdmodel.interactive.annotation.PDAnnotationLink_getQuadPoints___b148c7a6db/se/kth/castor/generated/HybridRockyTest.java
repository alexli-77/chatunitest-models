package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeData: {}
runtimeMetrics: {"executionTimeMillisTotal": 5905, "executionsPerSecondMaximum": 23.5, "instancesCreatedCountTotal": 1}, runtimeWarnings: {}, compilationResult: SUCCESSFUL, methodDebugInfo: {} }
runtimeFacts:

### JUnit Test:
@Test public void testGetQuadPointsNullValueForNoKey() throws Exception { PDAcroForm acroform = new PDAcroForm(); pdDocument.setAcroForm(acroform); assertThat("Should be no value for key \"quadPoint\" in dictionary.", linkAnnot.getQuadPoints(), is((null))); }

### Runtime Environment:
PDFBox version: pdfbox-app-2.0.24
Operating System: Mac OS X 10.13.6 (High Sierra) x86_64 ibm@Apple-MacBook-Pro ~ % java -version Java HotSpot(TM) 64-Bit Server VM launcher: Apple Inc. JavaFX WebViewLauncher The Java™ Virtual Machine launched using 1.8.0_192 --enable-preview Version: Eclipse OpenJ9 Assembly Build jvmci-9+19 .jar to run with -Xmixed (-XX:+UnlockExperimentalVMOptions -XX:-UseCompressedOops). Max Heap Size: 1g Memory Used: 4 MB Free Space: Total: 8 GB Actual:  5% Committed: 288MB Recommended: Unlimited Limit: 4GB Classpath: /Library/Java/JavaVirtualMachines/jdk1.8.0_192.jre/lib/ext/apple_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_192.jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_192.jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_192.jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_192.jre

}