package kenshu_kadai_001;

public class DentakuExec {
	public static void main(String[] args) {
		long firstNumber = 0;
		long secondNumber = 0;
		String operator = null;
		int count = 1;
		
		Dentaku dentaku = new Dentaku(); //クラスの呼び出し
		
		//入力処理=====
		firstNumber = dentaku.InputNumber(count);
		count++;
		operator = dentaku.InputOprator();
		secondNumber = dentaku.InputNumber(count);
		//=====End
		
		dentaku.Result(firstNumber, secondNumber, operator); //結果の出力
	}
}
