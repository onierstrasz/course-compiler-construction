/**
 * @author Oscar Nierstrasz
 * $Id: StraightLineInterpreter.java 22532 2008-11-04 14:10:22Z oscar $
 */

package interpreter;

import java.io.Reader;
import java.io.InputStream;

import parser.ParseException;
import parser.StraightLineParser;
import syntaxtree.Goal;

public class StraightLineInterpreter {
	Goal parse;
	StraightLineParser parser;

	public StraightLineInterpreter(Reader in) {
		this.parser = new StraightLineParser(in);
		this.initParse();
	}

	public StraightLineInterpreter(InputStream in) {
		this.parser = new StraightLineParser(in);
		this.initParse();
	}

	private void initParse() {
		try {
			this.parse = this.parser.Goal();
		}
		catch (ParseException e) {
			System.out.println("Lexer Error : \n"+ e.toString());
		}
	}

	public static void main(String [] args) {
		System.err.println("Enter straightline code to interpret:");		
		System.out.println(new StraightLineInterpreter(System.in).interpret());
	}

	public String interpret() {
		assert(this.parse != null);
		InterpreterVisitor visitor = new InterpreterVisitor();
		visitor.visit(this.parse);
		return visitor.result();
	}

}
