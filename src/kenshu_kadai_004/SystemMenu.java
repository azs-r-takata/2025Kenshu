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
	List<String> taskName = new ArrayList<>();
	List<Integer> priority = new ArrayList<>();
	List<String> deadLine = new ArrayList<>();
	List<Date> checkDate = new ArrayList<>();
	int count = 0;
	
	//タスク登録メソッド=====
	public void RegTask() {
		while(true) {
			String name;
			int pri;
			String day;
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
				pri = scanner.nextInt();
			}catch(InputMismatchException e) { //整数以外が入力された場合
				System.out.println("入力が不正です。優先度は1～5の整数で入力してください。");
				continue;
			}
			
			if(pri < 1 || pri > 5) { //入力した数が範囲外だった場合
				System.out.println("入力が不正です。優先度は1～5の整数で入力してください。");
				continue;
			}
			
			
			try {
				System.out.print("締切日（yyyy-MM-dd）: ");
				scanner = new Scanner(System.in); //締切日の入力
				day = scanner.next();
				
				String[] checkString = day.split("-"); //入力された文字列を年・月・日で分ける
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
				date = checkFormat.parse(day);
				
				day = checkFormat.format(date);
				
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
			
			
			
			taskName.add(name);  //タスク名のリストに追加
			priority.add(pri);   //優先度のリストに追加
			deadLine.add(day);   //締切日のリストに追加
			checkDate.add(date); 
			
			System.out.println("→ 登録しました！"); //登録した旨を出力
			count++;
			break;
		}
	}
	//=====End
	
	//一覧表示（登録順）メソッド=====
	public void RegOrder() {
		System.out.println("=== タスク一覧 ===");
		for(int i = 0; i < taskName.size(); i++) {
			System.out.println("[" + (i + 1) + "] ・" + taskName.get(i) + " | 優先度:" + priority.get(i) + " | 締切:" + deadLine.get(i)); //登録されたタスクの表示
			System.out.println();
		}
	}
	//=====End
	
	//締切順に表示メソッド=====
	public void DeaOrder() {
		String[] deaName = taskName.toArray(new String[taskName.size()]);        //タスク名リストの内容を配列に格納
		Integer[] deaPriority = priority.toArray(new Integer[priority.size()]); //優先度リストの内容を配列に格納
		String[] deaDeadLine = deadLine.toArray(new String[deadLine.size()]);   //締切日リストの内容を配列に格納
		Date[] deaDate = checkDate.toArray(new Date[checkDate.size()]);
		
		for(int i = 0; i < deaDate.length; i++) {
			for(int j = 0; j < deaDate.length - (i + 1); j++) {
	    		int result = deaDate[j].compareTo(deaDate[j + 1]);	 //締切日が短い順に並べ替え
	    		if(result > 0) {
	    			String temp1 = deaName[j];
	    		    int temp2 = deaPriority[j];
	    		    String temp3 = deaDeadLine[i];
	    		    deaName[j] = deaName[j + 1];
	    		    deaPriority[j] = deaPriority[j + 1];
	    		    deaDeadLine[j] = deaDeadLine[j + 1];
	    		    deaName[j + 1] = temp1;
	    		    deaPriority[j + 1] = temp2;
	    		    deaDeadLine[j + 1] = temp3;
	    		}
	    	}
		}
		
		System.out.println("=== 締切順一覧 ===");
		for(int i = 0; i < deaName.length; i++) {
			System.out.println("[" + (i + 1) + "] ・" + deaName[i] + " | 優先度:" + deaPriority[i] + " | 締切:" + deaDeadLine[i]); //結果の出力
			System.out.println();
		}
	}
	//=====End
	
	//優先度順に表示メソッド=====
	public void PriOrder() {
		String[] priName = taskName.toArray(new String[taskName.size()]);         //タスク名リストの内容を配列に格納
		Integer[] priPriority = priority.toArray(new Integer[priority.size()]);  //優先度リストの内容を配列に格納
		String[] priDeadLine = deadLine.toArray(new String[deadLine.size()]);    //締切日リストの内容を配列に格納
		
	    for(int i = 0; i < priPriority.length; i++) { //優先度が高い順に並べ替え
	    	for(int j = 0; j < priPriority.length - (i + 1); j++) {
	    		if(priPriority[j] < priPriority[j + 1]) {
	    		    String temp1 = priName[j];
	    		    int temp2 = priPriority[j];
	    		    String temp3 = priDeadLine[i];
	    		    priName[j] = priName[j + 1];
	    		    priPriority[j] = priPriority[j + 1];
	    		    priDeadLine[j] = priDeadLine[j + 1];
	    		    priName[j + 1] = temp1;
	    		    priPriority[j + 1] = temp2;
	    		    priDeadLine[j + 1] = temp3;
	    		}
	    	}
	    }
	    
	    System.out.println("=== 優先度順一覧 ===");
		for(int i = 0; i < priPriority.length; i++) {
			System.out.println("[" + (i + 1) + "] ・" + priName[i] + " | 優先度:" + priPriority[i] + " | 締切:" + priDeadLine[i]); //結果の出力
			System.out.println();
		}
	}
	//=====End
	
	//タスク削除メソッド=====
	public void DelTask() {
		System.out.println("=== タスク一覧 ===");
		for(int i = 0; i < taskName.size(); i++) {
			System.out.println("[" + (i + 1) + "] ・" + taskName.get(i) + " | 優先度:" + priority.get(i) + " | 締切:" + deadLine.get(i)); //タスク一覧の表示
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
			
			if(delNumber < 0 || delNumber > taskName.size()) { //登録されているタスク数以上の数が入力された場合
				System.out.println("該当する番号がありません。入力し直してください。");
				continue;
			}
			else {
				taskName.remove(delNumber - 1);  //タスク名の削除
				priority.remove(delNumber - 1);  //優先度の削除
				deadLine.remove(delNumber - 1);  //締切日の削除
				checkDate.remove(delNumber - 1);
				System.out.println("→ 削除しました！"); //削除した旨の表示
				break;
			}
		}
	}
	//=====End
	
	//タスク更新メソッド=====
	public void UpdateTask() {
		System.out.println("=== タスク一覧 ===");
		for(int i = 0; i < taskName.size(); i++) {
			System.out.println("[" + (i + 1) + "] ・" + taskName.get(i) + " | 優先度:" + priority.get(i) + " | 締切:" + deadLine.get(i)); //タスク一覧の表示
			System.out.println();
		}
		
		while(true) {
			int updateNumber;
			String name = " ";
			int pri = 0;
			String day = " ";
			Date date;
			
			try {
				System.out.print("更新したいタスクの番号を入力してください: ");
				scanner = new Scanner(System.in); //更新したいタスクの番号を入力
				updateNumber = scanner.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("入力が不正です。入力し直してください。");
				continue;
			}
			
			if(updateNumber < 0 || updateNumber > taskName.size()) { //登録されているタスク数以上の数が入力された場合
				System.out.println("該当する番号がありません。入力し直してください。");
				continue;
			}
			
			try {
				System.out.print("新しい作業名（変更しない場合は空Enter） [" + taskName.get(updateNumber - 1) + "]: ");
				scanner = new Scanner(System.in); //変更したいタスク名の入力
				
				String checker = scanner.nextLine();
				if(checker.isEmpty()) { //空Enterが押された際の処理
					name = taskName.get(updateNumber - 1);
				}
				else { //入力された際の処理
					name = scanner.next();
				}
			}catch(InputMismatchException e) {
				System.out.println("入力し直してください");
				continue;
			}
			
			try {
				System.out.print("新しい優先度（1～5） [" + priority.get(updateNumber - 1) + "]: ");
				scanner = new Scanner(System.in); //変更したい優先度の入力
				pri = scanner.nextInt();
			}catch(InputMismatchException e) {
				System.out.println("入力が不正です。優先度は1～5の整数で入力してください。");
				continue;
			}
			
			if(pri < 1 || pri > 5) { //優先度の範囲外を入力した際の処理
				System.out.println("入力が不正です。優先度は1～5の整数で入力してください。");
				continue;
			}
			
			
			try {
				System.out.print("新しい締切日（yyyy-MM-dd） [" + deadLine.get(updateNumber - 1) + "]: ");
				scanner = new Scanner(System.in); //変更したい締切日の入力
				day = scanner.next();
				
				String[] checkString = day.split("-");          //入力した文字列を年・月・日で分ける
				int[] checkInt = new int[checkString.length];
				for(int i = 0; i < checkString.length; i++) {
				    checkInt[i] = Integer.parseInt(checkString[i]); //分けた文字列を整数に変換
				}
				System.out.println(checkInt[0]);
				System.out.println(checkInt[1]);
				System.out.println(checkInt[2]);
				
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
				date = checkFormat.parse(day);
				
				day = checkFormat.format(date);
				
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
			
			taskName.set(updateNumber - 1, name); //タスク名を変更
			priority.set(updateNumber - 1, pri);  //優先度を変更
			deadLine.set(updateNumber - 1, day);  //締切日を変更
			System.out.println("→ 更新しました！");
			break;
		}
	}
	//=====End
}
