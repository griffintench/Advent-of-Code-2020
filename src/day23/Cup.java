package day23;

public class Cup {
    private final int label;
    
    private Cup next;
    
    private Cup nextLowest;
    
    public Cup(final int label) {
        this.label = label;
    }
    
    public void setNext(final Cup nextCup) {
        this.next = nextCup;
    }
    
    public void setNextLowest(final Cup nextLowestCup) {
        nextLowest = nextLowestCup;
    }
    
    public Cup getNext() {
        return next;
    }
    
    public int getLabel() {
        return label;
    }
    
    public Cup getNextLowest() {
        return nextLowest;
    }
    
    public Cup getCupWithLabel(final int n) {
        if (label == n) {
            return this;
        }
        Cup current = next;
        while (current != this) {
            if (current.label == n) {
                return current;
            }
            current = current.next;
        }
        System.out.println("Couldn't find label " + n);
        return null;
    }
}
