package kadai_018;

abstract public class Kato_Chapter18 {
	public String familyName;
	public String givenName;
	public String address;
	
	public Kato_Chapter18() { //初期化
		familyName = "加藤";
		address = "東京都中野区〇×";
	}
	
	public void setGivenName() { //オーバーライド用
		givenName = "Name";
	}
	
	public void commonIntroduce() {
		System.out.println("名前は" + this.familyName + this.givenName + "です"); //名前の出力
		System.out.println("住所は" + this.address + "です"); //住所の出力
	}
	
	abstract public void eachIntroduce(); //抽象メソッド
	
	public void execIntroduce() { //各メソッドの実行
		setGivenName();
		commonIntroduce();
		eachIntroduce();
	}
}
