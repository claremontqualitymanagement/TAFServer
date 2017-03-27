import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import se.claremont.autotest.common.logging.LogLevel;
import se.claremont.autotest.common.logging.LogPost;
import se.claremont.autotest.common.reporting.testrunreports.TafBackendServerTestRunReporter;
import se.claremont.autotest.common.testcase.TestCase;
import se.claremont.autotest.common.testrun.TestRun;
import se.claremont.autotest.common.testset.TestSet;
import se.claremont.autotest.restsupport.RestResponse;
import se.claremont.autotest.restsupport.RestSupport;
import se.claremont.tafbackend.server.HttpServer;
import se.claremont.tafbackend.server.Settings;

import java.util.List;

/**
 * Testing rest services
 *
 * Created by jordam on 2017-03-18.
 */
public class RestTest {
    RestSupport restSupport;
    TestCase currentTestCase;
    static List<String> originalTestCaseListing;
    static HttpServer server;
    static int testServerPort = 2222;
    static String testServerPortAsString = String.valueOf(testServerPort);

    @BeforeClass
    public static void classSetup(){
        TestRun.setSettingsValue(se.claremont.autotest.common.testrun.Settings.SettingParameters.URL_TO_TAF_BACKEND, "http://anyserver:81/taf");
        Settings.port = testServerPort;
        server = new HttpServer();
        server.setToTestMode();
        server.start();
    }

    @Before
    public void startup(){
        currentTestCase = new TestCase(null, "dummy");
        restSupport = new RestSupport(currentTestCase);
    }

    @AfterClass
    public static void classTeardown(){
        server.stop();
    }

    @Test
    public void postLogPostTest(){
        se.claremont.autotest.common.logging.LogPost logPost = new se.claremont.autotest.common.logging.LogPost(LogLevel.INFO, "This is the log post message.");
        String response = restSupport.responseBodyFromPostRequest("http://127.0.0.1:"+ testServerPortAsString + "/taf/v1/log", "application/json", logPost.toJson());
        Assert.assertNotNull("No response", response);
        Assert.assertTrue(response, response.toLowerCase().contains("ok"));
    }

    @Test
    public void postTestCaseTest(){
        se.claremont.autotest.common.testcase.TestCase testCase = new se.claremont.autotest.common.testcase.TestCase(null, "UnitTestTestCaseName");
        String response = restSupport.responseBodyFromPostRequest("http://127.0.0.1:" + testServerPortAsString + "/taf/v1/testcase", "application/json", testCase.toJson());
        Assert.assertNotNull("No response", response);
        Assert.assertTrue(response, response.toLowerCase().contains("ok"));
    }

    @Test
    public void viewTestCase(){
        se.claremont.autotest.common.testcase.TestCase testCase = new se.claremont.autotest.common.testcase.TestCase(null, "UnitTestTestCaseName");
        String response = restSupport.responseBodyFromPostRequest("http://127.0.0.1:" + testServerPortAsString + "/taf/v1/testcase", "application/json", testCase.toJson());
        Assert.assertNotNull("No response", response);
        Assert.assertTrue(response, response.toLowerCase().contains("ok"));
        response = restSupport.responseBodyFromGetRequest("http://127.0.0.1:" + testServerPortAsString + "/taf/v1/testcase/0");
        Assert.assertNotNull(response);
        System.out.println(response);
    }


    @Test
    public void postTestRunTest() throws InterruptedException {
        TestRun.initializeIfNotInitialized();
        TestRun.testRunName = "TemporaryTestRun";
        TafBackendServerTestRunReporter tafBackendServerTestRunReporter = new TafBackendServerTestRunReporter();
        TestCase testCase = new TestCase();
        testCase.log(LogLevel.INFO, "Message");
        tafBackendServerTestRunReporter.evaluateTestCase(testCase);
        FakeTestSet fakeTestSet = new FakeTestSet();
        tafBackendServerTestRunReporter.evaluateTestSet(fakeTestSet);
        String response = restSupport.responseBodyFromPostRequest("http://127.0.0.1:" + testServerPortAsString + "/taf/v1/testrun", "application/json", tafBackendServerTestRunReporter.toJson());
        Assert.assertNotNull("No response", response);
        Assert.assertTrue(response, response.toLowerCase().contains("ok"));
    }

    @Test
    public void getLandingPage(){
        RestResponse response = restSupport.responseFromGetRequest("http://127.0.0.1:" + testServerPortAsString + "/taf");
        Assert.assertNotNull("No response", response);
        Assert.assertTrue(response.toString(), response.isSuccessful());
        Assert.assertTrue(response.toString(), response.body.contains("TAF"));
    }

    @Test
    public void getVersion(){
        RestResponse response = restSupport.responseFromGetRequest("http://127.0.0.1:" + testServerPortAsString + "/taf/version");
        Assert.assertNotNull("No response", response);
        Assert.assertTrue(response.toString(), response.isSuccessful());
        Assert.assertTrue(response.toString(), response.body.contains("TAF"));
    }

    @Test
    public void getApiVersion(){
        RestResponse response = restSupport.responseFromGetRequest("http://127.0.0.1:" + testServerPortAsString + "/taf/apiversion");
        Assert.assertNotNull("No response", response);
        Assert.assertTrue(response.toString(), response.isSuccessful());
        Assert.assertTrue(response.toString(), response.body.contains("TAF"));
    }

    @Test
    public void logPostJsonMapperWorks(){
        LogPost logPost = new LogPost(LogLevel.INFO, "This is the log post message.");
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(logPost);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String response = restSupport.responseBodyFromPostRequest("http://127.0.0.1:" + testServerPortAsString + "/taf/v1/log", "application/json", json);
        Assert.assertNotNull("No response", response);
        Assert.assertTrue(response, response.toLowerCase().contains("ok"));
    }

    class FakeTestSet extends TestSet{

    }

}
