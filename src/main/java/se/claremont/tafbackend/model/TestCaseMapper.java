package se.claremont.tafbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.autotest.common.testcase.TestCase;
import se.claremont.tafbackend.storage.TestCaseCacheList;
import se.claremont.tafbackend.webpages.ErrorPage;
import se.claremont.tafbackend.webpages.TestCasePage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps TAF TestCase objects from JSON to internally usable elements
 *
 * Created by jordam on 2017-03-18.
 */
public class TestCaseMapper {
    String testCaseJson;
    public static Map<String, TestCase> testCaseCache = new HashMap<>();
    public TestCaseMapper(String testCase){
        this.testCaseJson = testCase;
    }
    static ObjectMapper mapper = new ObjectMapper();

    public TestCase object(){
        if(testCaseJson == null) {
            System.out.println("Cannot create TestCase from a json string that is null.");
            return null;
        }
        if(testCaseCache.containsKey(testCaseJson)) {
            System.out.println("Getting test case object from cache.");
            return testCaseCache.get(testCaseJson);
        }
        try {
            System.out.print("Creating TestCase object from json: " + testCaseCache + "");
            TestCase result = mapper.readValue(testCaseJson, TestCase.class);
            if(result == null) {System.out.println("  => (Result: Oups! Could not create a TestCase object from json above.)");
            } else {
                System.out.println("  => (Result: TestCase object created successfully.)" + System.lineSeparator());
            }
            testCaseCache.put(testCaseJson, result);
            return result;
        } catch (IOException e) {
            System.out.println("Tried to create TestCase object from json:" + System.lineSeparator() + testCaseJson + System.lineSeparator() + "Got error: " + e.toString());
        }
        return null;
    }


    public void store(){
        if(testCaseJson == null) return;
        TestCaseCacheList.addIfNotAdded(testCaseJson);
        System.out.println("Saving test case.");
    }

    public String toHtml(){
        TestCase testCaseObject = object();
        if(testCaseObject == null) return ErrorPage.toHtml("<p>Error: Cannot create HTML page from null TestCase element.</p>");
        TestCasePage testCasePage = new TestCasePage(testCaseObject);
        return testCasePage.asHtml();
    }


}
