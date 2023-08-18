package TMDTBoBa.BoBaEcor.Models.BlogCustom;

import lombok.*;

@Data
@Getter
@Setter
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
}
