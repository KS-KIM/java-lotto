package lotto.domain.lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import lotto.domain.statistics.MatchCount;

/**
 * 선택한 로또 하나를 테스트하는 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class LottoTest {
	static Stream<List<LottoNumber>> invalidSize() {
		return Stream.of(
				null,
				new ArrayList<>(),
				Arrays.asList(LottoNumber.valueOf(1),
						LottoNumber.valueOf(2),
						LottoNumber.valueOf(3),
						LottoNumber.valueOf(43),
						LottoNumber.valueOf(44)),
				Arrays.asList(LottoNumber.valueOf(1),
						LottoNumber.valueOf(2),
						LottoNumber.valueOf(3),
						LottoNumber.valueOf(4),
						LottoNumber.valueOf(43),
						LottoNumber.valueOf(44),
						LottoNumber.valueOf(45)));
	}

	@Test
	@DisplayName("로또 번호 여섯 개를 받아서 로또 인스턴스를 생성한다")
	void constructor() {
		List<LottoNumber> lottoNumbers = Arrays.asList(LottoNumber.valueOf(1),
				LottoNumber.valueOf(2),
				LottoNumber.valueOf(3),
				LottoNumber.valueOf(43),
				LottoNumber.valueOf(44),
				LottoNumber.valueOf(45));
		assertThat(new Lotto(lottoNumbers)).isInstanceOf(Lotto.class);
	}

	@ParameterizedTest
	@DisplayName("로또 번호의 개수가 6개가 아닌 경우 예외가 발생한다")
	@MethodSource("invalidSize")
	void constructor_InvalidLottoNumbersSize(List<LottoNumber> lottoNumbers) {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Lotto(lottoNumbers));
	}

	@Test
	@DisplayName("중복된 수가 있는 경우 예외가 발생한다")
	void constructor_HasDuplicatedNumber() {
		List<LottoNumber> lottoNumbers = Arrays.asList(LottoNumber.valueOf(1),
				LottoNumber.valueOf(2),
				LottoNumber.valueOf(2),
				LottoNumber.valueOf(43),
				LottoNumber.valueOf(44),
				LottoNumber.valueOf(45));
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Lotto(lottoNumbers));
	}

	@Test
	@DisplayName("생성자를 통해 만들어진 로또 번호는 정렬되어 있다")
	void constructor_SortedValues() {
		Lotto lotto = new Lotto(Arrays.asList(LottoNumber.valueOf(45),
				LottoNumber.valueOf(44),
				LottoNumber.valueOf(43),
				LottoNumber.valueOf(1),
				LottoNumber.valueOf(2),
				LottoNumber.valueOf(3)));
		Lotto expected = new Lotto(Arrays.asList(LottoNumber.valueOf(1),
				LottoNumber.valueOf(2),
				LottoNumber.valueOf(3),
				LottoNumber.valueOf(43),
				LottoNumber.valueOf(44),
				LottoNumber.valueOf(45)));
		assertThat(lotto).isEqualTo(expected);
	}

	@ParameterizedTest
	@DisplayName("로또 숫자를 콤마 단위로 구분한 문자열로 받아 생성한다")
	@ValueSource(strings = {"1, 2, 3, 4, 5, 6", "1,2,3,4,5,6", "  1, 2, 3,4,    5,  6   "})
	void ofComma(String lottoNumbers) {
		assertThat(Lotto.ofComma(lottoNumbers)).isInstanceOf(Lotto.class);
	}

	@Test
	@DisplayName("두 로또를 비교하여 일치하는 로또 수의 개수를 카운트한다")
	void calculateMatchCount() {
		Lotto lotto = Lotto.of(1, 2, 3, 4, 5, 6);
		Lotto winningLotto = Lotto.of(2, 3, 4, 6, 7, 8);
		assertThat(lotto.calculateMatchCount(winningLotto)).isEqualTo(MatchCount.of(4));
	}
}
