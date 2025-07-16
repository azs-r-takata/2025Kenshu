package kadai_018;

abstract public class Kato_Chapter18 {
	public String familyName = "加藤";
	public String givenName;
	public String address = "東京都中野区〇×";
	
	abstract public void setGivenName(); //オーバーライド用

	public void commonIntroduce() {
		System.out.println("名前は" + this.familyName + this.givenName + "です"); //名前の出力
		System.out.println("住所は" + this.address + "です"); //住所の出力
	}
	
	abstract public void eachIntroduce(); //抽象メソッド
	
	public void execIntroduce() { //各メソッドの実行
		this.setGivenName();
		this.commonIntroduce();
		this.eachIntroduce();
	}
}
