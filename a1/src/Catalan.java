
public class Catalan {
	
	public static int answers = 0;
	
	//��ʵ��go����
	public static void go(Deque from, Deque to, Stack s){
		if((from.size() == 0) && (to.size() == 7) && (s.size() == 0)) {
			answers ++;
			return;
		}
		if(from.size() != 0) {  //��ջ
			int v = from.getFirst();
			s.push(v);
			from.removeFirst();
			go(from, to, s);
			s.pop();
			from.addFirst(v);  //����
		}
		if (s.size() != 0) {
			int v = s.pop();
			to.addFirst(v);
			go(from, to, s);
			to.removeFirst();
			s.push(v);         //����
		}
		return;
	}

	public static void main(String[] args) {
		Deque from = new Deque();
		Deque to = new Deque();
		Stack s = new Stack();
		
		for(int i=1;i<=7;i++) {
			from.addLast(i);
		}
		
		go(from, to, s);
		
		System.out.println(answers);
		

	}

}
