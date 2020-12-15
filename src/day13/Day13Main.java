package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day13/input.txt");
        final Scanner scan = new Scanner(inputFile);
       
        String line = scan.nextLine();
        final int earliestTime = Integer.parseInt(line);
        final String[] busIds = scan.nextLine().split(",");
        
        scan.close();
        
        System.out.println(part2(busIds));
    }
    
    private static int part1(final int earliestTime, final String[] busIds) {
        final List<Integer> busIdInts = new ArrayList<>();
        for (int i = 0; i < busIds.length; ++i) {
            if (!busIds[i].equals("x")) {
                busIdInts.add(Integer.parseInt(busIds[i]));
            }
        }
        
        int smallestTimestamp = Integer.MAX_VALUE;
        int correctBusId = 0;
        for (final int busIdInt : busIdInts) {
            final int remainder = earliestTime % busIdInt;
            final int timestamp = earliestTime + busIdInt - remainder;
            if (timestamp < smallestTimestamp) {
                smallestTimestamp = timestamp;
                correctBusId = busIdInt;
            }
        }
        
        return (smallestTimestamp - earliestTime) * correctBusId;
    }
    
    private static long part2(final String[] busIds) {
        // Notation is based on Wikipedia article on Chinese remainder theorem
        // See https://en.wikipedia.org/wiki/Chinese_remainder_theorem#Existence_(direct_construction)
        // I don't believe the problem specifically states they are all prime, but I can see that they are so I'm going with it
        final List<Integer> n = new ArrayList<>();
        final List<Integer> a = new ArrayList<>();
        for (int j = 0; j < busIds.length; ++j) { // used j so as not to confuse with i, used elsewhere
            if (!busIds[j].equals("x")) {
                final int n_i = Integer.parseInt(busIds[j]);
                int a_i = n_i - j;
                // I don't feel like looking up whether % is remainder or modulo so yeah
                while (a_i >= n_i) {
                    a_i -= n_i;
                }
                while (a_i < 0) {
                    a_i += n_i;
                }
                n.add(n_i);
                a.add(a_i);
            }
        }
        
        long product = 1; // denoted N in the article
        for (int i = 0; i < n.size(); ++i) {
            product *= n.get(i);
        }
        
        final List<Long> N = new ArrayList<>(); // the set of all N_i
        for (int i = 0; i < n.size(); ++i) {
            final long N_i = product / n.get(i);
            N.add(N_i);
        }
        
        final List<Long> M = new ArrayList<>(); // the set of all M_i
        final List<Long> m = new ArrayList<>(); // the set of all m_i
        // See https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
        // For all i: a = N_i, x = M_i, b = n_i, y = m_i, and gcd(a,b) = 1
        // The i in the Extended Euclidean Algorithm is replaced by j here
        for (int i = 0; i < n.size(); ++i) {
            long r_jminus2 = N.get(i); // initialized to r_0
            long s_jminus2 = 1; // initialized to s_0
            long t_jminus2 = 0; // initialized to t_0
            long r_jminus1 = n.get(i); // initialized to r_1
            long s_jminus1 = 0; // initialized to s_1
            long t_jminus1 = 1; // initialized to t_1
            for (int j = 2; r_jminus1 != 0; ++j) {
                final long q_jminus1 = r_jminus2 / r_jminus1;
                final long r_j = r_jminus2 - q_jminus1 * r_jminus1;
                final long s_j = s_jminus2 - q_jminus1 * s_jminus1;
                final long t_j = t_jminus2 - q_jminus1 * t_jminus1;
                r_jminus2 = r_jminus1;
                s_jminus2 = s_jminus1;
                t_jminus2 = t_jminus1;
                r_jminus1 = r_j;
                s_jminus1 = s_j;
                t_jminus1 = t_j;
            }
            M.add(s_jminus2);
            m.add(t_jminus2);
        }
        
        long x = 0;
        for (int i = 0; i < n.size(); ++i) {
            x += a.get(i) * M.get(i) * N.get(i);
        }
        while (x < 0) {
            x += product;
        }
        while (x >= product) {
            x -= product;
        }
        
        return x;
    }
}
