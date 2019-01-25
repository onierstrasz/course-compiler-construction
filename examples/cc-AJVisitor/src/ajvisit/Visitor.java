package ajvisit;

public interface Visitor {
	void visit(Nil l);
	void visit(Cons l);
}
