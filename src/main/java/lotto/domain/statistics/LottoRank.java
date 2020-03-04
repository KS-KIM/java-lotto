package lotto.domain.statistics;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import lotto.domain.PurchaseMoney;

/**
 * 로또 순위 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public enum LottoRank {
	FIRST(MatchCount.of(6), new PurchaseMoney(2_000_000_000L),
			(matchResult) -> matchResult.isSameMatchCount(MatchCount.of(6)) && matchResult.isNotBonus()),
	SECOND(MatchCount.of(5), new PurchaseMoney(30_000_000L),
			(matchResult) -> matchResult.isSameMatchCount(MatchCount.of(5)) && matchResult.isBonus()),
	THIRD(MatchCount.of(5), new PurchaseMoney(1_500_000L)),
	FOURTH(MatchCount.of(4), new PurchaseMoney(50_000L)),
	FIFTH(MatchCount.of(3), new PurchaseMoney(5_000L)),
	MISS(MatchCount.of(0), new PurchaseMoney(0), (matchResult) -> matchResult.isMatchCountRangeClosedOf(0, 2));

	public static final String INVALID_RANK_MESSAGE = "식별할 수 없는 순위입니다.";

	private final MatchCount matchCount;
	private final PurchaseMoney winnings;
	private final Predicate<MatchResult> match;

	LottoRank(MatchCount matchCount, PurchaseMoney winnings) {
		this(matchCount, winnings, (matchResult) -> matchResult.isSameMatchCount(matchCount));
	}

	LottoRank(MatchCount matchCount, PurchaseMoney winnings, Predicate<MatchResult> match) {
		this.matchCount = matchCount;
		this.winnings = winnings;
		this.match = match;
	}

	public static LottoRank of(MatchResult matchResult) {
		return Arrays.stream(values())
				.filter(rank -> rank.match(matchResult))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(INVALID_RANK_MESSAGE));
	}

	public static List<LottoRank> of(List<MatchResult> matchResults) {
		return matchResults.stream()
				.map(LottoRank::of)
				.collect(Collectors.toList());
	}

	public static List<LottoRank> valuesAsReverse() {
		return Lists.reverse(Stream.of(values()).collect(Collectors.toList()));
	}

	private boolean match(MatchResult matchResult) {
		return match.test(matchResult);
	}

	public long calculateTotalWinnings(long amount) {
		return winnings.multiply(amount);
	}

	public MatchCount getMatchCount() {
		return matchCount;
	}

	public PurchaseMoney getWinnings() {
		return winnings;
	}
}
