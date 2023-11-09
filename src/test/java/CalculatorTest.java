import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @org.junit.jupiter.api.Test
    void calculateTest1() {
        String inputData = "0";
        System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
        Double ans = Calculator.calculate("10^2+10*5-3/2");
        assertEquals(148.5, ans);
    }

    @org.junit.jupiter.api.Test
    void calculateTest2() {
        String inputData = "0";
        System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
        Double ans = Calculator.calculate("sin(30) + cos(60) - tan(45) * ln(5)");

        Double exp = Math.sin(Math.toRadians(30)) + Math.cos(Math.toRadians(60)) - Math.tan(Math.toRadians(45)) * Math.log(5);
        assertEquals(exp, ans);
    }

    @org.junit.jupiter.api.Test
    void calculateTest3() {
        String inputData = "0";
        System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
        Double ans = Calculator.calculate("(2 + 2) * 2");

        assertEquals(8, ans);
    }

    @org.junit.jupiter.api.Test
    void calculateTest4() {
        String inputData = "3 a 1 b 5 c 6";
        System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
        Double ans = Calculator.calculate("a + b * c");
        assertEquals(31, ans);
    }

    @org.junit.jupiter.api.Test
    void calculateTest5() {
        String inputData = "3 a 4 b 6 c 7";
        System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
        Double ans = Calculator.calculate("-sqrt(a) * b^3 + 4*c");
        assertEquals(-404, ans);
    }
}