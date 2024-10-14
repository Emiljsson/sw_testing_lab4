package com.dt042g.laboration_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles parsing and calculation mathematical expression input
 * @author Emil Jönsson
 */
public class Parser {

    /**
     * Parses raw input into prioritized list of operations, then executes them in order.
     * @param input raw user input as string value.
     */
    public double parseInput(String input) throws NumberFormatException {

        double result = 0; // Calculation result container.
        int group = 0; // Nested expression depth tracker.
        char key = 'a'; // Initial placeholder char value.

        // Raw operation values container.
        List<StringBuilder> operations = new ArrayList<>();

        // Clear empty spaces.
        input = input.replace(" ", "");

        // Initial StringBuilder for outer expression.
        operations.add(group, new StringBuilder());

        for (char c: input.toCharArray()) {
            if (c == '(') {
                // Appends placeholder to outer expression and increases depth.
                operations.get(group).append(key);
                key += 1;
                group++;
                // Creates new builder for current depth if one does not already exist.
                if (group != operations.size()-1) {
                    operations.add(group, new StringBuilder());
                }
            }
            else {
                // Appends character to expression.
                operations.get(group).append(c);
                // Traverse back to outer expression.
                if (c == ')') {
                    group--;
                }
            }
        }
        // Restores key value to last used.
        key--;

        // Iterates over operations container, level by level, calculating deepest nested expressions first.
        for (int i = operations.size()-1; i >= 0; i--) {
            // Splits operations at divider.
            ArrayList<String> level = new ArrayList<>(Arrays.asList(operations.get(i).toString().split("\\)")));

            while (!level.isEmpty()) { // Calculates operations for level until empty.
                String operation = level.get(level.size()-1);
                result = calcExpression(operation);

                // Applies latest result to current key value placeholder.
                if (i != 0) {
                    for (int j = i; j >= 0; j--) {
                        // Replace placeholder and break if key is found.
                        if (operations.get(j).toString().contains(String.valueOf(key))) {
                            operations.set(j, new StringBuilder().append(
                                    operations.get(j).toString().replace(String.valueOf(key), String.valueOf(result)))
                            );
                            key--;
                            break;
                        }
                    }
                }
                // Removes spent operations.
                level.remove(operation);
            }
        }
        // Final result.
        return result;
    }

    /**
     * Parses operation instructions according to operator precedence rules and returns its result.
     * @param input operation as formatted string.
     * @return result of operation as double value.
     */
    private double calcExpression(String input) {
        List<StringBuilder> argBuilders = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (char c: input.toCharArray()) {
            if (!Character.isDigit(c)) {
                // Handler decimal points and negative numbers.
                if (c == '.' || (c == '-' && builder.isEmpty())) {
                    builder.append(c);
                } else {
                    // Save previous numbers.
                    argBuilders.add(i, builder);
                    i++;

                    // Create container for operator.
                    builder = new StringBuilder();
                    builder.append(c);

                    // Create container for next number.
                    argBuilders.add(i, builder);
                    builder = new StringBuilder();
                    i++;
                }
            } else {
                // Append number to builder.
                builder.append(c);
            }
        }
        // Adds final number
        argBuilders.add(i, builder);

        // Passes builders to finalized string argument list.
        LinkedList<String> args = new LinkedList<>();
        argBuilders.forEach(arg -> args.addLast(arg.toString()));

        // Sets initial result to first argument in case expression is a single negative number.
        double result = Float.parseFloat(args.get(0));

        int index;
        double a;
        double b;

        // Handle exponents.
        // Loops until no matches are encountered.
        while ((index = args.indexOf("^")) != -1) {
            a = Double.parseDouble(args.get(index-1));
            b = Double.parseDouble(args.get(index+1));

            result = Math.pow(a, b);

            // Replace operator with result and remove operands.
            args.set(index, String.valueOf(result));
            args.remove(index-1);
            args.remove(index);
        }

        // Handle division and multiplication.
        // Loops until no matches are encountered.
        while (args.contains("/") || args.contains("*")) {
            int divisionIndex = args.indexOf("/");
            int multiplicationIndex = args.indexOf("*");

            // Perform division if it appears first or if there's no multiplication.
            if ((divisionIndex < multiplicationIndex && divisionIndex != -1) || multiplicationIndex == -1) {
                index = divisionIndex;
                a = Double.parseDouble(args.get(index-1));
                b = Double.parseDouble(args.get(index+1));

                result = a / b;

            } else { // Perform multiplication.
                index = multiplicationIndex;
                a = Double.parseDouble(args.get(index-1));
                b = Double.parseDouble(args.get(index+1));

                result = a * b;
            }
            // Replace operator with result and remove operands.
            args.set(index, String.valueOf(result));
            args.remove(index - 1);
            args.remove(index);
        }

        // Handle addition and subtraction.
        // Loops until no matches are encountered.
        while (args.contains("-") || args.contains("+")) {
            int subtractionIndex = args.indexOf("-");
            int additionIndex = args.indexOf("+");

            // Perform subtraction if it appears first or if there's no addition.
            if ((subtractionIndex < additionIndex && subtractionIndex != -1) || additionIndex == -1) {
                index = subtractionIndex;
                a = Double.parseDouble(args.get(index-1));
                b = Double.parseDouble(args.get(index+1));

                result = a - b;

            } else { // Perform addition.
                index = additionIndex;
                a = Double.parseDouble(args.get(index-1));
                b = Double.parseDouble(args.get(index+1));

                result = a + b;
            }
            // Replace operator with result and remove operands.
            args.set(index, String.valueOf(result));
            args.remove(index - 1);
            args.remove(index);
        }

        return result;
    }
}


