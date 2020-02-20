package lotto.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 로또 통계 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class MatchResult {
	public static final String LOTTO_RESULT_NOT_FOUND_EXCEPTION = "통계를 수행할 로또 결과가 없습니다.";

	private final Map<LottoRank, Long> matchResult;

	public MatchResult(Map<LottoRank, Long> matchResult) {
		validate(matchResult);
		this.matchResult = new HashMap<>(matchResult);
	}

	private void validate(Map<LottoRank, Long> statistics) {
		if (Objects.isNull(statistics) || statistics.isEmpty()) {
			throw new IllegalArgumentException(LOTTO_RESULT_NOT_FOUND_EXCEPTION);
		}
	}

	public long calculateTotalProfits(Money money) {
		long totalWinning = calculateTotalWinnings();
		return totalWinning * 100 / money.get();
	}

	private long calculateTotalWinnings() {
		return matchResult.entrySet()
				.stream()
				.mapToLong(result -> result.getKey().calculateTotalWinnings(result.getValue()))
				.sum();
	}

	public Long findMatchCountByLottoRank(LottoRank rank) {
		return matchResult.getOrDefault(rank, 0L);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		MatchResult that = (MatchResult)object;
		return Objects.equals(matchResult, that.matchResult);
	}

	@Override
	public int hashCode() {
		return Objects.hash(matchResult);
	}
}