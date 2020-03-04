package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 여러 개의 로또를 테스트하는 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class LottoTicketTest {
	@Test
	@DisplayName("로또 티켓 생성자의 인자로 null을 넣은 경우 예외가 발생한다")
	void constructor() {
		assertThatThrownBy(() -> new LottoTicket(null)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("우승 로또와 로또 티켓을 비교하여 각 순위별로 맞춘 로또의 개수를 계산한다")
	void matchAll() {
		LottoTicket lottoTicket = new LottoTicket(Arrays.asList(
				Lotto.of(1, 2, 5, 43, 44, 45),
				Lotto.of(1, 2, 5, 43, 44, 45),
				Lotto.of(1, 2, 3, 43, 44, 45)));
		WinningLotto winningLotto = new WinningLotto(Lotto.of(1, 2, 5, 43, 44, 45), LottoNumber.of(7));

		List<MatchResult> expected = new ArrayList<>();
		expected.add(new MatchResult(MatchCount.of(6), false));
		expected.add(new MatchResult(MatchCount.of(6), false));
		expected.add(new MatchResult(MatchCount.of(5), false));

		assertThat(lottoTicket.matchAll(winningLotto)).isEqualTo(expected);
	}

	@Test
	@DisplayName("두 로또를 연결하여 새로운 로또 티켓을 발급한다")
	void concat() {
		LottoTicket lottoTicket1 = new LottoTicket(Collections.singletonList(Lotto.of(1, 2, 5, 43, 44, 45)));
		LottoTicket lottoTicket2 = new LottoTicket(Collections.singletonList(Lotto.of(1, 2, 3, 4, 5, 6)));
		assertThat(LottoTicket.of(Arrays.asList(lottoTicket1, lottoTicket2))).isInstanceOf(LottoTicket.class);
	}

	@Test
	@DisplayName("로또 티켓에 포함된 로또의 개수를 반환한다")
	void size() {
		LottoTicket lottoTicket = new LottoTicket(Arrays.asList(
				Lotto.of(1, 2, 5, 43, 44, 45),
				Lotto.of(1, 2, 5, 43, 44, 45),
				Lotto.of(1, 2, 3, 43, 44, 45)));
		assertThat(lottoTicket.size()).isEqualTo(3);
	}
}
