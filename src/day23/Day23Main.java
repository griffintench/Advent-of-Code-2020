package day23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day23Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day23/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final String line = scan.nextLine();
        final List<Integer> cups = new ArrayList<>();
        for (int i = 0; i < line.length(); ++i) {
            cups.add(line.charAt(i) - '0');
        }
        
        scan.close();
        
        System.out.println(part2(cups));
    }
    
    private static String part1(final List<Integer> cups) {
        int currentCupIndex = 0;
        
        for (int i = 0; i < 100; ++i) {
            final int currentCupLabel = cups.get(currentCupIndex);
            move(cups, currentCupIndex);
            currentCupIndex = (cups.indexOf(currentCupLabel) + 1) % cups.size();
        }
        
        final StringBuilder sb = new StringBuilder();
        boolean found1 = false;
        for (int i = 0; i < cups.size(); ++i) {
            if (found1) {
                sb.append(cups.get(i));
            }
            if (cups.get(i) == 1) {
                found1 = true;
            }
        }
        found1 = false;
        for (int i = 0; !found1 && i < cups.size(); ++i) {
            if (cups.get(i) == 1) {
                found1 = true;
            } else {
                sb.append(cups.get(i));
            }
        }
        
        return sb.toString();
    }
    
    private static void move(final List<Integer> cups, final int currentCupIndex) {
        final int numberOfCups = cups.size();
        
        final int currentLabel = cups.get(currentCupIndex);
        
        final int[] removedCups = new int[3];
        int removeIndex = currentCupIndex + 1;
        for (int i = 0; i < 3; ++i) {
            if (removeIndex >= cups.size()) {
                removeIndex = 0;
            }
            removedCups[i] = cups.remove(removeIndex);
        }
        
        int destLabel = currentLabel - 1;
        if (destLabel == 0) {
            destLabel = numberOfCups;
        }
        while (!cups.contains(destLabel)) {
            --destLabel;
            if (destLabel == 0) {
                destLabel = numberOfCups;
            }
        }
        
        final int destIndex = cups.indexOf(destLabel);
        for (int i = 1; i <= 3; ++i) {
            cups.add((destIndex + i) % (cups.size() + 1), removedCups[i - 1]);
        }
    }
    
    private static long part2(final List<Integer> cups) {
        
        final int originalCups = cups.size();
        
        Cup currentCup = new Cup(cups.get(0));
        Cup lastCup = currentCup;
        
        for (int i = 1; i < originalCups; ++i) {
            final Cup nextCup = new Cup(cups.get(i));
            lastCup.setNext(nextCup);
            lastCup = nextCup;
        }
        
        final Cup[] firstCups = new Cup[originalCups + 1];
        Cup cupToPlace = currentCup;
        while (cupToPlace != null) {
            firstCups[cupToPlace.getLabel()] = cupToPlace;
            cupToPlace = cupToPlace.getNext();
        }
        
        for (int i = 1; i < firstCups.length - 1; ++i) {
            firstCups[i + 1].setNextLowest(firstCups[i]);
        }
        
        final Cup firstSequentialCup = new Cup(originalCups + 1);
        lastCup.setNext(firstSequentialCup);
        lastCup = firstSequentialCup;
        lastCup.setNextLowest(firstCups[originalCups]);
        
        for (int i = originalCups + 2; i <= 1_000_000; ++i) {
            final Cup nextCup = new Cup(i);
            lastCup.setNext(nextCup);
            nextCup.setNextLowest(lastCup);
            lastCup = nextCup;
        }
        
        lastCup.setNext(currentCup);
        firstCups[1].setNextLowest(lastCup);
        
        for (int i = 0; i < 10_000_000; ++i) {
            final Cup oneAheadOfCurrent = currentCup.getNext();
            Cup threeAheadOfCurrent = oneAheadOfCurrent;
            for (int j = 0; j < 2; ++j) {
                threeAheadOfCurrent = threeAheadOfCurrent.getNext();
            }
            
            Cup destCup = currentCup.getNextLowest();
            while (destCup == oneAheadOfCurrent || destCup == oneAheadOfCurrent.getNext() || destCup == threeAheadOfCurrent) {
                destCup = destCup.getNextLowest();
            }
            final Cup oneAheadOfDest = destCup.getNext();
            
            currentCup.setNext(threeAheadOfCurrent.getNext());
            destCup.setNext(oneAheadOfCurrent);
            threeAheadOfCurrent.setNext(oneAheadOfDest);
            
            currentCup = currentCup.getNext();
        }
        
        final Cup labelOne = currentCup.getCupWithLabel(1);
        final Cup oneAhead = labelOne.getNext();
        final Cup twoAhead = oneAhead.getNext();
        
        return (long)oneAhead.getLabel() * (long)twoAhead.getLabel();
    }
}
