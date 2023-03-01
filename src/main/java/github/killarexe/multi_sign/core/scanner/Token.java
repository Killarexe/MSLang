package github.killarexe.multi_sign.core.scanner;

public class Token {
	private final TokenType type;
	private final Object literal;
	private final String value;
	private final int line;
	
	public Token(TokenType type, Object literal, String value, int line) {
		this.type = type;
		this.literal = literal;
		this.value = value;
		this.line = line;
	}
	
	public Object getLiteral() {
		return literal;
	}
	
	public String getValue() {
		return value;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public int getLine() {
		return line;
	}
	
	@Override
	public String toString() {
		return "Token: [" + "Type: " + type + ", " + "Literal: " + literal + ", " + "Lexeme: " + value + ", " + "Line: " + line +"]";
	}
	
	public enum TokenType {
		  // Single-character tokens.
		  LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
		  COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

		  // One or two character tokens.
		  NOT, NOT_EQUAL,
		  ASSIGN, EQUALS,
		  GREATER, GREATER_EQUAL,
		  LESS, LESS_EQUAL,
		  PLUS_EQUAL,
		  MINUS_EQUAL,
		  STAR_EQUAL,
		  SLASH_EQUAL,
		  
		  XOR, BIN_AND, BIN_OR, BIN_XOR, MODULO,
		  RIGHT_ARROW, LEFT_SHIFT, RIGHT_SHIFT,
		  AND_EQUAL, XOR_EQUAL, OR_EQUAL, MODULO_EQUAL, DECREASE, INCREASE,

		  // Literals.
		  IDENTIFIER, STRING, NUMBER,

		  // Keywords.
		  AND, ELSE, FALSE, FN, FOR, IF, NULL, OR,
		  PRINT, RETURN, TRUE, VAR, WHILE, DEFINE,
		  INCLUDE,

		  EOF
	}
}