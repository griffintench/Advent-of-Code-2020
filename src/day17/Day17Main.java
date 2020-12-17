package day17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day17Main {
    
    private static class Point {
        public final int x;
        public final int y;
        public final int z;
        public final int w;
        public Point(final int x, final int y, final int z, final int w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }
        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Point)) return false;
            Point other = (Point) o;
            return this.x == other.x && this.y == other.y && this.z == other.z && this.w == other.w;
        }
        @Override
        public final int hashCode() {
            return 17 * x + 19 * y + 31 * z + 37 * w;
        }
        public String toString() {
            return "[" + x + "," + y + "," + z + "," + w + "]";
        }
    }
    
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day17/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final Map<Point, Boolean> map = new HashMap<>();
        int x = 0;
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            for (int y = 0; y < line.length(); ++y) {
                map.put(new Point(x, y, 0, 0), line.charAt(y) == '#');
            }
            ++x;
        }
        
        scan.close();
        
        System.out.println(part2(map));
    }
    
    private static int part1(final Map<Point, Boolean> map) {
        
        Map<Point, Boolean> currentMap = map;
        
        for (int i = 0; i < 6; ++i) {
            final Map<Point, Boolean> nextMap = new HashMap<>();
            
            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            int minZ = Integer.MAX_VALUE;
            int maxZ = Integer.MIN_VALUE;
            for (final Point coords : currentMap.keySet()) {
                if (coords.x < minX) {
                    minX = coords.x;
                }
                if (coords.x > maxX) {
                    maxX = coords.x;
                }
                if (coords.y < minY) {
                    minY = coords.y;
                }
                if (coords.y > maxY) {
                    maxY = coords.y;
                }
                if (coords.z < minZ) {
                    minZ = coords.z;
                }
                if (coords.z > maxZ) {
                    maxZ = coords.z;
                }
            }
            
            for (int x = minX - 1; x <= maxX + 1; ++x) {
                for (int y = minY - 1; y <= maxY + 1; ++y) {
                    for (int z = minZ - 1; z <= maxZ + 1; ++z) {
                        final Point coords = new Point(x, y, z, 0);
                        final int activeNeighbours = activeNeighbours(currentMap, coords);
                        if (currentMap.containsKey(coords) && currentMap.get(coords)) {
                            nextMap.put(coords, activeNeighbours == 2 || activeNeighbours == 3);
                        } else {
                            nextMap.put(coords, activeNeighbours == 3);
                        }
                    }
                }
            }
            
            currentMap = nextMap;
        }
        
        int activeCount = 0;
        
        for (final Point coords : currentMap.keySet()) {
            if (currentMap.get(coords)) {
                ++activeCount;
            }
        }
        
        return activeCount;
    }
    
    private static int activeNeighbours(final Map<Point, Boolean> map, final Point coords) {
        int activeCount = 0;
        
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                for (int dz = -1; dz <= 1; ++dz) {
                    for (int dw = -1; dw <= 1; ++dw) {
                        if (dx != 0 || dy != 0 || dz != 0 || dw != 0) {
                            final Point newCoords = new Point(coords.x + dx, coords.y + dy, coords.z + dz, coords.w + dw);
                            if (map.containsKey(newCoords) && map.get(newCoords)) {
                                ++activeCount;
                                if (activeCount == 4) {
                                    return activeCount;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return activeCount;
    }
    
    private static int part2(final Map<Point, Boolean> map) {
        
        Map<Point, Boolean> currentMap = map;
        
        for (int i = 0; i < 6; ++i) {
            final Map<Point, Boolean> nextMap = new HashMap<>();
            
            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            int minZ = Integer.MAX_VALUE;
            int maxZ = Integer.MIN_VALUE;
            int minW = Integer.MAX_VALUE;
            int maxW = Integer.MIN_VALUE;
            for (final Point coords : currentMap.keySet()) {
                if (coords.x < minX) {
                    minX = coords.x;
                }
                if (coords.x > maxX) {
                    maxX = coords.x;
                }
                if (coords.y < minY) {
                    minY = coords.y;
                }
                if (coords.y > maxY) {
                    maxY = coords.y;
                }
                if (coords.z < minZ) {
                    minZ = coords.z;
                }
                if (coords.z > maxZ) {
                    maxZ = coords.z;
                }
                if (coords.w < minW) {
                    minW = coords.w;
                }
                if (coords.w > maxW) {
                    maxW = coords.w;
                }
            }
            
            for (int x = minX - 1; x <= maxX + 1; ++x) {
                for (int y = minY - 1; y <= maxY + 1; ++y) {
                    for (int z = minZ - 1; z <= maxZ + 1; ++z) {
                        for (int w = minW - 1; w <= maxW + 1; ++w) {
                            final Point coords = new Point(x, y, z, w);
                            final int activeNeighbours = activeNeighbours(currentMap, coords);
                            if (currentMap.containsKey(coords) && currentMap.get(coords)) {
                                nextMap.put(coords, activeNeighbours == 2 || activeNeighbours == 3);
                            } else {
                                nextMap.put(coords, activeNeighbours == 3);
                            }
                        }
                    }
                }
            }
            
            currentMap = nextMap;
        }
        
        int activeCount = 0;
        
        for (final Point coords : currentMap.keySet()) {
            if (currentMap.get(coords)) {
                ++activeCount;
            }
        }
        
        return activeCount;
    }
}
