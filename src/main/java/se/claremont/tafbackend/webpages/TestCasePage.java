package se.claremont.tafbackend.webpages;

import se.claremont.autotest.common.logging.KnownError;
import se.claremont.autotest.common.logging.LogLevel;
import se.claremont.autotest.common.logging.LogPost;
import se.claremont.autotest.common.reporting.HtmlStyles;
import se.claremont.autotest.common.reporting.UxColors;
import se.claremont.autotest.common.reporting.testcasereports.TestCaseLogReporterHtmlLogFile;
import se.claremont.autotest.common.reporting.testrunreports.HtmlSummaryReport;
import se.claremont.autotest.common.support.*;
import se.claremont.autotest.common.testcase.TestCase;
import se.claremont.autotest.common.testcase.TestCaseLogSection;
import se.claremont.autotest.common.testrun.Settings;
import se.claremont.autotest.common.testrun.TestRun;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jordam on 2017-03-19.
 */
public class TestCasePage {
    TestCase testCase;
    static String LF = System.lineSeparator();

    public TestCasePage(TestCase testCase){
        this.testCase = testCase;
        if(this.testCase.stopTime == null){
            if(this.testCase.testCaseLog.logPosts.size() > 0){
                LogPost lastLogPostOfTestCase = this.testCase.testCaseLog.logPosts.get(this.testCase.testCaseLog.logPosts.size()-1);
                this.testCase.stopTime = lastLogPostOfTestCase.date;
            } else if (this.testCase.startTime != null) {
                this.testCase.stopTime = this.testCase.startTime;
            } else {
                this.testCase.stopTime = new Date();
            }
        }
    }

    private String extraHeadSections(){
        return "    <title>" + testCase.testName + " execution log</title>" + LF +
                "    <link rel=\"stylesheet\" href=\"http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css\"/>";
    }

    public String asHtml(){
        String html = "<!DOCTYPE html>" + LF + "<html lang=\"en\">" + LF + LF +
                CommonSections.headSection(scriptSection(), styles(), extraHeadSections()) +
                "  <body>" + LF + LF +
                asHtmlSection() +
                "  </body>" + LF + LF +
                "</html>" + LF;
        return html;
    }

    /**
     * Enums for the class names of the HTML document to avoid orphan references.
     */
    private enum HtmlLogStyleNames{
        KNOWN_ERROR,
        KNOWN_ERRORS_NOT_ENCOUNTERED,
        TIMESTAMP,
        STRIPED,
        LOG_ROW,
        LOG_POSTS_LIST,
        KNOWN_ERRORS,
        HEAD,
        DATA,
        TEST_CASE_DATA,
        TEST_STEP_CLASS_NAME,
        TEST_STEP_NAME,
        TEST_CASE_NAME,
        TEST_CASE_DATA_PARAMETER_NAME,
        TEST_CASE_DATA_PARAMETER_VALUE
    }

    /**
     * Compiles a test case result to html content in a format that could be inserted into a web page
     *
     * @return HTML
     */
    private String asHtmlSection(){
        StringBuilder sb = new StringBuilder();
        sb.append("    <table id=\"").append(TestRunDetailsPage.HtmlStyleNames.CONTENT.toString()).append("\">").append(LF).append(LF);
        sb.append("      <tr>").append(LF);
        sb.append("        <td>").append(LF).append(LF);
        sb.append(htmlSectionBodyHeader());
        sb.append(htmlSectionEncounteredKnownErrors());
        sb.append(htmlSectionTestCaseData());
        sb.append(htmlSectionNonEncounteredKnownTestCaseErrors());
        sb.append(htmlSectionTestCaseLogEntries());
        sb.append(CommonSections.pageFooter());
        sb.append("        </td>").append(System.lineSeparator());
        sb.append("      </tr>").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append("    </table>").append(System.lineSeparator()).append(System.lineSeparator());
        return sb.toString();
    }

    /**
     * Used to append HTML style information to the HTML based testCaseLog
     * @return A HTML formatted string to incorporate in the style tag in the HTML testCaseLog
     */
    private static String styles(){
        return  "      .ui-accordion .ui-accordion-content  { padding:0px; }" + LF +
                TestCaseLogSection.htmlStyleInformation() +
                HtmlStyles.tableVerificationStyles() +
                LogPost.htmlStyleInformation() +
                "      b.good                  { color: " + UxColors.GREEN.getHtmlColorCode() + "; }" + LF +
                "      b.bad                   { color: " + UxColors.RED.getHtmlColorCode() + "; }" + LF +
                "      td." + SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.KNOWN_ERROR.toString()) + "           { color: " + UxColors.RED.getHtmlColorCode() + "; font-weight: bold; } " + LF +
                "      table." + SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.STRIPED.toString()) + "  { background-color:" + UxColors.WHITE.getHtmlColorCode() + "; }" + LF +
                "      table." + SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.STRIPED.toString()) + " tr:nth-child(even)                 { background-color: " + UxColors.LIGHT_GREY.getHtmlColorCode() + "; }" + LF +
                "     .logpost:nth-child(odd), .testdatapost:nth-child(odd)  { background-color: " + UxColors.LIGHT_GREY.getHtmlColorCode() + "; }" + LF +
                "     .logpost, .testdatapost                                { border-bottom: 1px solid " + UxColors.MID_GREY.getHtmlColorCode() + "; }" + LF +
                "      td.logPostLogLevel       { width: 130px; }" + LF +
                "      td.logMessage            { max-width: 99%; }" + LF +
                "      img.screenshot:hover     { margin: -1px -2px -2px -1px; width: 340px; }" + se.claremont.autotest.common.support.SupportMethods.LF +
                "      img.screenshot           { border: 0px none; width:105px; background: #999; }" + LF +
                HtmlStyles.asString() +
                //TableData.TableVerifierLoggingHtmlStyles.styles() +

                //W3C checker
                "      font.w3cvalidationinfo   { color: " + UxColors.DARK_GREY.getHtmlColorCode() + "; font-weight: bold; }" + LF +
                "      font.w3cvalidationerror  { color: " + UxColors.RED.getHtmlColorCode() + "; font-weight: bold; }" + LF +
                "      font.w3cvalidationother  { color: " + UxColors.DARK_GREY.getHtmlColorCode() + "; font-weight: bold; }" + LF +
                "      pre              { font-family: Consolas, Menlo, Monaco, Lucida Console, Liberation Mono, DejaVu Sans Mono, Bitstream Vera Sans Mono, Courier New, monospace, serif;" + LF +
                "                             margin-bottom: 10px;" + LF +
                "                             overflow: scroll;" + LF +
                "                             padding: 5px;" + LF +
                "                             background-color: #eee;" + LF +
                "                             width: 70%;" + LF +
                "                             padding-bottom: 20px!ie7;"  + LF +
                "                             max - height: 600px;" + LF +
                "      }" + LF +
                htmlStyleHelpOverlay();

    }

    private static String htmlStyleHelpOverlay(){
        return  "            #help {" + LF +
                "                vertical-align: top;" + LF +
                "                float: right;" + LF +
                "                text-align: right;" + LF +
                "                color: " + UxColors.LIGHT_BLUE.getHtmlColorCode() + ";" + LF +
                "            }" + LF +
                "" + LF +
                "            #helpText {" + LF +
                "                visibility: hidden;" + LF +
                "                left: 50px;" + LF +
                "                width: auto;" + LF +
                "                background-color: " + UxColors.DARK_BLUE.getHtmlColorCode() + ";" + LF +
                "                color: " + UxColors.WHITE.getHtmlColorCode() + ";" + LF +
                "                text-align: left;" + LF +
                "                padding: 15px;" + LF +
                "                border-radius: 6px;" + LF +
                "                position: absolute;" + LF +
                "                z-index: 1;" + LF +
                "            }" + LF +
                "" + LF +
                "            /* Show the tooltip text when you mouse over the tooltip container */" + LF +
                "            #help:hover #helpText {" + LF +
                "                visibility: visible;" + LF +
                "            }" + LF;
    }

    private String htmlSectionBodyHeader(){
        StringBuilder sb = new StringBuilder();
        sb.append(CommonSections.pageHeader());
        sb.append("    <div id=\"" + SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.HEAD.toString()) + "\">" + LF +
                "<br>" + LF + "<br>" + LF +
                //"      <a href=\"https://github.com/claremontqualitymanagement/TestAutomationFramework\" target=\"_blank\"><img alt=\"logo\" id=\"logo\" src=\"https://avatars3.githubusercontent.com/u/22028977?v=3&s=400\"></a>" + LF +
                "      <br><span class=\"pagetitle\">TAF test case results log</span>" + LF +
                "         <span class=\"pagetitle\" id=\"help\">(?)<span id=\"helpText\">" + helpText() +
                "         </span>" + LF +
                "      </span>" + LF +
                status() + "<br>" + LF +
                //"      <img alt=\"logo\" id=\"logo\" src=\"" + TestRun.settings.getValue(Settings.SettingParameters.PATH_TO_LOGO) + "\">" + LF +
                "      <h1>Test results for test case '" + testCase.testName + "'</h1>" + LF +
                "      <p>" + LF +
                "        Result status: " + StringManagement.enumCapitalNameToFriendlyString(testCase.resultStatus.toString()) + "<br>" + LF +
                "        Start time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(testCase.startTime) + "<br>" + LF +
                "        Stop time:  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(testCase.stopTime) + "<br>" + LF +
                "        Duration:   " + StringManagement.timeDurationAsString(testCase.startTime, testCase.stopTime) + LF +
                "      </p>" + LF +
                "      <p>" + LF +
                "         Number of verifications performed: " + numberOfVerificationsPerformed() + LF +
                "         <br>" + LF +
                "         Number of non-verifying execution steps performed: " + numberOfExecitionStepsPerformed() + LF +
                "      </p>" + LF +
                "      <br>" + LF +
                "    </div>" + LF + LF);
        return sb.toString();
    }

    private int numberOfVerificationsPerformed() {
        int count = 0;
        for(LogPost logPost : testCase.testCaseLog.logPosts){
            if(logPost.logLevel == LogLevel.VERIFICATION_PASSED || logPost.logLevel == LogLevel.VERIFICATION_FAILED){
                count++;
            }
        }
        return count;
    }

    private int numberOfExecitionStepsPerformed() {
        int count = 0;
        for(LogPost logPost : testCase.testCaseLog.logPosts){
            if(logPost.logLevel == LogLevel.EXECUTED){
                count++;
            }
        }
        return count;
    }

    private String helpText(){
        return  LF + LF + "<b>Brief help for interpreting the log</b><br>" + LF +
                "This test results log is divided into structured sections.<br>" + LF +
                "   * First there is some statistics and overview information.<br>" + LF +
                "   * The next section is test case data used during execution. If no test data was registered during test case execution this section is suppressed.<br>" + LF +
                "   * The log entries from the test run is the main part of the test case log<br>" + LF +
                "<br>" + LF +
                "<b>Log sections and log behavior</b>" + LF +
                "The log entries are grouped in test steps that are named after the actions in the test case.<br>" + LF +
                "Log sections with errors are expanded upon opening the log file." + LF +
                "If a log section title is clicked the log section log content display is toggled.<br>" +
                "<br>" + LF +
                "<b>Progress bar</b><br>" + LF +
                "Between log sections a thin line can be seen. This is a progress bar that indicate how much time was spent in this log section." + LF +
                "<br><br>" +
                "<b>Debug log rows diplay</b><br>" + LF +
                "More detailed information is displayed if the <b>debug checkbox</b> is checked. Then even debug log entries are displayed." + LF +
                "<br><br>" + LF +
                "<b>Test step class name</b><br>" + LF +
                "When hovering over a log section title the class name for the method producing the log entries are displayed in a tooltip." + LF +
                "<br><br>" +
                "<b>Screenshots, images and references</b><br>" + LF +
                "If screenshots exist in the log these will become larger upon hovering over them, and they till open in another browser window upon click.<br>" +
                "Some other relevant information will also be displayed in another window upon click, for example saved html content." + LF +
                "<br><br>" + LF +
                "More information could be reached by clicking the logo in the top left corner of this document and checking out the GitHub Wiki.<br>" + LF + LF;
    }

    private String status(){
        if(testCase.testCaseLog.hasEncounteredErrors()){
            return "      <p class=\"statussymbol\">Status: <b class=\"bad\">&#x2717;</b></p>" + LF;
        } else {
            return "      <p class=\"statussymbol\">Status: <b class=\"good\">&#x2713;</b></p>" + LF;
        }
    }

    private String htmlSectionTestCaseLogEntries(){
        return "<br>" + LF +
                "      <h2>Test case log</h2>" + LF +
                "     <label><input type=\"checkbox\" id=\"showDebugCheckbox\">Show verbose debugging information</label>" + LF +
                "     <div id=\"logpostlist\">" + LF + LF +
                testStepLogPostSections(testCase) + LF +
                "     </div>" + LF +
                "     <br><br>" + LF;
    }

    private String htmlSectionTestCaseData(){
        StringBuilder html = new StringBuilder();
        if(testCase.testCaseData.testCaseDataList.size() > 0){
            html.append("      <div id=\"testdata\" class=\"testcasedata expandable\" >").append(LF);
            html.append("         <h2>Test case data</h2>").append(LF);
            html.append("         <div id=\"expandable_content\">").append(LF);
            html.append("         <table class=\"").append(SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.STRIPED.toString())).append("\" id=\"").append(SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.TEST_CASE_DATA.toString())).append("\">").append(LF);
            for(ValuePair valuePair : testCase.testCaseData.testCaseDataList){
                html.append("           <tr><td class=\"").
                        append(SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.TEST_CASE_DATA_PARAMETER_NAME.toString())).
                        append("\">").append(valuePair.parameter).append("</td><td class=\"").
                        append(SupportMethods.enumMemberNameToLower(SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.TEST_CASE_DATA_PARAMETER_VALUE.toString()))).
                        append("\">").append(valuePair.value).append("</tr>").append(LF);
            }
            html.append("         </table>").append(LF);
            html.append("         </div>").append(LF);
            html.append("      </div>").append(LF).append(LF);
        }
        return html.toString();
    }

    private String htmlSectionEncounteredKnownErrors(){
        StringBuilder html = new StringBuilder();
        boolean knownErrorsEncountered = false;
        for(KnownError knownError : testCase.testCaseKnownErrorsList.knownErrors) {
            if(knownError.encountered()) {
                knownErrorsEncountered = true;
                break;
            }
        }
        if(!knownErrorsEncountered){
            for(KnownError knownError : testCase.testSetKnownErrors.knownErrors){
                if(knownError.encountered()){
                    knownErrorsEncountered = true;
                    break;
                }
            }
        }
        if(knownErrorsEncountered){
            html.append("    <div id=\"").append(SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.KNOWN_ERRORS.toString())).append("\">").append(LF);
            html.append("      <h2>Encountered known errors</h2>").append(LF);
            html.append("      <table class=\"encounteredknownerrors\">").append(LF);
            for(KnownError knownError : testCase.testCaseKnownErrorsList.knownErrors){
                if(knownError.encountered()){
                    html.append("        <tr><td class=\"").append(SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.KNOWN_ERROR.toString())).append("\">").append(knownError.description).append("</td></tr>").append(LF);
                }
            }
            for(KnownError knownError : testCase.testSetKnownErrors.knownErrors){
                if(knownError.encountered()){
                    html.append("        <tr><td class=\"").append(SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.KNOWN_ERROR.toString())).append("\">").append(knownError.description).append("</td></tr>").append(LF);
                }
            }
            html.append("      </table>").append(LF);
            html.append("    </div>").append(LF).append("<br><br>").append(LF);
        }
        return html.toString();
    }


    private String htmlSectionNonEncounteredKnownTestCaseErrors(){
        StringBuilder html = new StringBuilder();
        boolean hasKnownErrorsNotEncountered = false;
        for(KnownError knownError : testCase.testCaseKnownErrorsList.knownErrors) {
            if(!knownError.encountered()) {
                hasKnownErrorsNotEncountered = true;
                break;
            }
        }
        if(hasKnownErrorsNotEncountered){
            html.append("    <div id=\"").append(SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.KNOWN_ERRORS_NOT_ENCOUNTERED.toString())).append("\">").append(LF);
            html.append("      <h2>Known test case errors that were not encountered (possibly fixed)</h2>").append(LF);
            html.append("      <table>").append(LF);
            for(KnownError knownError : testCase.testCaseKnownErrorsList.knownErrors){
                if(!knownError.encountered()){
                    html.append("        <tr><td class=\"").append(SupportMethods.enumMemberNameToLower(TestCaseLogReporterHtmlLogFile.HtmlLogStyleNames.KNOWN_ERROR.toString())).append("\">").append(knownError.description).append("</td></tr>").append(LF);
                }
            }
            html.append("      </table>").append(LF);
            html.append("    </div>").append(LF).append(LF);
        }
        return html.toString();

    }

    private String testStepLogPostSections(TestCase testCase){
        if(testCase.testCaseLog.logPosts.size() == 0) return null;
        StringBuilder html = new StringBuilder();
        ArrayList<TestCaseLogSection> logSections = testCase.testCaseLog.toLogSections();
        for(TestCaseLogSection testCaseLogSection : logSections){
            html.append(testCaseLogSection.toHtml());
        }
        return html.toString();
    }


    private String scriptSection(){
        return "      <script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-latest.min.js\"></script>" + LF +
                "     <script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js\"></script>" + LF +
                "     <script type=\"text/javascript\">" + LF + LF +
                "         function showDebug(shouldShowDebug){" + LF +
                "           $(\".logpost.debug\").toggle(shouldShowDebug);" + LF +
                "         }" + LF +
                "" + LF +
                "         $(function() {" + LF +
                "           $(\"#showDebugCheckbox\").on(\"click\", function(evt) {" + LF +
                "              var shouldShowDebug = $(this).prop(\"checked\");" + LF +
                "              showDebug(shouldShowDebug);" + LF +
                "           });" + LF +
                "           showDebug(false);" + LF +
                "           $(\".expandable\").accordion({ collapsible: true, active: false, heightStyle: \"content\" });" + LF +
                "           $(\".expandable.initially-expanded\").accordion(\"option\", \"active\", 0);" + LF +
                "         });" + LF +
                "" + LF +
                "     </script>" + LF;
    }


}
