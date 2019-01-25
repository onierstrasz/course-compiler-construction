/**
 * @author Oscar Nierstrasz
 * $Id: Generator.java 22532 2008-11-04 14:10:22Z oscar $
 */

package compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import org.apache.bcel.generic.*;
import org.apache.bcel.Constants;

/**
 * Provides a higher level interface to the visitor for generating bytecode.
 */

public class Generator {
	private Hashtable<String,Integer> symbolTable;
	private InstructionFactory factory;
	private ConstantPoolGen cp;
	private ClassGen cg;
	private InstructionList il;
	private MethodGen method;
	private final String className;

	public Generator (String className) {
		this.className = className;
		this.symbolTable = new Hashtable<String,Integer>();
		this.cg = new ClassGen(className, "java.lang.Object", className + ".java",
				Constants.ACC_PUBLIC | Constants.ACC_SUPER, new String[] {});

		this.cp = this.cg.getConstantPool();
		this.factory = new InstructionFactory(this.cg, this.cp);

		this.il = new InstructionList();
		this.method = new MethodGen(Constants.ACC_PUBLIC | Constants.ACC_STATIC,
				Type.VOID, new Type[] { new ArrayType(Type.STRING, 1) },
				new String[] { "arg0" }, "main", className, il, this.cp);
	}
	
	public void generate(File folder) throws IOException {
		this.il.append(InstructionFactory.createReturn(Type.VOID));
		this.method.setMaxStack();
		this.method.setMaxLocals();
		this.cg.addMethod(this.method.getMethod());
		this.il.dispose();
		OutputStream out = new FileOutputStream(new File(folder, className + ".class"));
		this.cg.getJavaClass().dump(out);
	}

	private void pushSystemOut() {
		this.il.append(this.factory.createFieldAccess(
				"java.lang.System", "out",
				new ObjectType("java.io.PrintStream"), Constants.GETSTATIC));
	}

	private void genPrintTopNum() {
		this.il.append(this.factory.createInvoke("java.io.PrintStream", "print",
				Type.VOID, new Type[] { Type.INT }, Constants.INVOKEVIRTUAL));
	}
	
	private void genPrintString(String s) {
		pushSystemOut();
		this.il.append(new PUSH(this.cp, s));
		this.il.append(this.factory.createInvoke("java.io.PrintStream", "print",
				Type.VOID, new Type[] { Type.STRING }, Constants.INVOKEVIRTUAL));
	}

	/**
	 * say what we are doing (for debugging)
	 */
	private void say(String something) {
		// System.err.println(something);
	}

	public void prepareToPrint() {
		say("prepare to print");
		pushSystemOut();
	}

	public void stopPrinting() {
		say("print top value; print newline; stop printing");
		genPrintTopNum();
		genPrintString("\n");
	}

	public void printValue() {
		say("print top; print space");
		genPrintTopNum();
		genPrintString(" ");
	}

	public void add() {
		say("add top two stack values");
		this.il.append(new IADD());
	}

	public void subtract() {
		say("subtract top two stack values");
		this.il.append(new ISUB());
	}

	public void multiply() {
		say("multiply top two stack values");
		this.il.append(new IMUL());
	}

	public void divide() {
		say("divide top two stack values");
		this.il.append(new IDIV());
	}

	public void pushInt(int val) {
		say("push "+Integer.toString(val));
		this.il.append(new PUSH(this.cp, val));
	}

	public void assignValue(String id) {
		say("assign top to var " + id);
		this.il.append(InstructionFactory.createStore(Type.INT, getLocation(id)));
	}

	public void pushId(String id) {
		say("push value of " + id);
		this.il.append(InstructionFactory.createLoad(Type.INT, getLocation(id)));
	}

	private int getLocation(String id) {
		if(!this.symbolTable.containsKey(id)) {
			this.symbolTable.put(id, 1+this.symbolTable.size());
		}
		return this.symbolTable.get(id);
	}

}
