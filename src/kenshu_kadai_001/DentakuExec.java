package kenshu_kadai_001;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DentakuExec {
	public static void main(String[] args) {
		int firstNumber = 0;
		int secondNumber = 0;
		String operator = null;
		
		Dentaku dentaku = new Dentaku();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("1つ目の数値を入力してください:");
		while(true) {
			firstNumber = 0;
			scanner = new Scanner(System.in); //1つ目の数値の入力
			try {
				
				firstNumber = scanner.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("整数の入力ではありません");
				System.out.print("入力し直してください：");
				continue;
			}
			break;
		}
		System.out.println("→ " + firstNumber);
		System.out.println();
		
		System.out.print("演算子を入力してください（+ - * / %）:");
		while(true) {
			operator = null;
			scanner = new Scanner(System.in); //演算子の入力
			try {
				operator = scanner.next();
			}catch(InputMismatchException e) {
				System.out.println("文字列の入力ではありません");
				System.out.print("入力し直してください：");
			}
			
			if(operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equals("%")) {
				break;
			}
			else {
				System.out.println("演算子の入力ではありません");
				System.out.print("入力し直してください：");
			}
		}

		System.out.println("→ " + operator);
		System.out.println();
		
		System.out.print("2つ目の数値を入力してください:");
		while(true) {
			secondNumber = 0;
			scanner = new Scanner(System.in); //2つ目の数値の入力
			try {
				secondNumber = scanner.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("整数の入力ではありません");
				System.out.print("入力し直してください：");
				continue;
			}
			scanner.close();
			break;
		}
		System.out.println("→ " + secondNumber);
		System.out.println();
		
		dentaku.Result(firstNumber, secondNumber, operator);
	}
}
