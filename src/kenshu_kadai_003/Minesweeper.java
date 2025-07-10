package kenshu_kadai_003;

public class Minesweeper {
	public static void main(String[] args) {
		String[][] map = new String[12][12];
		String[][] mine = new String[12][12];
		boolean endCheck = false;
		
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				mine[i][j] = "0";
			}
		}
		
		SystemMS systemMS = new SystemMS(); //クラスの呼び出し
		
		//マインの設置=====
		for(int i = 0; i < 10; i++) {
			while(true) {
				int ran = (int)Math.floor(Math.random() * 10) + 1;
				int dom = (int)Math.floor(Math.random() * 10) + 1;
				
				if(!(mine[ran][dom].equals("*"))) {
					mine[ran][dom] = "*";
					break;
				}
				else {
					continue;
				}
			}
		}
		//=====End
		
		//マップの作成=====
		for(int i = 0; i < 11; i++) {
			for(int j = 0; j < 11; j++) {
				if(i == 0 && j == 0) {
					map[i][j] = " ";
				}
				else if (i == 0) {
					switch(j) {
					case 1 -> map[i][j] = "0";
					case 2 -> map[i][j] = "1";
					case 3 -> map[i][j] = "2";
					case 4 -> map[i][j] = "3";
					case 5 -> map[i][j] = "4";
					case 6 -> map[i][j] = "5";
					case 7 -> map[i][j] = "6";
					case 8 -> map[i][j] = "7";
					case 9 -> map[i][j] = "8";
					case 10 -> map[i][j] = "9";
					}
				}
				else if(j == 0) {
					switch(i) {
					case 1 -> map[i][j] = "A";
					case 2 -> map[i][j] = "B";
					case 3 -> map[i][j] = "C";
					case 4 -> map[i][j] = "D";
					case 5 -> map[i][j] = "E";
					case 6 -> map[i][j] = "F";
					case 7 -> map[i][j] = "G";
					case 8 -> map[i][j] = "H";
					case 9 -> map[i][j] = "I";
					case 10 -> map[i][j] = "J";
					}
				}
				else {
					map[i][j] = "#";
					
					//マインの以外の数字の設置=====
					if(!mine[i][j].equals("*")) {
						int checkCount = 0;
						for(int k = i - 1; k <= i + 1; k++) {
							for(int l = j - 1; l <= j + 1; l++) {
								if(mine[k][l].equals("*")) {
									checkCount++;
								}
							}
						}
						mine[i][j] = String.valueOf(checkCount);
					}
					//=====End
				}
				
				System.out.print(map[i][j] + "\t");
			}
			System.out.println();
			System.out.println();
		}
		//=====End
		
		//メソッドをゲーム終了の真偽値が帰ってくるまで繰り返す=====
		do {
			map = systemMS.InputPoint(map, mine); //消去するマスの入力と処理をするメソッド
			endCheck = systemMS.ChangeMap(map); //処理が終わった後のマップを表示するメソッド
		}while(endCheck == false);
		//=====End
		
		
		
	}
}
