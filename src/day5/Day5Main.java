package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Day5Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day5/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<BoardingPass> boardingPasses = new ArrayList<>();
        
        while(scan.hasNextLine()) {
            final String line = scan.nextLine();
            final BoardingPass boardingPass = new BoardingPass(line);
            boardingPasses.add(boardingPass);
        }
        
        scan.close();
        
        System.out.println(part2(boardingPasses));
    }
    
    private static int part1(final List<BoardingPass> boardingPasses) {
        int highestSeatId = 0;
        for (final BoardingPass boardingPass : boardingPasses) {
            final int seatId = boardingPass.getSeatId();
            if (seatId > highestSeatId) {
                highestSeatId = seatId;
            }
        }
        return highestSeatId;
    }
    
    private static int part2(final List<BoardingPass> boardingPasses) {
        final TreeSet<Integer> seatIds = new TreeSet<>();
        for (final BoardingPass boardingPass : boardingPasses) {
            seatIds.add(boardingPass.getSeatId());
        }
        final int smallestSeatId = seatIds.first();
        final int largestSeatId = seatIds.last();
        for (int seatId = smallestSeatId + 1; seatId < largestSeatId; ++seatId) {
            if (!seatIds.contains(seatId)) {
                return seatId;
            }
        }
        System.out.println("No missing seat ID");
        return -1;
    }
}
