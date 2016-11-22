import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class SupplifyScraper {
	
	
	// TODO 
	// Create a scraper to scrape a list of all names
	// of supplements from the sites.
	
	// Eg. scrape nootriment for all of the list items
	// so that we can tell the user very quickly if their 
	// supplement exists and we can complete their search.
	
    public static void main(String[] args) throws IOException  {
    	String url = "http://nootriment.com/aniracetam/";
    	
    	
    	Supplify(url, 2);
    }
    
	 // Type 1: Get Nootriment List of Supplements
     // Type 2: Get Nootriment Side Effects 
        
    // TODO
    // Make this work with a set of types so that we can 
	// run many operations at once without having
	// to continuously download the same page. 
    public static void Supplify(String url, int type) throws IOException {
    	System.out.printf("Fetching %s...\n", url);
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        
        if(type == 1) {
        	ArrayList<String> side_effects = getNootrimentList(doc);
            Set<String> s = new HashSet<String>(side_effects);
        	for(String effect : s) {
            	// All of the supplements available on nootriment.com
            	System.out.println(effect);
            }
        }
        if(type == 2) {
        	ArrayList<String> side_effects = getNootrimentSideEffects(doc);
            for(String effect : side_effects) {
            	// Full Paragraphs for side effects from nootriment.com
            	System.out.println(effect);
            }
        }
    }
    
    // When the user is doing a search we want to make sure that they can
    // only look for the drugs that are on nootriment.com
    // We also want to use this for 
    public static ArrayList<String> getNootrimentList(Document doc) {
    	ArrayList<String> sideEffectList = new ArrayList<String>();
    	Elements sideEffects = doc.select("nav span[class*=ubermenu");
        for(Element e : sideEffects) {
        	sideEffectList.add(e.text());
        } 
        return sideEffectList;
    }
    
    // Retrieves the Nootriment Site Side Effects if they exist.
    // Can check if the side effects section was found by checking
    // to see if the returned array size is 0.
    public static ArrayList<String> getNootrimentSideEffects(Document doc) {
    	ArrayList<String> sideEffectList = new ArrayList<String>();
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
