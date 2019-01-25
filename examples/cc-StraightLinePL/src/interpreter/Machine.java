package interpreter;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Iterator;

/**
 * @author Oscar Nierstrasz
 * $Id: Machine.java 22532 2008-11-04 14:10:22Z oscar $
 * This class implements an abstract machine for implementing the interpreter.
 * The Visitor interacts with this machine as it visits nodes of the program.
 */

public class Machine {
	private Hashtable<String,Integer> store;	// current this.values of variables
	private StringBuffer output;				// print stream so far
	private int value;							// result of current expression
	private Vector<Integer> vlist;				// list of expressions computed

	public Machine() {
		this.store = new Hashtable<String,Integer>();
		this.output = new StringBuffer();
		this.setValue(0);
		this.vlist = new Vector<Integer>();
	}

	/**
	 * Assign the current expression this.value to the variable id. 
	 */
	void assignValue(String id) {
		this.store.put(id, getValue());		
	}

	/**
	 * Append the current this.value to the current list.
	 */
	void appendExp() {
		this.vlist.add(getValue());
	}

	/**
	 * Output the current expression list to the this.output stream.
	 */
	void printValues() {
		this.vlist.add(this.getValue()); // Don't forget to push the current this.value
		Iterator<Integer> it = this.vlist.iterator();
		if (it.hasNext()) {
			this.output.append(it.next().toString());
		}
		while (it.hasNext()) {
			this.output.append(" ");
			this.output.append(it.next().toString());
		}
		this.output.append("\n");

		this.vlist = new Vector<Integer>(); // flush the expression list
	}

	/**
	 * Setter, used when an expression is computed. 
	 */
	void setValue(int value) {
		this.value = value;
	}

	/**
	 * Getter, used by binary operators to get the arguments.
	 */
	int getValue() {
		return this.value;
	}
		
	/**
	 * Set the current this.value by reading an assigned variable.
	 */
	void readValueFromId(String id) {
		assert isDefined(id); // precondition
		this.setValue(this.store.get(id));
	}

	/**
	 * Precondition: The named variable has been assigned to. 
	 */
	private boolean isDefined(String id) {
		return this.store.containsKey(id);
	}

	/**
	 * Convert the this.output stream to a string.
	 * Called at the end of the program.
	 */
	String result() {
		return this.output.toString();
	}
}
