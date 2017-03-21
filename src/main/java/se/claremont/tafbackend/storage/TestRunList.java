package se.claremont.tafbackend.storage;

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
public class TestRunList {
    @JsonProperty private static List<String> jsonStringList = new ArrayList<>();

    public static void addIfNotAdded(String jsonString){
        for(String json : jsonStringList){
            if(json.equals(jsonString)) return;
        }
        jsonStringList.add(jsonString);
        saveTestRunsToFile();
    }

    private static void saveTestRunsToFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(Settings.storageFile, TestRunList.jsonStringList);
        } catch (IOException e) {
            System.out.println(e.toString());
        };
    }
    public static void loadTestRunsFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TestRunList.jsonStringList = mapper.readValue(Settings.storageFile, TestRunList.jsonStringList.getClass());
        } catch (IOException e) {
            System.out.println(e.toString());
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
