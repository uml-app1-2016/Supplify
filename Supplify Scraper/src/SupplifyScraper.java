import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    	int[] types = {6};
    	Supplify(url, types);
    }
    
	 // Type 1: Get Nootriment List of Supplements
     // Type 2: Get Nootriment Side Effects 
     // Type 3: Get Nootriment Review Stats 
     // Type 4: Get the pros of the drug
     // Type 5: Get the cons of the drug
     // Type 6: Get the dosage of the drug
    
    public static void Supplify(String url, int[] type_list) throws IOException {
    	Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
    	for(int i : type_list) {
    		runType(i, doc);
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
    
    public static ArrayList<String[]> getStats(Document doc) {
    	String bars_class = ".review-wu-bars";
    	String effects_class = ".rev-option";
    	ArrayList<String[]> output = new ArrayList<String[]>();
    	Elements stats = doc.select(bars_class + " " + effects_class);
    	for(Element e : stats) {
    		String[] s = e.text().trim().split("\\s+");
    		output.add(s);
    	}
    	return output;
    }
    
    public static String getProsAndCons(Document doc, int type) {
    	String effect_class = "";
    	if(type == 4) {
    		effect_class = ".pros";
    	} else {
    		effect_class = ".cons";
    	}
    	
    	Elements li_elements = doc.select(effect_class + " li");
    	String s = "";
    	for(Element e : li_elements) {
    		s = s.concat(e.text()) + "\n";
    	}
    	return s;
    }
    
    public static ArrayList<String> getDosage(Document doc) {
    	ArrayList<String> dosage = new ArrayList<String>();
        String dosage_text = " Dosage:";
    	Elements dosageInfo = doc.select("h2:contains(" + dosage_text + ")");
        for(Element e : dosageInfo) {
        	while(e.nextElementSibling().tagName().equalsIgnoreCase("p")) {
        		dosage.add(e.nextElementSibling().text());
        		e = e.nextElementSibling();
        	}        	
        }
        return dosage;
    }
    
    public static void runType(int type, Document doc) {
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
        
        if(type == 3) {
        	ArrayList<String[]> stats = getStats(doc);
        	for(String[] s : stats) {
        		// We get back the name of the effect at s[0]
        		// and the value at s[1].
        		System.out.println(s[0] + " " + s[1]);
        	}
        	
        }
        if(type == 4) {
        	// Get back the pros of the drug
        	String pros = getProsAndCons(doc, type);
        	System.out.println(pros);
        }
        if(type == 5) {
        	// get back the cons of the drug
        	String pros = getProsAndCons(doc, type);
        	System.out.println(pros);
        }
        
        if(type == 6) {
        	ArrayList<String> dosage = getDosage(doc);
        	for(String d : dosage) {
            	// Full Paragraphs for dosages from nootriment.com
            	System.out.println(d);
            }
        } 
    }    
}
