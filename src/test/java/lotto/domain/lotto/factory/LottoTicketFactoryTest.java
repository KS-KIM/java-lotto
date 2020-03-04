package lotto.domain.lotto.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lotto.domain.PurchaseMoney;
import lotto.domain.lotto.LottoTicket;

/**
 * 수동과 자동 로또를 생성하는 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/03/03
 */
public class LottoTicketFactoryTest {
	@Test
	@DisplayName("생성자의 인자로 null을 입력한 경우 예외 발생")
	void constructor_null() {
		assertThatThrownBy(() -> new LottoTicketFactory(null))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("생성자의 인자로 빈 리스트를 입력한 경우 예외 발생")
	void constructor_empytList() {
		assertThatThrownBy(() -> new LottoTicketFactory(new ArrayList<>()))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("로또 숫자 리스트를 인자로 받아 로또 기계를 생성")
	void of_constructor() {
		List<String> lottoNumbers = Arrays.asList("1,2,3,4,5,6", "2,3,4,5,6,7", "40,41,42,43,44,45");
		LottoGenerative manualLottoTicketFactory = new ManualLottoTicketFactory(lottoNumbers);
		LottoGenerative autoLottoTicketFactory = new AutoLottoTicketFactory();
		assertThat(LottoTicketFactory.of(manualLottoTicketFactory, autoLottoTicketFactory))
				.isInstanceOf(LottoTicketFactory.class);
	}

	@Test
	@DisplayName("10,000원을 받아 수동로또 3장과 자동로또 7장을 합쳐 10장의 로또를 만듦")
	void generate() {
		List<String> lottoNumbers = Arrays.asList("1,2,3,4,5,6", "2,3,4,5,6,7", "40,41,42,43,44,45");
		LottoGenerative manualLottoTicketFactory = new ManualLottoTicketFactory(lottoNumbers);
		LottoGenerative autoLottoTicketFactory = new AutoLottoTicketFactory();
		LottoGenerative lottoMachine = LottoTicketFactory.of(manualLottoTicketFactory, autoLottoTicketFactory);
		LottoTicket lottoTicket = lottoMachine.generate(new PurchaseMoney(10_000L));
		assertThat(lottoTicket.size()).isEqualTo(10);
	}
}
