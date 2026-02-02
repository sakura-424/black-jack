package blackjack;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String answer = "y"; // プレイヤーの回答を保存
		
		System.out.println("-----  Game Start  -----");
		while (answer.equals("y")){
			// 山札を準備し、プレイヤーとディーラーのポイントを初期化
			Deck deck = new Deck();
			int playerPoint = 0; // プレイヤー側の点数
			int dealerPoint = 0; // ディーラーの点数
		
			// プレイヤー側の手札を作成
			ArrayList<Card> playerHand = createHand(deck);
			playerPoint = calcScore(playerHand);
			// ディーラー側の手札を作成
			ArrayList<Card> dealerHand = createHand(deck);
			dealerPoint = calcScore(dealerHand);
		
			// 現在の場の状況を表示
			System.out.println("あなたの手札：" + playerHand);
			System.out.println("あなたの点数：" + playerPoint  + "点");
			System.out.println("ディーラーの手札の内１枚：" + dealerHand.get(0));
			// プレイヤーのターン（もう一枚引くかどうか選択し計算）
			playerPoint = playPlayerTurn(deck, playerHand, sc, playerPoint);
			
			// プレイヤーがバーストしていない場合
			if (playerPoint <= 21) {
				System.out.println("ディーラーの手札：" + dealerHand);
				System.out.println("ディーラーの点数：" + dealerPoint  + "点");
				// ディーラーのターン
				dealerPoint = playDealerTurn(deck, dealerHand, playerPoint, dealerPoint);
			}
			// 結果の表示
			printResult(playerPoint, dealerPoint);
			// 次のゲームに進むか選択
			System.out.println("もう一度プレイしますか？ y or n(yes or no)");
			answer = sc.next();
		};
		sc.close(); // Scannerを閉じる
		System.out.println("-----  Game End  -----");
	}
	
	// カードの点数を計算する
	public static int calcScore(ArrayList<Card> hand) {
        int sum = 0; // 点数合計
        boolean hasAce = false; // Aを持っているかどうかのフラグ
        int point; // 引いたカードの得点
        
        // 手持ちのカードの点数を計算
        for (Card card : hand) {
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
	
	// カードを2枚配り手札を作る
	public static ArrayList<Card> createHand(Deck deck) {
		ArrayList<Card> hand = new ArrayList<Card>();
		// カードを2枚配る
		hand.add(deck.drawCard());
		hand.add(deck.drawCard());
		return hand;
	}
	
	// プレイヤーのターン（もう一枚引くか選択し、一枚引く度に手札と点数を開示）
	public static int playPlayerTurn(Deck deck, ArrayList<Card> hand, Scanner sc, int playerPoint) {
		String answer = "y";
		// プレイヤーがもう１枚引く選択をし、プレイヤーがバーストしていない場合
		while (answer.equals("y") && playerPoint <= 21) {
			System.out.println("もう一枚引きますか？ y or n(yes or no)");
			answer = sc.next();
			if (answer.equals("y")) {
				hand.add(deck.drawCard());
				playerPoint = calcScore(hand);
				System.out.println("あなたの手札：" + hand);
				System.out.println("あなたの点数：" + playerPoint  + "点");
				if (playerPoint > 21) {
					System.out.println("バーストしました......");
				}
			}
		}
		return playerPoint;
	}
	
	// ディーラーのターン（ディーラーが17点以上になるようにカードを引き、手札と点数を開示）
	public static int playDealerTurn(Deck deck, ArrayList<Card> hand, int playerPoint, int dealerPoint) {
		// ディーラーが17点以上になるようにカードを引く
		while (dealerPoint < 17)
		{
			System.out.println("ディーラーがカードを引きました。");
			hand.add(deck.drawCard());
			dealerPoint = calcScore(hand);
			System.out.println("ディーラーの手札：" + hand);
			System.out.println("ディーラーの点数：" + dealerPoint + "点");
		}
		return dealerPoint;
	}
	
	// ゲームの結果を判定し表示する
	public static void printResult(int playerPoint, int dealerPoint) {
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
	}
	
}
