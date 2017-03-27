package se.claremont.tafbackend.webpages;

/**
 * Support methods for web page creation
 *
 * Created by jordam on 2017-03-19.
 */
public class SupportMethods {

    public static String enumMemberNameToLower(String enumMemberName){
        StringBuilder sb = new StringBuilder();
        String[] words = enumMemberName.split("_");
        if(words.length > 0){
            sb.append(words[0].toLowerCase());
        }
        for(int i = 1; i < words.length ; i++){
            if(words[i].length() > 0){
                sb.append(words[i].substring(0, 1).toUpperCase());
                if(words[i].length() > 1){
                    sb.append(words[i].substring(1).toLowerCase());
                }
            }
        }
        return sb.toString();
    }

}
