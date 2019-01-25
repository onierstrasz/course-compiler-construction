/**
 * @author Oscar Nierstrasz
 * $Id: Examples.java 21776 2008-10-09 08:07:12Z oscar $
 */

package examples;

/**
 * Examples for use by the test cases.
 * The first field is the Java class name to use, the second is the code,
 * and the third is the expected result.
 */

public class Examples {

	private static String[][] eg = {
			{ "Eg0", "print(0)", "0\n" },
			{ "Eg1", "print(1+2*3, 4)", "7 4\n" },
			{ "Eg2", "a := 1; print(a)", "1\n" },
			{ "Eg3", "print((a := 1; a := a+a*a+a, a),a+1)", "3 4\n" },
			{ "Eg4", "a := 5 + 3; b := (print(a,a-1),10*a); print(b)", "8 7\n80\n" }
	};
	
	public static String className(int n) {
		return eg[n][0];
	}

	public static String code(int n) {
		return eg[n][1];
	}

	public static String result(int n) {
		return eg[n][2];
	}

	public static int length() {
		return eg.length;
	}

}
