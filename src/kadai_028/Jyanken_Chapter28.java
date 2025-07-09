package kadai_028;

import java.util.HashMap;
import java.util.Scanner;

public class Jyanken_Chapter28 {
	public String getMyChoice() {
		String hand = null;
		while(true) {
			System.out.println("自分のじゃんけんの手を入力しましょう");
			System.out.println("グーはrockのrを入力しましょう");
			System.out.println("チョキはscissorsのsを入力しましょう");
			System.out.println("パーはpaperのpを入力しましょう");
			
			Scanner scanner = new Scanner(System.in);
			hand = scanner.next();
			
			if(hand.equals("r")|| hand.equals("s") || hand.equals("p")) {
				scanner.close();
				break;
			}
			else {
				continue;
			}
		}
		return hand;
	}
	
	public String getRandom() {
		String[] hand = {"r", "s", "p"};
		int max= 2;
		int min = 0;
		int ram = (int)(Math.random() * (max - min + 1)) + min;
		
		return hand[ram];
	}
	
	public void playGame(String player, String enemy) {
		HashMap<String, String> hands = new HashMap<>();
		
		String[] key = {"r", "s", "p"};
		String[] words = {"グー", "チョキ", "パー"};
		
		for(int i = 0; i < 3; i++) {
			hands.put(key[i], words[i]);
		}
		
		System.out.println("自分の手は" + hands.get(player) + "相手の手は" + hands.get(enemy));
		
		if(player.equals(enemy)) {
			System.out.println("あいこです");
		}
		else if((player.equals("r") && enemy.equals("s")) || (player.equals("s") && enemy.equals("p")) || (player.equals("p") && enemy.equals("r"))){
			System.out.println("自分の勝ちです");
		}
		else if((player.equals("r") && enemy.equals("p")) || (player.equals("s") && enemy.equals("r")) || (player.equals("p") && enemy.equals("s"))){
			System.out.println("自分の負けです");
		}
	}
}
