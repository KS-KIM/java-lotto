package lotto.domain.lotto.factory;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import lotto.domain.PurchaseMoney;
import lotto.domain.lotto.LottoTicket;

/**
 * 수동과 자동 로또를 생성하는 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/29
 */
public class LottoTicketFactory implements LottoGenerative {
	private static final String LOTTO_FACTORY_NOT_FOUND_MESSAGE = "로또 생성기가 존재하지 않습니다.";
	private List<LottoGenerative> lottoFactories;

	public LottoTicketFactory(List<LottoGenerative> lottoFactories) {
		validateNullOrEmpty(lottoFactories);
		this.lottoFactories = lottoFactories;
	}

	public static LottoTicketFactory of(LottoGenerative... lottoFactories) {
		return Stream.of(lottoFactories)
				.collect(collectingAndThen(toList(), LottoTicketFactory::new));
	}

	private void validateNullOrEmpty(List<LottoGenerative> lottoFactories) {
		if (Objects.isNull(lottoFactories) || lottoFactories.isEmpty()) {
			throw new IllegalArgumentException(LOTTO_FACTORY_NOT_FOUND_MESSAGE);
		}
	}

	public LottoTicket generate(PurchaseMoney purchaseMoney) {
		return lottoFactories.stream()
				.map(lottoFactory -> lottoFactory.generate(purchaseMoney))
				.collect(collectingAndThen(toList(), LottoTicket::of));
	}
}
