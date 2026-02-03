package blackjack;

public class Dealer {
	private Deck deck = new Deck();
	
	 // 山札からディーラーがカードを配る
	public Card dealCard() {
		return deck.provideCard();
	}
}
