package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day7Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day7/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final Map<String, Map<String, Integer>> bagRules = new HashMap<>();
       
        while(scan.hasNextLine()) {
            final String line = scan.nextLine();
            addBagRule(bagRules, line);
        }
        
        scan.close();
        
        System.out.println(part2(bagRules));
    }
    
    private static void addBagRule(final Map<String, Map<String, Integer>> bagRules, final String newRule) {
        final String[] splitLine = newRule.split(" bags contain ");
        final String colour = splitLine[0];
        final Map<String, Integer> contents = new HashMap<>();
        if (!splitLine[1].equals("no other bags.")) {
            final String[] splitContents = splitLine[1].split(", ");
            for (int i = 0; i < splitContents.length; ++i) {
                final int quantity = splitContents[i].charAt(0) - '0';
                final String contentsColour = splitContents[i].substring(2).split(" bag")[0];
                contents.put(contentsColour, quantity);
            }
        }
        bagRules.put(colour, contents);
    }
    
    private static int part1(final Map<String, Map<String, Integer>> bagRules) {
        final Map<String, Boolean> containingShinyGold = new HashMap<>();
        final int colours = bagRules.size();
        while (containingShinyGold.size() != colours) {
            for (final String colour : bagRules.keySet()) {
                if (!containingShinyGold.containsKey(colour)) {
                    boolean containsShinyGold = false;
                    boolean knowable = true;
                    for (final String contentColour : bagRules.get(colour).keySet()) {
                        if (contentColour.equals("shiny gold")) {
                            containsShinyGold = true;
                        } else if (containingShinyGold.containsKey(contentColour)) {
                            if (containingShinyGold.get(contentColour)) {
                                containsShinyGold = true;
                            }
                        } else {
                            knowable = false;
                        }
                    }
                    if (containsShinyGold) {
                        containingShinyGold.put(colour, true);
                    } else if (knowable) {
                        containingShinyGold.put(colour, false);
                    }
                }
            }
        }
        
        int count = 0;
        for (final String colour : containingShinyGold.keySet()) {
            if (containingShinyGold.get(colour)) {
                ++count;
            }
        }
        
        return count;
    }
    
    private static int part2(final Map<String, Map<String, Integer>> bagRules) {
        final Map<String, Integer> bagsWithin = new HashMap<>();
        final int colours = bagRules.size();
        while (bagsWithin.size() != colours) {
            for (final String colour : bagRules.keySet()) {
                if (!bagsWithin.containsKey(colour)) {
                    int bags = 0;
                    boolean knowable = true;
                    for (final String contentColour : bagRules.get(colour).keySet()) {
                        if (bagsWithin.containsKey(contentColour)) {
                            bags += (1 + bagsWithin.get(contentColour)) * bagRules.get(colour).get(contentColour);
                        } else {
                            knowable = false;
                        }
                    }
                    if (knowable) {
                        bagsWithin.put(colour, bags);
                    }
                }
            }
        }
        
        return bagsWithin.get("shiny gold");
    }
}
