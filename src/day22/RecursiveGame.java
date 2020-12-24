package day22;

import java.util.HashSet;
import java.util.Set;

public class RecursiveGame {
    
    final Deck player1;
    
    final Deck player2;
    
    public RecursiveGame(final Deck player1, final Deck player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RecursiveGame)) return false;
        final RecursiveGame other = (RecursiveGame)o;
        return this.player1.equals(other.player1) && this.player2.equals(other.player2); 
    }
    
    @Override
    public int hashCode() {
        return player1.hashCode() * 79 + player2.hashCode();
    }
    
    public boolean play() {
        // true if player 1 wins, false if player 2 wins
        
        final Set<RecursiveGame> previousGames = new HashSet<>();
        
        Deck winner = null;
        while (player1.hasCards() && player2.hasCards() && winner == null) {
            if (previousGames.contains(this)) {
                winner = player1;
            } else {
                previousGames.add(this);
                
                final int card1 = player1.draw();
                final int card2 = player2.draw();
                
                Deck roundWinner = null;
                if (card1 <= player1.cardsRemaining() && card2 <= player2.cardsRemaining()) {
                    final Deck newPlayer1 = new Deck(player1.getFirstNCards(card1));
                    final Deck newPlayer2 = new Deck(player2.getFirstNCards(card2));
                    final RecursiveGame newGame = new RecursiveGame(newPlayer1, newPlayer2);
                    roundWinner = newGame.play() ? player1 : player2;
                } else {
                    roundWinner = card1 > card2 ? player1 : player2;
                }
                
                roundWinner.putOnBottom(roundWinner == player1 ? card1 : card2);
                roundWinner.putOnBottom(roundWinner == player1 ? card2 : card1);
            }
        }
        
        if (winner == null) {
            winner = player1.hasCards() ? player1 : player2;
        }
        
        return winner == player1;
    }
    
    public long getWinningScore() {
        final Deck winner = player1.hasCards() ? player1 : player2;
        return winner.getScore();
    }

}
