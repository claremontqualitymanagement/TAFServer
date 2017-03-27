package se.claremont.tafbackend.webpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.claremont.autotest.common.reporting.UxColors;
import se.claremont.autotest.common.reporting.testrunreports.TafBackendServerTestRunReporter;
import se.claremont.autotest.common.testcase.TestCase;
import se.claremont.tafbackend.model.TestCaseMapper;
import se.claremont.tafbackend.model.TestRunMapper;
import se.claremont.tafbackend.server.Settings;
import se.claremont.tafbackend.storage.TestRunList;

import java.text.SimpleDateFormat;

/**
 * Test run listing page
 *
 * Created by jordam on 2017-03-19.
 */
public class TestRunListingsPage {
    TafBackendServerTestRunReporter object;
    static ObjectMapper mapper = new ObjectMapper();
    private int numberOfTestRunsToDisplay = 5;

    public String toHtml(){
        return content();
    }

    String testRunResultListing(boolean displayTestCaseResultDistribution, int numberOfTestRunsToShow_ZeroForAll){
        StringBuilder sb = new StringBuilder();
        sb.append("        <table class=\"testrunlisting\"><tr><th>Start date</th><th>Start time</th><th>Stop time</th><th>Run name/test sets</th><th class=\"center\">Run<br>status</th>");
        if(displayTestCaseResultDistribution){
            sb.append("<th class=\"center\">Test Result<br>Distribution</th>");
        }
        sb.append("<th class=\"center\">Test<br>sets</th><th class=\"center\">Test<br>cases</th><th></th><th></th><th></th></tr>").append(System.lineSeparator());
        int numberOfTestRunsToShow;
        if(numberOfTestRunsToShow_ZeroForAll == 0){
            numberOfTestRunsToShow = TestRunList.size();
        } else if(numberOfTestRunsToShow_ZeroForAll > TestRunList.size()){
            numberOfTestRunsToShow = TestRunList.size();
        } else {
            numberOfTestRunsToShow = numberOfTestRunsToShow_ZeroForAll;
        }
        sb.append("        <h2>Test runs logged to TAF Server");
        if(TestRunList.size() > numberOfTestRunsToShow){
            sb.append(" (latest " + numberOfTestRunsToShow + " records)");
        }
        sb.append("</h2>").append(System.lineSeparator());
        for(int i = TestRunList.size() -1; i >= TestRunList.size() - numberOfTestRunsToShow; i--){
            String json = TestRunList.getItemAt(i);
            object = new TestRunMapper(json).object(mapper);
            if(object == null){
                System.out.println("Could not list TestRun from json '" + json + "' since it could not be turned into object.");
            } else {
                sb.append("            <tr class=\"testrunrow\">");

                sb.append("<td class=\"testrunstartdate linkhand\" onClick=\"window.location.href = 'taf/" + Settings.currentApiVersion + "/testrun/" + String.valueOf(i) + "'\">");
                sb.append(getStartDate());
                sb.append("</td>");

                sb.append("<td class=\"testrunstarttime linkhand\" onClick=\"window.location.href = 'taf/" + Settings.currentApiVersion + "/testrun/" + String.valueOf(i) + "'\">");
                sb.append(getStartTime());
                sb.append("</td>");

                sb.append("<td class=\"testrunstoptime linkhand\" onClick=\"window.location.href = 'taf/" + Settings.currentApiVersion + "/testrun/" + String.valueOf(i) + "'\">");
                sb.append(getStopTime());
                sb.append("</td>");

                sb.append("<td class=\"testrunname linkhand\" onClick=\"window.location.href = 'taf/" + Settings.currentApiVersion + "/testrun/" + String.valueOf(i) + "'\">");
                sb.append(getRunName(i));
                sb.append("</td>");

                //sb.append("<td class=\"testrunstatus ").append("passed").append("\">");
                sb.append("<td  onClick=\"window.location.href = 'taf/" + Settings.currentApiVersion + "/testrun/" + String.valueOf(i) + "'\" class=\"testrunstatus tooltip linkhand ").append(object.mostSevereErrorEncountered.toString().toLowerCase()).append("\">");
                sb.append(getSymbol(object.mostSevereErrorEncountered) + "<span class=\"tooltiptext\">").append(object.mostSevereErrorEncountered.toString().toLowerCase().replace("_", " ")).append("</span>");
                sb.append("</td>");

                if(displayTestCaseResultDistribution) {
                    sb.append("<td class=\"bar\">").append(System.lineSeparator());
                    sb.append(buildGraph(object, calculateMaxNumberOfTestCasesInAnyTestRun()));
                    sb.append("</td>").append(System.lineSeparator());
                }

                sb.append("<td class=\"testsetcount\">");
                sb.append(object.testSetJsonsList.size());
                sb.append("</td>");

                sb.append("<td class=\"testcasecount\">");
                sb.append(object.testCasesJsonsList.size());
                sb.append("</td>");

                sb.append("<td class=\"edittestrun linkhand\">");
                sb.append("<span onClick=\"editTestRun(").append(i).append(")\"> <i class=\"icon-edit-sign\"></i> </span>");
                sb.append("</td>");

                sb.append("<td class=\"emailtestrunlink linkhand\">");
                sb.append("<span onClick=\"emailLink(").append(i).append(")\"> <i class=\"fa fa-envelope\" aria-hidden=\"true\"></i> </span>");
                sb.append("</td>");

                sb.append("<td class=\"removetestrun linkhand\">");
                sb.append("<span onClick=\"removeTestRun(").append(i).append(")\"> <i class=\"fa fa-trash\"></i> </span>");
                sb.append("</td>");

                sb.append("</tr>");
                sb.append(System.lineSeparator());
            }
        }
        sb.append("        </table>").append(System.lineSeparator());
        return sb.toString();
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
        sb.append(testRunResultListing(false, 0));
        sb.append(CommonSections.pageFooter()).append(System.lineSeparator());
        sb.append("        </td>").append(System.lineSeparator());
        sb.append("      </tr>").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append("    </table>").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append("    </body>").append(System.lineSeparator());
        sb.append("</html>").append(System.lineSeparator());
        return sb.toString();
    }

    private String getSymbol(TestCase.ResultStatus mostSevereErrorEncountered) {
        switch (mostSevereErrorEncountered){
            case FAILED_WITH_BOTH_NEW_AND_KNOWN_ERRORS:
                return "<i class=\"fa fa-exclamation-circle\" aria-hidden=\"true\"></i>";
            case FAILED_WITH_ONLY_KNOWN_ERRORS:
                //return "<i class=\"fa fa-info-circle\" aria-hidden=\"true\"></i>";
                return "<i class=\"fa fa-exclamation-circle\" aria-hidden=\"true\"></i>";
                /*
            case FAILED_WITH_ONLY_NEW_ERRORS:
                return "<i class=\"fa fa-exclamation-triangle\" aria-hidden=\"true\"></i>";
            case UNEVALUATED:
                return "<i class=\"fa fa-minus-square\" aria-hidden=\"true\"></i>";
            case PASSED:
                return "<i class=\"fa fa-check-square\" aria-hidden=\"true\"></i>";
                */
            default:
                return "&shy;&#8203;&#x200B;";
        }
    }

    private String extraHeadEntrys() {
        return "<link href=\"//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css\" rel=\"stylesheet\">" + System.lineSeparator() +
                "<meta http-equiv=\"refresh\" content=\"60; URL=\"taf/testruns\">" + System.lineSeparator();
    }

    private String getRunName(int index){
        if(object == null) return "<i>unknown</i>";
        return "TestRun '" + object.testRunName + "'";

    }

    private String getStartDate(){
        if(object != null && object.runStartTime != null){
            return new SimpleDateFormat("yyyy-MM-dd").format(object.runStartTime);
        } else {
            return "<i>unknown</i>";
        }
    }

    private String getStartTime(){
        if(object != null && object.runStartTime != null){
            return new SimpleDateFormat("HH:mm:ss").format(object.runStartTime);
        } else {
            return "<i>unknown</i>";
        }
    }

    private String getStopTime(){
        if(object != null && object.runStopTime != null){
            return new SimpleDateFormat("HH:mm:ss").format(object.runStopTime);
        } else {
            return "<i>unknown</i>";
        }
    }

    String scripts(){
        StringBuilder sb = new StringBuilder();
        sb.append("<script type=\"text/javascript\">").append(System.lineSeparator());
        sb.append("   function removeTestRun(index) {").append(System.lineSeparator());
        sb.append("      if(confirm('This would remove test run ' + index)){;").append(System.lineSeparator());
        sb.append("         var httpMethod = 'DELETE';").append(System.lineSeparator());
        sb.append("         function callback(){console.log('Removed test run with index ' + index);}").append(System.lineSeparator());
        sb.append("         var url = 'taf/v1/testrun/' + index;").append(System.lineSeparator());
        sb.append("         httpRequest(url, callback, httpMethod);").append(System.lineSeparator());
        sb.append("         location.reload();").append(System.lineSeparator());
        sb.append("      }").append(System.lineSeparator());
        sb.append("   }").append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("   function readBody(xhr) {").append(System.lineSeparator());
        sb.append("      var data;").append(System.lineSeparator());
        sb.append("      if (!xhr.responseType || xhr.responseType === \"text\") {").append(System.lineSeparator());
        sb.append("         data = xhr.responseText;").append(System.lineSeparator());
        sb.append("      } else if (xhr.responseType === \"document\") {").append(System.lineSeparator());
        sb.append("         data = xhr.responseXML;").append(System.lineSeparator());
        sb.append("      } else {").append(System.lineSeparator());
        sb.append("         data = xhr.response;").append(System.lineSeparator());
        sb.append("      }").append(System.lineSeparator());
        sb.append("      return data;").append(System.lineSeparator());
        sb.append("   }").append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("   function httpRequest(url, callback, httpMethod){").append(System.lineSeparator());
        sb.append("      var xhttp = new XMLHttpRequest();").append(System.lineSeparator());
        sb.append("      var response = null;").append(System.lineSeparator());
        sb.append("      xhttp.onreadystatechange = function() {").append(System.lineSeparator());
        sb.append("         if (xhttp.readyState == 4 && xhttp.status == 200) {").append(System.lineSeparator());
        sb.append("             lastresponse = readBody(xhttp);").append(System.lineSeparator());
        sb.append("         }").append(System.lineSeparator());
        sb.append("      }").append(System.lineSeparator());
        sb.append("      xhttp.open(httpMethod, url, true);").append(System.lineSeparator());
        sb.append("      xhttp.send();").append(System.lineSeparator());
        sb.append("   }").append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("   function editTestRun(index){").append(System.lineSeparator());
        sb.append("      window.location.href = 'taf/v1/testrunedit/' + index;").append(System.lineSeparator());
        sb.append("   }").append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("   function emailLink(index){").append(System.lineSeparator());
        sb.append("      window.open('mailto:who@what.org?subject=Please, check this test run out&body=Use link below to view test run:%0D%0A%0D%0A' + window.location.origin + '/taf/v1/testrun/' + index +'%0D%0A%0D%0AThanks!%0D%0A%0D%0A');").append(System.lineSeparator());
        //sb.append("      window.location.href = 'taf/v1/testrunemail/' + index;").append(System.lineSeparator());
        sb.append("   }").append(System.lineSeparator());
        sb.append("</script>").append(System.lineSeparator());
        return sb.toString();
    }

    String extraStyles(){
        int distributionGraphsHeight = 10;
        StringBuilder sb = new StringBuilder();
        sb.append("table.testrunlisting {}").append(System.lineSeparator());
        sb.append(".center { text-align: center; }").append(System.lineSeparator());
        sb.append("tr.testrunrow {}").append(System.lineSeparator());
        sb.append("tr.testrunrow:hover { text-shadow: 1px 1px 5px #FF0000; background-color: " + UxColors.LIGHT_GREY.getHtmlColorCode() + "; }").append(System.lineSeparator());
        sb.append(".linkhand:hover  { cursor: pointer; cursor: hand; }").append(System.lineSeparator());
        sb.append("td.testrunstartdate { white-space: nowrap; }").append(System.lineSeparator());
        sb.append("td.testrunstarttime { white-space: nowrap; }").append(System.lineSeparator());
        sb.append("td.testrunstoptime { white-space: nowrap; }").append(System.lineSeparator());
        sb.append("td.testrunname { white-space: nowrap; max-width: 50%; text-overflow: ellipsis; }").append(System.lineSeparator());
        sb.append("td.testrunstatus { vertical-align: middle; text-align:center; width: 50px; text-shadow: 0px 0px 0px #FF0000;}").append(System.lineSeparator());
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.FAILED_WITH_ONLY_KNOWN_ERRORS.toString().toLowerCase()).append(" { color: ").append(UxColors.YELLOW.getHtmlColorCode()).append("; background-color: ").append(UxColors.GREEN.getHtmlColorCode()).append("; text-shadow: 0px 0px 0px #FF0000;}").append(System.lineSeparator());
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.PASSED.toString().toLowerCase()).append(" { color: ").append(UxColors.LIGHT_GREY.getHtmlColorCode()).append("; background-color: ").append(UxColors.GREEN.getHtmlColorCode()).append("; text-shadow: 0px 0px 0px #FF0000; }");
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.FAILED_WITH_ONLY_NEW_ERRORS.toString().toLowerCase()).append(" { color: ").append(UxColors.WHITE.getHtmlColorCode()).append("; background-color: ").append(UxColors.RED.getHtmlColorCode()).append("; text-shadow: 0px 0px 0px #FF0000; }");
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.FAILED_WITH_BOTH_NEW_AND_KNOWN_ERRORS.toString().toLowerCase()).append(" { color: ").append(UxColors.YELLOW.getHtmlColorCode()).append("; background-color: ").append(UxColors.RED.getHtmlColorCode()).append("; text-shadow: 0px 0px 0px #FF0000; }");
        sb.append("td.testrunstatus.").append(TestCase.ResultStatus.UNEVALUATED.toString().toLowerCase()).append(" { color: ").append(UxColors.LIGHT_GREY.getHtmlColorCode()).append("; background-color: ").append(UxColors.MID_GREY.getHtmlColorCode()).append("; text-shadow: 0px 0px 0px #FF0000; }");
        sb.append(".RESULTS_BAR { border-spacing: 0; padding: 4px; width: 100%; height: 100%; background-color: ").append(UxColors.MID_GREY.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        sb.append("td.bar { width: 200px; min-height: 15px; }").append(System.lineSeparator());
        sb.append("td.resultsgraph { border-collapse: collapse; height: ").append(distributionGraphsHeight).append("px ; }").append(System.lineSeparator());
        sb.append("td.resultsgraph.passed { background-color: ").append(UxColors.GREEN.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        sb.append("td.resultsgraph.warning { background-color: ").append(UxColors.YELLOW.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        sb.append("td.resultsgraph.unevaluated { background-color: ").append(UxColors.LIGHT_GREY.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        sb.append("td.resultsgraph.both { background-color: ").append(UxColors.ORANGE.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        sb.append("td.resultsgraph.bad { background-color: ").append(UxColors.RED.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        sb.append("td.resultsgraph.buffer { background-color: ").append(UxColors.MID_GREY.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        sb.append("td.testsetcount { text-align: center; }").append(System.lineSeparator());
        sb.append("td.testcasecount { text-align: center; }").append(System.lineSeparator());
        sb.append("td.edittestrun   { text-align:center; text-shadow: 0px 0px 0px #FF0000;} ").append(System.lineSeparator());
        sb.append("td.emailtestrunlink   { text-align:center; text-shadow: 0px 0px 0px #FF0000;} ").append(System.lineSeparator());
        sb.append("td.emailtestrun   { text-align:center; text-shadow: 0px 0px 0px #FF0000;} ").append(System.lineSeparator());
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
                "padding: 5px;\n" +
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

    private int calculateMaxNumberOfTestCasesInAnyTestRun(){
        int maxNumberOfTestRunsInAnyTestRun = 0;
        for(String json : TestRunList.getAll()){
            TestRunMapper testRunMapper = new TestRunMapper(json);
            Integer testCaseCount = testRunMapper.object(mapper).testCasesJsonsList.size();
            if(testCaseCount == null) continue;
            if(testCaseCount > maxNumberOfTestRunsInAnyTestRun)
                maxNumberOfTestRunsInAnyTestRun = testCaseCount;
        }
        System.out.println("Max test case count: " + maxNumberOfTestRunsInAnyTestRun);
        return maxNumberOfTestRunsInAnyTestRun;
    }

    private String buildGraph(TafBackendServerTestRunReporter testRunReporter, int maxNumberOfTestRunsInAnyTestRun){
        int successfulTestCases = 0;
        int failedTestCasesWithNewDeviations = 0;
        int testCasesWithOnlyKnownErrors = 0;
        int testCasesWithBothNewAndKnownErrors = 0;
        int unevaluatedCount = 0;
        String LF = System.lineSeparator();

        for(String json : testRunReporter.testCasesJsonsList){
            if(json == null) continue;
            TestCase testCase = null;
            TestCaseMapper testCaseMapper = new TestCaseMapper(json);
            testCase = testCaseMapper.object();
            if(testCase == null)continue;
            switch (testCase.resultStatus){
                case PASSED:
                    successfulTestCases++;
                    break;
                case FAILED_WITH_ONLY_NEW_ERRORS:
                    failedTestCasesWithNewDeviations++;
                    break;
                case FAILED_WITH_ONLY_KNOWN_ERRORS:
                    testCasesWithOnlyKnownErrors++;
                    break;
                case FAILED_WITH_BOTH_NEW_AND_KNOWN_ERRORS:
                    testCasesWithBothNewAndKnownErrors++;
                    break;
                default:
                    unevaluatedCount++;
                    break;
            }
        }
        int testCaseCount = successfulTestCases + testCasesWithBothNewAndKnownErrors + testCasesWithOnlyKnownErrors + failedTestCasesWithNewDeviations + unevaluatedCount;
        int bufferpercentage = 100-(100*(testCaseCount/maxNumberOfTestRunsInAnyTestRun));
        StringBuilder bar = new StringBuilder();
        bar.append("          <table class=\"").append(TestRunDetailsPage.HtmlStyleNames.RESULTS_BAR.toString()).append("\">").append(LF);
        bar.append("            <tr>").append(LF);
        if(successfulTestCases > 0){
            bar.append("              <td class=\"resultsgraph passed\" style=\"width: ").append((successfulTestCases * 100) / maxNumberOfTestRunsInAnyTestRun).append("%\"></td>").append(LF);
        }
        if(testCasesWithOnlyKnownErrors > 0){
            bar.append("              <td class=\"resultsgraph warning\" style=\"width: ").append((testCasesWithOnlyKnownErrors * 100) / maxNumberOfTestRunsInAnyTestRun).append("%\"></td>").append(LF);
        }
        if(unevaluatedCount > 0){
            bar.append("              <td class=\"resultsgraph unevaluated\" style=\"width: ").append((unevaluatedCount * 100) / maxNumberOfTestRunsInAnyTestRun).append("%\"></td>").append(LF);
        }
        if(testCasesWithBothNewAndKnownErrors > 0){
            bar.append("              <td class=\"resultsgraph both\" style=\"width: ").append((testCasesWithBothNewAndKnownErrors * 100) / maxNumberOfTestRunsInAnyTestRun).append("%\"></td>").append(LF);
        }
        if(failedTestCasesWithNewDeviations > 0){
            bar.append("              <td class=\"resultsgraph bad\" style=\"width: ").append((failedTestCasesWithNewDeviations * 100) / maxNumberOfTestRunsInAnyTestRun).append("%\"></td>").append(LF);
        }
        if(bufferpercentage > 0){
            bar.append("              <td class=\"resultsgraph buffer\" style=\"width: ").append(bufferpercentage).append("%\"></td>");
        }
        bar.append("            </tr>").append(LF);
        bar.append("          </table>").append(LF);
        return bar.toString();

    }
}
