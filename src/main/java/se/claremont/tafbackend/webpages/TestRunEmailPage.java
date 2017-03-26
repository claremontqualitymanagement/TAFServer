package se.claremont.tafbackend.webpages;

/**
 * Created by jordam on 2017-03-22.
 */
public class TestRunEmailPage {

    public String toHtml(String testRunId){
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>").append(System.lineSeparator());
        sb.append("<html lang=\"en\">").append(System.lineSeparator());
        sb.append("   <head>").append(System.lineSeparator());
        sb.append(CommonSections.headSection(scripts(), "", "")).append(System.lineSeparator());
        sb.append("   </head>").append(System.lineSeparator());
        sb.append("   <body>").append(System.lineSeparator());
        sb.append("    <table id=\"").append(TestRunDetailsPage.HtmlStyleNames.CONTENT.toString()).append("\">").append(LF).append(LF);
        sb.append("      <tr>").append(LF);
        sb.append("        <td>").append(LF).append(LF);
        sb.append(CommonSections.pageHeader()).append(System.lineSeparator());

        sb.append("      <h1>Send email.</h1>").append(System.lineSeparator());
        sb.append("         </td>").append(System.lineSeparator());
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


    private String scripts(){
        StringBuilder sb = new StringBuilder();
        sb.append("<script type\"text/javascript\">").append(System.lineSeparator());
        sb.append("   function sendMail(address, subject, body){").append(System.lineSeparator());
        sb.append("      window.open('mailto:' + address + '?subject=' + subject + '&body=' + body);");
        sb.append("   }").append(System.lineSeparator());
        sb.append("</script>").append(System.lineSeparator());
        return sb.toString();
    }

    private static String LF = System.lineSeparator();
}
