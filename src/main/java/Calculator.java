import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A class for implementing a function that parses and evaluates the value of an expression.
 */
public class Calculator {
    /**
     * A function that parses and calculates the value of an expression containing numbers,
     * operator signs and parentheses. If the expression is written correctly, it calculates
     * the value of the expression; otherwise, it displays an error message. Supports variable
     * names and various functions. If there are variables, their values are requested from the user.
     * @param expr expression to be calculated
     * @return result of the entered expression
     */
    public static double calculate(final String expr) {
        Map<String,Double> var = new HashMap<>();
        System.out.println("Enter the number of variables:");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        for (int i = 0; i < n; i++) {
            String v = input.next();
            Double d = input.nextDouble();
            var.put(v,d);
        }
        
        return new Object() {
            int pos = -1, ch;

            /**
             * Function that reads the next character of the entered expression.
             */
            void nextCh() {
                ch = (++pos < expr.length()) ? expr.charAt(pos) : -1;
            }

            /**
             * Function that compares the current character with a specific character.
             * @param chToRead the character with which the current one is compared
             * @return match or mismatch of values
             */
            boolean read(int chToRead) {
                while (ch == ' ') nextCh();
                if (ch == chToRead) {
                    nextCh();
                    return true;
                }
                return false;
            }

            /**
             * Recursively evaluate and parse expressions into numbers, variables, exponentiation, and functions.
             * @return the result of the part of the entire expression consisting of numbers, variables,
             * exponentiation, and functions
             */
            double parseFactor() {
                if (read('+'))
                    return +parseFactor();
                if (read('-'))
                    return -parseFactor();

                double x;
                int startPos = this.pos;
                if (read('(')) {
                    x = parseExpr();
                    if (!read(')'))
                        throw new RuntimeException("Missing ')'");

                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.')
                        nextCh();
                    x = Double.parseDouble(expr.substring(startPos, this.pos));

                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z')
                        nextCh();
                    String funcOrVar = expr.substring(startPos, this.pos);
                    if (var.get(funcOrVar) != null)
                        x = var.get(funcOrVar);
                    else {
                        if (read('(')) {
                            x = parseExpr();
                            if (!read(')')) throw new RuntimeException("Missing ')' after argument to " + funcOrVar);
                        } else {
                            x = parseFactor();
                        }
                        x = switch (funcOrVar) {
                            case "sqrt" -> Math.sqrt(x);
                            case "sin" -> Math.sin(Math.toRadians(x));
                            case "cos" -> Math.cos(Math.toRadians(x));
                            case "tan" -> Math.tan(Math.toRadians(x));
                            case "ln" -> Math.log(x);
                            default -> throw new RuntimeException("Unknown function: " + funcOrVar);
                        };
                    }

                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (read('^')) x = Math.pow(x, parseFactor());

                return x;
            }

            /**
             * Recursively evaluate and parse an expression into multiplications and divisions.
             * @return the result of the part of the entire expression consisting of multiplications and divisions
             */
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (read('*'))
                        x *= parseFactor();
                    else if (read('/'))
                        x /= parseFactor();
                    else return x;
                }
            }

            /**
             * Recursively evaluate and parse an expression into sums and subtractions.
             * @return the result of the part of the entire expression consisting of sums and subtractions
             */
            double parseExpr() {
                double x = parseTerm();
                for (;;) {
                    if (read('+'))
                        x += parseTerm();
                    else if (read('-'))
                        x -= parseTerm();
                    else
                        return x;
                }
            }

            /**
             * Recursively evaluating the value of an expression.
             * @return result of expression
             */
            double parse() {
                nextCh();
                double x = parseExpr();
                if (pos < expr.length())
                    throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

        }.parse();
    }
}
