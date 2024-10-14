# Laboration 2

## Environment & Tools
Windows 10 Home, IntelliJ Idea Ultimate 2023.2.1 , GIT 2.38.1.windows.1, Java 17.0.1, Apache Maven 3.9.6.

## Purpose
The purpose of this assignment is to create a calculator program while acting in accordance to Test Driven Development. Tests need to be developed that validate the implementation and its ability to calculate mathematical expressions in accordance to operator precedence.

Concrete goals:
* Create units tests that validate the functionality of the implementation, including parsing, calculation and order.
* Develop calculator application that supports addition, subtraction, multiplication, division, exponent and parentheses. The operators need to be performed in order according to operator precedence rules. 
* Correctly handle input both through command line arguments and input during runtime, space separation should also be handled by program parser.

## Procedures
### Early Unit Tests
The first step of the procedures is to create a few simple unit tests that can validate our implementation, this includes simple tests to validate that mathematical operations are performed correctly but also that input is parsed correctly. Unfortunately it is difficult to completely separate these conditions but it can be safely assumed that if input parsing is causing issues it should impact all tests while mathematical operator tests should impact only a select few. In addition to simple tests consisting of 'x operator y', test should be developed that attempt to input the expressions in expressions.json into the program. 

The rudimentary tests contain should consist of a simple expression using the desired operator and an expected outcome, the test is passed if the equality-assertion ```Assertions.assertEquals(expected, actual);``` returns true. As a development of the initial tests should also be developed that test how the operators are handled with both odd and even amounts of negative numbers, this ensures negative numbers are identified and handled correctly across all supported methods. Another simple test that verifies that space separation has no bearing on the result should also be created, in this case it compares the result of two identical expressions except one contains spaces between each character. 

In addition to the positive tests, negative tests should also be created that test how the program handles invalid input. This includes tests that attempt to input empty strings, strings that contain alphabetic characters and strings that contain invalid characters. The expected outcome of these tests should be that the program throws an exception and does not crash.

### Input handler
One of the stated goals is to handle user input both through runtime input but also through commandline arguments specified before execution, as such, a solution is required that can translate both types of input into one that can be used later by our calculation methods. First, a simple if statement can be implemented that checks if the user has provided any runtime arguments, if that is the case, the first argument is retrieved and saved to the `input` container. If no arguments are provided, they are supplied through input during runtime by prompting the user for a mathematical expression. The `Scanner` class and its `nextLine` method is used to retrieve the user's next input.

Although not necessary for any specified goals, a function can be implemented to recursively prompt the user for a new expression after each calculation unless the program is manually exited by typing exit, this makes is simpler to enter subsequent expressions. Now raw user input has been gathered by some means, the next step is to pass it to a parser. To this end the `Parser` class is created and an instance is initialized. But before the input can be passed to the parser, it needs to be ensured that the input is valid, this can be accomplished by creating an input validation class that checks if the input is going to create issues before it is passed to the parser. The validation class should check if the input is empty, if it contains any alphabetic characters and if it contains any invalid characters. If any of these conditions are met, the program should display an error message and prompt the user for a new expression.

Inside the `Parser` class a `parseInput` method is implemented which has the purpose to format the input and split it into usable operations if necessary. Before the expression itself is parsed it needs to be ensured space separators does not cause any issues while parsing the input string. The simplest solution is to remove all spaces as they do not serve any purpose other than readability, which is not a factor in this case. This can be accomplished with the use of the ``replace`` method, replacing all space characters with empty characters. Assuming the user has input a valid expression, it should now be ready to be parsed and calculated. As one of the stated goals is to ensure the implementation follows operator precedence rules, an order of operations needs to be established. Expressions within parentheses have the highest priority, internally ordered by expressions that are nested deepest to highest.

The parsing process is initialized by casting the input string to a char array, the desired result of this process is to remove all parentheses and identify the individual expression(s) contained within them while maintaining their original order. The idea is to calculate the inner expressions and replace parentheses in the outer expression with the result, to accomplish this a placeholder character can be appended in place of expressions within parentheses. To maintain proper order a tracking variable is initialized that measures how deeply nested the expression is by counting the amount of opening parentheses that were encountered before the expression in question. The current character in the iteration is then added to the string builder of that depth. As the length of the operations array acts as a final measure the maximum depth, a new `StringBuilder` should only be created when needed i.e. when a depth is encountered for the first time. When a closing parenthesis is encountered, the end of the expression can be marked and depth value decreased, indicating a return to the outer expression.

An example of how the process is intended to handle expressions.

``2 * ((1 + 2) - (2 + 5))`` -> An opening parenthesis is encountered, as such the inner expression needs to be handled before the outer expression: 

``2 * a`` is saved in outer operations array at depth (index) 0 and ``(1 + 2) - (2 + 5)`` remaining, depth is increased to 1.

Once again an opening parenthesis is encountered and a placeholder character is appended -> ``b - (2 - 5)``, depth value is increased once again, now represented by an index of 2.

The inner expression ``1 + 2`` is simply iterated over as no more opening parentheses are encountered, once a closing parenthesis is encountered a mark is appended to the end of the inner expression simply by not omitting the parenthesis character, it can now be retrieved from the operations array at index 2, ``Operations[2]`` should now return `1+2)`. Depth now decreases back to 1.

It is now possible to return to the inner expression ``b - (2 - 5)``, as a closing parenthesis has not been encountered yet at this depth, this expression does not yet have an end. Characters are appended to the expression until another opening parenthesis is encountered, the process of the last expression is then repeated. ``Operations[2]`` should now return `1+2)2+5)`.

The result of the parsing should be as follows:
``Operations[0] = 2*a``
``Operations[1] = b-c)``
``Operations[2] = 1+2)2+5)``

The parsed expressions can now be calculated in the reverse order they were added and replace each placeholder with the corresponding result as they are calculated.
To accomplish this a reverse iteration through the operations array can be performed which successively replaces placeholders with expression results.

### Calculator
When input has been parsed and split it into solvable expressions it should be passed as operation instructions to a calculator function. The string now needs to be converted to operators and operands which enables the actual calculation of the expression. The specific operators need to be identified and applied in proper order. The chosen method to accomplish this is to split the expression into a linked list of arguments where non-digit character are placed in their own containers. The exception to this is decimal points and subtraction operators without a number before it which imply that the following number is negative. Once the expression has been parsed, it is possible to run each calculation. The reason for using a linked list is ensuring index values that contains arguments that have already been handled are not left empty, instead arguments further into the list will be shifted into the empty spaces.

The highest priority supported operator in this program is the exponential operator ``^``. Operator precedence also dictates that operations are performed in left to right order, this makes the ``indexOf`` array method a suitable option find the position of `^` operators and respective operands because it returns the first instance of a match of the delimiter. Operands can be retrieved by using the give index value minus one and plus one respectively. Once an operator has been calculated it should be removed from the arguments along with the operands, then the result should take its place. `indexOf` returns -1 if no matching character is found, this means a while loop statement can be used to calculate exponential expressions until a value of -1 is returned.

The next highest priority operator is shared between multiplication ``*`` and division ``/``, if multiple instances are detected, left to right priority is once again applied. A similar method to the exponential operator can be applied here, though one must consider two different operators at once. One simple way to accomplish this is to compare the values returned by `indexOf`, the operator with the lowest index, unless it is -1, should be calculated first. This process can be repeated until both `indexOf` multiplication and division returns -1.

Finally, subtraction and addition can be calculated. These operations can be handled with a functionally identical block of code to the division and multiplication section, the only difference is the operators that are searched for.

Once all operators have been handled a final result should be reached which can be returned to the operations array.

Assuming the previous example is continued, the first operation to be handled would be ``2+5``, the operation should be split into the args 2, + and 5. Running ``indexOf`` in the addition/subtraction section should return args[1], which means addition should be performed with args[0] and args[2]. The final result of 7 replaces the original operator args[1], then, args[0] (2) is removed which shifts the other arguments to the left. Finally, args[1] (5) is removed from the list. Now, once all ``indexOf`` methods have returned -1, the return statement is reached. The return statement should return the result of the latest operation as all operators have been handled at this point.

The returned value replaces the last placeholder key that was added `c`, which result in: ``Operations[1] = b-7)``. The process should then continue with `1+2` which replaces `b`, and so on.

### Final Result
Once all inner expressions have been handled, ``Operations[0]`` can be calculated, which represents the final result. The expression can be sent to the calculator function, and the result of these operations can then be presented as the final result.

## Discussion
### Purpose Fulfillment
The final implementation of this assignment fulfills all stated goals. 

Unit tests have been created that validate all key functions of the implementation. All types of operators are tested individually ensuring they are detected during parsing and are calculated correctly with respective operands. Tests that validate results of nested expressions have some dependency of the standalone tests which is a weakness but also an unavoidable when calculating more complex expressions. There are also tests that validate that input with additional spaces return the same result as an identical expression without spaces. The current implementation passes all created tests and returns the expected values retrieved from ``expressions.json``.

A functioning calculator program has been created, it supports all operators and functions stated in the goals. The validity of the solution was tested with the unit tests that were previously highlighted in this discussion. The tests have ensured input parsing, calculation and operator precedence are handled in a correct manner.

Input both through command line argument and runtime instructions are handled correctly according to stated goals. The solution was verified by examining the result of both types of input, ensuring the same instructions return the same value regardless of where they were entered. Because unit tests cannot supply command line arguments, the testing was performed using the ``Application Run/Debug configuration`` function.

### Improvements
The methodology could have been improved in some ways, most importantly, more thought could have been put into the unit tests rather than the actual implementation. The implementation also focused too much on ensuring expected results were handled correctly, but not unexpected or unusual input. This issue has been mostly fixed with the complement changes, empty input and alphabetic characters should no longer stop the program, NumberFormatException has also been accounted for in the case that input used valid characters but wrong format. In summary, development should have had a larger focus on both developing tests and using them as a base for the future implementation. When it comes the actual implementation, nothing in particular needs to be changed as the program fulfills all stated goals and can handle som additional edge cases.

