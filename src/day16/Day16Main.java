package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day16Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day16/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<Field> fields = new ArrayList<>();
        String line = scan.nextLine();
        while (!line.equals("")) {
            final Field field = new Field(line);
            fields.add(field);
            line = scan.nextLine();
        }
        
        final List<List<Integer>> tickets = new ArrayList<>();
        
        scan.nextLine(); // "your ticket:"
        line = scan.nextLine();
        String[] splitLine = line.split(",");
        List<Integer> ticket = new ArrayList<>();
        for (int i = 0; i < splitLine.length; ++i) {
            ticket.add(Integer.parseInt(splitLine[i]));
        }
        tickets.add(ticket);
        
        scan.nextLine(); // blank line
        scan.nextLine(); // "nearby tickets:"
        
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            splitLine = line.split(",");
            ticket = new ArrayList<>();
            for (int i = 0; i < splitLine.length; ++i) {
                ticket.add(Integer.parseInt(splitLine[i]));
            }
            tickets.add(ticket);
        }
        
        scan.close();
        
        System.out.println(part2(fields, tickets));
    }
    
    private static int part1(final List<Field> fields, final List<List<Integer>> tickets) {
        int errorRate = 0;
        
        for (int i = 0; i < tickets.size(); ++i) {
            final List<Integer> ticket = tickets.get(i);
            for (int j = 0; j < ticket.size(); ++j) {
                final int fieldNumber = ticket.get(j);
                boolean valid = false;
                for (int k = 0; k < fields.size() && !valid; ++k) {
                    if (fields.get(k).intMatchesRange(fieldNumber)) {
                        valid = true;
                    }
                }
                if (!valid) {
                    errorRate += fieldNumber;
                }
            }
        }
        
        return errorRate;
    }
    
    private static long part2(final List<Field> fields, final List<List<Integer>> tickets) {
        final Field[] fieldsInOrder = new Field[fields.size()];
        final List<List<Integer>> validTickets = new ArrayList<>();
        
        for (int i = 0; i < tickets.size(); ++i) {
            final List<Integer> ticket = tickets.get(i);
            boolean ticketValid = true;
            for (int j = 0; j < ticket.size() && ticketValid; ++j) {
                final int fieldNumber = ticket.get(j);
                boolean numberValid = false;
                for (int k = 0; k < fields.size() && !numberValid; ++k) {
                    if (fields.get(k).intMatchesRange(fieldNumber)) {
                        numberValid = true;
                    }
                }
                if (!numberValid) {
                    ticketValid = false;
                }
            }
            if (ticketValid) {
                validTickets.add(ticket);
            }
        }
        
        while (!hasSixDepartures(fieldsInOrder)) {
            for (int i = 0; i < fields.size(); ++i) {
                final Field field = fields.get(i);
                if (!arrayContainsField(fieldsInOrder, field)) {
                    final boolean[] possibleFieldPlacements = new boolean[fieldsInOrder.length];
                    for (int j = 0; j < possibleFieldPlacements.length; ++j) {
                        possibleFieldPlacements[j] = fieldsInOrder[j] == null;
                    }
                    for (int j = 0; j < validTickets.size(); ++j) {
                        final List<Integer> ticket = validTickets.get(j);
                        for (int k = 0; k < ticket.size(); ++k) {
                            final int fieldNumber = ticket.get(k);
                            if (possibleFieldPlacements[k] && !field.intMatchesRange(fieldNumber)) {
                                possibleFieldPlacements[k] = false;
                            }
                        }
                    }
                    if (onlyOneTrue(possibleFieldPlacements)) {
                        for (int j = 0; j < possibleFieldPlacements.length; ++j) {
                            if (possibleFieldPlacements[j]) {
                                fieldsInOrder[j] = field;
                                System.out.println("Placed: " + field.getName());
                            }
                        }
                    }
                }
            }
        }
        
        long finalProduct = 1;
        final List<Integer> myTicket = validTickets.get(0);
        for (int i = 0; i < fieldsInOrder.length; ++i) {
            if (fieldsInOrder[i] != null && fieldsInOrder[i].isDepartureField()) {
                finalProduct *= myTicket.get(i);
            }
        }
        
        return finalProduct;
    }
    
    private static boolean onlyOneTrue(final boolean[] possibleFieldPlacements) {
        int trues = 0;
        for (int i = 0; i < possibleFieldPlacements.length && trues < 2; ++i) {
            if (possibleFieldPlacements[i]) {
                ++trues;
            }
        }
        return trues == 1;
    }
    
    private static boolean hasSixDepartures(final Field[] fieldsInOrder) {
        int departureCount = 0;
        for (int i = 0; i < fieldsInOrder.length; ++i) {
            if (fieldsInOrder[i] != null && fieldsInOrder[i].isDepartureField()) {
                ++departureCount;
                if (departureCount == 6) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean arrayContainsField(final Field[] fieldsInOrder, final Field field) {
        for (int i = 0; i < fieldsInOrder.length; ++i) {
            if(fieldsInOrder[i] == field) {
                return true;
            }
        }
        return false;
    }
}
