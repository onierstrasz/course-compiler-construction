package ajvisit;

public class SumList {
	public static void main(String[] args) {
		List l = new Cons(5, new Cons(4, new Cons(3, new Nil())));
		System.out.println("Sum = " + l.sum());
	}
}
