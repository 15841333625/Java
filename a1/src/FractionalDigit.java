// 13/17С������100λ�������Ǽ���
public class FractionalDigit {
	public int Remainder(int dividend, int divisor, int digit) {
		double temp = (double)dividend / divisor;           //�õ����
		temp *= Math.pow(10.0, (double)digit);              //����С�����digitλת������λ
		int result = (int)temp % (((int)temp / 10) * 10);   //����
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
