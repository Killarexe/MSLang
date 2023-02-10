package github.killarexe.multi_sign.core.error;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.scanner.Token.TokenType;

public class ErrorHandler {
	
	private static final String RED = "\u001B[38;2;255;0;0;1m";
	private static final String WHITE = "\u001B[0m";
	
	public static void error(int line, String message) {
		report(line, "", message);
	}
	
	public static void error(Token token, String message) {
		if(token.getType() == TokenType.EOF) {
			report(token.getLine(), " at end", message);
		}else {
			report(token.getLine(), "at '" + token.getValue() + "'", message);
		}
	}
	
	public static void error(RuntimeError error) {
		if(error.getToken().getType() == TokenType.EOF) {
			report(error.getToken().getLine(), " at end", error.getMessage());
		}else {
			report(error.getToken().getLine(), "at '" + error.getToken().getValue() + "'", error.getMessage());
		}
	}
	
	private static void report(int line, String where, String message) {
		System.err.println(String.format(RED + "[ERROR]: Error at line %d %s! Error: %s" + WHITE, line, where, message));
	}
}
