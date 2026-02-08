package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/* ブラックジャックゲーム */
/* クラス使わないver */
public class Main2 {
	public static final int BLACKJACK_NUM = 21;
	public static final int DEALER_STAND = 17;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		boolean continueGame = true; // ゲーム継続フラグ
		// ゲーム継続中のループ
		while (continueGame) {
			System.out.println("-----  Game Start  -----");
			// 山札を作る(int[]の配列が入ったリストを作成し、2つの数字を持つ配列 {スート, 数字}のカードを格納)
			ArrayList<int[]> deck = createDeck();	
			// プレイヤー側の手札を作成
			ArrayList<int[]> playerHand = createHand(deck);
			// ディーラー側の手札を作成
			ArrayList<int[]> dealerHand = createHand(deck);
			// プレイヤーの点数を計算
			int playerPoint = getSum(playerHand);
			// ディーラーの点数を計算
			int dealerPoint = getSum(dealerHand);

			//　場の状況を表示
			showGameState(playerHand, dealerHand, playerPoint);
		
			// プレイヤーのターン
			playerPoint = playPlayerTurn(playerPoint, sc, playerHand, deck);
			// ディーラーのターン
			dealerPoint = playDealerTurn(dealerPoint, playerPoint, dealerHand, deck);
			// 勝敗の表示
			printResult(playerPoint, dealerPoint);
			// ゲームを続けるか選択
			continueGame = isNextGame(sc, continueGame);
		}
		sc.close(); 
		System.out.println("-----  Game End  -----");
	}
	
	// 山札を作る
	public static ArrayList<int[]> createDeck() {
		final int RANK_MAX = 13; // トランプの数
		final int SUIT_TYPE = 4; // スートの種類の数
		
		// int[]の配列が入ったリストを作成 
		ArrayList<int[]> deck = new ArrayList<>();
		// ダイヤ、ハート、クラブ、スペードの4種 * 13枚
		for (int suit = 0; suit < SUIT_TYPE; suit++) {
			for (int number = 1; number <= RANK_MAX; number++) {
				// カードは2つの数字を持つ配列 {スート, 数字}
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
		// J, Q, Kは全て10点
		if (point > 10) {
			point = 10; 
		}
		return point;
	}
	
	// 場の状況を表示
	public static void showGameState(ArrayList<int[]> playerHand, ArrayList<int[]> dealerHand, int playerPoint) {
		System.out.println("【プレイヤーの手札】");
		for (int[] card : playerHand) {
			printCard(card);
		}
		System.out.println("プレイヤーの点数：" + playerPoint + "点");
		// ディーラーは手札を一枚だけ見せる
		System.out.println("【ディーラーの手札】");
		printCard(dealerHand.get(0));
		System.out.println("???の???");
	}
	
	// ヒット(カードを一枚引く)
	public static ArrayList<int[]> hit(ArrayList<int[]> deck, ArrayList<int[]> hand) {
		hand.add(deck.remove(0));
		return hand;
	}
	
	// 合計点数を計算する
	public static int getSum(ArrayList<int[]> hand) {
		int sum = 0;
		boolean hasAce = false;
		final int ACE = 1;
		
		for (int[] card : hand) {
			int point = toPoint(card);
			sum += point;
			if (point == ACE) {
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
		final int ACE = 1; 
		final int JACK = 11;
		final int QUEEN = 12;
		final int KING = 13;
		
		if (card[1] == ACE) {
			strNumber = "A";
		} else if (card[1] == JACK) {
			strNumber = "J"; 
		} else if (card[1] == QUEEN) {
			strNumber = "Q";
		} else if (card[1] == KING) {
			strNumber = "K";
		} else {
			strNumber = Integer.toString(card[1]);
		}
		return strNumber;
	}
	
	// suitを表示用に変換する
	public static String toStringSuit(int[] card) {
		String strSuit = null;
		final int SPADE = 0; 
		final int CLUB = 1;
		final int DIAMOND = 2;
		final int HEART = 3;
		
		if (card[0] == SPADE) {
			strSuit = "スペード";
		} else if (card[0] == CLUB) {
			strSuit = "クラブ"; 
		} else if (card[0] == DIAMOND) {
			strSuit = "ダイヤ";
		} else if (card[0] == HEART) {
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
		boolean continuePlayerTurn = true;
		while (continuePlayerTurn) {
			System.out.println("もう一枚引きますか？ y or n(yes or no)");
			String answer = sc.next();
			if ("y".equals(answer)) {
				playerHand = hit(deck, playerHand);
				playerPoint = getSum(playerHand);
				System.out.println("【プレイヤーの手札】");
				for (int[] card : playerHand) {
					printCard(card);
				}
				System.out.println("プレイヤーの点数：" + playerPoint + "点");
				if (playerPoint > BLACKJACK_NUM) {
					continuePlayerTurn = false;
					System.out.println("バーストしました......");
				}
			} else if ("n".equals(answer)) {
				continuePlayerTurn = false;
			}
		}
		return playerPoint;
	}
	
	// ディーラーのターン
	public static int playDealerTurn(int dealerPoint, int playerPoint, ArrayList<int[]> dealerHand, ArrayList<int[]> deck) {
		boolean continueDealerTurn = true;
		// ディーラーがスタンド状態、またはプレイヤーがバーストしていた場合ディーラーのターン終了
		if (dealerPoint >= DEALER_STAND || playerPoint > BLACKJACK_NUM) {
			continueDealerTurn = false;
		}
		while (continueDealerTurn)
		{
			System.out.println("ディーラーがカードを引きました。");
			dealerHand = hit(deck, dealerHand);
			dealerPoint = getSum(dealerHand);
			System.out.println("【ディーラーの手札】");
			for (int[] card : dealerHand) {
				printCard(card);
			}
			System.out.println("ディーラーの点数：" + dealerPoint + "点");
			// ディーラーがスタンド状態か判定
			if (dealerPoint >= DEALER_STAND) {
				continueDealerTurn = false;
			}
		}
		return dealerPoint;
	}

	// ゲームの結果を判定し表示する
	public static void printResult(int playerPoint, int dealerPoint) {
		// プレイヤーがバーストしていない場合
		if (playerPoint <= BLACKJACK_NUM) {
			// 点数開示
			System.out.println("プレイヤーの点数：" + playerPoint + "点");
			System.out.println("ディーラーの点数：" + dealerPoint + "点");
			if (dealerPoint > BLACKJACK_NUM) {
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
	
	// 次のゲームに進むか判定
	public static boolean isNextGame(Scanner sc, boolean continueGame) {
		boolean isValidInput = false; // 正しい入力がされたかどうかのフラグ
		while (!isValidInput) {
			System.out.println("もう一回プレイしますか？ y or n(yes or no)");
			String answer = sc.next();
			if ("y".equals(answer)) {
				isValidInput = true;
			} else if ("n".equals(answer)) {
				isValidInput = true;
				continueGame = false;
			} else {
				System.out.println("無効な入力です。y か n を入力してください。");
			}
		}
		return continueGame;
	}
}
