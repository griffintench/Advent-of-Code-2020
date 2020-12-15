package day5;

public class BoardingPass {
    private final int row;
    private final int col;
    
    public BoardingPass(final String seat) {
        final String rowString = seat.substring(0, 7);
        final String colString = seat.substring(7);
        
        row = getSeatNumber(rowString, 'B');
        col = getSeatNumber(colString, 'R');
    }
    
    private int getSeatNumber(final String str, final char one) {
        int seatNumber = 0;
        
        int powerOfTwo = 1;
        for (int i = str.length() - 1; i >= 0; --i) {
            if(str.charAt(i) == one) {
                seatNumber += powerOfTwo;
            }
            powerOfTwo *= 2;
        }
        
        return seatNumber;
    }
    
    public int getSeatId() {
        return row * 8 + col;
    }
}
