package day25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day25Main {
    
    private static int DIVISOR = 20201227;
    
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day25/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        String line = scan.nextLine();
        final int cardPublicKey = Integer.parseInt(line);
        line = scan.nextLine();
        final int doorPublicKey = Integer.parseInt(line);
        
        scan.close();
        
        System.out.println(part1(cardPublicKey, doorPublicKey));
    }
    
    private static long part1(final int cardPublicKey, final int doorPublicKey) {
        final int cardLoopSize = getLoopSize(cardPublicKey, 7);
        final long encryptionKey = transform(cardLoopSize, doorPublicKey);
        return encryptionKey;
    }
    
    private static int getLoopSize(final int publicKey, final int subjectNumber) {
        int value = 1;
        for (int loop = 1; loop < DIVISOR; ++loop) {
            value *= subjectNumber;
            value %= DIVISOR;
            if (value == publicKey) {
                return loop;
            }
        }
        // no valid loop size
        return -1;
    }
    
    private static long transform(final int loopSize, final int subjectNumber) {
        long value = 1;
        for (int loop = 1; loop <= loopSize; ++loop) {
            value *= subjectNumber;
            value %= DIVISOR;
        }
        return value;
    }
}
