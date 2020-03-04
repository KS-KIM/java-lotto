package lotto.domain.lotto.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import lotto.domain.PurchaseMoney;
import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.LottoNumber;
import lotto.domain.lotto.LottoTicket;

/**
 * 로또 번호 자동 생성 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class AutoLottoTicketFactory implements LottoGenerative {
	private final Random random;

	public AutoLottoTicketFactory() {
		this.random = new Random();
	}

	@Override
	public LottoTicket generate(PurchaseMoney purchaseMoney) {
		List<Lotto> lottoTicket = new ArrayList<>();
		while (purchaseMoney.canPayable(LOTTO_PRICE)) {
			purchaseMoney.pay(LOTTO_PRICE);
			lottoTicket.add(generate());
		}
		return new LottoTicket(lottoTicket);
	}

	private Lotto generate() {
		return random.ints(LottoNumber.MIN_VALUE, LottoNumber.MAX_VALUE + 1)
				.distinct()
				.limit(Lotto.SIZE)
				.mapToObj(LottoNumber::valueOf)
				.collect(Collectors.collectingAndThen(Collectors.toList(), Lotto::new));
	}
}
