package lotto.domain.statistics;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * 로또 순위 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class LottoRankTest {
	@Test
	@DisplayName("번호가 6개 일치하는 경우 1등이다")
	void of_FIRST() {
		MatchResult matchResult = new MatchResult(MatchCount.of(6), false);
		LottoRank rank = LottoRank.of(matchResult);
		assertThat(rank).isEqualTo(LottoRank.FIRST);
	}

	@Test
	@DisplayName("번호가 5개 일치하고, 보너스 번호가 일치하는 경우 2등이다")
	void of_SECOND() {
		MatchResult matchResult = new MatchResult(MatchCount.of(5), true);
		LottoRank rank = LottoRank.of(matchResult);
		assertThat(rank).isEqualTo(LottoRank.SECOND);
	}

	@Test
	@DisplayName("번호가 5개 일치하고, 보너스 번호가 일치하지 않는 경우 3등이다")
	void of_THIRD() {
		MatchResult matchResult = new MatchResult(MatchCount.of(5), false);
		LottoRank rank = LottoRank.of(matchResult);
		assertThat(rank).isEqualTo(LottoRank.THIRD);
	}

	@ParameterizedTest
	@DisplayName("번호가 4개 일치하는 경우 4등이다")
	@CsvSource(value = {"4,false", "4,true"})
	void of_FOURTH(int count, boolean isBonus) {
		MatchResult matchResult = new MatchResult(MatchCount.of(count), isBonus);
		LottoRank rank = LottoRank.of(matchResult);
		assertThat(rank).isEqualTo(LottoRank.FOURTH);
	}

	@ParameterizedTest
	@DisplayName("번호가 3개 일치하는 경우 5등이다")
	@CsvSource(value = {"3,false", "3,true"})
	void of_FIFTH(int count, boolean isBonus) {
		MatchResult matchResult = new MatchResult(MatchCount.of(count), isBonus);
		LottoRank rank = LottoRank.of(matchResult);
		assertThat(rank).isEqualTo(LottoRank.FIFTH);
	}

	@ParameterizedTest
	@DisplayName("번호가 0~2개 일치하는 경우 MISS이다")
	@CsvSource(value = {"2,false", "2,true", "1,false", "1,true", "0,false", "0,true"})
	void of_MISS(int count, boolean isBonus) {
		MatchResult matchResult = new MatchResult(MatchCount.of(count), isBonus);
		LottoRank rank = LottoRank.of(matchResult);
		assertThat(rank).isEqualTo(LottoRank.MISS);
	}

	@Test
	@DisplayName("일치 여부의 리스트를 입력받아 랭크로 변환한다")
	void of_MatchResults() {
		List<MatchResult> matchResults = new ArrayList<>();
		matchResults.add(new MatchResult(MatchCount.of(6), false));
		matchResults.add(new MatchResult(MatchCount.of(6), false));
		matchResults.add(new MatchResult(MatchCount.of(5), false));
		matchResults.add(new MatchResult(MatchCount.of(2), true));

		List<LottoRank> expect = new ArrayList<>();
		expect.add(LottoRank.FIRST);
		expect.add(LottoRank.FIRST);
		expect.add(LottoRank.THIRD);
		expect.add(LottoRank.MISS);

		assertThat(LottoRank.of(matchResults)).isEqualTo(expect);
	}

	@Test
	@DisplayName("로또 순위를 역순으로 반환한다")
	void valuesAsReverse() {
		List<LottoRank> expect = Arrays.asList(LottoRank.MISS,
				LottoRank.FIFTH,
				LottoRank.FOURTH,
				LottoRank.THIRD,
				LottoRank.SECOND,
				LottoRank.FIRST);
		assertThat(LottoRank.valuesAsReverse()).isEqualTo(expect);
	}
}
