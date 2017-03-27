package se.claremont.tafbackend.webpages;

/**
 * The About page for this server
 *
 * Created by jordam on 2017-03-23.
 */
public class AboutPage {

    public static String toHtml(){
        return InfoPage.toHtml("<h1>TAF Backend server</h1>" +
                "<p>" +
                "This server is meant to ease storage and viewing of test runs performed from TAF." +
                "</p><p>" +
                "TAF is an open source test automation framework to quickly get going with robust " +
                "and maintanable scripts for test automation. It is developed by Claremont, a " +
                "Swedish company.<br>For more information about TAF, please visit the " +
                "<a href=\"https://github.com/claremontqualitymanagement/TestAutomationFramework\" target=\"_blank\">" +
                "TAF home on GitHub</a>, and the " +
                "<a href=\"https://github.com/claremontqualitymanagement/TestAutomationFramework/wiki\" target=\"_blank\">" +
                "TAF wiki on that site</a>." +
                "</p><p>" +
                "<a href=\"taf/version\">Software version</a>" +
                "</p><p>" +
                "<a href=\"taf/apiversion\">Implemented API version</a>" +
                "</p>" +
                "<h2>ToDo</h2>" +
                "<ul>" +
                "<li>Create a page to use the update test run mechanisms (mainly give the test run a custom name).</li>" +
                "<li>Prettify whole server interface.</li>" +
                "<li>Move more of page creation mechanisms over to javascript on client side, from JSON.</li>" +
                "<li>Change test run store format to HashSet or maybe Set or some other data holder.</li>" +
                "<li>Change present test case cache to a proper test case storage mechanism.</li>" +
                "<li>Add dashboard graphs and listings.</li>" +
                "</ul>" +
                "The dashboards and listings would prefferably include graphs to help assess performance and resiliance " +
                "of test automation. Hence the following might be applicable.<br><br>" +
                "<b>Time based graps</b><br>" +
                "<ul>" +
                "<li>Number of test runs per week over time (to help assess automation value from usage)</li>" +
                "<li>Mean number of executed test case per test run per week over time (to help assess growing test suites)</li>" +
                "<li>Number of contributors to test automation code per week over time (to help assess knowledge and responsibility sharing)</li>" +
                "<li>Number of checkins of test code per week over time (to help assess change rate of application under test – or progress of test automation initiative)</li>" +
                "<li>Test run execution duration over time (to help identify potential timing problems over time)</li>" +
                "<li>Number of test cases run per week over time (for bragging)</li>" +
                "</ul>" +
                "<b>Listings</b><br>" +
                "<ul>" +
                "<li>Most erroneous test cases top list (to help identify test cases that maybe should be re-written)</li>" +
                "<li>Test cases that has not failed at all last month (to help identify test cases that maybe should be eliminated or re-designed)</li>" +
                "<li>Top contributors last month (for gamification to help spread test automation)</li>" +
                "</ul>");
    }
}
