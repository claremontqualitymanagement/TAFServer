package se.claremont.tafbackend.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

/**
 * Created by jordam on 2017-03-18.
 */
public class App {
    static PrintStream originalOutputChannel;
    static ByteArrayOutputStream testOutputChannel;

    private static void restoreOutputChannel(){
        System.setOut(originalOutputChannel);
    }

    private static String helpText(){
        return System.lineSeparator() +
                "TAF Backend Server" + System.lineSeparator() +
                "======================" + System.lineSeparator() +
                "Usage example: 'java -jar TafBackend.jar port=8080 store=C:\\temp\\TafBackend.db'" + System.lineSeparator() +
                "where port number is the web server port number for the server to enable access to:" + System.lineSeparator() + System.lineSeparator() +
                "http://localhost:8080/taf" + System.lineSeparator() +
                "TAF Backend Server uses a file based data storage." + System.lineSeparator() +
                "The 'store' parameter states what file to store test run information in." + System.lineSeparator() +
                "The name or file extension for the store file are irrelevant." + System.lineSeparator() + System.lineSeparator() +
                "Default TCP port is 80." + System.lineSeparator() +
                "Default storage file is 'TafBackend.db' in the same folder as this jar file." + System.lineSeparator();
    }

    private static void setPortIfStatedAsParameter(String[] args){
        for(String arg : args){
            if(arg.contains("=")){
                if(arg.split("=")[0].toLowerCase().equals("port")){
                    Integer port = Integer.valueOf(arg.split("=")[1]);
                    System.out.println("Setting server port to " + port + ".");
                    Settings.port = port;
                }
            }
        }
    }

    private static void setStoreFileIfStatedAsParameter(String[] args){
        for(String arg : args){
            if(arg.contains("=")){
                if(arg.split("=")[0].toLowerCase().equals("store")){
                    String fileName = arg.split("=")[1];
                    System.out.println("Setting store file to " + fileName + "." + System.lineSeparator());
                    Settings.storageFile = new File(fileName);
                }
            }
        }
    }

    private static void redirectConsoleOutput(){
        testOutputChannel = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOutputChannel));
    }


    public static void main(String[] args){
        //originalOutputChannel = System.out;
        System.out.println(helpText());
        setPortIfStatedAsParameter(args);
        setStoreFileIfStatedAsParameter(args);
        HttpServer server = new HttpServer();
        server.start();
        if(!server.isStarted()) {
            try {
                server.server.stop();
            } catch (Exception ignored) {
            }
            server.server.destroy();
            return;
        }

        try {
            server.server.join();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        } finally {
            server.stop();
        }
    }
}
