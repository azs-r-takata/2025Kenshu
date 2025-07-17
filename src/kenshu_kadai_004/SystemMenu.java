package kenshu_kadai_004;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SystemMenu {
	Scanner scanner;
	List<Tasks> tasks = new ArrayList<>();
	int count = 0;
	
	//タスク登録メソッド=====
	public void RegTask() {
		while(true) {
			String name;
			int priority;
			String deadline;
			Date date;
			
			try {
				System.out.print("作業名: ");
				scanner = new Scanner(System.in); //作業名の入力
				name = scanner.next();
			}catch(InputMismatchException e) {
				System.out.println("入力し直してください");
				continue;
			}
			
			try {
				System.out.print("優先度（1～5）: ");
				scanner = new Scanner(System.in); //優先度の入力
				priority = scanner.nextInt();
			}catch(InputMismatchException e) { //整数以外が入力された場合
				System.out.println("入力が不正です。優先度は1～5の整数で入力してください。");
				continue;
			}
			
			if(priority < 1 || priority > 5) { //入力した数が範囲外だった場合
				System.out.println("入力が不正です。優先度は1～5の整数で入力してください。");
				continue;
			}
			
			try {
				System.out.print("締切日（yyyy-MM-dd）: ");
				scanner = new Scanner(System.in); //締切日の入力
				deadline = scanner.next();
				
				String[] checkString = deadline.split("-"); //入力された文字列を年・月・日で分ける
				int[] checkInt = new int[checkString.length];
				for(int i = 0; i < checkString.length; i++) {
				    checkInt[i] = Integer.parseInt(checkString[i]); //それぞれをInt型に変換する。
				}
				
				if(checkInt[0] < 2000 || checkString[0].length() > 4 || checkString[0].length() < 4) { //年の入力に異常がないかチェック
					System.out.println("入力が不正です。日付は yyyy-MM-dd の形式で入力してください。");
					continue;
				}
				
				if(checkInt[1] < 1 || checkInt[1] > 12 || checkString[1].length() > 2 || checkString[1].length() < 2) {//月の入力に異常がないかチェック
					System.out.println("入力が不正です。日付は yyyy-MM-dd の形式で入力してください。");
					continue;
				}
				
				if(checkInt[2] < 1 || checkInt[2] > 31 || checkString[2].length() > 2 || checkString[2].length() < 2) {//日の入力に異常がないかチェック
					System.out.println("入力が不正です。日付は yyyy-MM-dd の形式で入力してください。");
					continue;
				}
				
				SimpleDateFormat checkFormat = new SimpleDateFormat("yyyy-MM-dd");
				date = checkFormat.parse(deadline);
				deadline = checkFormat.format(date);
				
			}catch(InputMismatchException e) {
				System.out.println("入力が不正です。日付は yyyy-MM-dd の形式で入力してください。");
				continue;
			}catch(ParseException e) {
				System.out.println("入力が不正です。日付は yyyy-MM-dd の形式で入力してください。");
				continue;
			}catch(IllegalArgumentException e) {
				System.out.println("入力が不正です。日付は yyyy-MM-dd の形式で入力してください。");
				continue;
			}
			this.tasks.add(new Tasks(name, priority, deadline, date));
			System.out.println("→ 登録しました！"); //登録した旨を出力
			count++;
			break;
		}
	}
	//=====End
	
	//一覧表示（登録順）メソッド=====
	public void RegOrder() {
		System.out.println("=== タスク一覧 ===");
		for(int i = 0; i < tasks.size(); i++) {
			System.out.println("[" + (i + 1) + "] ・" + tasks.get(i).name + " | 優先度:" + tasks.get(i).priority + " | 締切:" + tasks.get(i).deadline); //登録されたタスクの表示
			System.out.println();
		}
	}
	//=====End
	
	//締切順に表示メソッド=====
	public void DeaOrder() {
		List<DeadLineOrder> deadlineOrder = new ArrayList<>();
		deadlineOrder.clear();

		for(int i = 0; i < tasks.size(); i++) {
			deadlineOrder.add(new DeadLineOrder(tasks.get(i).name, tasks.get(i).priority, tasks.get(i).deadline, tasks.get(i).date) );
		}
		
		for(int i = 0; i < deadlineOrder.size(); i++) {
			for(int j = 0; j < deadlineOrder.size() - (i + 1); j++) {	 
	    		if(deadlineOrder.get(j).date.after(deadlineOrder.get(j + 1).date)) { //締切日が短い順に並べ替え
	    			String temp1 = deadlineOrder.get(j).name;
	    		    int temp2 = deadlineOrder.get(j).priority;
	    		    String temp3 = deadlineOrder.get(j).deadline;
	    		    deadlineOrder.get(j).name = deadlineOrder.get(j + 1).name;
	    		    deadlineOrder.get(j).priority = deadlineOrder.get(j + 1).priority;
	    		    deadlineOrder.get(j).deadline = deadlineOrder.get(j + 1).deadline;
	    		    deadlineOrder.get(j + 1).name = temp1;
	    		    deadlineOrder.get(j + 1).priority = temp2;
	    		    deadlineOrder.get(j + 1).deadline = temp3;
	    		}
	    	}
		}
		System.out.println("=== 締切順一覧 ===");
		for(int i = 0; i < deadlineOrder.size(); i++) {
			System.out.println("[" + (i + 1) + "] ・" + deadlineOrder.get(i).name + " | 優先度:" + deadlineOrder.get(i).priority + " | 締切:" + deadlineOrder.get(i).deadline); //結果の出力
			System.out.println();
		}
	}
	//=====End
	
	//優先度順に表示メソッド=====
	public void PriOrder() {
		List<PriorityOrder> priorityOrder = new ArrayList<>();
		priorityOrder.clear();

		for(int i = 0; i < tasks.size(); i++) {
			priorityOrder.add(new PriorityOrder(tasks.get(i).name, tasks.get(i).priority, tasks.get(i).deadline, tasks.get(i).date) );
		}
		
	    for(int i = 0; i < priorityOrder.size(); i++) { //優先度が高い順に並べ替え
	    	for(int j = 0; j < priorityOrder.size() - (i + 1); j++) {
	    		if(priorityOrder.get(j).priority < priorityOrder.get(j + 1).priority) {
	    		    String temp1 = priorityOrder.get(j).name;
	    		    int temp2 = priorityOrder.get(j).priority;
	    		    String temp3 = priorityOrder.get(j).deadline;
	    		    priorityOrder.get(j).name = priorityOrder.get(j + 1).name;
	    		    priorityOrder.get(j).priority = priorityOrder.get(j + 1).priority;
	    		    priorityOrder.get(j).deadline = priorityOrder.get(j + 1).deadline;
	    		    priorityOrder.get(j + 1).name = temp1;
	    		    priorityOrder.get(j + 1).priority = temp2;
	    		    priorityOrder.get(j + 1).deadline = temp3;
	    		}
	    	}
	    }
	    
	    System.out.println("=== 優先度順一覧 ===");
		for(int i = 0; i < priorityOrder.size(); i++) {
			System.out.println("[" + (i + 1) + "] ・" + priorityOrder.get(i).name + " | 優先度:" + priorityOrder.get(i).priority + " | 締切:" + priorityOrder.get(i).deadline); //結果の出力
			System.out.println();
		}
	}
	//=====End
	
	//タスク削除メソッド=====
	public void DelTask() {
		System.out.println("=== タスク一覧 ===");
		for(int i = 0; i < tasks.size(); i++) {
			System.out.println("[" + (i + 1) + "] ・" + tasks.get(i).name + " | 優先度:" + tasks.get(i).priority + " | 締切:" + tasks.get(i).deadline); //タスク一覧の表示
			System.out.println();
		}
		
		while(true) {
			int delNumber;
			try {
				System.out.print("削除したいタスクの番号を入力してください: ");
				scanner = new Scanner(System.in); //削除するタスクの入力
				delNumber = scanner.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("入力が不正です。入力し直してください。");
				continue;
			}catch(IndexOutOfBoundsException e) {
				System.out.println("入力が不正です。入力し直してください。");
				continue;
			}
			
			if(delNumber <= 0 || delNumber > tasks.size()) { //登録されているタスク数以上の数が入力された場合
				System.out.println("該当する番号がありません。入力し直してください。");
				continue;
			}
			else {
				tasks.remove(delNumber - 1);  //タスク名の削除
				System.out.println("→ 削除しました！"); //削除した旨の表示
				break;
			}
		}
	}
	//=====End
	
	//タスク更新メソッド=====
	public void UpdateTask() {
		System.out.println("=== タスク一覧 ===");
		for(int i = 0; i < tasks.size(); i++) {
			System.out.println("[" + (i + 1) + "] ・" + tasks.get(i).name + " | 優先度:" + tasks.get(i).priority + " | 締切:" + tasks.get(i).deadline); //タスク一覧の表示
			System.out.println();
		}
		
		while(true) {
			int updateNumber;
			String name = " ";
			int priority = 0;
			String deadline = " ";
			Date date;
			
			try {
				System.out.print("更新したいタスクの番号を入力してください: ");
				scanner = new Scanner(System.in); //更新したいタスクの番号を入力
				updateNumber = scanner.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("入力が不正です。入力し直してください。");
				continue;
			}
			
			if(updateNumber <= 0 || updateNumber > this.tasks.size()) { //登録されているタスク数以上の数が入力された場合
				System.out.println("該当する番号がありません。入力し直してください。");
				continue;
			}
			
			try {
				System.out.print("新しい作業名（変更しない場合は空Enter） [" + this.tasks.get(updateNumber - 1).name + "]: ");
				scanner = new Scanner(System.in); //変更したいタスク名の入力
				
				String checker = scanner.nextLine();
				if(checker.isEmpty()) { //空Enterが押された際の処理
					name = this.tasks.get(updateNumber - 1).name;
				}
				else { //入力された際の処理
					name = scanner.next();
				}
			}catch(InputMismatchException e) {
				System.out.println("入力し直してください");
				continue;
			}
			
			try {
				System.out.print("新しい優先度（1～5） [" + this.tasks.get(updateNumber - 1).priority + "]: ");
				scanner = new Scanner(System.in); //変更したい優先度の入力
				priority = scanner.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("入力が不正です。優先度は1～5の整数で入力してください。");
				continue;
			}
			
			if(priority < 1 || priority > 5) { //優先度の範囲外を入力した際の処理
				System.out.println("入力が不正です。優先度は1～5の整数で入力してください。");
				continue;
			}
			
			try {
				System.out.print("新しい締切日（yyyy-MM-dd） [" + this.tasks.get(updateNumber - 1).deadline + "]: ");
				scanner = new Scanner(System.in); //変更したい締切日の入力
				deadline = scanner.next();
				
				String[] checkString = deadline.split("-");          //入力した文字列を年・月・日で分ける
				int[] checkInt = new int[checkString.length];
				for(int i = 0; i < checkString.length; i++) {
				    checkInt[i] = Integer.parseInt(checkString[i]); //分けた文字列を整数に変換
				}

				if(checkInt[0] < 2000 || checkString[0].length() > 4 || checkString[0].length() < 4) { //年の内容に不備がないかチェック
					System.out.println("入力が不正です。入力し直してください。");
					continue;
				}
				
				if(checkInt[1] < 1 || checkInt[1] > 12 || checkString[1].length() > 2 || checkString[1].length() < 2) { //月の内容に不備がないかチェック
					System.out.println("入力が不正です。入力し直してください。");
					continue;
				}
				
				if(checkInt[2] < 1 || checkInt[2] > 31 || checkString[2].length() > 2 || checkString[2].length() < 2) {//日の内容に不備がないかチェック
					System.out.println("入力が不正です。入力し直してください。");
					continue;
				}
				
				SimpleDateFormat checkFormat = new SimpleDateFormat("yyyy-MM-dd");
				date = checkFormat.parse(deadline);
				deadline = checkFormat.format(date);
			}catch(InputMismatchException e) {
				System.out.println("入力が不正です。日付は yyyy-MM-dd の形式で入力してください。");
				continue;
			}catch(ParseException e) {
				System.out.println("入力が不正です。日付は yyyy-MM-dd の形式で入力してください。");
				continue;
			}catch(IllegalArgumentException e) {
				System.out.println("入力が不正です。日付は yyyy-MM-dd の形式で入力してください。");
				continue;
			}
			this.tasks.get(updateNumber - 1).name = name;
			this.tasks.get(updateNumber - 1).priority = priority;
			this.tasks.get(updateNumber - 1).deadline = deadline;
			System.out.println("→ 更新しました！");
			break;
		}
	}
	//=====End
}
