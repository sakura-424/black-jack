package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> cards;

	public Deck() {
		cards = new ArrayList<>();
		
		String[] suits = {"スペード", "ハート", "クラブ", "ダイヤ"};
		// マーク4種 * 数字13で回して山札を作る
		for (String suit : suits) {
			for (int number = 1; number <= 13; number++) {
				Card c = new Card(suit, number);
				cards.add(c);
			}
		}
		Collections.shuffle(cards); //カードをシャッフル
	}
	
	 // 山札からカードを提供する
	public Card provideCard() {
		return cards.remove(0);
	}
	
}
