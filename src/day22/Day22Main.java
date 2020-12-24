package day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day22Main {
    public static void run() throws FileNotFoundException {
        final File inputFile = new File("src/day22/input.txt");
        final Scanner scan = new Scanner(inputFile);
        
        scan.nextLine();
        String line = scan.nextLine();
        
        final List<Integer> player1Cards = new ArrayList<>();
        while (!line.isEmpty()) {
            player1Cards.add(Integer.parseInt(line));
            line = scan.nextLine();
        }
        
        scan.nextLine();
        
        final List<Integer> player2Cards = new ArrayList<>();
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            player2Cards.add(Integer.parseInt(line));
        }
        
        scan.close();
        
        final Deck player1 = new Deck(player1Cards);
        final Deck player2 = new Deck(player2Cards);
        
        System.out.println(part2(player1, player2));
    }
    
    private static long part1(final Deck player1, final Deck player2) {
        while (player1.hasCards() && player2.hasCards()) {
            Deck.advanceOneRound(player1, player2);
        }
        
        final Deck winner = (player1.hasCards()) ? player1 : player2;
        
        return winner.getScore();
    }
    
    private static long part2(final Deck player1, final Deck player2) {
        final RecursiveGame game = new RecursiveGame(player1, player2);
        
        game.play();
        
        return game.getWinningScore();
    }
}
