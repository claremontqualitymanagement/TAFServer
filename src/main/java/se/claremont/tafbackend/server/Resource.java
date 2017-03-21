package se.claremont.tafbackend.server;

import se.claremont.tafbackend.model.LogPostMapper;
import se.claremont.tafbackend.model.TestCaseMapper;
import se.claremont.tafbackend.model.TestRunMapper;
import se.claremont.tafbackend.storage.TestCaseList;
import se.claremont.tafbackend.storage.TestRunList;
import se.claremont.tafbackend.webpages.LandingPage;
import se.claremont.tafbackend.webpages.SupportMethods;
import se.claremont.tafbackend.webpages.TestRunListingsPage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by jordam on 2017-03-18.
 */
@Path("taf")
public class Resource {

    //GET /taf/version
    @GET
    @Path("version")
    @Produces(MediaType.TEXT_HTML)
    public String versionHtml() {
        System.out.println("Got a request for version.");
        return SupportMethods.toHtmlPage("TAF Backend code version 0.1.<br><br>Try a GET request to <i>'/apiversion'</i> for supported API version.");
    }

    //GET /taf/version
    @GET
    @Path("version")
    @Produces(MediaType.TEXT_PLAIN)
    public String versionPlainText() {
        System.out.println("Got a request for version.");
        return "TAF Backend code version 0.1.";
    }

    //GET /taf/version
    @GET
    @Path("apiversion")
    @Produces(MediaType.TEXT_HTML)
    public String apiVersion() {
        return SupportMethods.toHtmlPage("TAF Backend server REST API version: <i>'v1'</i>.");
    }

    //GET /taf/version
    @GET
    @Path("apiversion")
    @Produces(MediaType.TEXT_PLAIN)
    public String apiVersionText() {
        return "TAF Backend server REST API version: 'v1'.";
    }

    @GET
    @Path("v1/testcases")
    @Produces(MediaType.APPLICATION_JSON)
    public String testCases() {
        return String.join(System.lineSeparator(), TestCaseList.getAll());
    }

    @GET
    @Path("v1/testruns")
    @Produces(MediaType.APPLICATION_JSON)
    public String testRuns() {
        return String.join(System.lineSeparator(), TestRunList.getAll());
    }

    @GET
    @Path("v1/testruns")
    @Produces(MediaType.TEXT_HTML)
    public String testRunsAsHtml() {
        return new TestRunListingsPage().toHtml();
    }

    //GET /taf/
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String landingPage() {
        return LandingPage.toHtml();
    }

    /*
    Using the annotation @QueryParam allows us to give a parameter to our URI.
    In this case, if we type in the URI http://localhost:2222/home/param?name=Me
    our method will return "Hello, Me" to the browser.
    A @QueryParam will look for the given name of the parameter in the URI,
    here it is "name", and assign this to the following variable String name.
    By typing ?name=Me at the end of the URI, you assign the variable name to Me.
     */
    @GET
    @Path("v1/param")
    @Produces(MediaType.TEXT_PLAIN)
    public String paramMethod(@QueryParam("name") String name) {
        return "Hello, " + name;
    }

    /*
    In this case out value will be assigned to ".../{var}", which is of type String in this example.
    By typing http://localhost:2222/home/path/Me this method will return "Hello, Me" to the browser.
     */
    @GET
    @Path("v1/testcase/{var}")
    @Produces(MediaType.APPLICATION_JSON)
    public String testCaseGetMethod(@PathParam("var") String testCaseId) {
        return TestCaseList.getItemAt(Integer.valueOf(testCaseId));
    }

    /*
    In this case out value will be assigned to ".../{var}", which is of type String in this example.
    By typing http://localhost:2222/home/path/Me this method will return "Hello, Me" to the browser.
     */
    @GET
    @Path("v1/testrun/{var}")
    @Produces(MediaType.APPLICATION_JSON)
    public String testRunGetMethod(@PathParam("var") String testRunId) {
        return TestRunList.getItemAt(Integer.valueOf(testRunId));
    }

    /*
    In this case out value will be assigned to ".../{var}", which is of type String in this example.
    By typing http://localhost:2222/home/path/Me this method will return "Hello, Me" to the browser.
     */
    @GET
    @Path("v1/testrun/{var}")
    @Produces(MediaType.TEXT_HTML)
    public String testRunGetAsHtmlMethod(@PathParam("var") String testRunId) {
        return new TestRunMapper(TestRunList.getItemAt(Integer.valueOf(testRunId))).toHtml();
    }

    /*
    In this case out value will be assigned to ".../{var}", which is of type String in this example.
    By typing http://localhost:2222/home/path/Me this method will return "Hello, Me" to the browser.
     */
    @GET
    @Path("v1/testcase/{var}")
    @Produces(MediaType.TEXT_HTML)
    public String testCaseGetHtmlMethod(@PathParam("var") String testCaseId) {
        String html = new TestCaseMapper(TestCaseList.getItemAt(Integer.valueOf(testCaseId))).toHtml();
        if(html == null) html = SupportMethods.toHtmlPage("Could not find test case " + testCaseId);
        return html;
    }

    /*
    Here we say that this method will consume MediaType.APPLICATION_FORM_URLENCODED.
    In other words, this method expects fata from a HTML-form. We also say that the
    method will produce HTML-formatted data. Re-run your application and type in your
    name at index.html and see what happens. You should get "Hello, <your name>" as
    result in your browser.
     */

    @POST
    @Path("v1/log")
    public String log(String logPost) {
        System.out.println("Received POST request to /taf/v1/log/ with content: '" + logPost + "'.");
        try{
            new LogPostMapper(logPost).store();
            return "Ok";
        } catch (Exception e){
            return "Not Ok";
        }
    }

    @POST
    @Path("v1/testcase")
    public String postTestCase(String testCase) {
        System.out.println("Received POST request to /taf/v1/testcase/ with content: '" + testCase + "'.");
        try{
            new TestCaseMapper(testCase).store();
            return "Ok";
        } catch (Exception e){
            return "Not Ok";
        }
    }

    @POST
    @Path("v1/testrun")
    public String postTestRun(String testRun) {
        System.out.println("Received POST request to /taf/v1/testrun/ with content: '" + testRun + "'.");
        try{
            return new TestRunMapper(testRun).store();
        } catch (Exception e){
            return "Not Ok";
        }
    }
}