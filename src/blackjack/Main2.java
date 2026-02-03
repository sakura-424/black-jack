package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String answer = "y"; // プレイヤーの回答を保存
		
		System.out.println("-----  Game Start  -----");
		
		// 山札を作る
		ArrayList<int[]> deck = new ArrayList<>();
		// ダイヤ、ハート、クラブ、スペードの4種 * 13枚
		for (int suit = 0; suit < 4; suit++) {
			for (int number = 1; number <= 13; number++) {
				int[] card = {suit, number};
				deck.add(card);
			}
		}
		Collections.shuffle(deck); //カードをシャッフル
		
		// プレイヤー側の手札を作成
		// プレイヤーの点数を計算
		// ディーラー側の手札を作成
		// ディーラーの点数を計算
		
		// プレイヤーのターン（もう一枚引くかどうか選択し計算）
		
		// プレイヤーがバーストしていない場合、ディーラーのターン
		
		// 結果の表示
		
		sc.close(); 
		System.out.println("-----  Game End  -----");
		
	}
	
	// カードを受け取り、点数を返す
	public static int toPoint(int[] card) {
		int point = card[1];
		
		if (point > 10) {
			point = 10; 
		}
		return point;
	}
	
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
		return sum;
	}
	
}
