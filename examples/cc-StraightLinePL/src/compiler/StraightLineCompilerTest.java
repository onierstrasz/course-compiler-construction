/**
 * @author Oscar Nierstrasz
 * $Id: StraightLineCompilerTest.java 21776 2008-10-09 08:07:12Z oscar $
 */

package compiler;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import examples.Examples;

import java.io.BufferedReader;
import java.io.StringReader;

public class StraightLineCompilerTest {

	@BeforeClass public static void compileExamples() {
		for(int n=0; n < Examples.length(); n++) {
			compileExample(n);
		}
	}

	/**
	 * We introduce separate test methods so that JUnit will tell us how many tests are run.
	 */
	@Test public void testEg0() { testEg(0); }
	@Test public void testEg1() { testEg(1); }
	@Test public void testEg2() { testEg(2); }
	@Test public void testEg3() { testEg(3); }
	@Test public void testEg4() { testEg(4); }

	private void testEg(int n) {
		assertEquals(Examples.result(n),
				StraightLineCompiler.runMainForClass(Examples.className(n)));		
	}

	private static void compileExample(int n) {
		BufferedReader stream = new BufferedReader(new StringReader(Examples.code(n)));
		new StraightLineCompiler(stream).compileAs(Examples.className(n));
	}
}
