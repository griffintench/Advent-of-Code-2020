package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day11Main {
    
    private static final boolean isTestInput = false;
    
    private static String getFilePath() {
        return isTestInput ? "src/day11/testinput.txt" : "src/day11/input.txt";
    }
    
    private static int getMapWidth() {
        return isTestInput ? 10 : 97;
    }
    
    private static int getMapHeight() {
        return isTestInput ? 10 : 93;
    }
    
    public static void run() throws FileNotFoundException {
        final File inputFile = new File(getFilePath());
        final Scanner scan = new Scanner(inputFile);
        
        final char[][] map = new char[getMapWidth()][getMapHeight()];
       
        int y = 0;
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            for (int x = 0; x < line.length(); ++x) {
                map[x][y] = line.charAt(x);
            }
            ++y;
        }
        
        scan.close();
        
        System.out.println(part2(map));
    }
    
    private static int part1(final char[][] map) {
        boolean stable = false;
        while (!stable) {
            stable = true;
            final char[][] mapCopy = new char[map.length][map[0].length];
            for (int x = 0; x < map.length; ++x) {
                for (int y = 0; y < map[x].length; ++y) {
                    mapCopy[x][y] = map[x][y];
                }
            }
            for (int x = 0; x < map.length; ++x) {
                for (int y = 0; y < map[x].length; ++y) {
                    final char newState = newState(mapCopy, x, y);
                    if (newState != map[x][y]) {
                        stable = false;
                    }
                    map[x][y] = newState;
                }
            }
        }
        
        int occupied = 0;
        for (int x = 0; x < map.length; ++x) {
            for (int y = 0; y < map[x].length; ++y) {
                if (map[x][y] == '#') {
                    ++occupied;
                }
            }
        }
        return occupied;
    }
    
    private static char newState(final char[][] map, final int x, final int y) {
        if (map[x][y] == '.') {
            return '.';
        }
        if (map[x][y] == 'L') {
            if (x - 1 >= 0 && y - 1 >= 0 && map[x - 1][y - 1] == '#') return 'L';
            if (x - 1 >= 0 && map[x - 1][y] == '#') return 'L';
            if (x - 1 >= 0 && y + 1 < map[x - 1].length && map[x - 1][y + 1] == '#') return 'L';
            if (y - 1 >= 0 && map[x][y - 1] == '#') return 'L';
            if (y + 1 < map[x].length && map[x][y + 1] == '#') return 'L';
            if (x + 1 < map.length && y - 1 >= 0 && map[x + 1][y - 1] == '#') return 'L';
            if (x + 1 < map.length && map[x + 1][y] == '#') return 'L';
            if (x + 1 < map.length && y + 1 < map[x + 1].length && map[x + 1][y + 1] == '#') return 'L';
            return '#';
        }
        if (map[x][y] == '#') {
            int occupiedNeighbours = 0;
            if (x - 1 >= 0 && y - 1 >= 0 && map[x - 1][y - 1] == '#') ++occupiedNeighbours;
            if (x - 1 >= 0 && map[x - 1][y] == '#') ++occupiedNeighbours;
            if (x - 1 >= 0 && y + 1 < map[x - 1].length && map[x - 1][y + 1] == '#') ++occupiedNeighbours;
            if (y - 1 >= 0 && map[x][y - 1] == '#') ++occupiedNeighbours;
            if (y + 1 < map[x].length && map[x][y + 1] == '#') ++occupiedNeighbours;
            if (x + 1 < map.length && y - 1 >= 0 && map[x + 1][y - 1] == '#') ++occupiedNeighbours;
            if (x + 1 < map.length && map[x + 1][y] == '#') ++occupiedNeighbours;
            if (x + 1 < map.length && y + 1 < map[x + 1].length && map[x + 1][y + 1] == '#') ++occupiedNeighbours;
            if (occupiedNeighbours >= 4) {
                return 'L';
            }
            return '#';
        }
        System.out.println("Invalid state");
        return '-';
    }
    
    private static int part2(final char[][] map) {
        boolean stable = false;
        while (!stable) {
            stable = true;
            final char[][] mapCopy = new char[map.length][map[0].length];
            for (int x = 0; x < map.length; ++x) {
                for (int y = 0; y < map[x].length; ++y) {
                    mapCopy[x][y] = map[x][y];
                }
            }
            final char[][] newMap = newStates(mapCopy);
            for (int x = 0; x < map.length; ++x) {
                for (int y = 0; y < map[x].length; ++y) {
                    final char newState = newMap[x][y];
                    if (newState != map[x][y]) {
                        stable = false;
                    }
                    map[x][y] = newState;
                }
            }
        }
        
        int occupied = 0;
        for (int x = 0; x < map.length; ++x) {
            for (int y = 0; y < map[x].length; ++y) {
                if (map[x][y] == '#') {
                    ++occupied;
                }
            }
        }
        return occupied;
    }
    
    private static char[][] newStates(final char[][] map) {
        final char[][] newMap = new char[map.length][map[0].length];
        final int[][] visiblyOccupiedCounts = visiblyOccupied(map);
        for (int x = 0; x < map.length; ++x) {
            for (int y = 0; y < map[x].length; ++y) {
                if (map[x][y] == '.') {
                    newMap[x][y] = '.';
                } else if (map[x][y] == 'L') {
                    if (visiblyOccupiedCounts[x][y] > 0) {
                        newMap[x][y] = 'L';
                    } else {
                        newMap[x][y] = '#';
                    }
                } else {
                    if (visiblyOccupiedCounts[x][y] >= 5) {
                        newMap[x][y] = 'L';
                    } else {
                        newMap[x][y] = '#';
                    }
                }
            }
        }
        return newMap;
    }
    
    private static int[][] visiblyOccupied(final char[][] map) {
        final int[][] counts = new int[map.length][map[0].length];
        
        for (int x = 0; x < map.length; ++x) {
            for (int y = 0; y < map[0].length; ++y) {
                if (map[x][y] == '#') {
                    // Up and left
                    int tempX = x - 1;
                    int tempY = y - 1;
                    boolean done = false;
                    while (tempX >= 0 && tempY >= 0 && !done) {
                        ++counts[tempX][tempY];
                        if (map[tempX][tempY] == '#' || map[tempX][tempY] == 'L') {
                            done = true;
                        }
                        --tempX;
                        --tempY;
                    }
                    // Up
                    tempY = y - 1;
                    done = false;
                    while (tempY >= 0 && !done) {
                        ++counts[x][tempY];
                        if (map[x][tempY] == '#' || map[x][tempY] == 'L') {
                            done = true;
                        }
                        --tempY;
                    }
                    // Up and right
                    tempX = x + 1;
                    tempY = y - 1;
                    done = false;
                    while (tempX < map.length && tempY >= 0 && !done) {
                        ++counts[tempX][tempY];
                        if (map[tempX][tempY] == '#' || map[tempX][tempY] == 'L') {
                            done = true;
                        }
                        ++tempX;
                        --tempY;
                    }
                    // Left
                    tempX = x - 1;
                    done = false;
                    while (tempX >= 0 && !done) {
                        ++counts[tempX][y];
                        if (map[tempX][y] == '#' || map[tempX][y] == 'L') {
                            done = true;
                        }
                        --tempX;
                    }
                    // Right
                    tempX = x + 1;
                    done = false;
                    while (tempX < map.length && !done) {
                        ++counts[tempX][y];
                        if (map[tempX][y] == '#' || map[tempX][y] == 'L') {
                            done = true;
                        }
                        ++tempX;
                    }
                    // Down and left
                    tempX = x - 1;
                    tempY = y + 1;
                    done = false;
                    while (tempX >= 0 && tempY < map[tempX].length && !done) {
                        ++counts[tempX][tempY];
                        if (map[tempX][tempY] == '#' || map[tempX][tempY] == 'L') {
                            done = true;
                        }
                        --tempX;
                        ++tempY;
                    }
                    // Down
                    tempY = y + 1;
                    done = false;
                    while (tempY < map[x].length && !done) {
                        ++counts[x][tempY];
                        if (map[x][tempY] == '#' || map[x][tempY] == 'L') {
                            done = true;
                        }
                        ++tempY;
                    }
                    // Down and right
                    tempX = x + 1;
                    tempY = y + 1;
                    done = false;
                    while (tempX < map.length && tempY < map[tempX].length && !done) {
                        ++counts[tempX][tempY];
                        if (map[tempX][tempY] == '#' || map[tempX][tempY] == 'L') {
                            done = true;
                        }
                        ++tempX;
                        ++tempY;
                    }
                }
            }
        }
        
        return counts;
    }
}
