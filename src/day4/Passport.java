package day4;

import java.util.List;

public class Passport {
    private String birthYear;
    private String issueYear;
    private String expirationYear;
    private String height;
    private String hairColor;
    private String eyeColor;
    private String passportId;
    private String countryId;
    
    public Passport(final List<String> inputs) {
        for (final String input : inputs) {
            final String[] fields = input.split(" ");
            for (final String field : fields) {
                final String[] splitField = field.split(":");
                final String entry = splitField[1];
                if(splitField[0].equals("byr")) {
                    birthYear = entry;
                } else if(splitField[0].equals("iyr")) {
                    issueYear = entry;
                } else if(splitField[0].equals("eyr")) {
                    expirationYear = entry;
                } else if(splitField[0].equals("hgt")) {
                    height = entry;
                } else if(splitField[0].equals("hcl")) {
                    hairColor = entry;
                } else if(splitField[0].equals("ecl")) {
                    eyeColor = entry;
                } else if(splitField[0].equals("pid")) {
                    passportId = entry;
                } else if(splitField[0].equals("cid")) {
                    countryId = entry;
                } else {
                    System.out.println("Invalid field name: " + splitField[0]);
                }
            }
        }
    }
    
    public boolean isValidNotStrict() {
        return birthYear != null && issueYear != null && expirationYear != null && height != null
                && hairColor != null && eyeColor != null && passportId != null;
    }
    
    public boolean isValidStrict() {
        return isBirthYearValid()
                && isIssueYearValid()
                && isExpirationYearValid()
                && isHeightValid()
                && isHairColorValid()
                && isEyeColorValid()
                && isPassportIdValid();
    }
    
    private boolean isBirthYearValid() {
        if (birthYear == null) {
            return false;
        }
        final int birthYearInt = Integer.parseInt(birthYear);
        return birthYearInt >= 1920 && birthYearInt <= 2002;
    }
    
    private boolean isIssueYearValid() {
        if (issueYear == null) {
            return false;
        }
        final int issueYearInt = Integer.parseInt(issueYear);
        return issueYearInt >= 2010 && issueYearInt <= 2020;
    }
    
    private boolean isExpirationYearValid() {
        if (expirationYear == null) {
            return false;
        }
        final int expirationYearInt = Integer.parseInt(expirationYear);
        return expirationYearInt >= 2020 && expirationYearInt <= 2030;
    }
    
    private boolean isHeightValid() {
        if (height == null) {
            return false;
        }
        final String lastTwoChars = height.substring(height.length() - 2);
        final boolean isCm = lastTwoChars.equals("cm");
        final boolean isIn = isCm ? false : lastTwoChars.equals("in");
        if (!isCm && !isIn) {
            return false;
        }
        final int heightInt = Integer.parseInt(height.substring(0, height.length() - 2));
        if (isCm) {
            return heightInt >= 150 && heightInt <= 193;
        } else {
            return heightInt >= 59 && heightInt <= 76;
        }
    }
    
    private boolean isHairColorValid() {
        if (hairColor == null) {
            return false;
        }
        if (hairColor.charAt(0) != '#') {
            return false;
        }
        if (hairColor.length() != 7) {
            return false;
        }
        for (int i = 1; i < 7; ++i) {
            char c = hairColor.charAt(i);
            if ((c < '0' || c > '9') && (c < 'a' || c > 'f')) {
                return false;
            }
        }
        return true;
    }
    
    private static final String[] validEyeColors = { "amb", "blu", "brn", "gry", "grn", "hzl", "oth" };
    private boolean isEyeColorValid() {
        if (eyeColor == null) {
            return false;
        }
        for (int i = 0; i < validEyeColors.length; ++i) {
            if (validEyeColors[i].equals(eyeColor)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isPassportIdValid() {
        if (passportId == null) {
            return false;
        }
        if (passportId.length() != 9) {
            return false;
        }
        for (int i = 0; i < 9; ++i) {
            char c = passportId.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
