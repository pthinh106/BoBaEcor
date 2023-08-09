package TMDTBoBa.BoBaEcor.Models.BlogCustom;

public class RSSItem {
    private String title;
    private String link;
    private String publishedDate;
    private String image;

    public RSSItem(String title, String link, String publishedDate, String image) {
        this.title = title;
        this.link = link;
        this.publishedDate = publishedDate;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getImage() {
        return image;
    }
}
