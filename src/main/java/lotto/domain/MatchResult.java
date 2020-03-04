package lotto.domain;

import java.util.Objects;

/**
 * MatchResult class
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/03/04
 */
public class MatchResult {
	private static final String MATCH_COUNT_IS_NULL_MESSAGE = "일치한 개수를 가지고 있지 않습니다.";
	private static final String INVALID_MATCH_RESULT_MESSAGE = "존재할 수 없는 결과입니다.";

	private final MatchCount matchCount;
	private final boolean isBonus;

	public MatchResult(MatchCount matchCount, boolean isBonus) {
		validate(matchCount, isBonus);
		this.matchCount = matchCount;
		this.isBonus = isBonus;
	}

	private void validate(MatchCount matchCount, boolean isBonus) {
		validateNull(matchCount);
		validateResult(matchCount, isBonus);
	}

	private void validateResult(MatchCount matchCount, boolean isBonus) {
		if (matchCount.equals(MatchCount.of(MatchCount.MAX_MATCH)) && isBonus) {
			throw new IllegalArgumentException(INVALID_MATCH_RESULT_MESSAGE);
		}
	}

	private void validateNull(MatchCount matchCount) {
		if (Objects.isNull(matchCount)) {
			throw new IllegalArgumentException(MATCH_COUNT_IS_NULL_MESSAGE);
		}
	}

	public boolean isSameMatchCount(MatchCount matchCount) {
		return this.matchCount.equals(matchCount);
	}

	public boolean isMatchCountRangeClosedOf(int startInclusive, int endInclusive) {
		return matchCount.isRangeClosedOf(startInclusive, endInclusive);
	}

	public boolean isNotBonus() {
		return !isBonus;
	}

	public boolean isBonus() {
		return isBonus;
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
		return isBonus == that.isBonus && Objects.equals(matchCount, that.matchCount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(matchCount, isBonus);
	}
}
