package com.dt042g.laboration_2;

import java.util.Objects;
import java.util.Scanner;

/**
 * Starting point for calculator program, handles initial and repeating input.
 * @author Emil Jönsson
 */
public class Lab2 {

    /**
     * Initializes parser and passes program arguments, prompts user for input if arguments are empty.
     * @param args command line arguments.
     */
    static public void main(String[] args) {

        // Console messages.
        final String promptMessage = "Enter a mathematical expression to calculate or 'exit' to close the program.";
        final String exitMessage = "Exiting...";

        String input;

        Scanner scanner = new Scanner(System.in);

        if (args.length == 0) { // Checks if arguments are empty.
            do {
                System.out.println(promptMessage);
                input = scanner.nextLine();
                if (Objects.equals(input, "exit")) {
                    System.out.println(exitMessage);
                    return;
                }
            } while (!InputValidator.validateInput(input));

        } else { // Use args as input.
            input = args[0];
            if (!InputValidator.validateInput(input)) {
                do {
                    System.out.println(promptMessage);
                    input = scanner.nextLine();
                    if (Objects.equals(input, "exit")) {
                        System.out.println(exitMessage);
                        return;
                    }
                } while (!InputValidator.validateInput(input));
            }
        }

        Parser parser = new Parser();

        // Executes recursively until manual exit.
        while(true) {
            try {
                System.out.printf("Final result: %s = %s%n\n", input, parser.parseInput(input));
            } catch (NumberFormatException e) {
                System.out.println("Error: NumberFormatException.");
            }
            do {
                System.out.println(promptMessage);
                input = scanner.nextLine();
                if (Objects.equals(input, "exit")) {
                    System.out.println(exitMessage);
                    return;
                }
            } while (!InputValidator.validateInput(input));

        }
    }
}
