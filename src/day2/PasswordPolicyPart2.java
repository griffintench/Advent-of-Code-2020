package day2;

public class PasswordPolicyPart2 implements PasswordPolicy {

    private final int pos1; // zero-indexed
    
    private final int pos2;
    
    private final char letter;
    
    public PasswordPolicyPart2(final String policyString) {
        final String[] parts = policyString.split("[- ]");
        pos1 = Integer.parseInt(parts[0]) - 1;
        pos2 = Integer.parseInt(parts[1]) - 1;
        letter = parts[2].charAt(0);
    }
    
    @Override
    public boolean isValid(final String password) {
        final boolean pos1Contains = password.charAt(pos1) == letter;
        final boolean pos2Contains = password.charAt(pos2) == letter;
        
        return pos1Contains ^ pos2Contains;
    }

}
