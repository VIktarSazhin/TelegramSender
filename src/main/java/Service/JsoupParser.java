package Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupParser {
    public static String parsURL(){

        Document doc = null;
        try {
            doc = Jsoup.connect("http://34.135.157.38:8085/tomcattest/send-json.jsp").ignoreContentType(true).
                    get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = String.valueOf(doc.text());
//        String title = String.valueOf(doc.body()).replace("<body>","").replace("</body>","").replace("<p>","")
//                .replace("</p>","");
        return title;
    }
}
