package se.claremont.tafbackend.webpages;

import se.claremont.autotest.common.reporting.UxColors;
import se.claremont.autotest.common.testcase.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jordam on 2017-03-21.
 */
public class CommonSections {

    public static String headSection(){
        StringBuilder sb = new StringBuilder();
        sb.append("      <meta charset=\"UTF-8\">").append(System.lineSeparator());
        sb.append("      <link rel=\"shortcut icon\" href=\"http://46.101.193.212/TAF/images/facicon.png\">").append(LF);
        sb.append("      <title>TAF Server</title>").append(System.lineSeparator());
        sb.append("      <style>").append(System.lineSeparator());
        sb.append(CommonSections.styles());
        sb.append("      </style>").append(System.lineSeparator());
        return sb.toString();
    }

    private static String LF = System.lineSeparator();

    private static String styles() {
        String styles = "\t\t\t\n" +
                "      #" + TestRunDetailsPage.HtmlStyleNames.SOLVED_KNOWN_ERRORS.toString() + "               { color: " + UxColors.DARK_GREY.getHtmlColorCode() + "; }" + LF +
                "      body                  { font-family: Helvetica Neue, Helvetica, Arial, sans-serif; color: " + UxColors.DARK_GREY.getHtmlColorCode() + "; background-color: " + UxColors.LIGHT_BLUE.getHtmlColorCode() + "; }" + LF +
                "      a                     { color: " + UxColors.DARK_BLUE.getHtmlColorCode() + ";}" + LF +
                "      th                    { text-align: left; }" + LF +
                "      img.toplogo           { width: 30%; }" + LF +
                "      img.bottomlogo        { width: 20%; }" + LF +
                "      td.bottomlogo         { text-align: center; background-color: " + UxColors.WHITE.getHtmlColorCode() + "; }" + LF +
                "      table#" + TestRunDetailsPage.HtmlStyleNames.CONTENT.toString() + "      { background-color: " + UxColors.WHITE.getHtmlColorCode() + "; padding: 30px; margin: 30px; }" + LF +
                "      tr." + TestRunDetailsPage.HtmlStyleNames.HOVERABLE.toString() + ":hover           { background-color: " + UxColors.MID_GREY.getHtmlColorCode() + "; }" + LF +
                "      tr." + TestRunDetailsPage.HtmlStyleNames.SOLVED_KNOWN_ERRORS.toString() + "       { font-weight: bold; color: " + UxColors.DARK_GREY.getHtmlColorCode() + "; }" + LF +
                "      li." + TestRunDetailsPage.HtmlStyleNames.HOVERABLE.toString() + ":hover           { background-color: " + UxColors.MID_GREY.getHtmlColorCode() + "; }" + LF +
                "      table#" + TestRunDetailsPage.HtmlStyleNames.STATISTICS.toString() + "                           { background-color: " + UxColors.WHITE.getHtmlColorCode() + "; border-collapse: collapse; border: 1p solid " + UxColors.DARK_GREY.getHtmlColorCode() + "; }" + LF +
                "      ." + TestCase.ResultStatus.FAILED_WITH_ONLY_KNOWN_ERRORS.toString() + "             { color: " + UxColors.DARK_YELLOW.getHtmlColorCode() + "; }" + LF +
                "      ." + TestCase.ResultStatus.PASSED.toString() + "                                    { color: " + UxColors.GREEN.getHtmlColorCode() + "; }" + LF +
                "      ." + TestCase.ResultStatus.FAILED_WITH_BOTH_NEW_AND_KNOWN_ERRORS.toString() + "     { color: " + UxColors.ORANGE.getHtmlColorCode() + "; font-weight: bold; }" + LF +
                "      ." + TestCase.ResultStatus.FAILED_WITH_ONLY_NEW_ERRORS.toString() + "               { color: " + UxColors.RED.getHtmlColorCode() + "; font-weight: bold; }" + LF +
                "      ." + TestCase.ResultStatus.UNEVALUATED.toString() + "                               { color: " + UxColors.DARK_GREY.getHtmlColorCode() + "; text-align: center; }" + LF +
                "      ." + TestRunDetailsPage.HtmlStyleNames.COPYRIGHT.toString() + "                                 { background-color: " + UxColors.WHITE.getHtmlColorCode() + "; color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; text-align: center; }" + LF +
                "       tr#" + TestRunDetailsPage.HtmlStyleNames.STATISTICS_COUNT.toString() + "          { background-color: " + UxColors.MID_GREY.getHtmlColorCode() + "; }" + LF +
                "       tr#" + TestRunDetailsPage.HtmlStyleNames.STATISTICS_HEADER_ROW.toString() + "          { background-color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; color: " + UxColors.WHITE.getHtmlColorCode() + "; text-aligned: left; }" + LF +
                "       table." + TestRunDetailsPage.HtmlStyleNames.STRIPED_ROWS.toString() + "                                    { background-color: " + UxColors.MID_GREY.getHtmlColorCode() + "; text-align: left; }" + LF +
                "       tr.testcasesummaryheadline                                     { background-color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; color: " + UxColors.WHITE.getHtmlColorCode() + "; }" + LF +
                "       table." + TestRunDetailsPage.HtmlStyleNames.STRIPED_ROWS.toString() + " tr:nth-child(even)                 { background-color: " + UxColors.LIGHT_GREY.getHtmlColorCode() + "; }" + LF +
                "       .noerrorsexclamtaion    { color: black; font-weight: bold; }" + LF +
                "       h3#settingsheading      { color: " + UxColors.DARK_GREY.getHtmlColorCode() + "; }" + LF +
                "       table.settingsTable     { color: " + UxColors.DARK_GREY.getHtmlColorCode() + "; font-size: 80%; }" + LF +
                "      a." + TestRunDetailsPage.HtmlStyleNames.LICENSE_LINK.toString().toLowerCase() + "      { color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; text-decoration: none; }" + LF +
                "      a." + TestRunDetailsPage.HtmlStyleNames.LICENSE_LINK.toString().toLowerCase() + ":visited      { color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; text-decoration: none; }" + LF +
                "      a." + TestRunDetailsPage.HtmlStyleNames.LICENSE_LINK.toString().toLowerCase() + ":hover      { color: " + UxColors.DARK_BLUE.getHtmlColorCode() + "; text-decoration: underline; }" + LF +
                ".button {\n" +
                "  display: inline-block;\n" +
                "  height: 50px;\n" +
                "  line-height: 50px;\n" +
                "  padding-right: 30px;\n" +
                "  padding-left: 70px;\n" +
                "  position: relative;\n" +
                "  background-color:rgb(41,127,184);\n" +
                "  color:rgb(255,255,255);\n" +
                "  text-decoration: none;\n" +
                "  text-transform: uppercase;\n" +
                "  letter-spacing: 1px;\n" +
                "  margin-bottom: 15px;\n" +
                "  \n" +
                "  \n" +
                "  border-radius: 5px;\n" +
                "  -moz-border-radius: 5px;\n" +
                "  -webkit-border-radius: 5px;\n" +
                "  text-shadow:0px 1px 0px rgba(0,0,0,0.5);\n" +
                "-ms-filter:\"progid:DXImageTransform.Microsoft.dropshadow(OffX=0,OffY=1,Color=#ff123852,Positive=true)\";zoom:1;\n" +
                "filter:progid:DXImageTransform.Microsoft.dropshadow(OffX=0,OffY=1,Color=#ff123852,Positive=true);\n" +
                "\n" +
                "  -moz-box-shadow:0px 2px 2px rgba(0,0,0,0.2);\n" +
                "  -webkit-box-shadow:0px 2px 2px rgba(0,0,0,0.2);\n" +
                "  box-shadow:0px 2px 2px rgba(0,0,0,0.2);\n" +
                "  -ms-filter:\"progid:DXImageTransform.Microsoft.dropshadow(OffX=0,OffY=2,Color=#33000000,Positive=true)\";\n" +
                "filter:progid:DXImageTransform.Microsoft.dropshadow(OffX=0,OffY=2,Color=#33000000,Positive=true);\n" +
                "}\n" +
                "\n" +
                ".button span {\n" +
                "  position: absolute;\n" +
                "  left: 0;\n" +
                "  width: 50px;\n" +
                "  background-color:rgba(0,0,0,0.5);\n" +
                "  \n" +
                "  -webkit-border-top-left-radius: 5px;\n" +
                "-webkit-border-bottom-left-radius: 5px;\n" +
                "-moz-border-radius-topleft: 5px;\n" +
                "-moz-border-radius-bottomleft: 5px;\n" +
                "border-top-left-radius: 5px;\n" +
                "border-bottom-left-radius: 5px;\n" +
                "border-right: 1px solid  rgba(0,0,0,0.15);\n" +
                "}\n" +
                "\n" +
                ".button:hover span, .button.active span {\n" +
                "  background-color:rgb(0,102,26);\n" +
                "  border-right: 1px solid  rgba(0,0,0,0.3);\n" +
                "}\n" +
                "\n" +
                ".button:active {\n" +
                "  margin-top: 2px;\n" +
                "  margin-bottom: 13px;\n" +
                "\n" +
                "  -moz-box-shadow:0px 1px 0px rgba(255,255,255,0.5);\n" +
                "-webkit-box-shadow:0px 1px 0px rgba(255,255,255,0.5);\n" +
                "box-shadow:0px 1px 0px rgba(255,255,255,0.5);\n" +
                "-ms-filter:\"progid:DXImageTransform.Microsoft.dropshadow(OffX=0,OffY=1,Color=#ccffffff,Positive=true)\";\n" +
                "filter:progid:DXImageTransform.Microsoft.dropshadow(OffX=0,OffY=1,Color=#ccffffff,Positive=true);\n" +
                "}\n" +
                "\n" +
                "\n" +
                ".button.purple {\n" +
                "  background: #8e44ad;\n" +
                "}" +
                ".button.grey {\n" +
                "  background: lightgrey;\n" +
                "}";
        return styles;
    }

    /**
     * Produces a document footer for the summary reportTestRun.
     * @return HTML section for footer
     */
    public static String htmlElementCopyright(){
        //noinspection deprecation
        return "<br><br>" +
                "          <table width=\"100%\">" + System.lineSeparator() +
                "            <tr>" + System.lineSeparator() +
                "              <td class=\"bottomlogo\" width=\"100%\"><a href=\"http://www.claremont.se\"><img alt=\"Claremont logo\" class=\"bottomlogo\" src=\"http://46.101.193.212/TAF/images/claremontlogo.gif\"></a></td>" + System.lineSeparator() +
                "            </tr><tr>" + System.lineSeparator() +
                "              <td width=\"100%\" class=\"copyright\"><br>TAF is licensed under the <a href=\"https://www.apache.org/licenses/LICENSE-2.0\" target=\"_blank\" class=\"" + TestRunDetailsPage.HtmlStyleNames.LICENSE_LINK.toString().toLowerCase() + "\">Apache 2.0</a> license. &copy; Claremont " + new SimpleDateFormat("yyyy").format(new Date()) + ".</td>" + System.lineSeparator() +
                "            </tr>" + System.lineSeparator() +
                "          </table>" + System.lineSeparator();
    }

}
