public class Fibo {
	public static void main(String[] args) {
		Fibo f = new Fibo();
		System.out.println(f.fibo1(9)); // 这两种方法哪种效率更高？   ### 循环
		System.out.println(f.fibo2(9));
	}

	public int fibo1(int n) { // 使用方法（函数）递归来实现
		if(n == 1)
			return 1;
		else if(n == 2)
			return 2;
		else
			return fibo1(n - 1) + fibo1(n - 2);
	}

	public int fibo2(int n) { // 使用循环来实现
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

			//数组记录第n项的前两项
			for (int i = 2; i < n; i ++){
				result = f[0] + f[1];
				f[i % 2] = result;
			}
			return result;
		}
	}
}
