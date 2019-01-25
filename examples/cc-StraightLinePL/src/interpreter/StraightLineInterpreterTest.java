/**
 * @author Oscar Nierstrasz
 * $Id: StraightLineInterpreterTest.java 21776 2008-10-09 08:07:12Z oscar $
 */

package interpreter;

import static org.junit.Assert.*;
import org.junit.Test;

import examples.Examples;

import java.io.BufferedReader;
import java.io.StringReader;

public class StraightLineInterpreterTest {
	/**
	 * We introduce separate test methods so that JUnit will tell us how many tests are run.
	 */
	@Test public void testEg0() { testEg(0); }
	@Test public void testEg1() { testEg(1); }
	@Test public void testEg2() { testEg(2); }
	@Test public void testEg3() { testEg(3); }
	@Test public void testEg4() { testEg(4); }

	private void testEg(int n) {
		assertResult(Examples.code(n), Examples.result(n));
	}

	private void assertResult(String eg, String result) {
		BufferedReader program = new BufferedReader(new StringReader(eg));
		assertEquals(result,new StraightLineInterpreter(program).interpret());
	}
}
