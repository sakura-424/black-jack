package blackjack;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int playerPoint; // プレイヤー側の点数
		int dealerPoint; // ディーラーの点数
		int answer; // プレイヤーの回答を保存
		
		System.out.println("-----  Game Start  -----");
		while (true) {
			Deck deck = new Deck();
			playerPoint = 0;
			dealerPoint = 0;
			answer = 0;
		
			ArrayList<IntCard> playerHand = new ArrayList<IntCard>();
			ArrayList<IntCard> dealerHand = new ArrayList<IntCard>();
		
			// プレイヤーにカードを2枚配る
			playerHand.add(deck.drawCard());
			playerHand.add(deck.drawCard());
			playerPoint = calcScore(playerHand);
		
			// ディーラーにカードを2枚配る
			dealerHand.add(deck.drawCard());
			dealerHand.add(deck.drawCard());
			dealerPoint = calcScore(dealerHand);
		
			// 手札の内容と現在の点数を表示
			System.out.println("あなたの手札：" + playerHand);
			System.out.println("現在の点数：" + playerPoint);
		
			while (true) {
				System.out.println("もう一枚引きますか？ (1:Yes / 2:No)");
				answer = sc.nextInt();
				if (answer == 2) {
					break ;
				} else if (answer == 1) {
					playerHand.add(deck.drawCard());
					playerPoint = calcScore(playerHand);
					System.out.println("あなたの手札：" + playerHand);
					System.out.println("現在の点数：" + playerPoint);
					if (playerPoint > 21) {
						System.out.println("バーストしました......");
						break ;
					}		
				} // if
			} // while
			System.out.println("ディーラーの手札：" + dealerHand);
			System.out.println("ディーラーの点数：" + dealerPoint);
			if (playerPoint <= 21) {
				// ディーラーが17点以上になるようにカードを引く
				while (dealerPoint < 17)
				{
					System.out.println("ディーラーがカードを引きました。");
					dealerHand.add(deck.drawCard());
					dealerPoint = calcScore(dealerHand);
					System.out.println("ディーラーの手札：" + dealerHand);
					System.out.println("ディーラーの点数：" + dealerPoint);
				}
			}
			// 勝敗判定
			// プレイヤーがバーストしていない場合
			if (playerPoint <= 21) {
				if (dealerPoint > 21) {
					System.out.println("ディーラーがバーストしました。あなたの勝ちです。");
				} else if (playerPoint > dealerPoint) {
					System.out.println("あなたの勝ちです。");
				} else if (playerPoint == dealerPoint) {
					System.out.println("引き分けです。");
				} else {
					System.out.println("あなたの負けです。");
				}
			} else {
				System.out.println("あなたの負けです。");
			}
			System.out.println("もう一度プレイしますか？ (1:Yes / 2:No)");
			answer = sc.nextInt();
			if (answer == 2) {
				break ;
			}
		}
		sc.close(); // Scannerを閉じる
		System.out.println("-----  Game End  -----");
	}
	
	// カードの点数を計算する
	public static int calcScore(ArrayList<IntCard> hand) {
        int sum; // 点数合計
        boolean hasAce; // Aを持っているかどうかのフラグ
        int point; // 引いたカードの得点
        
        sum = 0;
        hasAce = false;
        for (IntCard card : hand) {
        	point = card.getPoint();
        	sum += point;
        	// もし1点(Aがある場合)の時
        	if (point == 1) {
        		hasAce = true;
        	}
        }
        // Aを持っていて、点数の合計が11以下の時、Aを10点として計算
        if (hasAce && sum <= 11) {
        	sum += 10;
        }
        return sum;
    }

}
