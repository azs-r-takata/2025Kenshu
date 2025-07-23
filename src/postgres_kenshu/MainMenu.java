package postgres_kenshu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
	public static void main(String[] args) {

		PostgresSystem postgresSystem = new PostgresSystem();
		boolean end = false;
		
		do {
			System.out.println("==============================");
			System.out.println("会員管理システム - メインメニュー");
			System.out.println("==============================");
			System.out.println("1. 会員一覧表示");
			System.out.println("2. 会員を新規登録");
			System.out.println("3. 会員情報を更新");
			System.out.println("4. 会員を削除");
			System.out.println("5. 会員IDで検索");
			System.out.println("0. 終了");
			System.out.println("==============================");
			System.out.print("操作を選択してください（0～5）:");
			
			postgresSystem.scanner = new Scanner(System.in); //項目の選択入力
			
			try{
				int menu = postgresSystem.scanner.nextInt();
				
				switch(menu) {
				case 1:
					System.out.println(">> メニュー「1」を選択");
					postgresSystem.DBShow(); //会員一覧表示メソッド
					break;
				case 2:
					System.out.println(">> メニュー「2」を選択");
					postgresSystem.DBAdd(); //会員新規登録メソッド
					break;
				case 3:
					System.out.println(">> メニュー「3」を選択");
					postgresSystem.DBUpdate(); //会員情報更新メソッド
					break;
				case 4:
					System.out.println(">> メニュー「4」を選択");
					postgresSystem.DBDelete(); //会員削除メソッド
					break;
				case 5:
					System.out.println(">> メニュー「5」を選択");
					postgresSystem.DBSearch(); //会員ID検索メソッド
					break;
				case 0:
					System.out.println(">> メニュー「0」を選択");
					end = postgresSystem.DBEnd(); //終了メソッド
					break;
				default:
					System.out.println("各項目の番号を入力してください。"); //項目番号以外が入力された場合の処理
					System.out.println();
				}
			}catch(InputMismatchException e) {                         //整数が入力されていない場合
				System.out.println("各項目の番号を入力してください。"); //項目番号以外が入力された場合の処理
				System.out.println();
			}
		}while(end == false); 
	}
}
