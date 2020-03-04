package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 로또 통계 테스트 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class MatchStatisticsTest {
	private MatchStatistics matchStatistics;

	@BeforeEach
	void setup() {
		Map<LottoRank, Long> statistics = new HashMap<>();
		statistics.put(LottoRank.FIRST, 1L);
		statistics.put(LottoRank.THIRD, 3L);
		statistics.put(LottoRank.FIFTH, 2L);
		statistics.put(LottoRank.MISS, 4L);
		matchStatistics = new MatchStatistics(statistics);
	}

	@Test
	@DisplayName("생성자 테스트")
	void constructor() {
		Map<LottoRank, Long> statistics = new HashMap<>();
		statistics.put(LottoRank.FIRST, 1L);
		statistics.put(LottoRank.THIRD, 3L);
		statistics.put(LottoRank.FIFTH, 2L);
		statistics.put(LottoRank.MISS, 5L);
		assertThat(new MatchStatistics(statistics)).isInstanceOf(MatchStatistics.class);
	}

	@Test
	@DisplayName("생성자에 null 값을 입력한 경우 예외가 발생한다.")
	void constructor_isNull() {
		assertThatThrownBy(() -> new MatchStatistics(null)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("생성자에 빈 map을 입력한 경우 예외가 발생한다.")
	void constructor_isEmpty() {
		Map<LottoRank, Long> statistics = new HashMap<>();
		assertThatThrownBy(() -> new MatchStatistics(statistics)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("총 수익률 연산 테스트")
	void calculateTotalProfits() {
		assertThat(matchStatistics.calculateTotalProfits()).isEqualTo(20045100L);
	}

	@Test
	@DisplayName("당첨자가 있을 때 몇명인지 가져오는 테스트")
	void findMatchCountByLottoRank() {
		assertThat(matchStatistics.findMatchCountByLottoRank(LottoRank.FIFTH)).isEqualTo(2L);
	}

	@Test
	@DisplayName("당첨자가 없을 때 기본 값을 반환하는지 테스트")
	void findMatchCountByLottoRank_DefaultRank() {
		assertThat(matchStatistics.findMatchCountByLottoRank(LottoRank.SECOND)).isEqualTo(0L);
	}
}
