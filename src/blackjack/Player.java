package blackjack;

import java.util.ArrayList;

public class Player {
	private ArrayList<Card> hand; // 手札
	private int point; // 点数
	
	public Player() {
		hand = new ArrayList<>();
	}
	
	public void addHand(Card card) {
		hand.add(card);
		point = calcScore();
	}
	
	// カードの点数を計算する
	private int calcScore() {
        int sum = 0; // 点数合計
        boolean hasAce = false; // Aを持っているかどうかのフラグ
        
        // 手持ちのカードの点数を計算
        for (Card card : hand) {
        	int p = card.convertNumber();
        	sum += p;
        	// もし1点(Aがある場合)の時
        	if (p == 1) {
        		hasAce = true;
        	}
        }
        // Aが1枚のみの場合、Aを11点として計算
        if (hasAce && sum <= 11) {
        	sum += 10;
        }
        return sum;
    }
}
