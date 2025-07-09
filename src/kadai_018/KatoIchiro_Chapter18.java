package kadai_018;

public class KatoIchiro_Chapter18 extends Kato_Chapter18 {
	public void setGivenName() { //メソッドのオーバーライド
		givenName = "一郎";
	}
	
	public void eachIntroduce() {
		System.out.println("好きな食べ物はリンゴです"); //抽象メソッド内の処理を追加
	}

}
