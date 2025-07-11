package kenshu_kadai_004;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskScheduler {
	public static void main(String[] args) {
		SystemMenu systemMenu = new SystemMenu(); //クラスの呼び出し
		boolean end = false;                      //システムの終了確認用
		
		do {
			System.out.println("=== タスク管理CLI ===");
			System.out.println("1. タスク登録");
			System.out.println("2. 一覧表示（登録順）");
			System.out.println("3. 締切順に表示");
			System.out.println("4. 優先度順に表示");
			System.out.println("5. タスク削除");
			System.out.println("6. タスク更新");
			System.out.println("exit. 終了");
			System.out.print("> ");
			
			systemMenu.scanner = new Scanner(System.in); //項目の選択入力
			
			try{
				int menu = systemMenu.scanner.nextInt();
				
				switch(menu) {
				case 1:
					systemMenu.RegTask();    //タスク登録メソッド
					break;
				case 2:
					systemMenu.RegOrder();   //一覧表示（登録順）メソッド
					break;
				case 3:
					systemMenu.DeaOrder();   //締切順に表示メソッド
					break;
				case 4:
					systemMenu.PriOrder();   //優先度順に表示メソッド
					break;
				case 5:
					systemMenu.DelTask();    //タスク削除メソッド
					break;
				case 6:
					systemMenu.UpdateTask(); //タスク更新メソッド
					break;
				default:
					System.out.println("各項目の番号もしくは「exit」を入力してください。"); //項目番号以外が入力された場合の処理
					System.out.println();
					continue;
				}
			}catch(InputMismatchException e) {          //整数が入力されていない場合
				String exit = systemMenu.scanner.next();
				if(exit.equals("exit")) {                //入力されたのが「exit」か確認。
					System.out.println("終了");
					systemMenu.scanner.close();
					end = true;                         //終了判定をON
				}
				else {
					System.out.println("各項目の番号もしくは「exit」を入力してください。");
					System.out.println();
					continue;
				}
			}
		}while(end == false); 
	}
}
