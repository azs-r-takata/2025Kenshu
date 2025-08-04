package postgres_kenshu002;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ShowSystem {
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String user = "postgres";
	String password = "Az-s06";

	public final Scanner scanner = new Scanner(System.in);
	
	List<String> member = new ArrayList<>();
	List<String> categoryAcb = new ArrayList<>();
	List<String> categoryNum = new ArrayList<>();
	
	public void readCSV() {
		try{
			Connection conn = DriverManager.getConnection(url, user, password);
			//データベース内にデータがあるか確認=====
			String exists = "SELECT EXISTS(SELECT * FROM usage_records_acb) ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(exists);
			boolean check = false;
			while(rs.next()) {
				check = rs.getBoolean("exists");
			}
			//=====End
			
			//CSVファイルの内容をデータベースへ取り込み（実装してほしい機能①）=====
			if(check == false) { //データベース内にデータがない場合に処理開始
				Path acbFile = Paths.get("C:\\7~8月AS研修\\PostgreSQL課題\\usage_records_acb.csv"); //ACBカードの利用履歴CSV
				Path numFile = Paths.get("C:\\7~8月AS研修\\PostgreSQL課題\\usage_records_num.csv"); //NUMカードの利用履歴CSV
				
				List<String> acbList = Files.readAllLines(acbFile, StandardCharsets.UTF_8);
				List<String> numList = Files.readAllLines(numFile, StandardCharsets.UTF_8);
			
				String insertACB = "INSERT INTO usage_records_acb (id, acb_card_id, acb_used_at, acb_amount, acb_merchant, acb_category_id, gps_lat, gps_lng) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				String insertNUM = "INSERT INTO usage_records_num (id, num_card_id, num_used_at, num_amount, num_merchant, num_category_id, terminal_id, device_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
					
				PreparedStatement pstmtACB = null;
				PreparedStatement pstmtNUM = null;
	
				for (int i = 1; i < acbList.size(); i++) {
					String[] acbLine = acbList.get(i).split(",");
					
					pstmtACB = conn.prepareStatement(insertACB);
					pstmtACB.setInt(1, Integer.parseInt(acbLine[0]));
					pstmtACB.setInt(2, Integer.parseInt(acbLine[1]));
					pstmtACB.setTimestamp(3, Timestamp.valueOf(acbLine[2]));
					pstmtACB.setInt(4, Integer.parseInt(acbLine[3]));
					pstmtACB.setString(5, acbLine[4]);
					pstmtACB.setInt(6, Integer.parseInt(acbLine[5]));
					pstmtACB.setDouble(7, Double.parseDouble(acbLine[6]));
					pstmtACB.setDouble(8, Double.parseDouble(acbLine[7]));
					
					conn.setAutoCommit(false);
					pstmtACB.executeUpdate();
					conn.commit();
				}
					
				for (int i = 1; i < numList.size(); i++) {
					String[] numLine = numList.get(i).split(",");
					
					pstmtNUM = conn.prepareStatement(insertNUM);
					pstmtNUM.setInt(1, Integer.parseInt(numLine[0]));
					pstmtNUM.setInt(2, Integer.parseInt(numLine[1]));
					//Date date = userFormat.parse(numLine[2]);
					pstmtNUM.setTimestamp(3, Timestamp.valueOf(numLine[2]));
					pstmtNUM.setInt(4, Integer.parseInt(numLine[3]));
					pstmtNUM.setString(5, numLine[4]);
					pstmtNUM.setInt(6, Integer.parseInt(numLine[5]));
					pstmtNUM.setString(7, numLine[6]);
					pstmtNUM.setString(8, numLine[7]);
					
					conn.setAutoCommit(false);
					pstmtNUM.executeUpdate();
					conn.commit();
				}
			}
			//=====End
			
			String getMember = "SELECT name FROM card_members";
			ResultSet rsMember = stmt.executeQuery(getMember);
			while(rsMember.next()) {
				member.add(rsMember.getString("name")); //会員名をリストに格納
			}
			
			String getCategoryAcb = "SELECT name FROM category_acb_master";
			ResultSet rsCategoryAcb = stmt.executeQuery(getCategoryAcb);
			while(rsCategoryAcb.next()) {
				categoryAcb.add(rsCategoryAcb.getString("name")); //ACBカードのカテゴリ名をリストに格納
			}
			
			String getCategoryNum = "SELECT name FROM category_num_master";
			ResultSet rsCategoryNum = stmt.executeQuery(getCategoryNum);
			while(rsCategoryNum.next()) {
				categoryNum.add(rsCategoryNum.getString("name")); //NUMカードのカテゴリ名をリストに格納
			}
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}catch (Exception e) {
			System.out.println("ファイルのアップロード失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
	}
	
	//利用履歴の出力（実装してほしい機能②）=====
	public void outputInformation() {
		String memberName = null;
		String cardNumber = null;
		Timestamp useTime = null;
		int amount = 0;
		String categoryName = null;
		String cardType = null;
		
		try(Connection conn = DriverManager.getConnection(url, user, password)){
			System.out.println("---------------- 利用履歴 ----------------");
			Timestamp start = null;
			Timestamp end = null;
			
			while(true) {
				try {
					System.out.print("開始日:");
					String checkStart = scanner.nextLine();
					
					if(checkStart.isEmpty()) { //空Enterが押された際の処理
						System.out.println("メインメニューに戻ります");
						return;
					}
					else { //入力された際の処理
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date parsedDate = dateFormat.parse(checkStart); 
						start = new Timestamp(parsedDate.getTime());
					}
					
					System.out.print("終了日:");
					String checkEnd = scanner.nextLine();
					if(checkEnd.isEmpty()) { //空Enterが押された際の処理
						System.out.println("メインメニューに戻ります");
						return;
					}
					else { //入力された際の処理
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date parsedDate = dateFormat.parse(checkEnd); 
						end = new Timestamp(parsedDate.getTime());
						break;
					}
				}catch (IllegalArgumentException e) {
		            System.out.println("無効な日付形式です。もう一度入力してください (例: 2025-07-24):");
		            continue;
				}
			}
			
			String select = "SELECT m.name AS member_name, c.card_number, a.acb_used_at AS used_at, a.acb_amount AS amount, ca.name AS category_name, c.card_type FROM usage_records_acb a JOIN credit_cards c ON a.acb_card_id = c.id JOIN card_members m ON c.member_id = m.id JOIN category_acb_master ca ON a.acb_category_id = ca.id WHERE a.acb_used_at BETWEEN ? AND ? UNION SELECT m.name AS member_name, c.card_number, n.num_used_at, n.num_amount, cn.name, c.card_type AS category_name FROM usage_records_num n JOIN credit_cards c ON n.num_card_id = c.id JOIN card_members m ON c.member_id = m.id JOIN category_num_master cn ON n.num_category_id = cn.id WHERE n.num_used_at BETWEEN ? AND ? ORDER BY member_name ASC";
			PreparedStatement pstmt = conn.prepareStatement(select);
			pstmt.setTimestamp(1, start);
			pstmt.setTimestamp(2, end);
			pstmt.setTimestamp(3, start);
			pstmt.setTimestamp(4, end);
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("カード情報");
			System.out.println("会員名\t|\tカード番号\t|\t利用日\t|\t金額\t|\tカテゴリ\t|\tカードタイプ");
			System.out.println("------------------------------------------------------------");
			
			while(rs.next()) {
				memberName = rs.getString("member_name");
				cardNumber = rs.getString("card_number");
				useTime = rs.getTimestamp("used_at");
				amount = rs.getInt("amount");
			    categoryName = rs.getString("category_name");
			    cardType = rs.getString("card_type");
			    
			    System.out.printf("%-5s| %-18s| %-22s| %-6s| %-10s| %-5s\n", memberName, cardNumber, useTime, amount, categoryName, cardType);
			}
			System.out.println();
			
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}catch (Exception e) {
			System.out.println("ファイルのアップロード失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
	}
	//=====End
	
	//非利用者の出力（実装してほしい機能③）=====
	public void extractionLostUser() {
		String memberName = null;
		String cardNumber = null;
		String email = null;
		String phone = null;
		Date expiration = null;
		String cardType = null;
		
		try(Connection conn = DriverManager.getConnection(url, user, password)){
            System.out.println("---------------- 最近利用していない会員 ----------------");
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime threeMonthsBefore = now.minusMonths(3);
			
			System.out.println("カード情報");
			System.out.println("会員名\\t|\\tカード番号\\t|\\tメールアドレス\\t|\\t電話番号\\t|\\t有効期限\\t|カード種類");
			String select = "SELECT m.name, c.card_number, m.email, m.phone, c.expiration_date, c.card_type FROM card_members m JOIN credit_cards c ON m.id = c.member_id JOIN usage_records_acb a ON c.id = a.acb_card_id WHERE NOT EXISTS (SELECT id FROM credit_cards WHERE acb_used_at BETWEEN ? AND ?) UNION SELECT m.name, c.card_number, m.email, m.phone, c.expiration_date, c.card_type FROM card_members m JOIN credit_cards c ON m.id = c.member_id JOIN usage_records_num n ON c.id = n.num_card_id WHERE NOT EXISTS (SELECT id FROM credit_cards WHERE num_used_at BETWEEN ? AND ?) ORDER BY name ASC";
			PreparedStatement pstmt = conn.prepareStatement(select);
			pstmt.setTimestamp(1, Timestamp.valueOf(threeMonthsBefore));
			pstmt.setTimestamp(2, Timestamp.valueOf(now));
			pstmt.setTimestamp(3, Timestamp.valueOf(threeMonthsBefore));
			pstmt.setTimestamp(4, Timestamp.valueOf(now));
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memberName = rs.getString("name");
				cardNumber = rs.getString("card_number");
				email = rs.getString("email");
				phone = rs.getString("phone");
				expiration = rs.getDate("expiration_date");
			    cardType = rs.getString("card_type");
			    
			    System.out.printf("%-5s| %-18s| %-22s| %-10s| %-12s| %-5s\n", memberName, cardNumber, email, phone, expiration, cardType);
			}
			System.out.println("------------------------------------------------");
			System.out.println();
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
	}
	//=====End
	
	//利用集計（実装してほしい機能④）=====
	public void outputSum() {
		try(Connection conn = DriverManager.getConnection(url, user, password)){
			System.out.println("---------------- 利用集計 ----------------");
			
			for(String cate : categoryAcb) {
				System.out.println("===" + cate + "===");
				String sumAcb = "SELECT to_char(a.acb_used_at, 'YYYY-MM') AS acb_month, SUM(a.acb_amount) FROM usage_records_acb a JOIN category_acb_master ca ON a.acb_category_id = ca.id WHERE ca.name LIKE ? GROUP BY acb_month order by acb_month";
				PreparedStatement pstmt = conn.prepareStatement(sumAcb);
				pstmt.setString(1, cate);
				ResultSet rsSupers = pstmt.executeQuery();
				
				while(rsSupers.next()) {
					String month = rsSupers.getString("acb_month");
					int sum = rsSupers.getInt("sum");
					System.out.println("◆" + month + ":" + sum + "円");
				}
				System.out.println();
			}
			
			for(String cate : categoryNum) {
				System.out.println("===" + cate + "===");
				String sumNum = "SELECT to_char(n.num_used_at, 'YYYY-MM') AS num_month, SUM(n.num_amount) FROM usage_records_num n JOIN category_num_master na ON n.num_category_id = na.id WHERE na.name LIKE ? GROUP BY num_month order by num_month";
				PreparedStatement pstmt = conn.prepareStatement(sumNum);
				pstmt.setString(1, cate);
				ResultSet rsGasStation = pstmt.executeQuery();
				
				while(rsGasStation.next()) {
					int sum = rsGasStation.getInt("sum");
					String month = rsGasStation.getString("num_month");
					System.out.println("◆" + month + ":" + sum + "円");
				}
	            System.out.println();
			}
			
			System.out.println("------------------------------------------------");
			System.out.println();
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
	}
	//=====End
	
	//各会員の最もよく使うカテゴリ（実装してほしい機能⑤）=====
	public void outputCategory() {
		try(Connection conn = DriverManager.getConnection(url, user, password)){
			System.out.println("---------------- 最もよく使うカテゴリ表示 ----------------");
			
			for(String mem : member) {
				int max = 0;
				String maxCategory = null;
				int maxSum = 0;
				String maxSumCategory = null;
				
				for(String cate : categoryAcb) {
					String counter ="SELECT COUNT(a.acb_card_id) FROM usage_records_acb a JOIN credit_cards c ON a.acb_card_id = c.id JOIN card_members m ON c.member_id = m.id JOIN category_acb_master ca ON a.acb_category_id = ca.id WHERE m.name LIKE ? AND ca.name LIKE ?";
					PreparedStatement pstmtTime = conn.prepareStatement(counter);
					pstmtTime.setString(1, mem);
					pstmtTime.setString(2, cate);
					ResultSet rsTime = pstmtTime.executeQuery();
					
					while(rsTime.next()) {
						int check = rsTime.getInt("count");
						if(max < check) {
							max = check;
							maxCategory = cate;
						}
					}
					
					String sum = "SELECT SUM(a.acb_amount) FROM usage_records_acb a JOIN credit_cards c ON a.acb_card_id = c.id JOIN card_members m ON c.member_id = m.id JOIN category_acb_master ca ON a.acb_category_id = ca.id WHERE m.name LIKE ? AND ca.name LIKE ?";
					PreparedStatement pstmtSum = conn.prepareStatement(sum);
					pstmtSum.setString(1, mem);
					pstmtSum.setString(2, cate);
					ResultSet rsSum = pstmtSum.executeQuery();
					
					while(rsSum.next()) {
						int check = rsSum.getInt("sum");
						if(maxSum < check) {
							maxSum = check;
							maxSumCategory = cate;
						}
					}
				}
				
				for(String cate : categoryNum) {
					String counter ="SELECT COUNT(n.num_card_id) FROM usage_records_num n JOIN credit_cards c ON n.num_card_id = c.id JOIN card_members m ON c.member_id = m.id JOIN category_num_master na ON n.num_category_id = na.id WHERE m.name LIKE ? AND na.name LIKE ?";
					PreparedStatement pstmtTime = conn.prepareStatement(counter);
					pstmtTime.setString(1, mem);
					pstmtTime.setString(2, cate);
					ResultSet rsTime = pstmtTime.executeQuery();
					
					while(rsTime.next()) {
						int check = rsTime.getInt("count");
						if(max < check) {
							max = check;
							maxCategory = cate;
						}
					}
					
					String sum ="SELECT SUM(n.num_amount) FROM usage_records_num n JOIN credit_cards c ON n.num_card_id = c.id JOIN card_members m ON c.member_id = m.id JOIN category_num_master na ON n.num_category_id = na.id WHERE m.name LIKE ? AND na.name LIKE ?";
					PreparedStatement pstmtSum = conn.prepareStatement(sum);
					pstmtSum.setString(1, mem);
					pstmtSum.setString(2, cate);
					ResultSet rsSum = pstmtSum.executeQuery();
					
					while(rsSum.next()) {
						int check = rsSum.getInt("sum");
						if(maxSum < check) {
							maxSum = check;
							maxSumCategory = cate;
						}
					}
				}
				
				System.out.println("===" + mem + "===");
				System.out.println("利用回数が最も多いカテゴリ：" + maxCategory);
				System.out.println("利用金額が最も多いカテゴリ：" + maxSumCategory);
				System.out.println();
			}
			System.out.println("------------------------------------------------");
			System.out.println();
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
	}
	//=====End
	
	//利用頻度クラスタリング（実装してほしい機能⑥）=====
	public void clusteringMember() {
        try(Connection conn = DriverManager.getConnection(url, user, password)){
        	System.out.println("---------------- 利用頻度クラスタリング ----------------");
			
        	LocalDateTime now = LocalDateTime.now();
			LocalDateTime threeMonthsBefore = now.minusMonths(3);
			int count = 1;
			
        	for(String mem : member) {
				int maxTime = 0;
				int maxSum = 0;
				String clustering = null;
				
				String timeAcb ="SELECT COUNT(a.acb_card_id) FROM usage_records_acb a JOIN credit_cards c ON a.acb_card_id = c.id JOIN card_members m ON c.member_id = m.id WHERE m.name LIKE ? AND a.acb_used_at BETWEEN ? AND ?";
				PreparedStatement pstmtAcbTime = conn.prepareStatement(timeAcb);
				pstmtAcbTime.setString(1, mem);
				pstmtAcbTime.setTimestamp(2, Timestamp.valueOf(threeMonthsBefore));
				pstmtAcbTime.setTimestamp(3, Timestamp.valueOf(now));
				ResultSet rsAcbTime = pstmtAcbTime.executeQuery();
				
				while(rsAcbTime.next()) {
					maxTime += rsAcbTime.getInt("count");
				}
				
				String timeNum ="SELECT COUNT(n.num_card_id) FROM usage_records_num n JOIN credit_cards c ON n.num_card_id = c.id JOIN card_members m ON c.member_id = m.id WHERE m.name LIKE ? AND n.num_used_at BETWEEN ? AND ?";
				PreparedStatement pstmtNumTime = conn.prepareStatement(timeNum);
				pstmtNumTime.setString(1, mem);
				pstmtNumTime.setTimestamp(2, Timestamp.valueOf(threeMonthsBefore));
				pstmtNumTime.setTimestamp(3, Timestamp.valueOf(now));
				ResultSet rsNumTime = pstmtNumTime.executeQuery();
				
				while(rsNumTime.next()) {
					maxTime += rsNumTime.getInt("count");
				}
				
				String sumAcb = "SELECT SUM(a.acb_amount) FROM usage_records_acb a JOIN credit_cards c ON a.acb_card_id = c.id JOIN card_members m ON c.member_id = m.id WHERE m.name LIKE ? AND a.acb_used_at BETWEEN ? AND ?";
				PreparedStatement pstmtAcbSum = conn.prepareStatement(sumAcb);
				pstmtAcbSum.setString(1, mem);
				pstmtAcbSum.setTimestamp(2, Timestamp.valueOf(threeMonthsBefore));
				pstmtAcbSum.setTimestamp(3, Timestamp.valueOf(now));
				ResultSet rsAcbSum = pstmtAcbSum.executeQuery();
				
				while(rsAcbSum.next()) {
					maxSum += rsAcbSum.getInt("sum");
				}
				
				String sumNum ="SELECT SUM(n.num_amount) FROM usage_records_num n JOIN credit_cards c ON n.num_card_id = c.id JOIN card_members m ON c.member_id = m.id WHERE m.name LIKE ? AND n.num_used_at BETWEEN ? AND ?";
				PreparedStatement pstmtNumSum = conn.prepareStatement(sumNum);
				pstmtNumSum.setString(1, mem);
				pstmtNumSum.setTimestamp(2, Timestamp.valueOf(threeMonthsBefore));
				pstmtNumSum.setTimestamp(3, Timestamp.valueOf(now));
				ResultSet rsNumSum = pstmtNumSum.executeQuery();
				
				while (rsNumSum.next()) {
					maxSum += rsNumSum.getInt("sum");
				}
				
				if (maxTime >= 30 || maxSum >= 100000) {
					clustering = "GOLD";
				}
				else if (maxTime >= 10 || maxSum >= 30000) {
					clustering = "SILVER";
				}
				else
				{
					clustering = "BRONZE";
				}
				
				System.out.println("会員ID\t|\t会員名\t|\tクラス\t");
				System.out.printf("%-3s| %-5s| %-7s\n", count, mem, clustering);
				System.out.println();
				count++;
			}
			System.out.println("------------------------------------------------");
			System.out.println();
		}catch (SQLException e) {
			System.out.println("接続失敗");
			e.printStackTrace();
			System.out.println("アプリを終了します。");
			System.exit(1);
		}
	}
	//=====End
	
	//終了処理=====
	public boolean endSystem() {
		System.out.println("クレジットカード情報管理システムを終了します。お疲れ様でした！");
		scanner.close();
		boolean end =true;
		return end;
	}
	//=====End
}
