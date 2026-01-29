package blackjack;

public class IntCard {
	private final int id; // 0~51までトランプごとのID
	private static final String[] suits = {"スペード", "ハート", "クラブ", "ダイヤ"};
	
	private static final String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	
	public IntCard(int id) {
		this.id = id;
	}
	
	public int getSuitIndex() {
		return id / 13;
	}
	
	public int getNumber() {
		return (id % 13) + 1;
	}
	
	// ブラックジャックでは10・J・Q・Kは全て10点
	public int getPoint() {
		int num;
		num = getNumber();
		if (num > 10) {
			return 10;
		}
		return num;
	}
	
	@Override
	public String toString() {
		String suitName = suits[getSuitIndex()];
		String rankName = ranks[getNumber() - 1];
		return suitName + "の" + rankName;
	}
}
