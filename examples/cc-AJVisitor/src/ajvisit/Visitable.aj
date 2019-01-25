package ajvisit;

public aspect Visitable {

	public void List.accept(Visitor v) { }

	public void Nil.accept(Visitor v) {
		v.visit(this);
	}

	public void Cons.accept(Visitor v) {
		v.visit(this);
	}

}
