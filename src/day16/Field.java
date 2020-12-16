package day16;

public class Field {
    
    private static class Range {
        private final int min;
        private final int max;
        public Range(final String rangeText) {
            final String[] splitText = rangeText.split("-");
            min = Integer.parseInt(splitText[0]);
            max = Integer.parseInt(splitText[1]);
        }
        public boolean isWithinRange(final int x) {
            return x >= min && x <= max;
        }
    }
    
    private final String fieldName;
    private final Range[] possibleRanges;
    
    public Field(final String ruleText) {
        final String[] splitRuleText = ruleText.split("(: )|( or )");
        fieldName = splitRuleText[0];
        possibleRanges = new Range[2];
        possibleRanges[0] = new Range(splitRuleText[1]);
        possibleRanges[1] = new Range(splitRuleText[2]);
    }
    
    public boolean intMatchesRange(final int x) {
        for (int i = 0; i < possibleRanges.length; ++i) {
            if (possibleRanges[i].isWithinRange(x)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isDepartureField() {
        return fieldName.contains("departure");
    }
    
    public String getName() {
        return fieldName;
    }
}
