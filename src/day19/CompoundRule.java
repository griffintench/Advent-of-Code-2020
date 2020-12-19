package day19;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompoundRule implements Rule {

    private final List<List<Integer>> rules;
    
    public CompoundRule(final String ruleString) {
        rules = new ArrayList<>();
        
        if (!ruleString.isEmpty()) {
            final String[] splitRules = ruleString.split(" \\| ");
            for (int i = 0; i < splitRules.length; ++i) {
                final List<Integer> rule = new ArrayList<>();
                
                final String[] splitRule = splitRules[i].split(" ");
                for (int j = 0; j < splitRule.length; ++j) {
                    rule.add(Integer.parseInt(splitRule[j]));
                }
                
                rules.add(rule);
            }
        }
        
    }
    
    @Override
    public List<Integer> checkMessage(final String message, final Map<Integer, Rule> ruleMap) {
        final List<Integer> successfulIndices = new ArrayList<>();
        final int numberOfRules = rules.size();
        if (numberOfRules == 0) {
            successfulIndices.add(0);
        } else if (numberOfRules == 1) {
            final List<Integer> ruleIndices = rules.get(0);
            
            if (ruleIndices.size() != 0) {
                
                final List<Integer> firstRuleSuccessfulIndices = ruleMap.get(ruleIndices.get(0)).checkMessage(message, ruleMap);
                
                final StringBuilder sb = new StringBuilder();
                for (int i = 1; i < ruleIndices.size(); ++i) {
                    sb.append(ruleIndices.get(i));
                    if (i < ruleIndices.size() - 1) {
                        sb.append(" ");
                    }
                }
                final Rule reducedRule = new CompoundRule(sb.toString());
                
                for (int i = 0; i < firstRuleSuccessfulIndices.size(); ++i) {
                    final int firstRuleSuccessfulIndex = firstRuleSuccessfulIndices.get(i);
                    final List<Integer> moreIndices = reducedRule.checkMessage(message.substring(firstRuleSuccessfulIndices.get(i)), ruleMap);
                    for (int j = 0; j < moreIndices.size(); ++j) {
                        successfulIndices.add(firstRuleSuccessfulIndex + moreIndices.get(j));
                    }
                }
            }
        } else {
            for (int i = 0; i < numberOfRules; ++i) {
                final List<Integer> currentRuleInts = rules.get(i);
                final StringBuilder sb = new StringBuilder();
                for (int j = 0; j < currentRuleInts.size(); ++j) {
                    sb.append(currentRuleInts.get(j));
                    if (j < currentRuleInts.size() - 1) {
                        sb.append(" ");
                    }
                }
                final Rule currentRule = new CompoundRule(sb.toString());
                successfulIndices.addAll(currentRule.checkMessage(message, ruleMap));
            }
        }
        
        return successfulIndices;
    }

}
