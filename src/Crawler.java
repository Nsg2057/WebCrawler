import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Crawler {
    static ExecutorService executorService = Executors.newCachedThreadPool();
    static ConcurrentHashMap<String, MetaData> m = new ConcurrentHashMap<String, MetaData>();
    static final String[] SOCIAL_MEDIA = new String[]{"facebook", "twitter", "instagram", "youtube", "linkedin"};
    static String inputURL = "www.nyu.edu";

    public static void main(String[] args) {
        createThread(inputURL);
        executorService.shutdown();
//        m.entrySet().stream().forEachOrdered(s -> {
//            System.out.println(s.getKey() + "  ---  " + s.getValue());
//        });
    }

    static MetaData createThread(String URL) {
        MetaData metaData = new MetaData();
        System.out.println(URL);
        URL = URL.startsWith("/") ? inputURL + URL : URL;
        System.out.println(URL);
        try {
            DATAGrabber dg = new DATAGrabber(URL);
            Future<MetaData> future = executorService.submit(dg);
//            while(!future2.isDone()) {
//                System.out.println("Extracting...");
//            }
            metaData = future.get();
            try {
//                System.out.println(metaData);
                String link = metaData.URL.replace(inputURL, "");
                m.put(link, metaData);
//                m.entrySet().stream().forEachOrdered(s -> {
//                    System.out.println(s.getKey() +"  ---  "+s.getValue());
//                });
            } catch (Exception ex) {
                // This exception was thrown during processing
                ex.printStackTrace();
            }
            System.out.println("Before List");
            System.out.println(Arrays.toString(metaData.getUrlList().toArray()));
            if (metaData.getUrlList().size() != 0) {
                List<String> filteredList = metaData.getUrlList().stream().filter(t -> !m.containsKey(t)).filter(s -> s.contains(inputURL) || s.startsWith("/")).collect(Collectors.toList());
                System.out.println("after List");
                System.out.println(Arrays.toString(filteredList.toArray()));
                for (String link :
                        filteredList) {
                    createThread(link);
                }
            }
        } catch (ExecutionException e) {
            System.out.println("Cause of ExecutionException = " + e.getCause());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metaData;
    }

    public static Predicate<String> hasLengthOf10 = new Predicate<String>() {
        @Override
        public boolean test(String t) {
            return !m.containsKey(t);
        }
    };


}
