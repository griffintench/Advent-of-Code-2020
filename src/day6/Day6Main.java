package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day6/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<TravelerGroup> travelerGroups = new ArrayList<>();
        
        final List<String> currentLines = new ArrayList<>();
        while(scan.hasNextLine()) {
            final String line = scan.nextLine();
            if (line.isEmpty()) {
                final TravelerGroup newTravelerGroup = new TravelerGroup(currentLines);
                travelerGroups.add(newTravelerGroup);
                currentLines.clear();
            } else {
                currentLines.add(line);
            }
        }
        final TravelerGroup lastTravelerGroup = new TravelerGroup(currentLines);
        travelerGroups.add(lastTravelerGroup);
        
        scan.close();
        
        System.out.println(part2(travelerGroups));
    }
    
    private static int part1(final List<TravelerGroup> travelerGroups) {
        int countSum = 0;
        
        for (final TravelerGroup travelerGroup : travelerGroups) {
            countSum += travelerGroup.getAnyoneAnsweredYesCount();
        }
        
        return countSum;
    }
    
    private static int part2(final List<TravelerGroup> travelerGroups) {
        int countSum = 0;
        
        for (final TravelerGroup travelerGroup : travelerGroups) {
            countSum += travelerGroup.getEveryoneAnsweredYesCount();
        }
        
        return countSum;
    }
}
