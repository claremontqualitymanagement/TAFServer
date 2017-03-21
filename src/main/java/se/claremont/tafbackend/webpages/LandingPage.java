package se.claremont.tafbackend.webpages;

import se.claremont.tafbackend.storage.TestCaseList;
import se.claremont.tafbackend.storage.TestRunList;

/**
 * Created by jordam on 2017-03-18.
 */
public class LandingPage {

    public static String toHtml(){
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>").append(System.lineSeparator());
        sb.append("<html lang=\"en\">").append(System.lineSeparator());
        sb.append("   <head>").append(System.lineSeparator());
        sb.append(CommonSections.headSection()).append(System.lineSeparator());
        sb.append("   </head>").append(System.lineSeparator());
        sb.append("   <body>").append(System.lineSeparator());
        sb.append("    <table id=\"").append(TestRunDetailsPage.HtmlStyleNames.CONTENT.toString()).append("\">").append(LF).append(LF);
        sb.append("      <tr>").append(LF);
        sb.append("        <td>").append(LF).append(LF);
        sb.append("      <img src=\"http://46.101.193.212/TAF/images/claremontlogo.gif\" alt=\"logo\">").append(System.lineSeparator());
        sb.append("      <h1>TAF Results HttpServer</h1>").append(System.lineSeparator());
        sb.append("      <table class=\"statistics\">").append(System.lineSeparator());
        sb.append("         <tr><td>Number of test runs registered:</td><td>").append(TestRunList.size()).append("</td></tr>").append(System.lineSeparator());
        sb.append("         <tr><td>Number of test cases treated since last restart:</td><td>").append(TestCaseList.size()).append("</td></tr>").append(System.lineSeparator());
        sb.append("      </table>").append(System.lineSeparator());
        sb.append("      <p>").append(System.lineSeparator());
        if(TestRunList.size() > 0){
            sb.append("      <a href=\"taf/v1/testrun/").append(TestRunList.size()-1).append("\" class=\"button purple\"><span class=\"checkmark centered\">✓</span>Latest test run</a>").append(System.lineSeparator());
        } else {
            sb.append("      <a href=\"taf/v1/testrun/").append(TestRunList.size()-1).append("\" class=\"button grey\"><span class=\"unavailable centered\">-</span>Latest test run</a> - No test run registered yet").append(System.lineSeparator());
        }
        sb.append("      </p>").append(System.lineSeparator());
        sb.append("      <p>").append(System.lineSeparator());
        sb.append("         <a href=\"taf/v1/testruns\" class=\"button purple\"><span class=\"checkmark centered\">✓</span>Test run list</a>").append(System.lineSeparator());
        sb.append("      </p>").append(System.lineSeparator());
        sb.append("        </td>").append(LF);
        sb.append("      </tr>").append(LF).append(LF);
        sb.append("      <tr>").append(LF);
        sb.append("        <td class=\"centered\">").append(LF);
        sb.append(     CommonSections.htmlElementCopyright());
        sb.append("        </td>").append(LF);
        sb.append("      </tr>").append(LF).append(LF);
        sb.append("    </table>").append(LF).append(LF);
        sb.append("</html>").append(System.lineSeparator());
        return sb.toString();
    }


    private static String LF = System.lineSeparator();



}
