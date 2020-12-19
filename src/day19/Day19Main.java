package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day19Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day19/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final Map<Integer, Rule> ruleMap = new HashMap<>();
        String line = scan.nextLine();
        while (!line.isEmpty()) {
            Rule.createRule(line, ruleMap);
            line = scan.nextLine();
        }
        
        final List<String> messages = new ArrayList<>();
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            messages.add(line);
        }
        
        scan.close();
        
        System.out.println(part2(ruleMap, messages));
    }
    
    private static int part1(final Map<Integer, Rule> ruleMap, final List<String> messages) {
        int matchCount = 0;
        final Rule rule0 = ruleMap.get(0);
        
        for (final String message : messages) {
            final List<Integer> matches = rule0.checkMessage(message, ruleMap);
            boolean validMatch = false;
            for (final int match : matches) {
                if (match == message.length()) {
                    validMatch = true;
                }
            }
            if (validMatch) {
                ++matchCount;
            }
        }
        
        return matchCount;
    }
    
    private static int part2(final Map<Integer, Rule> ruleMap, final List<String> messages) {
        final String rule8String = "8: 42 | 42 42 | 42 42 42 | 42 42 42 42 | 42 42 42 42 42 | 42 42 42 42 42 42";
        final String rule11String = "11: 42 31 | 42 42 31 31 | 42 42 42 31 31 31 | 42 42 42 42 31 31 31 31 | 42 42 42 42 42 31 31 31 31 31";
        
        Rule.createRule(rule8String, ruleMap);
        Rule.createRule(rule11String, ruleMap);
        
        return part1(ruleMap, messages);
    }
}
