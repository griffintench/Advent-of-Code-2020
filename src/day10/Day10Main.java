package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Day10Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day10/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final TreeSet<Integer> joltageRatings = new TreeSet<>();
       
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            final int joltageRating = Integer.parseInt(line);
            joltageRatings.add(joltageRating);
        }
        final int deviceJoltageRating = joltageRatings.last() + 3;
        joltageRatings.add(deviceJoltageRating);
        
        scan.close();
        
        System.out.println(part2(joltageRatings));
    }
    
    private static int part1(final TreeSet<Integer> joltageRatings) {
        int difference1Count = 0;
        int difference3Count = 0;
        
        int previousRating = 0;
        for (final int joltageRating : joltageRatings) {
            if (joltageRating - previousRating == 1) {
                ++difference1Count;
            } else if (joltageRating - previousRating == 3) {
                ++difference3Count;
            }
            previousRating = joltageRating;
        }
        
        return difference1Count * difference3Count;
    }
    
    private static long part2(final TreeSet<Integer> joltageRatings) {
        joltageRatings.add(0);
        final TreeMap<Integer, Long> arrangementMap = new TreeMap<>(); // <Joltage, Arrangements>
        Iterator<Integer> iter = joltageRatings.descendingIterator();
        while(iter.hasNext()) {
            final int joltageRating = iter.next();
            if (joltageRating == joltageRatings.last()) {
                arrangementMap.put(joltageRating, 1L);
            } else {
                long arrangements = 0;
                if (joltageRatings.contains(joltageRating + 1)) {
                    arrangements += arrangementMap.get(joltageRating + 1);
                }
                if (joltageRatings.contains(joltageRating + 2)) {
                    arrangements += arrangementMap.get(joltageRating + 2);
                }
                if (joltageRatings.contains(joltageRating + 3)) {
                    arrangements += arrangementMap.get(joltageRating + 3);
                }
                arrangementMap.put(joltageRating, arrangements);
            }
        }
        
        return arrangementMap.get(0);
    }
    
}
