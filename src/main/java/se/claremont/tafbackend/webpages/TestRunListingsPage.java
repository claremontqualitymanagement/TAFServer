package se.claremont.tafbackend.webpages;

import se.claremont.autotest.common.reporting.UxColors;
import se.claremont.autotest.common.reporting.testrunreports.TafBackendServerTestRunReporter;
import se.claremont.autotest.common.testcase.TestCase;
import se.claremont.tafbackend.model.TestRunMapper;
import se.claremont.tafbackend.server.Settings;
import se.claremont.tafbackend.storage.TestRunList;

import java.text.SimpleDateFormat;

/**
 * Created by jordam on 2017-03-19.
 */
public class TestRunListingsPage {
    TafBackendServerTestRunReporter object;

    public String toHtml(){
        return content();
    }


    private String content(){
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>").append(System.lineSeparator());
        sb.append("<html lang=\"en\">").append(System.lineSeparator());
        sb.append("    <head>").append(System.lineSeparator());
        sb.append(CommonSections.headSection(scripts(), extraStyles(), "")).append(System.lineSeparator());
        sb.append("    </head>").append(System.lineSeparator());
        sb.append("    <body>").append(System.lineSeparator());
        sb.append("    <table id=\"").append(TestRunDetailsPage.HtmlStyleNames.CONTENT.toString()).append("\">").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append("      <tr>").append(System.lineSeparator());
        sb.append("        <td>").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append(CommonSections.pageHeader()).append(System.lineSeparator());
        sb.append("        <h1>Test runs logged to TAF Server</h1>").append(System.lineSeparator());
        sb.append("        <table class=\"testrunlisting\"><tr><th>Start time</th><th>Stop time</th><th>Run name/test sets</th><th>Run status</th></tr>").append(System.lineSeparator());
        for(int i = TestRunList.size() -1; i >= 0; i--){
            String json = TestRunList.getItemAt(i);
            object = new TestRunMapper(json).object();
            if(object == null){
                System.out.println("Could not list TestRun from json '" + json + "' since it could not be turned into object.");
            } else {
                sb.append("            <tr class=\"testrunrow\"><td class=\"testrunstarttime\">");
                sb.append(getStartTime());
                sb.append("</td><td class=\"testrunstoptime\">");
                sb.append(getStopTime());
                sb.append("</td><td class=\"testrunname\">");
                sb.append(getRunName(i));
                //sb.append("</td><td class=\"testrunstatus ").append(object.mostSevereErrorEncountered.toString().toLowerCase()).append("\">");
                sb.append("</td><td class=\"testrunstatus ").append("passed").append("\">");
                sb.append("</td></tr>");
                sb.append(System.lineSeparator());
            }
        }
        sb.append("        </table>").append(System.lineSeparator());
        sb.append(CommonSections.pageFooter()).append(System.lineSeparator());
        sb.append("        </td>").append(System.lineSeparator());
        sb.append("      </tr>").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append("    </table>").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append("    </body>").append(System.lineSeparator());
        sb.append("</html>").append(System.lineSeparator());
        return sb.toString();
    }

    private String getRunName(int index){
        if(object == null) return "<i>unknown</i>";
        return "<a href=\"taf/" + Settings.currentApiVersion + "/testrun/" + String.valueOf(index) + "\" target=\"_blank\">TestRun '" + object.testRunName + "'</a>";

    }

    private String getStartTime(){
        if(object != null && object.getRunStartTime() != null){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object.getRunStartTime());
        } else {
            return "<i>unknown</i>";
        }
    }

    private String getStopTime(){
        if(object != null && object.getRunStopTime() != null){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object.getRunStopTime());
        } else {
            return "<i>unknown</i>";
        }
    }

    private String scripts(){
        return "";
    }

    private String extraStyles(){
        StringBuilder sb = new StringBuilder();
        sb.append("table.testrunlisting {}").append(System.lineSeparator());
        sb.append("tr.testrunrow {}").append(System.lineSeparator());
        sb.append("td.testrunstarttime {}").append(System.lineSeparator());
        sb.append("td.testrunstoptime {}").append(System.lineSeparator());
        sb.append("td.testrunname {}").append(System.lineSeparator());
        sb.append("td.testrunstatus { width: 50px; }").append(System.lineSeparator());
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.FAILED_WITH_ONLY_KNOWN_ERRORS.toString().toLowerCase()).append(" { background-color: ").append(UxColors.YELLOW.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.PASSED.toString().toLowerCase()).append(" { background-color: ").append(UxColors.GREEN.getHtmlColorCode()).append("; }");
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.FAILED_WITH_ONLY_NEW_ERRORS.toString().toLowerCase()).append(" { background-color: ").append(UxColors.RED.getHtmlColorCode()).append("; }");
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.FAILED_WITH_BOTH_NEW_AND_KNOWN_ERRORS.toString().toLowerCase()).append(" { background-color: ").append(UxColors.RED.getHtmlColorCode()).append("; }");
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.UNEVALUATED.toString().toLowerCase()).append(" { background-color: ").append(UxColors.MID_GREY.getHtmlColorCode()).append("; }");
        return sb.toString();
    }
}
