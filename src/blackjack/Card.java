package blackjack;

public class Card {
	private String suit;
	private int number;
	
	public Card(String suit, int number) {
		this.suit = suit;
		this.number = number;
	}
	
	// 点数計算 10, J, Q, Kは全て10点として換算
	public int getPoint() {
		if (number > 10) {
			return 10;
		}
		return number;
	}
	
	@Override
	public String toString() {
		String strNumber;
		
		if (number == 1) {
			strNumber = "A";
		} else if (number == 11) {
			strNumber = "J"; 
		} else if (number == 12) {
			strNumber = "Q";
		} else if (number == 13) {
			strNumber = "K";
		} else {
			strNumber = Integer.toString(number);
		}
		return suit + "の" + strNumber;
	}
	
}
