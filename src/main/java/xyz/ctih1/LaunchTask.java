package xyz.ctih1;

import java.util.TimerTask;

public class LaunchTask extends TimerTask {
    static String SERVER_IP = "";
    static boolean TESTING_ENV = true;

    @Override
    public void run() {
        if(SERVER_IP.equals("")) {
            throw new IllegalArgumentException("Please configure 'SERVER_IP' in LaunchTask");
        }
        Thread screenshot = new Thread(new Screenshot());
        screenshot.start();
    }
}