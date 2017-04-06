package server;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by 23878410v on 06/04/17.
 */
public class Utils {
    public static void initLogger(String filename, FileHandler fh, Logger logger){
        try {
            fh=new FileHandler(filename, false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        Logger l = logger.getLogger("");
        fh.setFormatter(new SimpleFormatter());
        l.addHandler(fh);
        l.setLevel(Level.ALL);
    }

}
