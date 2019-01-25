/**
 * @author Oscar Nierstrasz
 * $Id: CompilerVisitor.java 22532 2008-11-04 14:10:22Z oscar $
 */

package compiler;

import java.io.File;
import java.io.IOException;

import visitor.DepthFirstVisitor;
import syntaxtree.*;

/**
 * Here we this.generate bytecode by visiting interesting nodes of the parse tree.
 */

public class CompilerVisitor extends DepthFirstVisitor {
	Generator gen;

	public CompilerVisitor(String className) {
		this.gen = new Generator(className);
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
		
		this.gen.assignValue(id);
	}

	/**
	 * f0 -> "print"
	 * f1 -> "("
	 * f2 -> ExpList()
	 * f3 -> ")"
	 */
	public void visit(PrintStm n) {
		n.f0.accept(this);
		this.gen.prepareToPrint();
		// n.f1.accept(this);
		n.f2.accept(this);
		// n.f3.accept(this);
		this.gen.stopPrinting();
	}

	/**
	 * f0 -> ","
	 * f1 -> Exp()
	 */
	public void visit(AppendExp n) {
		// print the value previously computed
		this.gen.printValue();
		this.gen.prepareToPrint();
		// n.f0.accept(this);
		n.f1.accept(this);
	}

	/**
	 * f0 -> "+"
	 * f1 -> MulExp()
	 */
	public void visit(PlusOp n) {
		// n.f0.accept(this);
		n.f1.accept(this);
		this.gen.add();
	}

	/**
	 * f0 -> "-"
	 * f1 -> MulExp()
	 */
	public void visit(MinOp n) {
		// n.f0.accept(this);
		n.f1.accept(this);
		this.gen.subtract();
	}

	/**
	 * f0 -> "*"
	 * f1 -> PrimExp()
	 */
	public void visit(MulOp n) {
		// n.f0.accept(this);
		n.f1.accept(this);
		this.gen.multiply();
	}

	/**
	 * f0 -> "/"
	 * f1 -> PrimExp()
	 */
	public void visit(DivOp n) {
		// n.f0.accept(this);
		n.f1.accept(this);
		this.gen.divide();
	}

	/**
	 * f0 -> <IDENTIFIER>
	 */
	public void visit(ReadId n) {
		n.f0.accept(this);
		String id = n.f0.tokenImage;
		this.gen.pushId(id);
	}

	/**
	 * f0 -> <INTEGER_LITERAL>
	 */
	public void visit(Num n) {
		n.f0.accept(this);
		int val = new Integer(n.f0.tokenImage);
		this.gen.pushInt(val);
	}

	public void generateCode(File folder) throws IOException {
		this.gen.generate(folder);
	}

}
