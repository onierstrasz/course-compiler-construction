/**
 * @author Oscar Nierstrasz
 * $Id: InterpreterVisitor.java 22532 2008-11-04 14:10:22Z oscar $
 */

package interpreter;

import visitor.DepthFirstVisitor;
import syntaxtree.*;

/**
 * Here we implement the semantics of the straightline interpreter.
 * The Visitor interprets interesting nodes by directly interacting
 * with an abstract this.machine.
 */

public class InterpreterVisitor extends DepthFirstVisitor {
	Machine machine;

	public InterpreterVisitor() {
		this.machine = new Machine();
	}

	public String result() {
		return this.machine.result();
	}

	/**
	 * f0 -> WriteId()
	 * f1 -> ":="
	 * f2 -> Exp()
	 */
	public void visit(Assignment n) {
		n.f0.accept(this);
		// n.f1.accept(this);
		n.f2.accept(this);
		String id = n.f0.f0.tokenImage;
		
		this.machine.assignValue(id);
	}

	/**
	 * f0 -> "print"
	 * f1 -> "("
	 * f2 -> ExpList()
	 * f3 -> ")"
	 */
	public void visit(PrintStm n) {
		n.f0.accept(this);
		// n.f1.accept(this);
		n.f2.accept(this);
		// n.f3.accept(this);

		this.machine.printValues();
	}

	/**
	 * f0 -> ","
	 * f1 -> Exp()
	 */
	public void visit(AppendExp n) {
		// append the value previously computed
		this.machine.appendExp();
		
		// n.f0.accept(this);
		n.f1.accept(this);
	}

	/**
	 * f0 -> "+"
	 * f1 -> MulExp()
	 */
	public void visit(PlusOp n) {
		int src = this.machine.getValue();
		// n.f0.accept(this);
		n.f1.accept(this);
		int target = this.machine.getValue();
		this.machine.setValue(src + target);
	}

	/**
	 * f0 -> "-"
	 * f1 -> MulExp()
	 */
	public void visit(MinOp n) {
		int src = this.machine.getValue(); // oops -- duplicated code!
		// n.f0.accept(this);
		n.f1.accept(this);
		int target = this.machine.getValue();
		this.machine.setValue(src - target);
	}

	/**
	 * f0 -> "*"
	 * f1 -> PrimExp()
	 */
	public void visit(MulOp n) {
		int src = this.machine.getValue(); // oops -- duplicated code!
		// n.f0.accept(this);
		n.f1.accept(this);
		int target = this.machine.getValue();
		this.machine.setValue(src * target);
	}

	/**
	 * f0 -> "/"
	 * f1 -> PrimExp()
	 */
	public void visit(DivOp n) {
		int src = this.machine.getValue(); // oops -- duplicated code!
		// n.f0.accept(this);
		n.f1.accept(this);
		int target = this.machine.getValue();
		this.machine.setValue(src / target);
	}

	/**
	 * f0 -> <IDENTIFIER>
	 */
	public void visit(ReadId n) {
		n.f0.accept(this);
		String id = n.f0.tokenImage;

		this.machine.readValueFromId(id);
	}

	/**
	 * f0 -> <INTEGER_LITERAL>
	 */
	public void visit(Num n) {
		n.f0.accept(this);
		int val = new Integer(n.f0.tokenImage);

		this.machine.setValue(val);
	}

}
