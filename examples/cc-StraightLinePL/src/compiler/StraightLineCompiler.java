/**
 * @author Oscar Nierstrasz
 * $Id: StraightLineCompiler.java 22532 2008-11-04 14:10:22Z oscar $
 */

package compiler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.InputStream;
import java.lang.reflect.Method;

import parser.ParseException;
import parser.StraightLineParser;
import syntaxtree.Goal;

public class StraightLineCompiler {
	private static final File OUT_FOLDER = new File("out");
	private static final String DEFAULT_CLASS = "SLP";
	Goal parse;
	StraightLineParser parser;

	public StraightLineCompiler(Reader in) {
		this.parser = new StraightLineParser(in);
		this.initParse();
	}

	public StraightLineCompiler(InputStream in) {
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
		String slp = DEFAULT_CLASS;
		System.err.println("Enter straightline code to compile:");
		new StraightLineCompiler(System.in).compileAs(slp);
		System.out.println(runMainForClass(slp));
	}

	public void compileAs(String className) {
		assert(this.parse != null);
		CompilerVisitor visitor = new CompilerVisitor(className);
		visitor.visit(this.parse);
		try {
			visitor.generateCode(OUT_FOLDER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.err.println("DONE!\n");
	}

	public static String runMainForClass(String className) {
		String output = "";
		try {
			Class<?> cls = Class.forName(className);
			Method m = cls.getMethod("main", String[].class);
			PrintStream savedOut = System.out;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			System.setOut(new PrintStream(baos));
			m.invoke(null, new Object[] { new String[0] });
			System.setOut(savedOut);
			output = baos.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return output;
	}

}
