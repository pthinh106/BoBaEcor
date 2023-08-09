package TMDTBoBa.BoBaEcor.Models.BlogCustom;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class Kenh14RSSReader {
    public List<RSSItem> readRSSFeed(String rssFeedUrl) {
        List<RSSItem> items = new ArrayList<>();

        try {
            URL feedUrl = new URL(rssFeedUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndEntry entry;

            // Read the RSS feed
            for (Object o : input.build(new XmlReader(feedUrl)).getEntries()) {
                entry = (SyndEntry) o;
                String title = entry.getTitle();
                String link = entry.getLink();
                String publishedDate = entry.getPublishedDate().toString();
                String descriptionHtml = entry.getDescription().getValue();

                Document doc = Jsoup.parse(descriptionHtml);
                Elements imgElements = doc.select("img");
                String imageUrl = null;
                if (!imgElements.isEmpty()) {
                    Element imgElement = imgElements.get(0);
                    imageUrl = imgElement.attr("src");
                }

                RSSItem item = new RSSItem(title, link, publishedDate, imageUrl);
                items.add(item);
            }
        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }

        return items;
    }

}
