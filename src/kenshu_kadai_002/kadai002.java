package kenshu_kadai_002;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class kadai002 {
	public static void main(String[] args) {
		
		ArrayList<Products> products = new ArrayList<>();
		ArrayList<Integer> sum = new ArrayList<>();
		
		String name;
		int money;
		int total = 0;
		int maxValue = 0;
		int maxTime = 0;
		String valueName = null;
		String timeName = null;
		int count = 0;
		
		Scanner scanner; 
		
		while(true) {
			while(true) {
				System.out.println("商品名を入力してください");
				
				try {
					scanner = new Scanner(System.in); //商品名の入力
					name = scanner.next();
				}catch(InputMismatchException e) {
					System.out.println("文字列ではありません");
					System.out.println("入力し直してください");
					continue;
				}
				break;
			}
			if(name.equals("end")) { //「end」を入れたか確認
				scanner.close();
				break;
			}
			
			while(true) {
				System.out.println("金額を入力してください");
				
				try {
					scanner = new Scanner(System.in); //金額の入力
					money = scanner.nextInt();
				}catch(InputMismatchException e) {
					System.out.println("整数ではありません");
					System.out.println("入力し直してください");
					continue;
				}
				if(money < 0) {
					System.out.println("正の数ではありません");
					System.out.println("入力し直してください");
					continue;
				}
				break;
			}
			products.add(new Products(name,money));
			count++;
		}
		
		System.out.println("=== 商品別売上分析 ===");
		for(int i = 0; i < count; i++) {
			int ram = (int)Math.floor(Math.random() * 50); //回数をランダムの設定
			sum.add(products.get(i).money * ram); 
			total += sum.get(i);
			
			if(maxValue < sum.get(i)) {
				maxValue = sum.get(i);
				valueName = products.get(i).name;
			}
			
			if(maxTime < ram) {
				maxTime = ram;
				timeName = products.get(i).name;
			}
			System.out.println(products.get(i).name + ": 回数=" + ram + " 合計=" + sum.get(i) + " 平均=" + (sum.get(i) / ram)); //結果の出力
		}
		System.out.println();
		
		System.out.println("=== 全体統計 ===");
		System.out.println("総売上：" + total + "円");
		System.out.println("最も売上が多かった商品：" + valueName + "（" + maxValue + "円）");
		System.out.println("最もよく売れた商品：" + timeName + "（" + maxTime + "回）");
	}
}
