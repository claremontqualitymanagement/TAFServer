package se.claremont.tafbackend.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.tafbackend.server.Settings;
import se.claremont.tafbackend.webpages.ErrorPage;
import se.claremont.tafbackend.webpages.InfoPage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Storing JSON string for each test run. Use new TestRunMapper() with that string to obtain a TestRun object.
 *
 * Created by jordam on 2017-03-19.
 */
@JsonIgnoreProperties({"testMode"})
public class TestRunList {
    //Todo: Should probably be converted to a HashMap with id as key to avoid problems when multiple people use the same data, and someone removes an item
    @JsonProperty private static List<String> jsonStringList = new ArrayList<>();
    static boolean testMode = false;

    public static void addIfNotAdded(String jsonString){
        for(String json : jsonStringList){
            if(json.equals(jsonString)) return;
        }
        jsonStringList.add(jsonString);
        saveTestRunsToFile();
    }

    public static void setToTestMode(){
        testMode = true;
        jsonStringList = new ArrayList<>();
    }

    private static void saveTestRunsToFile() {
        //Todo: Should probably attempt to run in a tread pool of one thread to avoid concurrency problems with wider use
        if(testMode)return;
        long startTime = System.currentTimeMillis();
        System.out.println("Saving changes to DB.");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Settings.storageFile, TestRunList.jsonStringList);
            System.out.println("Finished saving changes to DB after " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        } catch (IOException e) {
            System.out.println("Could not save changes to DB. Error: " + e.toString());
        };
    }
    public static void loadTestRunsFromFile() {
        if(testMode)return;
        long startTime = System.currentTimeMillis();
        System.out.println("Loading test runs from DB.");
        ObjectMapper mapper = new ObjectMapper();
        try {
            TestRunList.jsonStringList = mapper.readValue(Settings.storageFile, TestRunList.jsonStringList.getClass());
            System.out.println("Finished loading test runs from DB after " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        } catch (IOException e) {
            System.out.println("Storage file for test run information not found. It will be created when the first test run is registered.");
        }
    }

    public static int getIdFor(String jsonString){
        for(int i = 0; i < jsonStringList.size(); i++){
            if(jsonStringList.get(i).equals(jsonString)) return i;
        }
        return -1;
    }


    public static List<String> getAll(){
        return jsonStringList;
    }

    public static int size(){
        return jsonStringList.size();
    }

    public static String getItemAt(int index){
        if(index < 0 || index > jsonStringList.size()-1) return null;
        return jsonStringList.get(index);
    }

    public static String update(String testRunId, String testRunJson){
        Integer id = Integer.valueOf(testRunId);
        if(id == null) return ErrorPage.toHtml("<p>Cannot update test run. Id "+ testRunId + " is not a proper number.</p>");
        if(id > size() || id < 0) return ErrorPage.toHtml("<p>Cannot update test run with id " + id + ". Index out of range for stored test runs.</p>");
        jsonStringList.set(id, testRunJson);
        return InfoPage.toHtml("<p>Updated test run with id " + id + ".</p></p><button onclick=\"window.history.back();\">Go Back</button>");
    }

    public static String tryDelete(String testRunId) {
        Integer id = Integer.valueOf(testRunId);
        if(id == null) return ErrorPage.toHtml("<p>Cannot delete test run with id '" + testRunId + "'. Failed to interprete an id number from it.</p>");
        int idVerified = (int)id;
        if(size() < id) return ErrorPage.toHtml("<p>Cannot delete test run with id '" + id.toString() + "'. Highest id is '" + size() + "'.</p>");
        if(id < 0) return ErrorPage.toHtml("<p>Cannot delete test run with negative id. Request for removal of test run with id '" + id + "' denied.</p>");
        String contentToRemove = jsonStringList.get(id);
        System.out.println("Removing test run with id " + id + ". Removed content below: " + System.lineSeparator() + contentToRemove);
        System.out.println(jsonStringList.remove(idVerified));
        System.out.println("Content for test run with id " + testRunId + " removed.");
        saveTestRunsToFile();
        //Todo: Should remove each test case in test run from cache rather than emptying cache
        TestCaseCacheList.emptyCache();
        return InfoPage.toHtml("<p>Removed test run with id " + id + ".</p><p>Removed content:</p><p>" + contentToRemove + "</p><button onclick=\"window.history.back();\">Go Back</button>");
    }
}
