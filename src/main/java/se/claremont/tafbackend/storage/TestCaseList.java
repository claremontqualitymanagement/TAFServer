package se.claremont.tafbackend.storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Storing JSON string for each test case. Use new TestCaseMapper() with that string to obtain a TestCase object.
 *
 * Created by jordam on 2017-03-19.
 */
public class TestCaseList {
    public static List<String> jsonStringList = new ArrayList<>();

    public static void addIfNotAdded(String testCaseMapperTestCaseString){
        for(String json : jsonStringList){
            if(json.equals(testCaseMapperTestCaseString)) return;
        }
        jsonStringList.add(testCaseMapperTestCaseString);
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
        if(index < 0 || index > jsonStringList.size()) return null;
        return jsonStringList.get(index);
    }
}
