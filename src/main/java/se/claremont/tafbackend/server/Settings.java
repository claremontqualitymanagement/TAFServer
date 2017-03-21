package se.claremont.tafbackend.server;

import java.io.File;

/**
 * Created by jordam on 2017-03-19.
 */
public class Settings {
    public static int port = 80;
    public static File storageFile = new File("TafBackend.db");
    public static String currentApiVersion = "v1";
}
