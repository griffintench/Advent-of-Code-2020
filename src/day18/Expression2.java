package day18;

import java.util.ArrayList;
import java.util.List;

public class Expression2 {
    private static enum Operation {
        ADDITION, MULTIPLICATION;
    }
    
    private final String expressionString; // for debugging purposes
    
    private final List<Operation> operations;
    
    private final List<Expression2> operands;
    
    private final long value;
    
    public Expression2(final String expressionString) {
        
        if (expressionString.equals("4 * (5 + 6)")) {
            int y = 5;
            ++y;
        }
        
        this.expressionString = expressionString;
        this.operations = new ArrayList<>();
        this.operands = new ArrayList<>();
        
        if (!expressionString.contains(" ")) {
            // no spaces, must be a single number
            
            this.value = Long.parseLong(expressionString);
        }
        else {
            int i = 0;
            while (i < expressionString.length()) {
                if (expressionString.charAt(i) == '(') {
                    int openParenIndex = i;
                    int openParenCount = 0;
                    while (openParenCount != 1 || expressionString.charAt(i) != ')') {
                        if (expressionString.charAt(i) == '(') {
                            ++openParenCount;
                        } else if (expressionString.charAt(i) == ')') {
                            --openParenCount;
                        }
                        ++i;
                    }
                    final String newExpressionString = expressionString.substring(openParenIndex + 1, i);
                    final Expression2 expressionWithinParens = new Expression2(newExpressionString);
                    operands.add(expressionWithinParens);
                    i += 2;
                } else if (expressionString.charAt(i) >= '1' && expressionString.charAt(i) <= '9') {
                    final int numberStartIndex = i;
                    while (i < expressionString.length() && expressionString.charAt(i) != ' ') {
                        ++i;
                    }
                    final String numberString = expressionString.substring(numberStartIndex, i);
                    operands.add(new Expression2(numberString));
                    ++i;
                } else {
                    // operation
                    operations.add(expressionString.charAt(i) == '+' ? Operation.ADDITION : Operation.MULTIPLICATION);
                    i += 2;
                }
            }
            this.value = -1;
        }
        
        if (operands.size() < operations.size()) {
            // big yikes
            // debug here
            int x = 5;
            ++x;
        }
    }
    
    private Expression2(final long value) {
        this.expressionString = Long.toString(value);
        this.operations = new ArrayList<>();
        this.operands = new ArrayList<>();
        this.value = value;
    }
    
    public long evaluate() {
        if (this.value == -1) {
            if (this.operations.isEmpty()) {
                return this.operands.get(0).evaluate();
            }
            final int firstAddition = findFirstAddition();
            if (firstAddition == -1) {
                // no addition; compute first multiplication
                final long product = operands.get(0).evaluate() * operands.get(1).evaluate();
                operands.remove(0);
                operands.remove(0);
                operands.add(0, new Expression2(product));
                operations.remove(0);
            } else {
                final long sum = operands.get(firstAddition).evaluate() + operands.get(firstAddition + 1).evaluate();
                operands.remove(firstAddition);
                operands.remove(firstAddition);
                operands.add(firstAddition, new Expression2(sum));
                operations.remove(firstAddition);
            }
            return this.evaluate();
        } else {
            return this.value;
        }
    }
    
    private int findFirstAddition() {
        for (int i = 0; i < operations.size(); ++i) {
            if (operations.get(i) == Operation.ADDITION) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public String toString() {
        return expressionString;
    }
}
