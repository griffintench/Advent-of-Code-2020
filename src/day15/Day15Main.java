package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day15Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day15/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<Integer> startingNumbers = new ArrayList<>();
        final String line = scan.nextLine();
        final String[] splitLine = line.split(",");
        for (int i = 0; i < splitLine.length; ++i) {
            startingNumbers.add(Integer.parseInt(splitLine[i]));
        }
        
        scan.close();
        
        System.out.println(part2(startingNumbers));
    }
    
    private static int part1(final List<Integer> startingNumbers) {
        final List<Integer> gameNumbers = new ArrayList<>();
        for (int i = 0; i < 2020; ++i) {
            if (i < startingNumbers.size()) {
                gameNumbers.add(startingNumbers.get(i));
            } else {
                final int lastNumberSpoken = gameNumbers.get(i - 1);
                int j = i - 2;
                boolean found = false;
                while (!found && j >= 0) {
                    if (gameNumbers.get(j) == lastNumberSpoken) {
                        found = true;
                    }
                    --j;
                }
                if (found) {
                    gameNumbers.add(i - j - 2);
                } else {
                    gameNumbers.add(0);
                }
            }
        }
        
        return gameNumbers.get(2019);
    }
    
    private static int part2(final List<Integer> startingNumbers) {
        final Map<Integer, Integer> gameNumberMap = new HashMap<>();
        int mostRecentNumber = startingNumbers.get(0);
        
        for (int i = 1; i < 30000000; ++i) {
            int nextNumber = 0;
            if (i < startingNumbers.size()) {
                nextNumber = startingNumbers.get(i);
            } else {
                nextNumber = gameNumberMap.containsKey(mostRecentNumber) ? i - 1 - gameNumberMap.get(mostRecentNumber) : 0;
            }
            gameNumberMap.put(mostRecentNumber, i - 1);
            mostRecentNumber = nextNumber;
        }
        
        return mostRecentNumber;
    }
}
