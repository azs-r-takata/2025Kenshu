package kadai_028;

public class JyankenExec_Chapter28 {
	public static void main(String[] args) {
		String player = null;
		String enemy = null;
		
		Jyanken_Chapter28 jyanken = new Jyanken_Chapter28();
		
		player = jyanken.getMyChoice();
		enemy = jyanken.getRandom();
		
		jyanken.playGame(player, enemy);
	}
}