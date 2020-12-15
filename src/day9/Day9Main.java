package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day9Main {
    
    private static final boolean isTestInput = false;
    
    private static String getFilePath() {
        return isTestInput ? "src/day9/testinput.txt" : "src/day9/input.txt";
    }
    
    private static int getPreambleSize() {
        return isTestInput ? 5 : 25;
    }
    
    public static void run() throws FileNotFoundException {
        final File inputFile = new File(getFilePath());
        final Scanner scan = new Scanner(inputFile);
        
        final List<Long> numbers = new ArrayList<>();
       
        while(scan.hasNextLine()) {
            final String line = scan.nextLine();
            final long number = Long.parseLong(line);
            numbers.add(number);
        }
        
        scan.close();
        
        System.out.println(part2(numbers));
    }
    
    private static long part1(final List<Long> numbers) {
        final int preambleSize = getPreambleSize();
        for (int i = preambleSize; i < numbers.size(); ++i) {
            boolean found = false;
            final long sum = numbers.get(i);
            for (int j = i - preambleSize; j < i && !found; ++j) {
                final long addend1 = numbers.get(j);
                for (int k = j + 1; k < i && !found; ++k) {
                    if (addend1 + numbers.get(k) == sum) {
                        found = true;
                    }
                }
            }
            if (!found) {
                return sum;
            }
        }
        
        System.out.println("None!");
        return -1;
    }
    
    private static long part2(final List<Long> numbers) {
        final long sum = isTestInput ? 127 : 27911108; // answer from part 1
        for (int i = 0; i < numbers.size(); ++i) {
            long sumSoFar = 0;
            sumSoFar += numbers.get(i);
            int j = i;
            do {
                ++j;
                sumSoFar += numbers.get(j);
                if (sum == sumSoFar) {
                    long smallest = Long.MAX_VALUE;
                    long largest = Long.MIN_VALUE;
                    for (int k = i; k <= j; ++k) {
                        final long current = numbers.get(k);
                        if (current < smallest) {
                            smallest = current;
                        }
                        if (current > largest) {
                            largest = current;
                        }
                    }
                    return smallest + largest;
                }
            } while (sumSoFar < sum && j < numbers.size());
        }
        
        return -1;
    }
}
