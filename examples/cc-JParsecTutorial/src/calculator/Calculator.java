package calculator;

import org.codehaus.jparsec.*;
import org.codehaus.jparsec.functors.*;

/**
 * http://jparsec.codehaus.org/jparsec2+Tutorial
 */
public class Calculator {

	enum BinaryOperator implements Binary<Double> {
		PLUS {
			public Double map(Double a, Double b) {
				return a + b;
			}
		},
		MINUS {
			public Double map(Double a, Double b) {
				return a - b;
			}
		},
		MUL {
			public Double map(Double a, Double b) {
				return a * b;
			}
		},
		DIV {
			public Double map(Double a, Double b) {
				return a / b;
			}
		}
	}

	enum UnaryOperator implements Unary<Double> {
		NEG {
			public Double map(Double n) {
				return -n;
			}
		}
	}

	static final Parser<Double> NUMBER = Terminals.DecimalLiteral.PARSER
			.map(new Map<String, Double>() {
				public Double map(String s) {
					return Double.valueOf(s);
				}
			});

	private static final Terminals OPERATORS = Terminals.operators("+", "-",
			"*", "/", "(", ")");

	static final Parser<Void> IGNORED = Parsers.or(Scanners.JAVA_LINE_COMMENT,
			Scanners.JAVA_BLOCK_COMMENT, Scanners.WHITESPACES).skipMany();

	static final Parser<?> TOKENIZER = Parsers.or(
			Terminals.DecimalLiteral.TOKENIZER, OPERATORS.tokenizer());

	static Parser<?> term(String... names) {
		return OPERATORS.token(names);
	}

	static final Parser<BinaryOperator> WHITESPACE_MUL = term("+", "-", "*",
			"/").not().retn(BinaryOperator.MUL);

	static <T> Parser<T> op(String name, T value) {
		return term(name).retn(value);
	}

	static Parser<Double> calculator(Parser<Double> atom) {
		Parser.Reference<Double> ref = Parser.newReference();
		Parser<Double> unit = ref.lazy().between(term("("), term(")")).or(atom);
		Parser<Double> parser = new OperatorTable<Double>()
				.infixl(op("+", BinaryOperator.PLUS), 10)
				.infixl(op("-", BinaryOperator.MINUS), 10)
				.infixl(op("*", BinaryOperator.MUL).or(WHITESPACE_MUL), 20)
				.infixl(op("/", BinaryOperator.DIV), 20)
				.prefix(op("-", UnaryOperator.NEG), 30).build(unit);
		ref.set(parser);
		return parser;
	}

	public static final Parser<Double> CALCULATOR = calculator(NUMBER).from(
			TOKENIZER, IGNORED);

	public static void main(String[] args) {
		System.out.println(CALCULATOR.parse("1 + 2 (4 - 3) /*comment*/"));
	}
}
