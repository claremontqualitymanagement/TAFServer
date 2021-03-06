package se.claremont.tafbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.autotest.common.reporting.testrunreports.TafBackendServerTestRunReporter;

import java.io.IOException;

/**
 * Maps TAF TestSet objects from JSON to internally usable elements.
 *
 * Created by jordam on 2017-03-20.
 */
public class TestSetMapper {
    String testSet = null;
    public TafBackendServerTestRunReporter.TafBackendServerTestSet testSetObject = null;

    public TestSetMapper(String testSet){
        if(testSet == null)return;
        this.testSet = testSet;
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.testSetObject = mapper.readValue(testSet, TafBackendServerTestRunReporter.TafBackendServerTestSet.class);
            System.out.println(this.testSetObject.toString());
            System.out.println("Creating TestSet from '" + testSet + "'.");
        } catch (IOException e) {
            System.out.println("Could not create TestSet from '" + testSet + "'.");
            System.out.println(e.toString());
        }
    }

}
