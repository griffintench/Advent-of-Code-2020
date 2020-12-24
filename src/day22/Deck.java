package day22;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Deck {
    final Deque<Integer> cards;
    
    public Deck(final List<Integer> cards) {
        this.cards = new LinkedList<>(cards);
    }
    
    public boolean hasCards() {
        return !cards.isEmpty();
    }
    
    public long getScore() {
        long score = 0;
        
        int multiplier = cards.size();
        for (final int card : cards) {
            score += multiplier * card;
            --multiplier;
        }
        
        return score;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Deck)) return false;
        final Deck other = (Deck)o;
        if (this.cards.size() != other.cards.size()) return false;
        final Iterator<Integer> thisItr = this.cards.iterator();
        final Iterator<Integer> otherItr = other.cards.iterator();
        while (thisItr.hasNext()) {
            if (thisItr.next() != otherItr.next()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int code = 0;
        
        final Iterator<Integer> itr = cards.descendingIterator();
        int multiplier = 1;
        while (itr.hasNext()) {
            code += multiplier * itr.next();
            multiplier *= 53;
        }
        
        return code;
    }
    
    public int draw() {
        return cards.pop();
    }
    
    public void putOnBottom(final int card) {
        cards.addLast(card);
    }
    
    public int cardsRemaining() {
        return cards.size();
    }
    
    public List<Integer> getFirstNCards(final int n) {
        final List<Integer> firstNCards = new ArrayList<>();
        final Iterator<Integer> itr = cards.iterator();
        for (int i = 0; i < n; ++i) {
            firstNCards.add(itr.next());
        }
        return firstNCards;
    }
    
    public static void advanceOneRound(final Deck d1, final Deck d2) {
        final int d1Card = d1.cards.pop();
        final int d2Card = d2.cards.pop();
        
        if (d1Card > d2Card) {
            d1.cards.addLast(d1Card);
            d1.cards.addLast(d2Card);
        } else if (d2Card > d1Card) {
            d2.cards.addLast(d2Card);
            d2.cards.addLast(d1Card);
        } else {
            // should never happen
            System.out.println("equal cards");
        }
    }
}
