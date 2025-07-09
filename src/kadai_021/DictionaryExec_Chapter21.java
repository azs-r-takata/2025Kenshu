package kadai_021;

public class DictionaryExec_Chapter21 {
	public static void main(String[] args) {
		String[] word = {"apple", "banana", "grape", "orange"};
		
		Dictionary_Chapter21 dictionary = new Dictionary_Chapter21();
		
		for(String check : word) {
			dictionary.search(check);
		}
		
	}
}
