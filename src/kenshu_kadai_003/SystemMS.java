package kenshu_kadai_003;

import java.util.Scanner;

public class SystemMS {
	Scanner scanner = new Scanner(System.in);
	boolean end = false;
	
	public String[][] InputPoint(String[][] maps, String[][] mines) {
		System.out.print("開きたいマスを指定してください（例: A3）: ");
		int tate = 0;
		int yoko = 0;
		
		while(true) {
			scanner = new Scanner(System.in); //消去したいマスの入力
			String block = scanner.next();
			System.out.println();
			
			if(block.length() > 2) {
				System.out.println("入力文字が多すぎます! ２文字で入力してください。");
				System.out.print("開きたいマスを指定し直してください: ");
				continue;
			}else if(block.length() < 2) {
				System.out.println("入力文字が少なすぎます! ２文字で入力してください。");
				System.out.print("開きたいマスを指定し直してください: ");
				continue;
			}
			
			String[] check = {block.substring(0,1), block.substring(1,2)};
			
			//入力値の１つ目のないように対しての処理=====
			switch(check[0]) {
			case "A":
				tate = 1;
				break;
			case "B":
				tate = 2;
				break;
			case "C":
				tate = 3;
				break;
			case "D":
				tate = 4;
				break;
			case "E":
				tate = 5;
				break;
			case "F":
				tate = 6;
				break;
			case "G":
				tate = 7;
				break;
			case "H":
				tate = 8;
				break;
			case "I":
				tate = 9;
				break;
			case "J":
				tate = 10;
				break;
			default: //想定していた入力でない場合、やり直し
				System.out.println("大文字英字と数字を入力してください！");
				System.out.print("開きたいマスを指定し直してください: ");
				continue;
			}
			//=====End
			
			try {
				yoko = Integer.parseInt(check[1]) + 1; //入力された数字を文字列から数値に変換
			}catch(IllegalArgumentException e) { //想定された入力でない場合、やり直し
				System.out.println("大文字英字と数字を入力してください！");
				System.out.print("開きたいマスを指定し直してください: ");
				continue;
			}
			break;
		}
		
		
		if(mines[tate][yoko].equals("*")) { //指定したマスがマインの場所であるか判定
			maps[tate][yoko] = " ";
			for(int i = 1; i < 11; i++) {
				for(int j = 1; j < 11; j++) {
					if(!(maps[i][j].equals(" "))) {
						maps[i][j] = mines[i][j];
					}
				}
			}
			
			System.out.println("地雷を踏みました！ ゲームオーバー。"); //ゲームオーバーの表示
			System.out.println();
			end = true; //ゲーム終了の真偽値ON
			scanner.close();
		}
		else { //マインの場所でない場合
			int checkCount = 0;
			maps[tate][yoko] = " ";
			
			for(int i = 1; i < 11; i++) {
				for(int j = 1; j < 11; j++) {
					if(maps[i][j].equals(" ")) {
						checkCount++;
					}
				}
			}
			
			if(checkCount >= 90) { //マイン以外のマスを削除できたか判定
				System.out.println("ゲームクリア！");//ゲームクリアの表示
				System.out.println();
				end = true; //ゲーム終了の真偽値ON
				scanner.close();
			}
			else {
				for(int i = tate - 1; i <= tate + 1; i++) { //削除したマスの周りを表示
					for(int j = yoko - 1; j <= yoko + 1; j++) {
						if(!(i == 0) && !(i == 11) && !(j == 0) && !(j == 11) && !(maps[i][j].equals(" "))) {
							maps[i][j] = mines[i][j];
						}
					}
				}
			}
			
		}
		return maps;
	}
	
	public boolean ChangeMap(String[][] maps) { //現在のマップの表示
		for(int i = 0; i < 11; i++) {
			for(int j = 0; j < 11; j++) {
				System.out.print(maps[i][j] + "\t");
			}
			System.out.println();
			System.out.println();
		}
		return end;	
	}
}
