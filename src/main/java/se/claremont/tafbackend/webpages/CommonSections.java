package se.claremont.tafbackend.webpages;

import se.claremont.autotest.common.reporting.TafVersionGetter;
import se.claremont.autotest.common.reporting.UxColors;
import se.claremont.autotest.common.reporting.testrunreports.HtmlSummaryReport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Shared sections for web pages - for unified look and feel.
 *
 * Created by jordam on 2017-03-21.
 */
public class CommonSections {

    public static String headSection(String scripts, String extraStyles, String extraHeadEntrys){
        StringBuilder sb = new StringBuilder();
        sb.append("      <meta charset=\"UTF-8\">").append(System.lineSeparator());
        sb.append("      <link rel=\"shortcut icon\" href=\"http://46.101.193.212/TAF/images/facicon.png\">").append(LF);
        sb.append("      <title>TAF Server</title>").append(System.lineSeparator());
        sb.append("      <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>").append(LF);
        sb.append("      <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">").append(LF);
        sb.append("      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>").append(System.lineSeparator());
        sb.append("      <style>").append(System.lineSeparator());
        sb.append(CommonSections.styles(extraStyles));
        sb.append("      </style>").append(System.lineSeparator());
        sb.append("      <script type=\"text/javascript\">").append(System.lineSeparator());
        sb.append("         var baseAddress = window.location.origin;").append(System.lineSeparator());
        sb.append("         document.write(\"<base href='\" + window.location.origin + \"' />\");").append(System.lineSeparator());
        sb.append("      </script>").append(System.lineSeparator());
        sb.append(scripts);
        sb.append(extraHeadEntrys);
        return sb.toString();
    }

    private static String LF = System.lineSeparator();

    private static String styles(String extraStyles) {
        return "\t\t\t\n" +
                "      body                    { font-family: Helvetica Neue, Helvetica, Arial, sans-serif; font-size: 14px; width:90%; margin-left: 2%; margin-top: 1%; color: " + UxColors.DARK_GREY.getHtmlColorCode() + "; background-color: " + UxColors.LIGHT_BLUE.getHtmlColorCode() + "; }" + LF +
                "      h1, h2                  { margin-top: 20px; margin-bottom: 10px; line-height: 1.1; font-family: inherit; }" + LF +
                "      h1                      { font-size:24px; }" + LF +
                "      h2                      { font-size:20px; }" + LF +
                "      table#" + TestRunDetailsPage.HtmlStyleNames.CONTENT.toString() + "           { background-color: white ; padding: 20px; }" + LF +
                "      table.pageFooter            { border: 0px solid " + UxColors.WHITE.getHtmlColorCode() + "; }" + LF +
                "      .pagetitle              { color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; font-size:24px; font-weight: bold; }" + LF +
                "      .pageFooter                  { border: 0px none; width: 100%; color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; text-align: center; align: center; }" + LF +
                "      a                     { color: " + UxColors.DARK_BLUE.getHtmlColorCode() + ";}" + LF +
                "      th                    { text-align: left; }" + LF +
                "      img.toplogo           { width: 30%; }" + LF +
                "      div.topmenu           { width: 70%; background-color: " + UxColors.LIGHT_BLUE.getHtmlColorCode() + "; }" + LF +
                "      span.topmenuelement   { color: " + UxColors.WHITE.getHtmlColorCode() + "; padding-left:10px; padding-right:10px; display:inline-block;}" + LF +
                "      span.topmenuelement:hover   { color: " + UxColors.WHITE.getHtmlColorCode() + "; background-color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; }" + LF +
                "      img.bottomlogo        { width: 20%; }" + LF +
                "      td.bottomlogo         { text-align: center; background-color: " + UxColors.WHITE.getHtmlColorCode() + "; }" + LF +
                "      a." + TestRunDetailsPage.HtmlStyleNames.LICENSE_LINK.toString().toLowerCase() + "      { color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; text-decoration: none; }" + LF +
                "      a." + TestRunDetailsPage.HtmlStyleNames.LICENSE_LINK.toString().toLowerCase() + ":visited      { color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; text-decoration: none; }" + LF +
                "      a." + TestRunDetailsPage.HtmlStyleNames.LICENSE_LINK.toString().toLowerCase() + ":hover      { color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; text-decoration: underline; }" + LF +
                extraStyles;
    }

    public static String pageHeader(){
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"http://www.claremont.se\"><img class=\"toplogo\" src=\"http://46.101.193.212/TAF/images/claremontlogo.gif\"></a>").append(System.lineSeparator());
        sb.append("<div class=\"topmenu\">");
        sb.append("<a href=\"taf\"><span class=\"topmenuelement\"><i class=\"fa fa-home\"></i> Start</span></a>");
        sb.append("<a href=\"taf/").append(se.claremont.tafbackend.server.Settings.currentApiVersion).append("/testruns\"><span class=\"topmenuelement\">Test run listing</span></a>");
        sb.append("<a href=\"taf/about\"><span class=\"topmenuelement\">About</span></a>");
        sb.append("</div>").append(System.lineSeparator());
        return sb.toString();

    }

    /**
     * Produces a document pageFooter for the summary reportTestRun.
     * @return HTML section for pageFooter
     */
    public static String pageFooter(){
        String versionInfo = "";
        String version = TafVersionGetter.tafVersion();
        if(version != null){
            versionInfo = "<a href=\"https://github.com/claremontqualitymanagement/TestAutomationFramework/releases\" target=\"_blank\" class=\"" + HtmlSummaryReport.HtmlStyleNames.LICENSE_LINK.toString().toLowerCase() + "\">";
            versionInfo += "TAF version " + version + ".</a>";
        }
        //noinspection deprecation
        return "<br><br>" +
                "          <table class=\"pageFooter\" width=\"100%\">" + LF +
                "            <tr>" + LF +
                "              <td class=\"bottomlogo\" width=\"100%\"><a href=\"http://www.claremont.se\"><img alt=\"Claremont logo\" class=\"bottomlogo\" src=\"http://46.101.193.212/TAF/images/claremontlogo.gif\"></a></td>" + LF +
                "            </tr><tr>" + LF +
                "              <td width=\"100%\" class=\"" + HtmlSummaryReport.HtmlStyleNames.COPYRIGHT.toString() + "\"><br>TAF is licensed under the <a href=\"https://www.apache.org/licenses/LICENSE-2.0\" target=\"_blank\" class=\"" + HtmlSummaryReport.HtmlStyleNames.LICENSE_LINK.toString().toLowerCase() + "\">Apache 2.0 license</a>. &copy; Claremont " + new SimpleDateFormat("yyyy").format(new Date()) + "." + versionInfo + "</td>" + LF +
                "            </tr>" + LF +
                "          </table>" + LF;
    }



}
