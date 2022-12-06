import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DATAGrabber implements Callable<MetaData> {
    private final String URL;
    String htmlPage;
    private static final Pattern titleRex = Pattern.compile("<title[^>]*>(.*)<\\/title>");
    private static final Pattern descriptionRex = Pattern.compile("<meta\\s+(?=[^>]*name\\s*=\\s*(\"|')description\\1)[^>]*content\\s*=\\s*(\"|')(.*?)\\2[^>]*>");
    private static final Pattern urlRex = Pattern.compile("<a\\s+(?:[^>]*?\\s+)?href=\"([^\"]*)\"");

    public DATAGrabber(String URL) {
        this.URL = URL;
    }


    @Override
    public MetaData call() throws Exception {
        this.htmlPage = new HTMLDownloader(URL).getHTML();
        MetaData md = new MetaData();

        try {
            md.setTitle(getMatch(titleRex).toArray()[0].toString());

        }catch (Exception e){
            System.out.println("No Title Found");
        }
        try{
            md.setSize(htmlPage.getBytes("utf-8").length);
            md.setURL(URL);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            String description = getMatch(descriptionRex).toArray()[0].toString();
            md.setDescription(description.substring(description.indexOf("content=\"") + 9, description.indexOf("\"", description.indexOf("content=\"") + 9)));
        } catch (Exception ex) {
            // This exception was thrown during processing
//            ex.printStackTrace();
            System.out.println("No Description Found");
        }
        md.setUrlList(urlFilter(new ArrayList<>(getMatch(urlRex))));
        md.setNumberOfUrl(md.getUrlList().size());
        return md;
    }

    List<String> urlFilter(List<String> list){

        return list.stream().filter(a -> !a.startsWith("#")).collect(Collectors.toList());

    }


    Set<String> getMatch(Pattern p) {
        Set<String> list = null;
        try {
            Matcher m = p.matcher(htmlPage);
            list = new HashSet<String>();
            Matcher matcher = p.matcher(htmlPage);
            while (matcher.find()) {
                list.add(matcher.group(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
