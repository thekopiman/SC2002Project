import camsdisplay.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CAMSapp {

    /**
     * Main function to enter app program
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        DisplayLogin cams = new DisplayLogin(true);

        while (true) {

            cams.startScreen();
        }

    }
}
