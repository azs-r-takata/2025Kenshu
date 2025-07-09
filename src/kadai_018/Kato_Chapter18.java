package kadai_018;

abstract public class Kato_Chapter18 {
	public String familyName;
	public String givenName;
	public String address;
	
	public Kato_Chapter18() {
		familyName = "加藤";
		address = "東京都中野区〇×";
	}
	
	public void setGivenName() {
		givenName = "Name";
	}
	
	public void commonIntroduce() {
		System.out.println("名前は" + this.familyName + this.givenName + "です");
		System.out.println("住所は" + this.address + "です");
	}
	
	abstract public void eachIntroduce();
	
	public void execIntroduce() {
		setGivenName();
		commonIntroduce();
		eachIntroduce();
	}
}
