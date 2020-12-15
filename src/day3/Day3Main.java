package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day3Main {
    private static final boolean isTestInput = false;
    
    private static String getFilePath() {
        final StringBuilder sb = new StringBuilder("src/day3/");
        if(isTestInput) {
            sb.append("test");
        }
        sb.append("input.txt");
        return sb.toString();
    }
    
    // Not zero-indexed.
    private static final int getMapWidth() {
        return isTestInput ? 11 : 31;
    }

    // Not zero-indexed.
    private static final int getMapHeight() {
        return isTestInput ? 11 : 323;
    }
    
    public static void run() throws FileNotFoundException {
        final File inputFile = new File(getFilePath());
        final Scanner scan = new Scanner(inputFile);
        
        final boolean[][] map = new boolean[getMapHeight()][getMapWidth()]; // map[row][col]
        
        int row = 0;
        while(scan.hasNextLine()) {
            final String line = scan.nextLine();
            for(int col = 0; col < line.length(); ++col) {
                map[row][col] = line.charAt(col) == '#';
            }
            ++row;
        }
        
        scan.close();
        
        System.out.println(part2(map));
    }
    
    private static int part1(final boolean[][] map) {
        return treeCount(map, 3, 1);
    }
    
    private static int part2(final boolean[][] map) {
        int result = 1;
        result *= treeCount(map, 1, 1);
        result *= treeCount(map, 3, 1);
        result *= treeCount(map, 5, 1);
        result *= treeCount(map, 7, 1);
        result *= treeCount(map, 1, 2);
        return result;
    }
    
    private static int treeCount(final boolean[][] map, final int slopeX, final int slopeY) {
        int curRow = slopeY;
        int curCol = slopeX;
        int count = 0;
        
        while(curRow < map.length) {
            if(map[curRow][curCol]) {
                ++count;
            }
            curRow += slopeY;
            curCol += slopeX;
            curCol %= map[0].length;
        }
        
        return count;
    }
}
