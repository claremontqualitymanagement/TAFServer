package se.claremont.tafbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.autotest.common.reporting.testrunreports.TafBackendServerTestRunReporter;
import se.claremont.tafbackend.storage.TestRunList;
import se.claremont.tafbackend.webpages.ErrorPage;
import se.claremont.tafbackend.webpages.SupportMethods;
import se.claremont.tafbackend.webpages.TestRunDetailsPage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jordam on 2017-03-18.
 */
public class TestRunMapper {
    public static Map<String, TafBackendServerTestRunReporter> testRunCache = new HashMap<>();
    String testRunResult;

    public TestRunMapper(String testRunResult){
        this.testRunResult = testRunResult;
    }

    public TafBackendServerTestRunReporter object(ObjectMapper mapper){
        if(testRunResult == null) {
            System.out.println("Cannot create TafBackendServerTestRunReporter from a json string that is null.");
            return null;
        }
        if(testRunCache.containsKey(testRunResult)) return testRunCache.get(testRunResult);
        try {
            System.out.print("Creating test run object from json: " + testRunResult);
            TafBackendServerTestRunReporter result = mapper.readValue(testRunResult, TafBackendServerTestRunReporter.class);
            if(result == null) {System.out.println("  => (Result: Oups! Could not create a Test run object from json: '" + testRunResult + "').");
            } else {
                System.out.println("  => (Result: Test run object created successfully.)" + System.lineSeparator());
            }
            testRunCache.put(testRunResult, result);
            return result;
        } catch (IOException e) {
            System.out.println("Tried to create TestResult from:" + System.lineSeparator() + testRunResult + System.lineSeparator() + "Got error: " + e.toString());
        }
        return null;
    }

    public TafBackendServerTestRunReporter object(){
        if(testRunResult == null) {
            System.out.println("Cannot create TafBackendServerTestRunReporter from a json string that is null.");
            return null;
        }
        if(testRunCache.containsKey(testRunResult)) {
            System.out.println("Getting test run object from cache.");
            return testRunCache.get(testRunResult);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.print("Creating test run object from json: " + testRunResult);
            TafBackendServerTestRunReporter result = mapper.readValue(testRunResult, TafBackendServerTestRunReporter.class);
            if(result == null) {System.out.println("  => (Result: Oups! Could not create a Test run object from json: '" + testRunResult + "').");
            } else {
                System.out.println("  => (Result: Test run object created successfully.)" + System.lineSeparator());
            }
            testRunCache.put(testRunResult, result);
            return result;
        } catch (IOException e) {
            System.out.println("Tried to create TestResult from:" + System.lineSeparator() + testRunResult + System.lineSeparator() + "Got error: " + e.toString());
        }
        return null;
    }

    public String store(){
        System.out.println("Saving test run to DB.");
        TestRunList.addIfNotAdded(testRunResult);
        return "Ok. Test run results stored.";
    }

    public String toHtml(){
        TafBackendServerTestRunReporter element = object();
        if(element == null) return ErrorPage.toHtml("<p>Error: Cannot create HTML page from null TestRunResult element.</p>");
        return new TestRunDetailsPage(element).toHtml();
    }
}
