package lotto.view;

import lotto.domain.LottoTicket;
import lotto.domain.MatchResult;
import lotto.domain.PurchaseCount;
import lotto.util.TextUtil;

/**
 * 출력 클래스
 *
 * @version 1.0.0
 * @author K.S.KIM
 * @since 2020/02/19
 */
public class OutputView {
	public static final String TOTAL_BUY_FORMAT_MESSAGE = "수동으로 %d장, 자동으로 %d장을 구매했습니다.\n";
	public static final String STATISTICS_TITLE_MESSAGE = "당첨 통계\n---------";
	public static final String TOTAL_PROFIT_FORMAT_MESSAGE = "총 수익률은 %d%%입니다.\n";

	private OutputView() {
	}

	public static void printLottoPurchaseCount(PurchaseCount manualCount, PurchaseCount autoCount) {
		System.out.printf(TOTAL_BUY_FORMAT_MESSAGE, manualCount.get(), autoCount.get());
	}

	public static void printLottoTicket(LottoTicket lottoTicket) {
		System.out.println(TextUtil.generateLottoTicketText(lottoTicket));
	}

	public static void printStatistics(MatchResult matchResult) {
		System.out.println(STATISTICS_TITLE_MESSAGE);
		System.out.print(TextUtil.generateMatchResultText(matchResult));
	}

	public static void printTotalProfits(long totalProfits) {
		System.out.printf(TOTAL_PROFIT_FORMAT_MESSAGE, totalProfits);
	}

	public static void printError(String message) {
		System.out.println(message);
	}
}
