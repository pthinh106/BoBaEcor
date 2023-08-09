package TMDTBoBa.BoBaEcor.Controller.Home;

import TMDTBoBa.BoBaEcor.Models.BlogCustom.RSSItem;
import com.rometools.rome.feed.synd.SyndEntry;
import TMDTBoBa.BoBaEcor.Models.BlogCustom.Kenh14RSSReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class Kenh14RSSController {
    @Autowired
    private Kenh14RSSReader rssReader;

    @GetMapping("/doc-bao")
    public String index(Model model) {
        String rssFeedUrl = "https://vnexpress.net/rss/giai-tri.rss";

        List<RSSItem> items = rssReader.readRSSFeed(rssFeedUrl);
        if (items != null) {
            model.addAttribute("items", items);
        } else {
            model.addAttribute("error", "Failed to read RSS feed.");
        }
        return "home/rss";
    }
}
