package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day2/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<PasswordPolicyPair> passwords = new ArrayList<>();
        
        final boolean isPart1 = false;
        
        while(scan.hasNextLine()) {
            final String line = scan.nextLine();
            final PasswordPolicyPair pair = new PasswordPolicyPair(line, isPart1);
            passwords.add(pair);
        }
        
        scan.close();
        
        System.out.println(countValid(passwords));
    }
    
    private static int countValid(final List<PasswordPolicyPair> passwords) {
        int count = 0;
        for(final PasswordPolicyPair password : passwords) {
            if(password.isValid()) {
                ++count;
            }
        }
        return count;
    }
}
