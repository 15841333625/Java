// 13/17小数点后第100位的数字是几？
public class FractionalDigit {
	public int Remainder(int dividend, int divisor, int digit) {
		double temp = (double)dividend / divisor;           //得到结果
		temp *= Math.pow(10.0, (double)digit);              //将第小数点后digit位转化到个位
		int result = (int)temp % (((int)temp / 10) * 10);   //求余
		return result;
	}

	public static void main(String[] args) {
		int d = 13;
		int q = 17;
		int a = 0;

		FractionalDigit f = new FractionalDigit();
		a = f.Remainder(d, q, 100);
		
		System.out.println(a);

	}

}
