package day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day20Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day20/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<Tile> tiles = new ArrayList<>();
        while (scan.hasNextLine()) {
            String line = scan.nextLine(); // Tile ####:
            final int tileId = Integer.parseInt(line.substring(5, line.length() - 1));
            final char[][] tilePixels = new char[10][10];
            for (int i = 0; i < 10; ++i) {
                line = scan.nextLine();
                for (int j = 0; j < 10; ++j) {
                    tilePixels[i][j] = line.charAt(j);
                }
            }
            tiles.add(new Tile(tileId, tilePixels));
            if (scan.hasNextLine()) {
                scan.nextLine();
            }
        }
        
        scan.close();
        
        System.out.println(part2(tiles));
    }
    
    private static long part1(final List<Tile> tiles) {
        long cornerProduct = 1;
        
        final Map<Integer, Integer> matches = new HashMap<>();
        
        for (int i = 0; i < tiles.size(); ++i) {
            for (int j = i + 1; j < tiles.size(); ++j) {
                final Tile iTile = tiles.get(i);
                final Tile jTile = tiles.get(j);
                if (iTile.linesUpWith(jTile)) {
                    final int iId = iTile.getId();
                    final int jId = jTile.getId();
                    matches.put(iId, matches.getOrDefault(iId, 0) + 1);
                    matches.put(jId, matches.getOrDefault(jId, 0) + 1);
                }
            }
        }
        
        for (final int tileId : matches.keySet()) {
            if (matches.get(tileId) == 2) {
                cornerProduct *= tileId;
            }
        }
        
        return cornerProduct;
    }
    
    private static int part2(final List<Tile> tiles) {
        for (int i = 0; i < tiles.size(); ++i) {
            for (int j = i + 1; j < tiles.size(); ++j) {
                final Tile iTile = tiles.get(i);
                final Tile jTile = tiles.get(j);
                iTile.linesUpWith(jTile);
            }
        }
        System.out.println("Connections created.");
        
        final int mapTileDimension = (int) Math.round(Math.sqrt(tiles.size()));
        final Tile[][] tilePlacements = new Tile[mapTileDimension][mapTileDimension];
        tilePlacements[0][0] = tiles.get(indexOfCorner(tiles));
        while (tilePlacements[0][0].getEastNeighbour() == null || tilePlacements[0][0].getSouthNeighbour() == null) {
            tilePlacements[0][0].rotate();
        }
        
        for (int i = 0; i < mapTileDimension; ++i) {
            if (i != 0) {
                tilePlacements[i][0] = tilePlacements[i - 1][0].getSouthNeighbour();
                for (int j = 0; j < 3; ++j) {
                    final Tile northNeighbour = tilePlacements[i][0].getNorthNeighbour();
                    if (northNeighbour != tilePlacements[i - 1][0]) {
                        tilePlacements[i][0].rotate();
                    }
                }
                if (tilePlacements[i][0].getEastNeighbour() == null) {
                    tilePlacements[i][0].flip();
                }
            }
            for (int j = 1; j < mapTileDimension; ++j) {
                tilePlacements[i][j] = tilePlacements[i][j - 1].getEastNeighbour();
                for (int k = 0; k < 3; ++k) {
                    final Tile westNeighbour = tilePlacements[i][j].getWestNeighbour();
                    if (westNeighbour != tilePlacements[i][j - 1]) {
                        tilePlacements[i][j].rotate();
                    }
                }
                // flip if necessary
                final boolean topRowFlip = i == 0 && tilePlacements[i][j].getNorthNeighbour() != null;
                final boolean laterRowFlip = i != 0 && tilePlacements[i][j].getNorthNeighbour() != tilePlacements[i - 1][j];
                if (topRowFlip || laterRowFlip) {
                    tilePlacements[i][j].flip();
                    tilePlacements[i][j].rotate();
                    tilePlacements[i][j].rotate();
                }
            }
        }
        System.out.println("Tile placements determined.");
        
        final int mapCharDimension = mapTileDimension * 8;
        final char[][] fullMap = new char[mapCharDimension][mapCharDimension];
        
        for (int i = 0; i < mapCharDimension; ++i) {
            for (int j = 0; j < mapCharDimension; ++j) {
                final Tile tile = tilePlacements[i / 8][j / 8];
                final char[][] pixels = tile.getPixels();
                fullMap[i][j] = pixels[i % 8 + 1][j % 8 + 1];
            }
        }
        
        final Tile fullMapTile = new Tile(0, fullMap);
        System.out.println("Full map created.");
        
        final boolean[][] seaMonsterParts = new boolean[fullMap.length][fullMap.length];
        int seaMonsters = 0;
        for (int i = 0; i < 3 && seaMonsters == 0; ++i) {
            fullMapTile.rotate();
            seaMonsters = countSeaMonsters(fullMapTile, seaMonsterParts);
            System.out.println("Orientation checked.");
        }
        if (seaMonsters == 0) {
            fullMapTile.flip();
            for (int i = 0; i < 3 && seaMonsters == 0; ++i) {
                fullMapTile.rotate();
                seaMonsters = countSeaMonsters(fullMapTile, seaMonsterParts);
                System.out.println("Orientation checked.");
            }
        }
        
        System.out.println("Sea monsters counted.");
        
        int roughnessCount = 0;
        for (int i = 0; i < seaMonsterParts.length; ++i) {
            for (int j = 0; j < seaMonsterParts[i].length; ++j) {
                if (!seaMonsterParts[i][j] && fullMapTile.getPixels()[i][j] == '#') {
                    ++roughnessCount;
                }
            }
        }
        
        return roughnessCount;
    }
    
    private static int indexOfCorner(final List<Tile> tiles) {
        for (int i = 0; i < tiles.size(); ++i) {
            final Tile tile = tiles.get(i);
            int neighbourCount = 0;
            if (tile.getNorthNeighbour() == null) {
                ++neighbourCount;
            }
            if (tile.getEastNeighbour() == null) {
                ++neighbourCount;
            }
            if (tile.getSouthNeighbour() == null) {
                ++neighbourCount;
            }
            if (tile.getWestNeighbour() == null) {
                ++neighbourCount;
            }
            if (neighbourCount == 2) {
                return i;
            }
        }
        return -1;
    }
    
    private static int countSeaMonsters(final Tile map, final boolean[][] seaMonsterParts) {
        //                   # 
        // #    ##    ##    ###
        //  #  #  #  #  #  #   
        // ^ this is 20 across
        for (int i = 0; i < seaMonsterParts.length; ++i) {
            for (int j = 0; j < seaMonsterParts[i].length; ++j) {
                seaMonsterParts[i][j] = false;
            }
        }
        final char[][] pixels = map.getPixels();
        int seaMonsters = 0;
        for (int i = 0; i + 2 < pixels.length; ++i) {
            for (int j = 0; j + 19 < pixels[i].length; ++j) {
                if (foundSeaMonster(pixels, i, j)) {
                    ++seaMonsters;
                    // what you are about to see is ugly code :)
                    seaMonsterParts[i][j + 18] = true;
                    seaMonsterParts[i + 1][j] = true;
                    seaMonsterParts[i + 1][j + 5] = true;
                    seaMonsterParts[i + 1][j + 6] = true;
                    seaMonsterParts[i + 1][j + 11] = true;
                    seaMonsterParts[i + 1][j + 12] = true;
                    seaMonsterParts[i + 1][j + 17] = true;
                    seaMonsterParts[i + 1][j + 18] = true;
                    seaMonsterParts[i + 1][j + 19] = true;
                    seaMonsterParts[i + 2][j + 1] = true;
                    seaMonsterParts[i + 2][j + 4] = true;
                    seaMonsterParts[i + 2][j + 7] = true;
                    seaMonsterParts[i + 2][j + 10] = true;
                    seaMonsterParts[i + 2][j + 13] = true;
                    seaMonsterParts[i + 2][j + 16] = true;
                }
            }
        }
        return seaMonsters;
    }
    
    private static boolean foundSeaMonster(final char[][] pixels, final int i, final int j) {
        if (pixels[i][j + 18] != '#') {
            return false;
        }
        if (pixels[i + 1][j] != '#') {
            return false;
        }
        if (pixels[i + 1][j + 5] != '#') {
            return false;
        }
        if (pixels[i + 1][j + 6] != '#') {
            return false;
        }
        if (pixels[i + 1][j + 11] != '#') {
            return false;
        }
        if (pixels[i + 1][j + 12] != '#') {
            return false;
        }
        if (pixels[i + 1][j + 17] != '#') {
            return false;
        }
        if (pixels[i + 1][j + 18] != '#') {
            return false;
        }
        if (pixels[i + 1][j + 19] != '#') {
            return false;
        }
        if (pixels[i + 2][j + 1] != '#') {
            return false;
        }
        if (pixels[i + 2][j + 4] != '#') {
            return false;
        }
        if (pixels[i + 2][j + 7] != '#') {
            return false;
        }
        if (pixels[i + 2][j + 10] != '#') {
            return false;
        }
        if (pixels[i + 2][j + 13] != '#') {
            return false;
        }
        if (pixels[i + 2][j + 16] != '#') {
            return false;
        }
        return true;
    }
}
