package postgres_kenshu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PostgresSystem {
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String user = "postgres";
	String password = "Az-s06";

	public final Scanner scanner = new Scanner(System.in);
	
	public void showDB() {
		System.out.println("---------------- 会員一覧 ----------------");
		System.out.println("ID\t|\t名前\t|\tメールアドレス\t|\t電話番号\t|\t住所");
		System.out.println("------------------------------------------------------------");
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			String select = "SELECT id, name, adress, phone, city, delete_flag FROM members";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(select);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String adress = rs.getString("adress");
				String phone = rs.getString("phone");
				String city = rs.getString("city");
				boolean delete_flag = rs.getBoolean("delete_flag");
				
				if(delete_flag == false) {
					System.out.printf("%-4s| %-10s| %-25s| %-15s| %-10s\n", id, name, adress, phone, city);
				}
			}
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
		System.out.println("------------------------------------------------------------");
	}
	
	public void addDB() {
		String name = null;
		String adress = null;
		String phone = null;
		String city = null;
		
		System.out.println("---------------- 新規会員登録 ----------------");
		while(true) {
			System.out.print("会員名：");
			try {
				String checker = scanner.nextLine();
				if(checker.isEmpty()) { //空Enterが押された際の処理
					System.out.println("※会員名は必須です");
					System.out.println("入力し直してください");
					continue;
				}
				else { //入力された際の処理
					name = checker;
				}
			}catch (InputMismatchException e) {
				System.out.println("入力された内容が無効です");
				System.out.println("入力し直してください");
				continue;
			}
			
			System.out.print("メールアドレス：");
			try {
				String checker = scanner.nextLine();
				if(checker.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) { //空Enterが押された際の処理
					adress = checker;
				}
				else { //入力された際の処理
					System.out.println("※メールアドレスの形式が不正です");
					System.out.println("入力し直してください");
					continue;
				}
			}catch (InputMismatchException e) {
				System.out.println("入力された内容が無効です");
				System.out.println("入力し直してください");
				continue;
			}
			
			System.out.print("電話番号（任意）：");
			try {
				String checker = scanner.nextLine();
				if(checker.isEmpty()) { //空Enterが押された際の処理
					phone = " ";
				}
				else { //入力された際の処理
					 phone= checker;
				}
			}catch (InputMismatchException e) {
				System.out.println("入力された内容が無効です");
				System.out.println("入力し直してください");
				continue;
			}
			
			System.out.print("住所（任意）：");
			try {
				String checker = scanner.nextLine();
				if(checker.isEmpty()) { //空Enterが押された際の処理
					 city = " ";
				}
				else { //入力された際の処理
					 city = checker;
				}
			}catch (InputMismatchException e) {
				System.out.println("入力された内容が無効です");
				System.out.println("入力し直してください");
				continue;
			}
			System.out.println("------------------------------------------------");
			break;
		}
		
		System.out.print("この内容で登録しますか？（y/n）：");
		String check = scanner.next();
		int count = getNumber();
		if(check.equalsIgnoreCase("y")) {
			try (Connection conn = DriverManager.getConnection(url, user, password)) {
				if(count <= 0) {
					count = 1;
				}
				count += 1;
				String insert = "INSERT INTO members (id, name, adress, phone, city, delete_flag) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(insert);
				pstmt.setInt(1, count);
				pstmt.setString(2, name);
				pstmt.setString(3, adress);
				pstmt.setString(4, phone);
				pstmt.setString(5, city);
				pstmt.setBoolean(6, false);
				
				conn.setAutoCommit(false);
				pstmt.executeUpdate();
				conn.commit();
				
				System.out.println(">>> 登録が完了しました。（会員ID：" + (count) +"）");
			}catch (SQLException e) {
				System.out.println("接続失敗");
				e.printStackTrace();
				System.out.println("アプリを終了します。");
				System.exit(1);
			}
		}
		else {
			return;
		}
	}
	
	public void updateDB() {
		int id_number = 0;
		String name = null;
		String adress = null;
		String phone = null;
		String city = null;
		int count = getNumber();
		
		System.out.println("---------------- 会員情報更新 ----------------");
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			while (true) {
				try {
					System.out.print("更新対象の会員ID：");
					
					String checker = scanner.nextLine();
					if(checker.isEmpty()) { //空Enterが押された際の処理
						return;
					}
					else { //入力された際の処理
						id_number = Integer.parseInt(checker);
						
						if(id_number == 0 || id_number > count) {
							System.out.println(">>> 該当するIDの会員が見つかりません");
							System.out.println("入力し直してください");
							continue;
						}
						System.out.println();
						break;
					}
				}catch (InputMismatchException e) {
					System.out.println("入力された内容が無効です");
					System.out.println("入力し直してください");
					continue;
				}
			}
			
			while(true) {
				System.out.print("新しい会員名：");
				try {
					String checker = scanner.nextLine();
					if(checker.isEmpty()) { //空Enterが押された際の処理
						System.out.println("※会員名は必須です");
						System.out.println("入力し直してください");
						continue;
					}
					else { //入力された際の処理
						name = checker;
					}
				}catch (InputMismatchException e) {
					System.out.println("入力された内容が無効です");
					System.out.println("入力し直してください");
					continue;
				}
				
				System.out.print("新しいメールアドレス：");
				try {
					String checker = scanner.nextLine();
					if(checker.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) { //空Enterが押された際の処理
						adress = checker;
					}
					else { //入力された際の処理
						System.out.println("※メールアドレスの形式が不正です");
						System.out.println("入力し直してください");
						continue;
					}
				}catch (InputMismatchException e) {
					System.out.println("入力された内容が無効です");
					System.out.println("入力し直してください");
					continue;
				}
				
				System.out.print("新しい電話番号：");
				try {
					String checker = scanner.nextLine();
					if(checker.isEmpty()) { //空Enterが押された際の処理
						phone = " ";
					}
					else { //入力された際の処理
						 phone= checker;
					}
				}catch (InputMismatchException e) {
					System.out.println("入力された内容が無効です");
					System.out.println("入力し直してください");
					continue;
				}
				
				System.out.print("新しい住所：");
				try {
					String checker = scanner.nextLine();
					if(checker.isEmpty()) { //空Enterが押された際の処理
						 city = " ";
					}
					else { //入力された際の処理
						 city = checker;
					}
				}catch (InputMismatchException e) {
					System.out.println("入力された内容が無効です");
					System.out.println("入力し直してください");
					continue;
				}
				System.out.println("------------------------------------------------");
				break;
			}
			
			System.out.print("この内容で更新しますか？（y/n）：");
			String check = scanner.next();
			if(check.equalsIgnoreCase("y")) {
				String update = "UPDATE members SET name = ?, adress = ?, phone = ?, city = ? WHERE id = ?";
				PreparedStatement pstmt = conn.prepareStatement(update);
				pstmt.setString(1, name);
				pstmt.setString(2, adress);
				pstmt.setString(3, phone);
				pstmt.setString(4, city);
				pstmt.setInt(5, id_number);
					
				conn.setAutoCommit(false);
				pstmt.executeUpdate();
				conn.commit();
					
				System.out.println(">>> 会員ID" + id_number +"の情報を更新しました。");
			}
			else {
				return;
			}
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
	}
	
	public void deleteDB() {
		int id_number = 0;
		String name = null;
		String adress = null;
		int count = getNumber();
		
		System.out.println("---------------- 会員削除 ----------------");
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			while (true) {
				try {
					System.out.print("削除対象の会員ID：");
					
					String checker = scanner.nextLine();
					if(checker.isEmpty()) { //空Enterが押された際の処理
						return;
					}
					else { //入力された際の処理
						id_number = Integer.parseInt(checker);
						
						if(id_number == 0 || id_number > count) {
							System.out.println(">>> 該当するIDの会員が見つかりません");
							System.out.println("入力し直してください");
							continue;
						}
						System.out.println();
						break;
					}
				}catch (InputMismatchException e) {
					System.out.println("入力された内容が無効です");
					System.out.println("入力し直してください");
					continue;
				}
			}
			
			String select = "SELECT name, adress FROM members WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(select);
			pstmt.setInt(1, id_number);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				name = rs.getString("name");
				adress = rs.getString("adress");
			}
			
			System.out.println("会員名：" + name);
			System.out.println("メールアドレス：" + adress);
			System.out.println("------------------------------------------------");
			
			System.out.print("本当に削除してよろしいですか？（y/n）：");
			String check = scanner.next();
			if(check.equalsIgnoreCase("y")) {
				String update = "UPDATE members SET delete_flag = TRUE WHERE id = ?";
				PreparedStatement pstmt_update = conn.prepareStatement(update);
				pstmt_update.setInt(1, id_number);
					
				conn.setAutoCommit(false);
				pstmt_update.executeUpdate();
				conn.commit();
					
				System.out.println(">>> 会員ID" + id_number +"を削除しました。(論理削除)");
			}
			else {
				return;
			}
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
	}
	
	public void searchDB() {
		int id_number = 0;
		int count = getNumber();
		String name = null;
		String adress = null;
		String phone = null;
		String city = null;
		boolean delete_flag = false;
		
		System.out.println("---------------- 会員検索 ----------------");
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			while (true) {
				try {
					System.out.print("検索する会員ID：");
					
					String checker = scanner.nextLine();
					if(checker.isEmpty()) { //空Enterが押された際の処理
						return;
					}
					else { //入力された際の処理
						id_number = Integer.parseInt(checker);
						
						if(id_number == 0 || id_number > count) {
							System.out.println(">>> 該当するIDの会員が見つかりません");
							System.out.println("入力し直してください");
							continue;
						}
						System.out.println();
						break;
					}
				}catch (InputMismatchException e) {
					System.out.println("入力された内容が無効です");
					System.out.println("入力し直してください");
					continue;
				}
			}
			
			System.out.println("---会員詳細（ID:" + id_number +  "）---");
			
			String select = "SELECT name, adress, phone, city, delete_flag FROM members WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(select);
			pstmt.setInt(1, id_number);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				name = rs.getString("name");
			    adress = rs.getString("adress");
				phone = rs.getString("phone");
				city = rs.getString("city");
			    delete_flag = rs.getBoolean("delete_flag");
			}
			
			System.out.println("名前　　　　：" + name);
			System.out.println("メールアドレス：" + adress);
			System.out.println("電話番号　　：" + phone);
			System.out.println("住所　　　　：" + city);
			System.out.println("削除フラグ　：" + delete_flag);
			System.out.println("------------------------------------------------");
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
	}
	
	public boolean endDB() {
		System.out.println("会員管理システムを終了します。お疲れ様でした！");
		scanner.close();
		boolean end =true;
		return end;
	}
	
	public int getNumber() {
		int count = 0;
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			Statement stmt = conn.createStatement();
		    String last = "SELECT id FROM members ORDER BY id DESC LIMIT 1";
		    ResultSet rs = stmt.executeQuery(last);
		
		    while(rs.next()) {
			     count =  rs.getInt("id");	
		    }
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
		return count;
	}
}
