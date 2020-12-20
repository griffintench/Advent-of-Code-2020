package day20;

public class Tile {
    private final int id;
    
    private final char[][] pixels;
    
    private Tile northNeighbour;
    
    private Tile eastNeighbour;
    
    private Tile southNeighbour;
    
    private Tile westNeighbour;
    
    public Tile(final int id, final char[][] pixels) {
        this.id = id;
        this.pixels = pixels;
    }
    
    public void rotate() {
        // 90 degrees clockwise
        final char[][] newPixels = new char[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; ++i) {
            for (int j = 0; j < pixels[i].length; ++j) {
                newPixels[j][pixels.length - 1 - i] = pixels[i][j];
            }
        }
        for (int i = 0; i < pixels.length; ++i) {
            for (int j = 0; j < pixels[i].length; ++j) {
                pixels[i][j] = newPixels[i][j];
            }
        }
        final Tile oldNorthNeighbour = northNeighbour;
        northNeighbour = westNeighbour;
        westNeighbour = southNeighbour;
        southNeighbour = eastNeighbour;
        eastNeighbour = oldNorthNeighbour;
    }
    
    public void flip() {
        // horizontal flip
        final char[][] newPixels = new char[pixels.length][pixels[0].length];
        for (int i = 0; i < pixels.length; ++i) {
            for (int j = 0; j < pixels[i].length; ++j) {
                newPixels[i][pixels.length - 1 - j] = pixels[i][j];
            }
        }
        for (int i = 0; i < pixels.length; ++i) {
            for (int j = 0; j < pixels[i].length; ++j) {
                pixels[i][j] = newPixels[i][j];
            }
        }
        final Tile oldWestNeighbour = westNeighbour;
        westNeighbour = eastNeighbour;
        eastNeighbour = oldWestNeighbour;
    }
    
    public boolean linesUpWith(final Tile other) {
        if (topMatchesBottom(other)) {
            this.northNeighbour = other;
            other.southNeighbour = this;
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            this.rotate();
            if (topMatchesBottom(other)) {
                this.northNeighbour = other;
                other.southNeighbour = this;
                return true;
            }
        }
        this.flip();
        if (topMatchesBottom(other)) {
            this.northNeighbour = other;
            other.southNeighbour = this;
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            this.rotate();
            if (topMatchesBottom(other)) {
                this.northNeighbour = other;
                other.southNeighbour = this;
                return true;
            }
        }
        for (int i = 0; i < 3; ++i) {
            other.rotate();
            if (topMatchesBottom(other)) {
                this.northNeighbour = other;
                other.southNeighbour = this;
                return true;
            }
            for (int j = 0; j < 3; ++j) {
                this.rotate();
                if (topMatchesBottom(other)) {
                    this.northNeighbour = other;
                    other.southNeighbour = this;
                    return true;
                }
            }
            this.flip();
            if (topMatchesBottom(other)) {
                this.northNeighbour = other;
                other.southNeighbour = this;
                return true;
            }
            for (int j = 0; j < 3; ++j) {
                this.rotate();
                if (topMatchesBottom(other)) {
                    this.northNeighbour = other;
                    other.southNeighbour = this;
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean topMatchesBottom(final Tile other) {
        // compare this tile's top row to other tile's bottom row
        for (int j = 0; j < pixels[0].length; ++j) {
            if (this.pixels[0][j] != other.pixels[other.pixels.length - 1][j]) {
                return false;
            }
        }
        return true;
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return Integer.toString(id);
    }
    
    public Tile getNorthNeighbour() {
        return northNeighbour;
    }
    
    public Tile getEastNeighbour() {
        return eastNeighbour;
    }
    
    public Tile getSouthNeighbour() {
        return southNeighbour;
    }
    
    public Tile getWestNeighbour() {
        return westNeighbour;
    }
    
    public char[][] getPixels() {
        return pixels;
    }
}
