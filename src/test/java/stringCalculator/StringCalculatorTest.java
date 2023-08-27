package stringCalculator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class StringCalculatorTest {
    private StringCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new StringCalculator();
    }

    @DisplayName(value = "빈 문자열 또는 null 값을 입력할 경우 0을 반환해야 한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void emptyOrNull(final String text) {
        assertThat(calculator.add(text)).isZero();
    }

    @DisplayName(value = "숫자 하나를 문자열로 입력할 경우 해당 숫자를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1"})
    void oneNumber(final String text) {
        assertThat(calculator.add(text)).isSameAs(Integer.parseInt(text));
    }

    @DisplayName(value = "숫자가 아닌 하나의 문자열로 입력할 경우 해당 RuntimeException 예외를 throw 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "ㄱ", "A", ",", ";"})
    void oneIsNotNumber(final String text) {
        Assertions.assertThatThrownBy(() -> calculator.add(text))
            .isInstanceOf(RuntimeException.class);
    }

    @DisplayName(value = "숫자 두개를 쉼표(,) 구분자로 입력할 경우 두 숫자의 합을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1,2"})
    void twoNumbers(final String text) {
        assertThat(calculator.add(text)).isSameAs(3);
    }

    @DisplayName(value = "구분자를 쉼표(,) 이외에 콜론(:)을 사용할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"1,2:3"})
    void colons(final String text) {
        assertThat(calculator.add(text)).isSameAs(6);
    }

    @DisplayName(value = "//와 \\n 문자 사이에 커스텀 구분자를 지정할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"//;\n1;2;3"})
    void customDelimiter(final String text) {
        assertThat(calculator.add(text)).isSameAs(6);
    }

    @DisplayName(value = "문자열 계산기에 음수를 전달하는 경우 RuntimeException 예외 처리를 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"-1,2:3", "-1", "-1:2", "//;\n1;2;-3"})
    void negative(final String text) {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> calculator.add(text));
    }
}