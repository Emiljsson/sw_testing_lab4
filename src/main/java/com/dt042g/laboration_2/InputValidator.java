package com.dt042g.laboration_2;

import java.util.Arrays;
import java.util.List;

/**
 * Validates input string for calculator program.
 * @author Emil Jönsson
 */
public class InputValidator {

    private static final List<Character> validCharacters = Arrays.asList('+', '-', '*', '/', '(', ')', '^', '.');

    /**
     * Validates input string, checking for empty input, invalid characters and mismatched parentheses.
     * @param input input string to validate.
     * @return true if input is valid, false otherwise.
     */
    static boolean validateInput(String input) {
        input = input.replace(" ", "");
        // Check if input is empty.
        if (input.isEmpty()) {
            System.out.println("Error: Input is empty.");
            return false;
        }
        int openParentheses = 0;
        int closeParentheses = 0;

        char[] inputArray = input.toCharArray();
        for (char c : inputArray) {
            // Check if input contains invalid characters.
            if (!Character.isDigit(c) && !validCharacters.contains(c)){
                System.out.println("Error: Input contains invalid characters.");
                return false;
            }
            if (c == '(') {
                openParentheses++;
            } else if (c == ')') {
                closeParentheses++;
            }
        }

        if (openParentheses != closeParentheses) {
            System.out.println("Error: Mismatched parentheses.");
            return false;
        }
        return true;
    }
}
