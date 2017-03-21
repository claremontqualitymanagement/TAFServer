package se.claremont.tafbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.autotest.common.logging.LogPost;

/**
 * Created by jordam on 2017-03-18.
 */
public class LogPostMapper {
    String logPostJson;
    LogPost logPost;

    public LogPostMapper(String logPostJson){
        System.out.println("Creating LogPostMapper from '" + logPostJson + "'.");
        ObjectMapper mapper = new ObjectMapper();
        try {
            logPost = mapper.readValue(logPostJson, LogPost.class);
            System.out.println(logPost.toHtmlTableRow());
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        this.logPostJson = logPostJson;
    }

    public void store(){
        System.out.println("Saving logpost to DB.");
    }
}
