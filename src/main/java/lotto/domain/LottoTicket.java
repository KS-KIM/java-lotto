package lotto.domain;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 여러 개의 로또를 가지는 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class LottoTicket {
	private static final String INVALID_LOTTO_NUMBERS_SIZE_MESSAGE = "로또 숫자가 없습니다.";

	private final List<Lotto> lottoTicket;

	public LottoTicket(List<Lotto> lottoTicket) {
		validate(lottoTicket);
		this.lottoTicket = Collections.unmodifiableList(lottoTicket);
	}

	public static LottoTicket of(List<LottoTicket> tickets) {
		return tickets.stream()
				.flatMap(ticket -> ticket.lottoTicket.stream())
				.collect(collectingAndThen(toList(), LottoTicket::new));
	}

	public List<MatchResult> matchAll(WinningLotto winningLotto) {
		return lottoTicket.stream()
				.map(winningLotto::match)
				.collect(toList());
	}

	private void validate(List<Lotto> lottoTicket) {
		if (Objects.isNull(lottoTicket)) {
			throw new IllegalArgumentException(INVALID_LOTTO_NUMBERS_SIZE_MESSAGE);
		}
	}

	public int size() {
		return lottoTicket.size();
	}

	public List<Lotto> getLottoTicket() {
		return lottoTicket;
	}
}
