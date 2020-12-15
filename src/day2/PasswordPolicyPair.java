package day2;

public class PasswordPolicyPair {

    private final PasswordPolicy policy;
    
    private final String password;
    
    public PasswordPolicyPair(final String unseparatedPair, final boolean part1) {
        final String[] parts = unseparatedPair.split(":");
        policy = part1 ? new PasswordPolicyPart1(parts[0]) : new PasswordPolicyPart2(parts[0]);
        password = parts[1].trim();
    }
    
    public boolean isValid() {
        return policy.isValid(password);
    }
    
}
