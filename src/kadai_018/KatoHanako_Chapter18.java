package kadai_018;

public class KatoHanako_Chapter18 extends Kato_Chapter18 {
	public void setGivenName() { //メソッドのオーバーライド
		givenName = "花子";
	}
	
	public void eachIntroduce() { 
		System.out.println("趣味は読書です"); //抽象メソッド内の処理を追加
	}

}
