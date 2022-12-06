import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.concurrent.Callable;

public class HTMLDownloader {
private String URL;
    public HTMLDownloader(String URL) {
        this.URL=URL;
    }

    public String getHTML() throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL myURL = new URL(URL.toLowerCase().startsWith("http") ? URL :  "https://" + URL);
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            String inputLine = "No DATA";
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    myURLConnection.getInputStream()));
            while ((inputLine = in.readLine()) != null)
//                System.out.println(inputLine);
                stringBuilder.append(inputLine);
//                System.out.println("hello");
            in.close();
        }
        catch (MalformedURLException e) {
            System.out.println(e.getMessage() + " new URL() failed " + URL  );
        }
        catch (IOException e) {
            System.out.println(e.getMessage() + "  " + URL  );
        }

        return stringBuilder.toString();
    }
}
