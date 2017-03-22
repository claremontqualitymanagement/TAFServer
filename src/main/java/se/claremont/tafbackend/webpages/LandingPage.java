package se.claremont.tafbackend.webpages;

import se.claremont.autotest.common.reporting.UxColors;
import se.claremont.tafbackend.server.Settings;
import se.claremont.tafbackend.storage.TestCaseCacheList;
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
        sb.append(CommonSections.headSection(scripts(), extraStyles(), "")).append(System.lineSeparator());
        sb.append("   </head>").append(System.lineSeparator());
        sb.append("   <body>").append(System.lineSeparator());
        sb.append("    <table id=\"").append(TestRunDetailsPage.HtmlStyleNames.CONTENT.toString()).append("\">").append(LF).append(LF);
        sb.append("      <tr>").append(LF);
        sb.append("        <td>").append(LF).append(LF);
        sb.append(CommonSections.pageHeader()).append(System.lineSeparator());
        sb.append("      <h1>TAF Results HttpServer</h1>").append(System.lineSeparator());
        sb.append("      <table class=\"statistics\">").append(System.lineSeparator());
        sb.append("         <tr><td>Number of test runs registered:</td><td>").append(TestRunList.size()).append("</td></tr>").append(System.lineSeparator());
        sb.append("         <tr><td>Number of test cases treated since last restart:</td><td>").append(TestCaseCacheList.size()).append("</td></tr>").append(System.lineSeparator());
        sb.append("      </table>").append(System.lineSeparator());
        sb.append("      <p>").append(System.lineSeparator());
        if(TestRunList.size() > 0){
            sb.append("      <a href=\"taf/").append(Settings.currentApiVersion).append("/testrun/").append(TestRunList.size()-1).append("\" class=\"button purple\"><span class=\"checkmark centered\">✓</span>Latest test run</a>").append(System.lineSeparator());
        } else {
            sb.append("      <a href=\"taf/").append(Settings.currentApiVersion).append("/testrun/").append(TestRunList.size()-1).append("\" class=\"button grey\"><span class=\"unavailable centered\">-</span>Latest test run</a> - No test run registered yet").append(System.lineSeparator());
        }
        sb.append("      </p>").append(System.lineSeparator());
        sb.append("      <p>").append(System.lineSeparator());
        sb.append("         <a href=\"taf/").append(Settings.currentApiVersion).append("/testruns\" class=\"button purple\"><span class=\"checkmark centered\">✓</span>Test run list</a>").append(System.lineSeparator());
        sb.append("      </p>").append(System.lineSeparator());
        sb.append("        </td>").append(LF);
        sb.append("      </tr>").append(LF).append(LF);
        sb.append("      <tr>").append(LF);
        sb.append("        <td class=\"centered\">").append(LF);
        sb.append(     CommonSections.pageFooter());
        sb.append("        </td>").append(LF);
        sb.append("      </tr>").append(LF).append(LF);
        sb.append("    </table>").append(LF).append(LF);
        sb.append("</html>").append(System.lineSeparator());
        return sb.toString();
    }


    private static String LF = System.lineSeparator();


    private static String scripts(){
        return "";
    }

    private static String extraStyles(){
        StringBuilder sb = new StringBuilder();
        sb.append("table.statistics {}").append(System.lineSeparator());
        sb.append(".button { display: inline-block; height: 50px; line-height: 50px; padding-right: 30px; padding-left: 70px; position: relative; background-color:rgb(41,127,184); color:rgb(255,255,255); ");
        sb.append("text-decoration: none; text-transform: uppercase; letter-spacing: 1px; margin-bottom: 15px; border-radius: 5px; -moz-border-radius: 5px; -webkit-border-radius: 5px; text-shadow:0px 1px 0px rgba(0,0,0,0.5); ");
        sb.append("-ms-filter:\"progid:DXImageTransform.Microsoft.dropshadow(OffX=0,OffY=1,Color=#ff123852,Positive=true)\";zoom:1; filter:progid:DXImageTransform.Microsoft.dropshadow(OffX=0,OffY=1,Color=#ff123852,Positive=true); ");
        sb.append("-moz-box-shadow:0px 2px 2px rgba(0,0,0,0.2);\n" +
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
                "}").append(System.lineSeparator());
                sb.append(".button.purple { background: #8e44ad; }");
                sb.append(".button.grey { background: ").append(UxColors.MID_GREY.getHtmlColorCode()).append("; }").append(System.lineSeparator());
        return sb.toString();
    }

}
