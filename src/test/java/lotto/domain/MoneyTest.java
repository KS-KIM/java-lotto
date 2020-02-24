package lotto.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 로또 구입 금액 테스트 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class MoneyTest {
	@Test
	@DisplayName("생성자 테스트")
	void constructor() {
		assertThat(new Money(14000)).isExactlyInstanceOf(Money.class);
	}

	@Test
	@DisplayName("생성자에서 구입 금액이 1000으로 나누어 떨어지지 않는 경우")
	void constructor_MoneyDivideByUnit() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Money(14500));
	}

	@Test
	@DisplayName("곱셈 테스트")
	void multiply() {
		Money money = new Money(14000);
		assertThat(money.multiply(100L)).isEqualTo(1_400_000L);
	}

	@Test
	@DisplayName("구입할 수 있는 개수 테스트")
	void calculateBuyCount() {
		Money money = new Money(14000);
		assertThat(money.calculateBuyCount()).isEqualTo(14);
	}
}
