package se.claremont.tafbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.autotest.common.testcase.TestCase;
import se.claremont.tafbackend.storage.TestCaseCacheList;
import se.claremont.tafbackend.webpages.ErrorPage;
import se.claremont.tafbackend.webpages.TestCasePage;

import java.io.IOException;

/**
 * Created by jordam on 2017-03-18.
 */
public class TestCaseMapper {
    String testCase;

    public TestCaseMapper(String testCase){
        this.testCase = testCase;
    }

    public TestCase object(){
        if(testCase == null) {
            System.out.println("Cannot create TestCase from a json string that is null.");
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("Creating TestCase object from json '" + testCase + "'.");
            return mapper.readValue(testCase, TestCase.class);
        } catch (IOException e) {
            System.out.println("Tried to create TestCase object from json:" + System.lineSeparator() + testCase + System.lineSeparator() + "Got error: " + e.toString());
        }
        return null;
    }


    public void store(){
        if(testCase == null) return;
        TestCaseCacheList.addIfNotAdded(testCase);
        System.out.println("Saving test case.");
    }

    public String toHtml(){
        TestCase testCaseObject = object();
        if(testCaseObject == null) return ErrorPage.toHtml("<p>Error: Cannot create HTML page from null TestCase element.</p>");
        TestCasePage testCasePage = new TestCasePage(testCaseObject);
        return testCasePage.asHtml();
    }


}
