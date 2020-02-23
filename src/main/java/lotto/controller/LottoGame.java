package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.LottoGeneratable;
import lotto.domain.LottoNumber;
import lotto.domain.Lottos;
import lotto.domain.MatchResult;
import lotto.domain.Money;
import lotto.domain.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

/**
 * 입출력과 도메인 사이의
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/23
 */
public class LottoGame {
	private final LottoGeneratable lottosFactory;

	public LottoGame(LottoGeneratable lottosFactory) {
		this.lottosFactory = lottosFactory;
	}

	public void run() {
		Money purchaseMoney = new Money(InputView.inputLottoMoney());
		Lottos lottos = lottosFactory.generate(purchaseMoney);
		OutputView.printLottos(lottos);

		WinningLotto winningLotto = generateWinningLotto();
		MatchResult matchResult = lottos.matchAll(winningLotto);
		OutputView.printStatistics(matchResult);
		OutputView.printTotalProfits(matchResult.calculateTotalProfits(purchaseMoney));
	}

	private WinningLotto generateWinningLotto() {
		Lotto lotto = Lotto.ofComma(InputView.inputWinningLotto());
		LottoNumber bonusNumber = LottoNumber.of(InputView.inputBonusBall());
		return new WinningLotto(lotto, bonusNumber);
	}
}
