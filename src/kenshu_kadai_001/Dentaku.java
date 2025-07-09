package kenshu_kadai_001;

public class Dentaku {
	
	public void Result(int first, int second, String ope) {
		int result = 0;
		
		while(true) {
			try {
				switch(ope) {
				case "+":
					result = first + second;
				    break;
				case "-":
					result = first - second;
					break;
				case "*": 
					result = first * second;
					break;
				case "/":
					result = first / second;
					break;
				case "%":
					result = first % second;
					break;
				}
			}catch(ArithmeticException e){
				System.out.println("結果：" + first + " " + ope + " " + second + " = 計算できません（０で割ることはできません）");
				break;
			}
			
		    System.out.println("結果：" + first + " " + ope + " " + second + " = " + result);
		    break;
		}
		
	}
}
