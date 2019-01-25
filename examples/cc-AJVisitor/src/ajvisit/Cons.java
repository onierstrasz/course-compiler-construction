package ajvisit;

class Cons implements List {
	int head;
	List tail;
	Cons(int head, List tail) {
		this.head = head;
		this.tail = tail;
	}
}