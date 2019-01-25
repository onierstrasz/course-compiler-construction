package ajvisit;

public class SumVisitor implements Visitor {
	int sum = 0;
	public void visit(Nil l) { }

	public void visit(Cons l) {
		sum = sum + l.head;
		l.tail.accept(this);
	}

	public static void main(String[] args) {
		List l = new Cons(5, new Cons(4,
				new Cons(3, new Nil())));
		SumVisitor sv = new SumVisitor();
		l.accept(sv);
		System.out.println("Sum = " + sv.sum);
	}
}
