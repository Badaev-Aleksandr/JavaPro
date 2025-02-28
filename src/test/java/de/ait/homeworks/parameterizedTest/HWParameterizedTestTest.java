package de.ait.homeworks.parameterizedTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HWParameterizedTestTest {

    private HWParameterizedTest hwParameterizedTest;

    @BeforeEach
    public void setUp() {
        hwParameterizedTest = new HWParameterizedTest();
    }

    @ParameterizedTest
    @DisplayName("разные целые числа ")
    @CsvSource({
            "5,5,10",
            "15,15,30",
            "-10,-10,-20",
            "0,-1,-1"
    })
    void testAdd(int a, int b, int expected) {
        assertEquals(expected, hwParameterizedTest.add(a, b));
    }

    @ParameterizedTest
    @DisplayName("Проверка на четность целых четных чисел")
    @ValueSource(ints = {0, 2, 4, 6, 8, 10})
    void testIsEvenPositive(int number) {
        assertTrue(hwParameterizedTest.isEven(number));
    }

    @ParameterizedTest
    @DisplayName("Проверка на четность целых нечетных чисел")
    @ValueSource(ints = {1, 3, 5, 7, 9, 11, 13})
    void testIsEvenNegative(int number) {
        assertFalse(hwParameterizedTest.isEven(number));
    }

    @ParameterizedTest
    @DisplayName("Проверка деления чисел")
    @CsvSource({
            "0.0,15.5,0",
            "-9,3,-3",
            "18,6,3",
            "7,2,3.5"
    })
    void testDivide(double a, double b, double expected) {
        assertEquals(expected, hwParameterizedTest.divide(a, b));
    }

    @ParameterizedTest
    @DisplayName("проверка деления на ноль")
    @CsvSource({
            "11.11,0,Нельзя делить на ноль"
    })
    void testDivideByZero(double a, double b, String expected) {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            hwParameterizedTest.divide(a, b);
        });
        assertEquals(expected, exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Проверка длинны String, а так же же null и пустой строки")
    @CsvSource({
            "Hello,5",
            "Hello World,11",
            "'', 0",
            ",-1",
            "'10', 2"
    })
    void testGetLength(String str, long expected) {
        assertEquals(expected, hwParameterizedTest.getLength(str));
    }

    @ParameterizedTest
    @DisplayName("Проверка на содержание заданного параметра в строке, позитивные")
    @CsvSource({
            "Hello Alex, lo",
            "Hello Alex, A",
            "Hello Alex, lex",
            "Hello Alex, llo ",
            "'', ''"
    })
    void testContainsWordPositive(String str, String word) {
        assertTrue(hwParameterizedTest.containsWord(str, word));
    }

    @ParameterizedTest
    @DisplayName("Проверка на содержание заданного параметра в строке, негативные")
    @CsvSource({
            "Hello Alex, losa",
            "Hello Alex, Allex",
            "Hello Alex, Alexa",
            "Hello Alex, llo Alix "
    })
    void testContainsWordNegative(String str, String word) {
        assertFalse(hwParameterizedTest.containsWord(str, word));
    }

    @ParameterizedTest
    @DisplayName("Проверка на null ")
    @CsvSource({
            "Hello Alex,",
            ", Allex",
    })
    void testContainsWordByNull(String str, String word) {
        assertFalse(hwParameterizedTest.containsWord(str, word));
    }
}
