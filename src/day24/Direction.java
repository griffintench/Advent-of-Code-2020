package day24;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    EAST, SOUTHEAST, SOUTHWEST, WEST, NORTHWEST, NORTHEAST;
    
    public static List<Direction> getDirectionsFromString(final String str) {
        final List<Direction> directions = new ArrayList<>();
        int i = 0;
        while (i < str.length()) {
            if (str.charAt(i) == 'e') {
                directions.add(EAST);
                ++i;
            } else if (str.charAt(i) == 'w') {
                directions.add(WEST);
                ++i;
            } else if (str.charAt(i) == 'n') {
                if (str.charAt(i + 1) == 'e') {
                    directions.add(NORTHEAST);
                } else {
                    directions.add(NORTHWEST);
                }
                i += 2;
            } else {
                if (str.charAt(i + 1) == 'e') {
                    directions.add(SOUTHEAST);
                } else {
                    directions.add(SOUTHWEST);
                }
                i += 2;
            }
        }
        return directions;
    }
}
