package day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day18Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day18/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        final List<Expression2> expressions = new ArrayList<>();
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            final Expression2 expression = new Expression2(line);
            expressions.add(expression);
        }
        
        scan.close();
        
        System.out.println(part2(expressions));
    }
    
    private static long part1(final List<Expression> expressions) {
        long sum = 0;
        for (final Expression expression : expressions) {
            final long evaluatedValue = expression.evaluate();
            sum += evaluatedValue;
        }
        return sum;
    }
    
    private static long part2(final List<Expression2> expressions) {
        long sum = 0;
        for (final Expression2 expression : expressions) {
            final long evaluatedValue = expression.evaluate();
            sum += evaluatedValue;
        }
        return sum;
    }
    
    
}
