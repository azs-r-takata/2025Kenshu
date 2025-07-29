package postgres_kenshu002;

import java.util.InputMismatchException;

public class CardInfromation {
	public static void main(String[] args) {

		ShowSystem showSystem = new ShowSystem();
		boolean end = false;
		
		showSystem.readCSV();
		
		do {
			System.out.println("==============================");
			System.out.println("利用状況確認システム - メインメニュー");
			System.out.println("==============================");
			System.out.println("1. 利用履歴確認");
			System.out.println("2. 不使用の会員確認");
			System.out.println("3. 利用集計");
			System.out.println("4. 各会員の最もよく使うカテゴリ");
			System.out.println("5. 利用頻度クラスタリング");
			System.out.println("0. 終了");
			System.out.println("==============================");
			System.out.print("操作を選択してください（0～5）:");
			
			try{
				int menu = showSystem.scanner.nextInt(); 
				showSystem.scanner.nextLine();
				switch(menu) {
				case 1:
					System.out.println(">> メニュー「1」を選択");
					showSystem.outputInformation(); //利用履歴出力メソッド
					break;
				case 2:
					System.out.println(">> メニュー「2」を選択");
					showSystem.extractionLostUser(); //非使用者抽出メソッド
					break;
				case 3:
					System.out.println(">> メニュー「3」を選択");
					showSystem.outputSum(); //利用集計メソッド
					break;
				case 4:
					System.out.println(">> メニュー「4」を選択");
					showSystem.outputCategory(); //「最もよく使うカテゴリ」出力メソッド
					break;
				case 5:
					System.out.println(">> メニュー「5」を選択");
					showSystem.clusteringMember(); //クラスタリングメソッド
					break;
				case 0:
					System.out.println(">> メニュー「0」を選択");
					end = showSystem.endSystem(); //終了メソッド
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
