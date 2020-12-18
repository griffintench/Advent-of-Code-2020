package day18;

public class Expression {
    
    private static enum Operation {
        ADDITION, MULTIPLICATION, NOOP;
    }
    
    private final String expressionString; // for debugging purposes
    
    private final Operation operation;
    
    private final Expression operand1;
    
    private final Expression operand2;
    
    private final long value;
    
    public Expression(final String expressionString) {
        this.expressionString = expressionString;
        final int length = expressionString.length();
        if (!expressionString.contains(" ")) {
            operation = Operation.NOOP;
            operand1 = null;
            operand2 = null;
            value = Integer.parseInt(expressionString);
        } else if (expressionString.charAt(length - 1) == ')') {
            // find corresponding open paren
            int openParenIndex = length - 1;
            int closeParenCount = 0;
            while (openParenIndex >= 0 && (closeParenCount != 1 || expressionString.charAt(openParenIndex) != '(')) {
                final char c = expressionString.charAt(openParenIndex);
                if (c== '(') {
                    --closeParenCount;
                } else if (c == ')') {
                    ++closeParenCount;
                }
                --openParenIndex;
            }
            if (openParenIndex <= 0) {
                // this is so cheating but w/e
                final String actualExpressionString = expressionString.substring(1, length - 1);
                final Expression actualExpression = new Expression(actualExpressionString);
                operation = Operation.NOOP;
                operand1 = null;
                operand2 = null;
                value = actualExpression.evaluate();
            } else {
                operation = expressionString.charAt(openParenIndex - 2) == '+' ? Operation.ADDITION : Operation.MULTIPLICATION;
                operand1 = new Expression(expressionString.substring(0, openParenIndex - 3));
                operand2 = new Expression(expressionString.substring(openParenIndex + 1, length - 1));
                value = -1;
            }
        } else {
            // find last space
            int lastSpaceIndex = length - 1;
            while (expressionString.charAt(lastSpaceIndex) != ' ') {
                --lastSpaceIndex;
            }
            operation = expressionString.charAt(lastSpaceIndex - 1) == '+' ? Operation.ADDITION : Operation.MULTIPLICATION;
            operand1 = new Expression(expressionString.substring(0, lastSpaceIndex - 2));
            operand2 = new Expression(expressionString.substring(lastSpaceIndex + 1));
            value = -1;
        }
    }
    
    public long evaluate() {
        if (operation == Operation.NOOP) {
            return value;
        } else if (operation == Operation.ADDITION) {
            return operand1.evaluate() + operand2.evaluate();
        } else {
            return operand1.evaluate() * operand2.evaluate();
        }
    }
    
    @Override
    public String toString() {
        return expressionString;
    }
    
}
