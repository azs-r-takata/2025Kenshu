package kadai_018;

public class KatoTaro_Chapter18 extends Kato_Chapter18 {
	public void setGivenName() { //メソッドのオーバーライド
		givenName = "太郎";
	}
	
	public void eachIntroduce() {
		System.out.println("私はJavaが得意です"); //抽象メソッド内の処理を追加
	}
}
