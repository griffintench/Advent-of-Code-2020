package day24;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day24Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day24/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<String> directionStrings = new ArrayList<>();
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            directionStrings.add(line);
        }
        
        scan.close();
        
        System.out.println(part2(directionStrings));
    }
    
    private static int part1(final List<String> directionStrings) {
        // see http://devmag.org.za/2013/08/31/geometry-with-hex-coordinates/ for coordinate system
        final Map<Point, Boolean> map = new HashMap<>(); // true for black, false for white
        for (final String directionString : directionStrings) {
            int curX = 0;
            int curY = 0;
            final List<Direction> directions = Direction.getDirectionsFromString(directionString);
            for (final Direction dir : directions) {
                if (dir == Direction.EAST) {
                    ++curX;
                } else if (dir == Direction.NORTHEAST) {
                    ++curY;
                } else if (dir == Direction.NORTHWEST) {
                    --curX;
                    ++curY;
                } else if (dir == Direction.SOUTHEAST) {
                    ++curX;
                    --curY;
                } else if (dir == Direction.SOUTHWEST) {
                    --curY;
                } else if (dir == Direction.WEST) {
                    --curX;
                } else {
                    System.out.println("Invalid direction");
                }
            }
            final Point location = new Point(curX, curY);
            if (map.containsKey(location)) {
                map.put(location, !map.get(location));
            } else {
                map.put(location, true);
            }
        }
        
        int blackTiles = 0;
        
        for (final boolean value : map.values()) {
            if (value) {
                ++blackTiles;
            }
        }
        
        return blackTiles;
    }
    
    private static int part2(final List<String> directionStrings) {
        Map<Point, Boolean> map = new HashMap<>(); // true for black, false for white
        for (final String directionString : directionStrings) {
            int curX = 0;
            int curY = 0;
            final List<Direction> directions = Direction.getDirectionsFromString(directionString);
            for (final Direction dir : directions) {
                if (dir == Direction.EAST) {
                    ++curX;
                } else if (dir == Direction.NORTHEAST) {
                    ++curY;
                } else if (dir == Direction.NORTHWEST) {
                    --curX;
                    ++curY;
                } else if (dir == Direction.SOUTHEAST) {
                    ++curX;
                    --curY;
                } else if (dir == Direction.SOUTHWEST) {
                    --curY;
                } else if (dir == Direction.WEST) {
                    --curX;
                } else {
                    System.out.println("Invalid direction");
                }
            }
            final Point location = new Point(curX, curY);
            if (map.containsKey(location)) {
                map.put(location, !map.get(location));
            } else {
                map.put(location, true);
            }
        }
        
        for (int day = 1; day <= 100; ++day) {
            final Map<Point, Boolean> mapIncludingNeighbours = new HashMap<>();
            for (final Point key : map.keySet()) {
                mapIncludingNeighbours.put(key, map.get(key));
                final List<Point> neighbours = getNeighbours(key);
                for (final Point neighbour : neighbours) {
                    if (!mapIncludingNeighbours.containsKey(neighbour)) {
                        mapIncludingNeighbours.put(neighbour, false);
                    }
                }
            }
            
            final Map<Point, Boolean> newMap = new HashMap<>();
            for (final Point key : mapIncludingNeighbours.keySet()) {
                final int blackTileNeighbours = blackTileNeighbourCount(mapIncludingNeighbours, key);
                if (mapIncludingNeighbours.get(key)) {
                    // black
                    newMap.put(key, blackTileNeighbours == 1 || blackTileNeighbours == 2);
                } else {
                    // white
                    newMap.put(key, blackTileNeighbours == 2);
                }
            }
            
            map = newMap;
        }
        
        int blackTiles = 0;
        
        for (final boolean value : map.values()) {
            if (value) {
                ++blackTiles;
            }
        }
        
        return blackTiles;
    }
    
    private static int blackTileNeighbourCount(final Map<Point, Boolean> map, final Point p) {
        int count = 0;
        
        final List<Point> neighbours = getNeighbours(p);
        for (final Point neighbour : neighbours) {
            if (map.containsKey(neighbour) && map.get(neighbour)) {
                ++count; 
            }
        }
        
        return count;
    }
    
    private static List<Point> getNeighbours(final Point p) {
        final List<Point> neighbours = new ArrayList<>();
        
        // east
        neighbours.add(new Point(p.x + 1, p.y));
        // northeast
        neighbours.add(new Point(p.x, p.y + 1));
        // northwest
        neighbours.add(new Point(p.x - 1, p.y + 1));
        // southeast
        neighbours.add(new Point(p.x + 1, p.y - 1));
        // southwest
        neighbours.add(new Point(p.x, p.y - 1));
        // west
        neighbours.add(new Point(p.x - 1, p.y));
        
        return neighbours;
    }
}
