package day19;

import java.util.List;
import java.util.Map;

public interface Rule {
    
    // returns the index of the first char after the check
    public List<Integer> checkMessage(final String message, final Map<Integer, Rule> ruleMap);
    
    public static void createRule(final String ruleString, final Map<Integer, Rule> ruleMap) {
        final String[] split1 = ruleString.split(": ");
        final int ruleIndex = Integer.parseInt(split1[0]);
        Rule rule;
        if (split1[1].charAt(0) == '"') {
            rule = new BaseRule(split1[1].charAt(1));
        } else {
            rule = new CompoundRule(split1[1]);
        }
        ruleMap.put(ruleIndex, rule);
    }
}
