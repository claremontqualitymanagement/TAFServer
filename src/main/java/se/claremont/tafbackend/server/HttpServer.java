package se.claremont.tafbackend.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import se.claremont.tafbackend.storage.TestRunList;

/**
 * Created by jordam on 2017-03-18.
 */
public class HttpServer {

    ResourceConfig config = new ResourceConfig();
    Server server;

    public void start(){
        config.packages("se.claremont.tafbackend");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        server = new Server(Settings.port);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");
        try {
            server.start();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        TestRunList.loadTestRunsFromFile();
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
        }catch (Exception e){
            System.out.println("Error stopping HTTP server: " + e.toString());
        }
    }
}
