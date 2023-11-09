public class Calculator {
    public static double calculate(final String expr) {
        return new Object() {
            int pos = -1, ch;

            void nextCh() {
                ch = (++pos < expr.length()) ? expr.charAt(pos) : -1;
            }

            boolean read(int chToRead) {
                while (ch == ' ') nextCh();
                if (ch == chToRead) {
                    nextCh();
                    return true;
                }
                return false;
            }

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
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (read('^')) x = Math.pow(x, parseFactor());

                return x;
            }

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
