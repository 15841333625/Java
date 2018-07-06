public class Fibo {
	public static void main(String[] args) {
		Fibo f = new Fibo();
		System.out.println(f.fibo1(9)); // �����ַ�������Ч�ʸ��ߣ�   ### ѭ��
		System.out.println(f.fibo2(9));
	}

	public int fibo1(int n) { // ʹ�÷������������ݹ���ʵ��
		if(n == 1)
			return 1;
		else if(n == 2)
			return 2;
		else
			return fibo1(n - 1) + fibo1(n - 2);
	}

	public int fibo2(int n) { // ʹ��ѭ����ʵ��
		if (n <= 0)
			return 0;
		else if (n == 1)
			return 1;
		else if(n == 2)
			return 2;
		else {
			int[] f = new int[2];
			f[0] = 1;
			f[1] = 2;
			int result = 0;

			//�����¼��n���ǰ����
			for (int i = 2; i < n; i ++){
				result = f[0] + f[1];
				f[i % 2] = result;
			}
			return result;
		}
	}
}
