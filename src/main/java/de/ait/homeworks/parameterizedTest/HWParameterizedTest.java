package de.ait.homeworks.parameterizedTest;


public class HWParameterizedTest {

    public int add(int a, int b) {
        return a + b;
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    public double divide(double a, double b) throws ArithmeticException {
        if (b == 0.0) {
            throw new ArithmeticException("Нельзя делить на ноль");
        }
        return a / b;
    }

    public long getLength(String str) {
        if (str == null) {
            return -1;
        }
        return str.length();
    }

    public boolean containsWord(String text, String word) {
        if (text == null || word == null) {
            return false;
        }
        if(text.isEmpty() || word.isEmpty()) {
            return false;
        }
        return text.contains(word);
    }
}
