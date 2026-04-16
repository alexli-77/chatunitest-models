package se.kth.castor.generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HybridRockyTest {

runtimeEnvironment:
JavaRuntime{version="15"}
dependencies:
compileOnly("com.google.guava:failureaccess:jar:${versions['failureaccess']}") compileOnly("javax.annotation:jsr250-api:jar:${versions['jsr250Api']}") runtime ("junit:junit:jar:${versions['junit']}", "net.sourceforge.lame:jlaw:zip:jar:${versions['libLaw']}", "commons-logging:commons-logging:jar:${versions['commonsLogging']}", "logback-classic:logback-classic:jar:${versions['logBackClassic']}", "ch.qos.logback:logback-core:jar:${versions['logBackCore']}", "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:jar:${versions['jsonDataFormatJacksonYaml']}", "com.typesafe:config:pom:${versions['typeSafeConfig']}", "com.squareup.okhttp3:okhttp:jar:${versions['okHttp']}", "de.undercouch.gradle.tasks:downloadtask:plugin:${versions['downloadTaskPlugin']}", "com.auth0:java-jwt:jar:${versions['javaJWT']}", "com.beust:klaxon:pom:${versions['klaxonPom']}", "com.github.tomakehurst:wiremock-standalone:jar:${versions['wireMockStandAlone']}", "net.sf.opencsv:opencsv:jar:${versions['openCsv']}", "uk.co.datumedge:htmlunitdriverwrapper:jar:${versions['htmlUnitDriverWrapper']}", "info.picocli:picocli:jar:${versions['picoCLI']}", "com.michaelborovoy:marathonrunner:jar:${versions['marathonRunner']}", "org.testng:testng:pom:${versions['testNG']}", "com.atlassian.performanceplugins:jiraactions:{}:pom" , "org.eclipse.jetty:jetty-server:jar:9.3.12") testCompileOnly ("junit:junit:jar:${versions['junit']}") }

}