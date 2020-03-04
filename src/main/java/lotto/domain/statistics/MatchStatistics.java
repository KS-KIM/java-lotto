package lotto.domain.statistics;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static lotto.domain.lotto.factory.LottoGenerative.LOTTO_PRICE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 로또 통계 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class MatchStatistics {
	private static final String LOTTO_RESULT_NOT_FOUND_EXCEPTION = "통계를 수행할 로또 결과가 없습니다.";
	private static final long DEFAULT_VALUE = 0L;
	private static final int MULTIPLY_PERCENTAGE = 100;

	private final Map<LottoRank, Long> matchResult;

	public MatchStatistics(Map<LottoRank, Long> matchResult) {
		validate(matchResult);
		this.matchResult = new HashMap<>(matchResult);
	}

	public static MatchStatistics ofMatchRanks(List<LottoRank> matchResults) {
		return matchResults.stream()
				.collect(collectingAndThen(groupingBy(Function.identity(), counting()), MatchStatistics::new));
	}

	private void validate(Map<LottoRank, Long> statistics) {
		if (Objects.isNull(statistics) || statistics.isEmpty()) {
			throw new IllegalArgumentException(LOTTO_RESULT_NOT_FOUND_EXCEPTION);
		}
	}

	public long calculateTotalProfits() {
		long totalWinning = calculateTotalWinnings();
		long totalPurchaseMoney = calculateTotalPurchaseMoney();
		return totalWinning * MULTIPLY_PERCENTAGE / totalPurchaseMoney;
	}

	private long calculateTotalWinnings() {
		return matchResult.entrySet()
				.stream()
				.mapToLong(result -> result.getKey().calculateTotalWinnings(result.getValue()))
				.sum();
	}

	private long calculateTotalPurchaseMoney() {
		return matchResult.values()
				.stream()
				.mapToLong(LOTTO_PRICE::multiply)
				.sum();
	}

	public Long findMatchCountByLottoRank(LottoRank rank) {
		return matchResult.getOrDefault(rank, DEFAULT_VALUE);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		MatchStatistics that = (MatchStatistics)object;
		return Objects.equals(matchResult, that.matchResult);
	}

	@Override
	public int hashCode() {
		return Objects.hash(matchResult);
	}
}
