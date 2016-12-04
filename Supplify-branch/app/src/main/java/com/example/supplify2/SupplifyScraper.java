package com.example.supplify2;
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

/**
 * Created by yildiz on 12/3/16.
 */
public class SupplifyScraper {

    // TODO
    // Create a scraper to scrape a list of all names
    // of supplements from the sites.

    // Eg. scrape nootriment for all of the list items
    // so that we can tell the user very quickly if their
    // supplement exists and we can complete their search.

    public static HashMap<String, ArrayList<String>> nootrimentScraper(String name, int[] types) throws IOException, IllegalArgumentException  {
        String url = "http://nootriment.com/" + name;
        return Supplify(url, types);
    }

    private static String getTypeName(int type) {
        switch(type) {
            case 1:
                return "SuppList";
            case 2:
                return "SideEffects";
            case 3:
                return "Stats";
            case 4:
                return "Pros";
            case 5:
                return "Cons";
            case 6:
                return "Dosage";
        }
        return "Out of range...";
    }

    // Type 1: Get Nootriment List of Supplements
    // Type 2: Get Nootriment Side Effects
    // Type 3: Get Nootriment Review Stats
    // Type 4: Get the pros of the drug
    // Type 5: Get the cons of the drug
    // Type 6: Get the dosage of the drug

    private static HashMap<String, ArrayList<String>> Supplify(String url, int[] type_list) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for(int i : type_list) {
            map.put(getTypeName(i), runType(i, doc));
        }
        return map;
    }

    // When the user is doing a search we want to make sure that they can
    // only look for the drugs that are on nootriment.com
    // We also want to use this for
    private static ArrayList<String> getNootrimentList(Document doc) {
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
    private static ArrayList<String> getNootrimentSideEffects(Document doc) {
        ArrayList<String> sideEffectList = new ArrayList<String>();
        String sideEffectsText =  "Side Effects";
        Elements sideEffects = doc.select("h2:contains(" + sideEffectsText + ")");
        for(Element e : sideEffects) {
            while(e.nextElementSibling().tagName().equalsIgnoreCase("p")) {
                sideEffectList.add(e.nextElementSibling().text());
                e = e.nextElementSibling();
            }
        }
        return sideEffectList;
    }

    private static ArrayList<String> getStats(Document doc) {
        String bars_class = ".review-wu-bars";
        String effects_class = ".rev-option";
        ArrayList<String> output = new ArrayList<String>();
        Elements stats = doc.select(bars_class + " " + effects_class);
        for(Element e : stats) {
            output.add(e.text());
        }
        return output;
    }

    private static ArrayList<String> getProsAndCons(Document doc, int type) {
        String effect_class = "";
        if(type == 4) {
            effect_class = ".pros";
        } else {
            effect_class = ".cons";
        }

        Elements li_elements = doc.select(effect_class + " li");
        ArrayList<String> s = new ArrayList<String>();
        for(Element e : li_elements) {
            s.add(e.text());
        }
        return s;
    }

    private static ArrayList<String> getDosage(Document doc) {
        ArrayList<String> dosage = new ArrayList<String>();
        String dosage_text = " Dosage";
        Elements dosageInfo = doc.select("h2:contains(" + dosage_text + ")");
        if(dosageInfo.size() == 0) {
            dosageInfo = doc.select("h2:contains(Dosing)");
        }
        for(Element e : dosageInfo) {
            while(e.nextElementSibling().tagName().equalsIgnoreCase("p")) {
                dosage.add(e.nextElementSibling().text());
                e = e.nextElementSibling();
            }
        }
        return dosage;
    }

    private static ArrayList<String> runType(int type, Document doc) {
        if(type == 1) {
            // All of the supplements available on nootriment.com
            ArrayList<String> side_effects = getNootrimentList(doc);
            Set<String> s = new HashSet<String>(side_effects);
            return (ArrayList<String>) s;
        }
        if(type == 2) {
            ArrayList<String> side_effects = getNootrimentSideEffects(doc);
            return side_effects;
        }

        if(type == 3) {
            ArrayList<String> stats = getStats(doc);
            return stats;
        }
        if(type == 4) {
            // Get back the pros of the drug
            ArrayList<String> pros = getProsAndCons(doc, type);
            return pros;
        }
        if(type == 5) {
            // get back the cons of the drug
            ArrayList<String> cons = getProsAndCons(doc, type);
            return cons;
        }

        if(type == 6) {
            // Full Paragraphs for dosages from nootriment.com
            ArrayList<String> dosage = getDosage(doc);
            return dosage;
        }
        return null;
    }


}
