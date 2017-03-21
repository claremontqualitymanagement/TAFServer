package se.claremont.tafbackend.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.io.ByteArrayOutputStream;
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
                "Usage example: 'java -jar TafBackend.jar port=8080'" + System.lineSeparator() +
                "where port number is the web server port number for the server to enable access to:" + System.lineSeparator() + System.lineSeparator() +
                "http://localhost:8080/taf" + System.lineSeparator() + System.lineSeparator() +
                "Default value is port=80" + System.lineSeparator() + System.lineSeparator();
    }

    private static void setPortIfStatedAsParameter(String[] args){
        for(String arg : args){
            if(arg.contains("=")){
                if(arg.split("=")[0].toLowerCase().equals("port")){
                    Integer port = Integer.valueOf(arg.split("=")[1]);
                    System.out.println("Setting server port to " + port + "." + System.lineSeparator());
                    Settings.port = port;
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
        Settings.port = 2222;
        HttpServer server = new HttpServer();
        System.out.println("Starting server." + System.lineSeparator());
        //redirectConsoleOutput();
        server.start();
        /*
        restoreOutputChannel();
        if(!server.isStarted()){
            System.out.println(testOutputChannel.toString());
        } else { */
            System.out.println(System.lineSeparator() + "Server started." + System.lineSeparator());
        //}

        try {
            server.server.join();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        } finally {
            server.stop();
            System.out.println("Server stopped." + System.lineSeparator());
        }
    }
}
