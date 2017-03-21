package se.claremont.tafbackend.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.tafbackend.server.Settings;

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
        if(testMode)return;
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Settings.storageFile, TestRunList.jsonStringList);
        } catch (IOException e) {
            System.out.println(e.toString());
        };
    }
    public static void loadTestRunsFromFile() {
        if(testMode)return;
        ObjectMapper mapper = new ObjectMapper();
        try {
            TestRunList.jsonStringList = mapper.readValue(Settings.storageFile, TestRunList.jsonStringList.getClass());
        } catch (IOException e) {
            System.out.println("Storage file for test run information not found. It will be created when the first test run is registered.");
        };
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
}
