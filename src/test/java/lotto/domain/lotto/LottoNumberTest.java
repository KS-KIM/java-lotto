package lotto.domain.lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 로또 수 테스트 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class LottoNumberTest {
	@ParameterizedTest
	@DisplayName("로또 번호 범위가 정상인 경우 인스턴스를 반환한다")
	@ValueSource(ints = {1, 10, 45})
	void of(int value) {
		assertThat(LottoNumber.valueOf(value)).isInstanceOf(LottoNumber.class);
	}

	@ParameterizedTest
	@DisplayName("로또 번호 범위에서 벗어나는 경우 예외가 발생한다")
	@ValueSource(ints = {-1, 0, 46})
	void of_OutOfRange(int actual) {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> LottoNumber.valueOf(actual));
	}
}
