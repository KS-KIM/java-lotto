package lotto.domain;

import java.util.List;

/**
 * 수동과 자동 로또를 생성하는 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/29
 */
public class LottoMachine {
	LottoGeneratable manualLottoTicketMachine;
	LottoGeneratable autoLottoTicketMachine;

	public LottoMachine(List<String> lottoNumbers) {
		this.manualLottoTicketMachine = new ManualLottoTicketFactory(lottoNumbers);
		this.autoLottoTicketMachine = new AutoLottoTicketFactory();
	}

	public LottoTicket generate(PurchaseInformation purchaseInformation) {
		PurchaseMoney purchaseMoney = purchaseInformation.getPurchaseMoney();
		LottoTicket manualLottoTicket = manualLottoTicketMachine.generate(purchaseMoney);
		LottoTicket autoLottoTicket = autoLottoTicketMachine.generate(purchaseMoney);
		return manualLottoTicket.concat(autoLottoTicket);
	}
}