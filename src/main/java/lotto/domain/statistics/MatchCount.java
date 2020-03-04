package lotto.domain.statistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 로또 일치 개수 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/20
 */
public class MatchCount {
	public static final String MATCH_COUNT_OUT_OF_RANGE_MESSAGE = "일치하는 로또 수가 범위를 벗어났습니다";
	public static final int MIN_MATCH = 0;
	public static final int MAX_MATCH = 6;
	private static final Map<Integer, MatchCount> MATCH_COUNT_CACHE = new HashMap<>();

	static {
		for (int matchCount = MIN_MATCH; matchCount <= MAX_MATCH; matchCount++) {
			MATCH_COUNT_CACHE.put(matchCount, new MatchCount(matchCount));
		}
	}

	private final int count;

	private MatchCount(int count) {
		validate(count);
		this.count = count;
	}

	public static MatchCount of(int count) {
		validate(count);
		return MATCH_COUNT_CACHE.get(count);
	}

	private static void validate(int count) {
		if (count < MIN_MATCH || count > MAX_MATCH) {
			throw new IllegalArgumentException(MATCH_COUNT_OUT_OF_RANGE_MESSAGE);
		}
	}

	public boolean isRangeClosedOf(int startInclusive, int endInclusive) {
		return count >= startInclusive && count <= endInclusive;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		MatchCount that = (MatchCount)object;
		return count == that.count;
	}

	@Override
	public int hashCode() {
		return Objects.hash(count);
	}

	@Override
	public String toString() {
		return String.valueOf(count);
	}
}
