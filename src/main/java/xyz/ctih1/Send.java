package xyz.ctih1;


import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;

public class Send implements Runnable{
    static final String serverAddress = LaunchTask.SERVER_IP;

    public void run() {
        try {
            if (!LaunchTask.TESTING_ENV) {
                URL url = new URL(serverAddress);
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "multipart/form-data; boundary=");
                con.setDoInput(true);
                con.setConnectTimeout(10 * 1000); // Sets what the application determines as "too long of time". the *1000 converts the first number (default 10s) into milliseconds.
                con.setDoOutput(true);
                OutputStream out = con.getOutputStream();
                DataOutputStream image = new DataOutputStream(out);
                byte[] fileContents = Files.readAllBytes(Objects.requireNonNull(Screenshot.getPath()));
                image.write(fileContents, 0, fileContents.length);
                con.getResponseCode();
                con.disconnect();
            }
            else {
                System.out.println("LaunchTask.TESTING_ENV is set to true. Ignoring 'send'");
            }
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}