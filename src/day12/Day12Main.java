package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day12Main {
    
    private static enum Direction {
        north, south, west, east;
    }
    
    private static class Instruction {
        public final char action;
        public final int value;
        public Instruction(final String instStr) {
            action = instStr.charAt(0);
            value = Integer.parseInt(instStr.substring(1));
        }
    }

    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day12/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<Instruction> instructions = new ArrayList<>();
       
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            final Instruction instruction = new Instruction(line);
            instructions.add(instruction);
        }
        
        scan.close();
        
        System.out.println(part2(instructions));
    }
    
    private static int part1(final List<Instruction> instructions) {
        Direction dir = Direction.east;
        int x = 0;
        int y = 0;
        
        for (final Instruction instruction : instructions) {
            if (instruction.action == 'N') {
                y += instruction.value;
            } else if (instruction.action == 'S') {
                y -= instruction.value;
            } else if (instruction.action == 'E') {
                x += instruction.value;
            } else if (instruction.action == 'W') {
                x -= instruction.value;
            } else if (instruction.action == 'L') {
                int degreesRemaining = instruction.value;
                while (degreesRemaining > 0) {
                    if (dir == Direction.east) {
                        dir = Direction.north;
                    } else if (dir == Direction.south) {
                        dir = Direction.east;
                    } else if (dir == Direction.west) {
                        dir = Direction.south;
                    } else if (dir == Direction.north) {
                        dir = Direction.west;
                    }
                    degreesRemaining -= 90;
                }
            } else if (instruction.action == 'R') {
                int degreesRemaining = instruction.value;
                while (degreesRemaining > 0) {
                    if (dir == Direction.east) {
                        dir = Direction.south;
                    } else if (dir == Direction.south) {
                        dir = Direction.west;
                    } else if (dir == Direction.west) {
                        dir = Direction.north;
                    } else if (dir == Direction.north) {
                        dir = Direction.east;
                    }
                    degreesRemaining -= 90;
                }
            } else if (instruction.action == 'F') {
                if (dir == Direction.east) {
                    x += instruction.value;
                } else if (dir == Direction.south) {
                    y -= instruction.value;
                } else if (dir == Direction.west) {
                    x -= instruction.value;
                } else if (dir == Direction.north) {
                    y += instruction.value;
                }
            }
        }
        
        final int xDist = (x > 0) ? x : -x;
        final int yDist = (y > 0) ? y : -y;
        
        return xDist + yDist;
    }
    
    private static int part2(final List<Instruction> instructions) {
        int x = 0;
        int y = 0;
        int waypointX = 10;
        int waypointY = 1;
        
        for (final Instruction instruction : instructions) {
            if (instruction.action == 'N') {
                waypointY += instruction.value;
            } else if (instruction.action == 'S') {
                waypointY -= instruction.value;
            } else if (instruction.action == 'E') {
                waypointX += instruction.value;
            } else if (instruction.action == 'W') {
                waypointX -= instruction.value;
            } else if (instruction.action == 'L') {
                int degreesRemaining = instruction.value;
                while (degreesRemaining > 0) {
                    final int oldWaypointY = waypointY;
                    waypointY = waypointX;
                    waypointX = -oldWaypointY;
                    degreesRemaining -= 90;
                }
            } else if (instruction.action == 'R') {
                int degreesRemaining = instruction.value;
                while (degreesRemaining > 0) {
                    final int oldWaypointY = waypointY;
                    waypointY = -waypointX;
                    waypointX = oldWaypointY;
                    degreesRemaining -= 90;
                }
            } else if (instruction.action == 'F') {
                x += waypointX * instruction.value;
                y += waypointY * instruction.value;
            }
        }
        
        final int xDist = (x > 0) ? x : -x;
        final int yDist = (y > 0) ? y : -y;
        
        return xDist + yDist;
    }
    
}
