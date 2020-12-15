package day2;

public class PasswordPolicyPart1 implements PasswordPolicy {

    private final int minOccurrences;
    
    private final int maxOccurrences;
    
    private final char letter;
    
    public PasswordPolicyPart1(final String policyString) {
        final String[] parts = policyString.split("[- ]");
        minOccurrences = Integer.parseInt(parts[0]);
        maxOccurrences = Integer.parseInt(parts[1]);
        letter = parts[2].charAt(0);
    }
    
    @Override
    public boolean isValid(final String password) {
        int count = 0;
        for(int i = 0; i < password.length(); ++i) {
            if(password.charAt(i) == letter) {
                ++count;
                if(count > maxOccurrences) {
                    return false;
                }
            }
        }
        return count >= minOccurrences;
    }
    
}
