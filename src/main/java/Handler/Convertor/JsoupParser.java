package Handler.Convertor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupParser {
    public static void parsURL(){

        Document doc = null;
        try {
            doc = Jsoup.connect("https://api.coinlore.net/api/ticker/?id=90").ignoreContentType(true).
                    get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = String.valueOf(doc.body());
        System.out.println(title);
    }
}
