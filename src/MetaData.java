import java.util.List;

public class MetaData {
    String title;
    String description;
    String URL;
    Integer size;
    Integer numberOfUrl;
    List<String> urlList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNumberOfUrl() {
        return numberOfUrl;
    }

    public void setNumberOfUrl(Integer numberOfUrl) {
        this.numberOfUrl = numberOfUrl;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    @Override
    public String toString() {
        return "MetaData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", URL='" + URL + '\'' +
                ", size=" + size +
                ", numberOfUrl=" + numberOfUrl +
                ", urlList=" + urlList +
                '}';
    }
}
