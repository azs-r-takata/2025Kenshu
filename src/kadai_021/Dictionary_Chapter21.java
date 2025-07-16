package kadai_021;

import java.util.HashMap;

public class Dictionary_Chapter21 {
	HashMap<String, String> dic = new HashMap<>();
	
	public Dictionary_Chapter21() {  //初期化
		String[] key = {"apple", "peach", "banana", "lemon", "pear", "kiwi", "strawberry", "grape", "muscat", "cherry"};
		String[] words = {"りんご", "桃", "バナナ", "レモン", "梨", "キウィ", "いちご", "ぶどう", "マスカット", "さくらんぼ"};
		
		for(int i = 0; i < key.length; i++) {
			dic.put(key[i], words[i]);
		}
	}
	
	
	public void search(String name) {
		if(dic.containsKey(name)) { //HashMap内に対応するキーがあるか判定
			System.out.println(name + "の意味は" + dic.get(name));
		}
		else {
			System.out.println(name + "は辞書に存在しません");
		}
	}
}
