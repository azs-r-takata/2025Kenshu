package kenshu_kadai_001;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Dentaku {
	Scanner scanner;
	
	public long InputNumber(int count) {
		long number;
		System.out.print(count + "つ目の数値を入力してください:");
		
		while (true) {
			number = 0;
			scanner = new Scanner(System.in); //1つ目の数値の入力
			try {
				number = scanner.nextInt();
			}catch (InputMismatchException e) {
				System.out.println("整数の入力ではありません");
				System.out.print("入力し直してください：");
				continue;
			}
			break;
		}
		System.out.println("→ " + number);
		System.out.println();
		return number;
	}
	
	public String InputOprator() {
		String operator;
		System.out.print("演算子を入力してください（+ - * / %）:");
		
		while (true) {
			operator = null;
			scanner = new Scanner(System.in); //演算子の入力
			try {
				operator = scanner.next();
			}catch (InputMismatchException e) {
				System.out.println("文字列の入力ではありません");
				System.out.print("入力し直してください：");
			}
			
			if (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equals("%")) {
				break;
			}
			else {
				System.out.println("演算子の入力ではありません");
				System.out.print("入力し直してください：");
			}
		}
		System.out.println("→ " + operator);
		System.out.println();
		return operator;
	}
	
	public void Result(long first, long second, String operator) {
		long result = 0;
		
		try {
			switch (operator) { //入力された記号ごとの計算
				case "+" -> result = first + second;
				case "-" -> result = first - second;
				case "*" -> result = first * second;
				case "/" -> result = first / second;
				case "%" -> result = first % second;
			}
		}catch(ArithmeticException e){
			System.out.println("結果：" + first + " " + operator + " " + second + " = 計算できません（０で割ることはできません）"); //計算が出来なかった際の出力
			return;
		}
		System.out.println("結果：" + first + " " + operator + " " + second + " = " + result); //結果の出力
		return;
	}
}
