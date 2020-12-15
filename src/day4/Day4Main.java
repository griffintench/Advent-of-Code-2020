package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day4/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<Passport> passports = new ArrayList<>();
        
        final List<String> currentRows = new ArrayList<>();
        while(scan.hasNextLine()) {
            final String line = scan.nextLine();
            if(line.equals("")) {
                final Passport newPassport = new Passport(currentRows);
                passports.add(newPassport);
                currentRows.clear();
            } else {
                currentRows.add(line);
            }
        }
        final Passport newPassport = new Passport(currentRows);
        passports.add(newPassport);
        
        scan.close();
        
        System.out.println(part2(passports));
    }
    
    private static int part1(final List<Passport> passports) {
        int count = 0;
        for(final Passport passport : passports) {
            if(passport.isValidNotStrict()) {
                ++count;
            }
        }
        return count;
    }
    
    private static int part2(final List<Passport> passports) {
        int count = 0;
        for(final Passport passport : passports) {
            if(passport.isValidStrict()) {
                ++count;
            }
        }
        return count;
    }
}
