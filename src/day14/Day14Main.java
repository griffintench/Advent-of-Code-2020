package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day14Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day14/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<String> instructions = new ArrayList<>();
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            instructions.add(line);
        }
        
        scan.close();
        
        System.out.println(part2(instructions));
    }
    
    private static long part1(final List<String> instructions) {
        final Map<Integer, String> memory = new HashMap<>();
        String bitmask = "";
        
        for (final String instruction : instructions) {
            final String[] splitInstruction = instruction.split("( = )|\\[|\\]");
            if (instruction.charAt(1) == 'a') {
                // mask
                bitmask = splitInstruction[1];
            } else if (instruction.charAt(1) == 'e') {
                // memory
                final int address = Integer.parseInt(splitInstruction[1]);
                final long longValue = Long.parseLong(splitInstruction[3]);
                final String preMaskValue = longToBinaryString(longValue);
                final String postMaskValue = applyPart1Mask(preMaskValue, bitmask);
                memory.put(address, postMaskValue);
            } else {
                System.out.println("bad instruction");
            }
        }
        
        long sum = 0;
        for (final String memoryValue : memory.values()) {
            sum += binaryStringToLong(memoryValue);
        }
        
        return sum;
    }
    
    private static String longToBinaryString(final long value) {
        final StringBuilder sb = new StringBuilder();
        
        long remainder = value;
        long divisor = 34359738368L; // 2^35
        while (divisor > 0) {
            if (remainder >= divisor) {
                sb.append('1');
                remainder -= divisor;
            } else {
                sb.append('0');
            }
            
            divisor /= 2;
        }
        
        return sb.toString();
    }
    
    private static String applyPart1Mask(final String value, final String bitmask) {
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < bitmask.length(); ++i) {
            final char bitmaskChar = bitmask.charAt(i);
            if (bitmaskChar == 'X') {
                sb.append(value.charAt(i));
            } else {
                sb.append(bitmaskChar);
            }
        }
        
        return sb.toString();
    }
    
    private static long binaryStringToLong(final String strValue) {
        long longValue = 0L;
        
        long place = 34359738368L;
        int i = 0;
        while (place > 0) {
            if (strValue.charAt(i) == '1') {
                longValue += place;
            }
            place /= 2;
            ++i;
        }
        
        return longValue;
    }
    
    private static long part2(final List<String> instructions) {
        final Map<String, Long> memory = new HashMap<>();
        String bitmask = "";
        
        for (final String instruction : instructions) {
            final String[] splitInstruction = instruction.split("( = )|\\[|\\]");
            if (instruction.charAt(1) == 'a') {
                // mask
                bitmask = splitInstruction[1];
            } else if (instruction.charAt(1) == 'e') {
                // memory
                final long longAddress = Long.parseLong(splitInstruction[1]);
                final String preMaskAddress = longToBinaryString(longAddress);
                final long value = Long.parseLong(splitInstruction[3]);
                final String postMaskAddress = applyPart2Mask(preMaskAddress, bitmask);
                final List<String> allAddresses = getAllAddresses(postMaskAddress);
                for (final String address : allAddresses) {
                    memory.put(address, value);
                }
            } else {
                System.out.println("bad instruction");
            }
        }
        
        long sum = 0;
        for (final long memoryValue : memory.values()) {
            sum += memoryValue;
        }
        
        return sum;
    }
    
    private static List<String> getAllAddresses(final String addressTemplate) {
        for (int i = 0; i < addressTemplate.length(); ++i) {
            if (addressTemplate.charAt(i) == 'X') {
                final String bitmaskPart1 = addressTemplate.substring(0, i);
                final String bitmaskPart2 = addressTemplate.substring(i + 1);
                
                final String version1 = bitmaskPart1 + '0' + bitmaskPart2;
                List<String> bitmasks = getAllAddresses(version1);
                final String version2 = bitmaskPart1 + '1' + bitmaskPart2;
                bitmasks.addAll(getAllAddresses(version2));
                
                return bitmasks;
            }
        }
        // no X's
        List<String> bitmasks = new ArrayList<>();
        bitmasks.add(addressTemplate);
        
        return bitmasks;
    }
    
    private static String applyPart2Mask(final String address, final String bitmask) {
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < bitmask.length(); ++i) {
            final char bitmaskChar = bitmask.charAt(i);
            if (bitmaskChar == '0') {
                sb.append(address.charAt(i));
            } else {
                sb.append(bitmaskChar);
            }
        }
        
        return sb.toString();
    }
}
