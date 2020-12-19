package day19;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseRule implements Rule {

    private final char c;
    
    public BaseRule(final char c) {
        this.c = c;
    }
    
    @Override
    public List<Integer> checkMessage(final String message, final Map<Integer, Rule> ruleMap) {
        final List<Integer> indices = new ArrayList<>();
        if (!message.isEmpty() && message.charAt(0) == c) {
            indices.add(1);
        }
        return indices;
    }

}
