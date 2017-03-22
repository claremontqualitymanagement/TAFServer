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
        sb.append(CommonSections.headSection(scripts(), extraStyles(), extraHeadEntrys())).append(System.lineSeparator());
        sb.append("    </head>").append(System.lineSeparator());
        sb.append("    <body>").append(System.lineSeparator());
        sb.append("    <table id=\"").append(TestRunDetailsPage.HtmlStyleNames.CONTENT.toString()).append("\">").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append("      <tr>").append(System.lineSeparator());
        sb.append("        <td>").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append(CommonSections.pageHeader()).append(System.lineSeparator());
        sb.append("        <h1>Test runs logged to TAF Server</h1>").append(System.lineSeparator());
        sb.append("        <table class=\"testrunlisting\"><tr><th>Start date</th><th>Start time</th><th>Stop time</th><th>Run name/test sets</th><th>Run status</th><th></th><th></th></tr>").append(System.lineSeparator());
        for(int i = TestRunList.size() -1; i >= 0; i--){
            String json = TestRunList.getItemAt(i);
            object = new TestRunMapper(json).object();
            if(object == null){
                System.out.println("Could not list TestRun from json '" + json + "' since it could not be turned into object.");
            } else {
                sb.append("            <tr class=\"testrunrow\">");

                sb.append("<td class=\"testrunstartdate\">");
                sb.append(getStartDate());
                sb.append("</td>");

                sb.append("<td class=\"testrunstarttime\">");
                sb.append(getStartTime());
                sb.append("</td>");

                sb.append("<td class=\"testrunstoptime\">");
                sb.append(getStopTime());
                sb.append("</td>");

                sb.append("<td class=\"testrunname\">");
                sb.append(getRunName(i));
                sb.append("</td>");

                //sb.append("<td class=\"testrunstatus ").append("passed").append("\">");
                sb.append("<td class=\"testrunstatus tooltip ").append(object.mostSevereErrorEncountered.toString().toLowerCase()).append("\">Status<span class=\"tooltiptext\">").append(object.mostSevereErrorEncountered.toString().toLowerCase().replace("_", " ")).append("</span>");
                sb.append("</td>");

                sb.append("<td class=\"edittestrun\">");
                sb.append("<span onClick=\"editTestRun(").append(i).append(")\"><i class=\"icon-edit-sign\"></i></span>");
                sb.append("</td>");

                sb.append("<td class=\"removetestrun\">");
                sb.append("<span onClick=\"removeTestRun(").append(i).append(")\"><i class=\"fa fa-trash\"></i></span>");
                sb.append("</td>");

                sb.append("</tr>");
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

    private String extraHeadEntrys() {
        return "<link href=\"//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css\" rel=\"stylesheet\">" + System.lineSeparator() +
                "<meta http-equiv=\"refresh\" content=\"60; URL=\"taf/testruns\">" + System.lineSeparator();
    }

    private String getRunName(int index){
        if(object == null) return "<i>unknown</i>";
        return "<a href=\"taf/" + Settings.currentApiVersion + "/testrun/" + String.valueOf(index) + "\" target=\"_blank\">TestRun '" + object.testRunName + "'</a>";

    }

    private String getStartDate(){
        if(object != null && object.getRunStartTime() != null){
            return new SimpleDateFormat("yyyy-MM-dd").format(object.getRunStartTime());
        } else {
            return "<i>unknown</i>";
        }
    }

    private String getStartTime(){
        if(object != null && object.getRunStartTime() != null){
            return new SimpleDateFormat("HH:mm:ss").format(object.getRunStartTime());
        } else {
            return "<i>unknown</i>";
        }
    }

    private String getStopTime(){
        if(object != null && object.getRunStopTime() != null){
            return new SimpleDateFormat("HH:mm:ss").format(object.getRunStopTime());
        } else {
            return "<i>unknown</i>";
        }
    }

    private String scripts(){
        StringBuilder sb = new StringBuilder();
        sb.append("<script type=\"text/javascript\">").append(System.lineSeparator());
        sb.append("   function removeTestRun(index) {").append(System.lineSeparator());
        sb.append("      if(confirm('This would remove test run ' + index)){;").append(System.lineSeparator());
        sb.append("         var deleteCallback = function() { alert('deleted!'); }").append(System.lineSeparator());
        sb.append("         var httpMethod = 'DELETE';").append(System.lineSeparator());
        sb.append("         var url = 'taf/v1/testrun/' + index;").append(System.lineSeparator());
        sb.append("         httpRequest(url, deleteCallback, httpMethod);").append(System.lineSeparator());
        sb.append("         location.reload();").append(System.lineSeparator());
        sb.append("      }").append(System.lineSeparator());
        sb.append("   }").append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("   function httpRequest(url, callback, httpMethod){").append(System.lineSeparator());
        sb.append("      var xhttp = new XMLHttpRequest();").append(System.lineSeparator());
        sb.append("      xhttp.onreadystatechange = function() {").append(System.lineSeparator());
        sb.append("         if (xhttp.readyState == 4 && xhttp.status == 200) {").append(System.lineSeparator());
        sb.append("             var jsonParse = JSON.parse(xhttp.responseText);").append(System.lineSeparator());
        sb.append("             callback(jsonParse);").append(System.lineSeparator());
        sb.append("         }").append(System.lineSeparator());
        sb.append("      }").append(System.lineSeparator());
        sb.append("      xhttp.open(httpMethod, url, true);").append(System.lineSeparator());
        sb.append("      xhttp.send();").append(System.lineSeparator());
        sb.append("   }").append(System.lineSeparator());
        sb.append("</script>").append(System.lineSeparator());
        return sb.toString();
    }

    private String extraStyles(){
        StringBuilder sb = new StringBuilder();
        sb.append("table.testrunlisting {}").append(System.lineSeparator());
        sb.append("tr.testrunrow {}").append(System.lineSeparator());
        sb.append("td.testrunstartdate { white-space: nowrap; }").append(System.lineSeparator());
        sb.append("td.testrunstarttime { white-space: nowrap; }").append(System.lineSeparator());
        sb.append("td.testrunstoptime { white-space: nowrap; }").append(System.lineSeparator());
        sb.append("td.testrunname { max-width: 50%; }").append(System.lineSeparator());
        sb.append("td.testrunstatus { width: 50px; }").append(System.lineSeparator());
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.FAILED_WITH_ONLY_KNOWN_ERRORS.toString().toLowerCase()).append(" { color: ").append(UxColors.YELLOW.getHtmlColorCode()).append("; background-color: ").append(UxColors.YELLOW.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.PASSED.toString().toLowerCase()).append(" { color: ").append(UxColors.GREEN.getHtmlColorCode()).append("; background-color: ").append(UxColors.GREEN.getHtmlColorCode()).append("; }");
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.FAILED_WITH_ONLY_NEW_ERRORS.toString().toLowerCase()).append(" { color: ").append(UxColors.RED.getHtmlColorCode()).append("; background-color: ").append(UxColors.RED.getHtmlColorCode()).append("; }");
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.FAILED_WITH_BOTH_NEW_AND_KNOWN_ERRORS.toString().toLowerCase()).append(" { color: ").append(UxColors.RED.getHtmlColorCode()).append("; background-color: ").append(UxColors.RED.getHtmlColorCode()).append("; }");
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.UNEVALUATED.toString().toLowerCase()).append(" { color: ").append(UxColors.MID_GREY.getHtmlColorCode()).append("; background-color: ").append(UxColors.MID_GREY.getHtmlColorCode()).append("; }");
        sb.append(".tooltip {\n" +
                "position: relative;\n" +
                "display: inline-block;\n" +
                "}" +
                "" +
                ".tooltip .tooltiptext {\n" +
                "visibility: hidden;\n" +
                "background-color: black;\n" +
                "color: #fff;\n" +
                "width: 200px;\n" +
                "text-align: left;\n" +
                "font-size: 90%;\n" +
                "border-radius: 6px;\n" +
                "padding: 5px 0;\n" +
                "" +
                "/* Position the tooltip */\n" +
                "position: absolute;\n" +
                "z-index: 1;\n" +
                "}\n" +
                "" +
                ".tooltip:hover .tooltiptext {\n" +
                "visibility: visible;\n" +
                "}\n");
        return sb.toString();
    }
}
