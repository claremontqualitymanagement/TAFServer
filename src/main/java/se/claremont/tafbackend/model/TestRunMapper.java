package se.claremont.tafbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.autotest.common.reporting.testrunreports.TafBackendServerTestRunReporter;
import se.claremont.tafbackend.storage.TestRunList;
import se.claremont.tafbackend.webpages.SupportMethods;
import se.claremont.tafbackend.webpages.TestRunDetailsPage;

import java.io.IOException;

/**
 * Created by jordam on 2017-03-18.
 */
public class TestRunMapper {
    String testRunResult;

    public TestRunMapper(String testRunResult){
        this.testRunResult = testRunResult;
    }

    public TafBackendServerTestRunReporter object(){
        if(testRunResult == null) {
            System.out.println("Cannot create TafBackendServerTestRunReporter from a json string that is null.");
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("Creating test run from '" + testRunResult + "'.");
            return mapper.readValue(testRunResult, TafBackendServerTestRunReporter.class);
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
        if(element == null) return SupportMethods.toHtmlPage("Error. Cannot create HTML page from null TestRunResult element.");
        return new TestRunDetailsPage(element).toHtml();
    }
}
