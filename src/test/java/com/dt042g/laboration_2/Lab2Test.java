package com.dt042g.laboration_2;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.Map;

/**
 * Class for testing all aspects of the calculator implementation.
 * @author Emil Jönsson
 */
public class Lab2Test {
    Parser parser = new Parser();
    private Map<String, Long> jsonExpressions;

    /**
     * Tests if empty input is correctly identified and handled.
     */
    @Test
    public void testEmptyInput() {
        String input = "";
        Assertions.assertFalse(InputValidator.validateInput(input));
    }

    /**
     * Tests if input with invalid characters is correctly identified and handled.
     */
    @Test
    public void testInvalidCharacters() {
        String input = "a";
        Assertions.assertFalse(InputValidator.validateInput(input));
    }

    /**
     * Tests if input with mismatched parentheses is correctly identified and handled.
     */
    @Test
    public void testMismatchedParentheses() {
        String input = "1 + (2";
        Assertions.assertFalse(InputValidator.validateInput(input));
    }

    /**
     * Tests if spaces are correctly trimmed from input.
     */
    @Test
    public void testSpaces() {
        double actual = parser.parseInput("1 + 2");
        double expected = parser.parseInput("1+2");
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Tests if input with decimal numbers is correctly parsed without exceptions.
     */
    @Test
    public void testDecimalInput() {
        String input = "1.5 + 2.5";
        double expected = 4.0;
        double actual = parser.parseInput(input);
        Assertions.assertTrue(InputValidator.validateInput(input));
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Tests if zero division is handled without exceptions.
     */
    @Test
    public void testZeroDivision() {
        String input = "1 / 0";
        double expected = Double.POSITIVE_INFINITY;
        double actual = parser.parseInput(input);
        Assertions.assertTrue(InputValidator.validateInput(input));
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Reads expressions from expressions.json file and stores them in a Map.
     */
    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        JSONParser JSONparser = new JSONParser();
        try {
            Object object = JSONparser.parse(new FileReader("lab2_expressions/expressions.json"));
            JSONObject jsonObject = (JSONObject) object;
            this.jsonExpressions = (Map<String, Long>) jsonObject;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns a JSON expression from the expressions.json file.
     * @param index index of the expression to return.
     * @return JSON expression as a Map.Entry.
     */
    public Map.Entry<String, Long> getJsonExpression(int index) {
        return jsonExpressions.entrySet().stream().toList().get(index);
    }

    /**
     * Tests if addition is correctly handled.
     */
    @Test
    public void testJsonAddition() {
        Map.Entry<String, Long> expression = getJsonExpression(0);
            Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                    "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if subtraction is correctly handled.
     */
    @Test
    public void testJsonSubtraction() {
        Map.Entry<String, Long> expression = getJsonExpression(1);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if division is correctly handled.
     */
    @Test
    public void testJsonDivision() {
        Map.Entry<String, Long> expression = getJsonExpression(2);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if multiplication is correctly handled.
     */
    @Test
    public void testJsonMultiplication() {
        Map.Entry<String, Long> expression = getJsonExpression(3);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if exponentiation is correctly handled.
     */
    @Test
    public void testJsonExponentiation() {
        Map.Entry<String, Long> expression = getJsonExpression(4);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if exponentiation is correctly handled with multiple digits.
     */
    @Test
    public void testJsonExponentiationMultipleDigits() {
        Map.Entry<String, Long> expression = getJsonExpression(5);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if addition is correctly handled with multiple digits and operands.
     */
    @Test
    public void testJsonAdditionMultipleDigits() {
        Map.Entry<String, Long> expression = getJsonExpression(6);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if excess spaces are correctly trimmed from input.
     */
    @Test
    public void testJsonAdditionWithSpaces() {
        Map.Entry<String, Long> expression = getJsonExpression(7);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if addition and multiplication are correctly prioritized.
     */
    @Test
    public void testJsonAdditionAndMultiplication() {
        Map.Entry<String, Long> expression = getJsonExpression(8);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if addition, subtraction and multiplication are correctly prioritized.
     */
    @Test
    public void testJsonAdditionSubtractionMultiplication() {
        Map.Entry<String, Long> expression = getJsonExpression(9);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if exponentiation is correctly prioritized over subtraction.
     */
    @Test
    public void testJsonExponentiationSubtraction() {
        Map.Entry<String, Long> expression = getJsonExpression(10);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if negative numbers are correctly handled in multiplication.
     */
    @Test
    public void testJsonNegativeMultiplication() {
        Map.Entry<String, Long> expression = getJsonExpression(11);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if negative numbers are correctly handled in division and multiplication and in correct order.
     */
    @Test
    public void testJsonDivisionMultiplication() {
        Map.Entry<String, Long> expression = getJsonExpression(12);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if multiplication is correctly prioritized over addition and subtraction,
     * and if division/multiplication are prioritized left to right.
     */
    @Test
    public void testOrderOfOperations() {
        Map.Entry<String, Long> expression = getJsonExpression(13);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }

    /**
     * Tests if exponentiation is correctly prioritized over other operations.
     */
    @Test
    public void testOrderOfOperationsWithExponentiation() {
        Map.Entry<String, Long> expression = getJsonExpression(14);
        Assertions.assertEquals(parser.parseInput(expression.getKey()), (double) expression.getValue(),
                "Assertion failed for equation: " + expression.getKey());
    }
}