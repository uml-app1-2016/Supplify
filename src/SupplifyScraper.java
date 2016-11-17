import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;


public class SupplifyScraper {
    public static void main(String[] args) throws IOException {
    	String url = "http://nootriment.com/aniracetam/";
    	System.out.printf("Fetching %s...\n", url);
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        ArrayList<String> side_effects = getNootrimentSideEffects(doc);
        for(String effect : side_effects) {
        	System.out.println(effect);
        }
    }
    // Retrieves the Nootriment Site Side Effects if they exist.
    // Can check if the side effects section was found by checking
    // to see if the returned array size is 0.
    public static ArrayList<String> getNootrimentSideEffects(Document doc) {
    	ArrayList<String> sideEffectList = new ArrayList<String>();;
        String sideEffectsText = "Potential Side Effects:";
    	Elements sideEffects = doc.select("h2:contains(" + sideEffectsText + ")");
        for(Element e : sideEffects) {
        	while(e.nextElementSibling().tagName().equalsIgnoreCase("p")) {
        		sideEffectList.add(e.nextElementSibling().text());
        		e = e.nextElementSibling();
        	}        	
        }
        return sideEffectList;
    }
}
