import org.junit.*;
import se.claremont.autotest.common.testcase.TestCase;
import se.claremont.autotest.restsupport.RestSupport;
import se.claremont.tafbackend.server.HttpServer;
import se.claremont.tafbackend.server.Settings;
import se.claremont.tafbackend.storage.TestCaseList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordam on 2017-03-19.
 */
public class WebTest {
    RestSupport restSupport;
    TestCase currentTestCase;
    static List<String> originalTestCsaeListing;
    static HttpServer server;
    static int testServerPort = 2222;
    static String testServerPortAsString = String.valueOf(testServerPort);

    @BeforeClass
    public static void classSetup(){
        Settings.port = testServerPort;
        originalTestCsaeListing = TestCaseList.jsonStringList;
        TestCaseList.jsonStringList = new ArrayList<>();
        server = new HttpServer();
        server.start();
    }

    @AfterClass
    public static void classTeardown(){
        server.stop();
        TestCaseList.jsonStringList = originalTestCsaeListing;
    }
    @Before
    public void startup(){
        currentTestCase = new TestCase(null, "dummy");
        restSupport = new RestSupport(currentTestCase);
    }


    @Test
    public void viewTestCase(){
        se.claremont.autotest.common.testcase.TestCase testCase = new se.claremont.autotest.common.testcase.TestCase(null, "UnitTestTestCaseName");
        String response = restSupport.responseBodyFromPostRequest("http://127.0.0.1:" + testServerPortAsString + "/taf/v1/testcase", "application/json", testCase.toJson());
        Assert.assertNotNull("No response", response);
        Assert.assertTrue(response, response.toLowerCase().contains("ok"));
        response = restSupport.responseBodyFromGetRequest("http://127.0.0.1:" + testServerPortAsString + "/taf/v1/testcasehtml/0");
        Assert.assertNotNull(response);
        System.out.println(response);
    }
}
