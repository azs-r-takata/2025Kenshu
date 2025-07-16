package kadai_028;

import java.util.HashMap;
import java.util.Scanner;

public class Jyanken_Chapter28 {
	public static final String ROCK = "r";
	public static final String SCISSORS = "s";
	public static final String PAPER = "p";
	
	//プレイヤーの入力の処理=====
	public String getMyChoice() {
		String hand = null;
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("自分のじゃんけんの手を入力しましょう");
			System.out.println("グーはrockのrを入力しましょう");
			System.out.println("チョキはscissorsのsを入力しましょう");
			System.out.println("パーはpaperのpを入力しましょう");
			
			scanner = new Scanner(System.in); //入力処理
			hand = scanner.next();
			
			if(hand.equals(ROCK)|| hand.equals(SCISSORS) || hand.equals(PAPER)) { //正しいものが入力されたか確認
				scanner.close();
				break;
			}
			else {
				continue;
			}
		}
		return hand;
	}
	//=====End
	
	//相手の手をランダムで入力=====
	public String getRandom() {
		String[] hand = {ROCK, SCISSORS, PAPER};
		int ram = (int)Math.floor(Math.random() * 3);
		
		return hand[ram];
	}
	//=====End
	
	//じゃんけんの結果を出力=====
	public void playGame(String player, String enemy) {
		HashMap<String, String> hands = new HashMap<>();
		
		String[] key = {ROCK, SCISSORS, PAPER};
		String[] words = {"グー", "チョキ", "パー"};
		
		for(int i = 0; i < 3; i++) {
			hands.put(key[i], words[i]);
		}
		
		System.out.println("自分の手は" + hands.get(player) + "相手の手は" + hands.get(enemy));
		
		if(player.equals(enemy)) {
			System.out.println("あいこです");
		}
		else if((player.equals(ROCK) && enemy.equals(SCISSORS)) || (player.equals(SCISSORS) && enemy.equals(PAPER)) || (player.equals(PAPER) && enemy.equals(ROCK))){
			System.out.println("自分の勝ちです");
		}
		else if((player.equals(ROCK) && enemy.equals(PAPER)) || (player.equals(SCISSORS) && enemy.equals(ROCK)) || (player.equals(PAPER) && enemy.equals(SCISSORS))){
			System.out.println("自分の負けです");
		}
	}
	//=====End
}
