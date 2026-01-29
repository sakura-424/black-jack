package blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<IntCard> cardList;

	public Deck() {
		cardList = new ArrayList<>();
		for (int i = 0; i < 52; i++) {
			cardList.add(new IntCard(i));
		}
		Collections.shuffle(cardList);
	}
	
	public IntCard drawCard() {
		return cardList.remove(0);
	}
	
}
