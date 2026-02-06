package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/* クラスを使わないver */
public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		boolean continueGame = true; // ゲーム継続フラグ
		// ゲーム継続中のループ
		while (continueGame) {
			System.out.println("-----  Game Start  -----");
			// 山札を作る
			ArrayList<int[]> deck = createDeck();	
			// プレイヤー側の手札を作成
			ArrayList<int[]> playerHand = createHand(deck);
			// プレイヤーの点数を計算
			int playerPoint = getSum(playerHand);
			// ディーラー側の手札を作成
			ArrayList<int[]> dealerHand = createHand(deck);
			// ディーラーの点数を計算
			int dealerPoint = getSum(dealerHand);
		
			//　場の状況を表示
			System.out.println("【あなたの手札】");
			for (int[] card : playerHand) {
				printCard(card);
			}
			System.out.println("合計点数：" + playerPoint + "点");
			System.out.println("【ディーラーの手札の内1枚】");
			printCard(dealerHand.get(0));
		
			// プレイヤーのターン（もう一枚引くかどうか選択し計算）
			playerPoint = playPlayerTurn(playerPoint, sc, playerHand, deck);
			// プレイヤーがバーストしていないかチェック
			if (playerPoint < 21) {
				// ディーラーのターン
				dealerPoint = playDealerTurn(dealerPoint, dealerHand, deck);
			}
			// 結果の表示
			printResult(playerPoint, dealerPoint);
			
			System.out.println("もう一枚プレイしますか？ y or n(yes or no)");
			String answer = sc.next();
			if ("n".equals(answer)) {
				continueGame = false;
			}
		}
		sc.close(); 
		System.out.println("-----  Game End  -----");
	}
	
	public static ArrayList<int[]> createDeck() {
		ArrayList<int[]> deck = new ArrayList<>();
		// ダイヤ、ハート、クラブ、スペードの4種 * 13枚
		for (int suit = 0; suit < 4; suit++) {
			for (int number = 1; number <= 13; number++) {
				int[] card = {suit, number};
				deck.add(card);
			}
		}
		Collections.shuffle(deck); //カードをシャッフル
		return deck;
	}
	
	// 手札を作成する
	public static ArrayList<int[]> createHand(ArrayList<int[]> deck) {
		ArrayList<int[]> hand = new ArrayList<>();
		hand.add(deck.remove(0));
		hand.add(deck.remove(0));
		return hand;
	}
	
	// カードを受け取り、点数を返す
	public static int toPoint(int[] card) {
		int point = card[1];
		
		if (point > 10) {
			point = 10; 
		}
		return point;
	}
	
	// 合計点数を計算する
	public static int getSum(ArrayList<int[]> hand) {
		int sum = 0;
		boolean hasAce = false;
		
		for (int[] card : hand) {
			int point = toPoint(card);
			sum += point;
			if (point == 1) {
				hasAce = true;
			}
		}
		 // Aが1枚のみの場合、Aを11点として計算
        if (hasAce && sum <= 11) {
        	sum += 10;
        }
		return sum;
	}
	
	// numberを表示用に変換する
	public static String toStringNumber(int[] card) {
		String strNumber = null;
		
		if (card[1] == 1) {
			strNumber = "A";
		} else if (card[1] == 11) {
			strNumber = "J"; 
		} else if (card[1] == 12) {
			strNumber = "Q";
		} else if (card[1] == 13) {
			strNumber = "K";
		} else {
			strNumber = Integer.toString(card[1]);
		}
		return strNumber;
	}
	
	// suitを表示用に変換する
	public static String toStringSuit(int[] card) {
		String strSuit = null;
		
		if (card[0] == 0) {
			strSuit = "スペード";
		} else if (card[0] == 1) {
			strSuit = "クラブ"; 
		} else if (card[0] == 2) {
			strSuit = "ダイヤ";
		} else if (card[0] == 3) {
			strSuit = "ハート";
		}
		return strSuit;
	}
	
	// 変換した数字とスートを使ってカードを表示
	static void printCard(int[] card) {
		String suit = toStringSuit(card);
		String number = toStringNumber(card);
		System.out.println(suit + "の" + number);
	}
	
	// プレイヤーのターン
	public static int playPlayerTurn(int playerPoint, Scanner sc, ArrayList<int[]> playerHand, ArrayList<int[]> deck) {
		boolean moreDraw = true;
		while (moreDraw && playerPoint <= 21) {
			System.out.println("もう一枚引きますか？ y or n(yes or no)");
			String answer = sc.next();
			if ("n".equals(answer)) {
				moreDraw = false;
			}
			if (moreDraw) {
				playerHand.add(deck.remove(0));
				playerPoint = getSum(playerHand);
				System.out.println("【あなたの手札】");
				for (int[] card : playerHand) {
					printCard(card);
				}
				System.out.println("合計点数：" + playerPoint + "点");
				if (playerPoint > 21) {
					System.out.println("バーストしました......");
				}
			}
		}
		return playerPoint;
	}
	
	// ディーラーのターン
	public static int playDealerTurn(int dealerPoint, ArrayList<int[]> dealerHand, ArrayList<int[]> deck) {
		while (dealerPoint < 17)
		{
			System.out.println("ディーラーがカードを引きました。");
			dealerHand.add(deck.remove(0));
			dealerPoint = getSum(dealerHand);
			System.out.println("【ディーラーの手札】");
			for (int[] card : dealerHand) {
				printCard(card);
			}
			System.out.println("合計点数：" + dealerPoint + "点");
		}
		return dealerPoint;
	}

	// ゲームの結果を判定し表示する
	public static void printResult(int playerPoint, int dealerPoint) {
		// プレイヤーがバーストしていない場合
		if (playerPoint <= 21) {
			// 点数開示
			System.out.println("あなたの点数：" + playerPoint + "点");
			System.out.println("ディーラーの点数：" + dealerPoint + "点");
			if (dealerPoint > 21) {
				System.out.println("ディーラーがバーストしました。あなたの勝ちです。");
			} else if (playerPoint > dealerPoint) {
				System.out.println("あなたの勝ちです。");
			} else if (playerPoint == dealerPoint) {
				System.out.println("引き分けです。");
			} else if (playerPoint < dealerPoint) {
				System.out.println("あなたの負けです。");
			}
		} else {
			System.out.println("あなたの負けです。");
		}
	}
}
