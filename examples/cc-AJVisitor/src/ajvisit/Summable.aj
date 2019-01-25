package ajvisit;

public aspect Summable {

	public int List.sum() {
		return 0;
	}

	public int Nil.sum() {
		return 0;
	}

	public int Cons.sum() {
		return head + tail.sum();
	}

}
