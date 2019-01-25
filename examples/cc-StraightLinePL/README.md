
# Straight-line Compiler and Interpreter

The project implements an interpreter and a bytecode compiler for the StraightLine programming language shown on slide 28 of Jens Palsberg's lecture notes.

The examples are used in lectures 1 (Introduction), 4 (Parsing in Practice) and 8 (Code Generation).

<http://www.cs.ucla.edu/~palsberg/course/cs132/lec.pdf>

# Running the examples

The compiler and interpreter use JavaCC and JTB (Java Tree Builder) to build the parser and visitors for the parse tree.

<http://compilers.cs.ucla.edu/cs132/setup.html>

You must first install javacc and jtb.
Then run "make parser" to generate the parser code.

The compiler uses BCEL (5.2) to generate the Java bytecode.

<http://jakarta.apache.org/site/downloads/downloads_bcel.cgi>

You must add the bcel jar to the class path to run the compiler.

Examples are in examples.Examples
The interpreter class is interpreter.StraightLineInterpreter.
Simply run it as an application and enter a code example.
(NB: enter <CNTL>-D to signle the end of input.)

StraightLineInterpreterTest will exercise all the examples.

Running compiler.StraightLineCompiler.main will generate class files for all example programs in the folder "out".  You can then run them directly, for example:

	java Eg4

## Original Grammar from the slides

	Stm -> Stm ; Stm
	Stm -> id := Exp
	Stm -> print ( ExpList )
	Exp -> id
	Exp -> num
	Exp -> Exp Binop Exp
	Exp -> ( Stm , Exp )
	ExpList -> Exp , ExpList
	ExpList -> Exp
	Binop -> +
	Binop -> -
	Binop -> /
	Binop -> *

	a := 5 + 3; b := (print(a,a-1),10*a); print(b)

 =>

	8 7
	80

## JavaCC Grammar

	Goal ->	Stm ( ; Stm ) *
	Stm ->	id := Exp
		|	print ( ExpList )
	Exp	->	MulExp ( (+|-) MulExp ) *
	MulExp -> PrimExp ( (*|/) PrimExp ) *
	PrimExp -> id
		|	num
		|	( Stm , Exp )
	ExpList -> Exp ( , Exp ) *

