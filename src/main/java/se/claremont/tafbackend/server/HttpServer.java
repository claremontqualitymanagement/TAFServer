package se.claremont.tafbackend.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import se.claremont.tafbackend.storage.TestRunList;

/**
 * The HTTP server used for REST services end-points as well as web display
 *
 * Created by jordam on 2017-03-18.
 */
public class HttpServer {

    ResourceConfig config = new ResourceConfig();
    Server server;

    public void start(){
        System.out.println(System.lineSeparator() + "Starting TAF Backend Server at port " + Settings.port + "." + System.lineSeparator());
        config.packages("se.claremont.tafbackend");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        server = new Server(Settings.port);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");
        try {
            server.start();
        }catch (Exception e){
            System.out.println(System.lineSeparator() + e.toString());
        }
        if(isStarted()){
            System.out.println(System.lineSeparator() + "Server started." + System.lineSeparator());
        } else {
            System.out.println(System.lineSeparator() + "Could not start server." + System.lineSeparator());
            return;
        }
        TestRunList.loadTestRunsFromFile();
        System.out.println(System.lineSeparator() + "Server ready to serve." + System.lineSeparator());
    }

    public void setToTestMode(){
        TestRunList.setToTestMode();
    }

    public boolean isStarted(){
        return (server != null && !server.isFailed());
    }

    public void stop(){
        try{
            server.stop();
            server.destroy();
            System.out.println("Server stopped." + System.lineSeparator());
        }catch (Exception e){
            System.out.println("Error stopping HTTP server: " + e.toString());
        }
    }
}
