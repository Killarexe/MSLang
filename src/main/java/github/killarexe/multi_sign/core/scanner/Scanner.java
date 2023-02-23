package github.killarexe.multi_sign.core.scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.killarexe.multi_sign.core.error.ErrorHandler;
import github.killarexe.multi_sign.core.scanner.Token.TokenType;

public class Scanner {
	private final String source;
	private final List<Token> tokens;
	private int start, current, line;
	private static final Map<String, TokenType> keywords;
	
	 static {
		 keywords = new HashMap<>();
		 keywords.put("and",    TokenType.AND);
		 keywords.put("class",  TokenType.CLASS);
		 keywords.put("else",   TokenType.ELSE);
		 keywords.put("false",  TokenType.FALSE);
		 keywords.put("for",    TokenType.FOR);
		 keywords.put("fn",    TokenType.FN);
		 keywords.put("if",     TokenType.IF);
		 keywords.put("null",   TokenType.NULL);
		 keywords.put("or",     TokenType.OR);
		 keywords.put("print",  TokenType.PRINT);
		 keywords.put("return", TokenType.RETURN);
		 keywords.put("super",  TokenType.SUPER);
		 keywords.put("this",   TokenType.THIS);
		 keywords.put("true",   TokenType.TRUE);
		 keywords.put("var",    TokenType.VAR);
		 keywords.put("while",  TokenType.WHILE);
		 keywords.put("define", TokenType.DEFINE);
		 keywords.put("include", TokenType.INCLUDE);
	 }
	
	public Scanner(String source) {
		this.line = 1;
		this.start = 0;
		this.current = 0;
		this.source = source;
		this.tokens = new ArrayList<>();
	}
	
	public List<Token> scanTokens(){
		while(!isEndOfFile()) {
			start = current;
			scanToken();
		}
		tokens.add(new Token(TokenType.EOF, null, "", line));
		return tokens;
	}
	
	private void scanToken() {
		char c = advence();
		switch (c) {
	      	case '(': addToken(TokenType.LEFT_PAREN); break;
	      	case ')': addToken(TokenType.RIGHT_PAREN); break;
	      	case '{': addToken(TokenType.LEFT_BRACE); break;
	      	case '}': addToken(TokenType.RIGHT_BRACE); break;
	      	case ',': addToken(TokenType.COMMA); break;
	      	case '.': addToken(TokenType.DOT); break;
	      	case '-': 
	      		addToken(match('-') ? TokenType.DECREASE : match('=') ? TokenType.MINUS_EQUAL : TokenType.MINUS);
	      		break;
	      	case '+':
	      		addToken(match('+') ? TokenType.INCREASE : match('=') ? TokenType.PLUS_EQUAL : TokenType.PLUS);
	      		break;
	      	case ';': addToken(TokenType.SEMICOLON); break;
	      	case '*': addToken(match('=') ? TokenType.STAR_EQUAL : TokenType.STAR); break;
	      	case '%': addToken(match('=') ? TokenType.MODULO_EQUAL : TokenType.MODULO); break;
	      	case '^':
	      		addToken(match('^') ? TokenType.XOR : match('=') ? TokenType.XOR_EQUAL : TokenType.BIN_XOR);
	      		break;
	      	case '&': 
	      		addToken(match('&') ? TokenType.AND : match('=') ? TokenType.AND_EQUAL : TokenType.BIN_AND);
	      		break;
	      	case '|':
	      		addToken(match('|') ? TokenType.OR : match('=') ? TokenType.OR_EQUAL : TokenType.BIN_OR);
	      		break;
	      	case '!':
	      		addToken(match('=') ? TokenType.NOT_EQUAL : TokenType.NOT);
	      		break;
	      	case '=':
	      		addToken(match('=') ? TokenType.EQUALS : match('>') ? TokenType.RIGHT_ARROW : TokenType.ASSIGN);
	      		break;
	      	case '<':
	      		addToken(match('=') ? TokenType.LESS_EQUAL : match('<') ? TokenType.LEFT_SHIFT : TokenType.LESS);
	      		break;
	      	case '>':
	      		addToken(match('=') ? TokenType.GREATER_EQUAL : match('>') ? TokenType.RIGHT_SHIFT : TokenType.GREATER);
	      		break;
	      	case '/':
	      		comment();
	      		break;
	      	case '"':
	      		string();
	      		break;
	      	case '\n':
	      		line++;
	      		break;
	      	default:
	      		if(Character.isWhitespace(c)) {
	      			break;
	      		}
	      		if(Character.isDigit(c)) {
	      			number();
	      			break;
	      		}
	      		if(Character.isAlphabetic(c) || c == '_') {
	      			identifier();
	      			break;
	      		}
	      		ErrorHandler.error(line, "Unexpected character '" + c +"'!");
	      		break;
	    }
	}
	
	private void comment() {
		if(match('/')) {
  			while(peek() != '\n' && !isEndOfFile()) {
  				advence();
  			}
  		}else if(match('*')){
  			while(peek() != '*' || peekNext() != '/') {
  				if(peek() == '\n') {
  					line++;
  				}
  				advence();
  			}
  			advence();
  			advence();
  		}else{
  			addToken(match('=') ? TokenType.SLASH_EQUAL : TokenType.SLASH);
  		}
	}
	
	private void identifier() {
		while(Character.isAlphabetic(peek()) || Character.isDigit(peek()) || peek() == '_') {
			advence();
		}
		String text = source.substring(start, current);
		TokenType type = keywords.get(text);
		if(type == null) {
			type = TokenType.IDENTIFIER;
		}
		addToken(type);
	}
	
	private void number() {
		while(Character.isDigit(peek()) || (peek() == '_' && Character.isDigit(peekNext()))) {
			advence();
		}
		if(peek() == '.' && Character.isDigit(peekNext())) {
			advence();
			while(Character.isDigit(peek()) || (peek() == '_' && Character.isDigit(peekNext()))) {
				advence();
			}
		}
		addToken(TokenType.NUMBER, Double.parseDouble(source.substring(start, current)));
	}
	
	private void string() {
		while(peek() != '"' && !isEndOfFile()) {
			if(peek() == '\n') line++;
			advence();
		}
		if(isEndOfFile()) {
			ErrorHandler.error(line, "Unexpected '\"' to end this string!");
			return;
		}
		advence();
		String value = source.substring(start + 1, current - 1);
		addToken(TokenType.STRING, value);
	}
	
	private char peekNext() {
		return current + 1 >= source.length() ? '\0' : source.charAt(current + 1);
	}
	
	private char peek() {
		return isEndOfFile() ? '\0' : source.charAt(current);
	}
	
	private boolean match(char value) {
		if(isEndOfFile() || source.charAt(current) != value) {
			return false;
		}
		current++;
		return true;
	}
	
	private char advence() {
		return source.charAt(current++);
	}
	
	private void addToken(TokenType type) {
		addToken(type, null);
	}
	
	private void addToken(TokenType type, Object literal) {
		String value = source.substring(start, current);
		tokens.add(new Token(type, literal, value, line));
	}
	
	private boolean isEndOfFile() {
		return current >= source.length();
	}
}