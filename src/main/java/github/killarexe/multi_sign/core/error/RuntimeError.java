package github.killarexe.multi_sign.core.error;

import github.killarexe.multi_sign.core.scanner.Token;

public class RuntimeError extends RuntimeException{
	
	private static final long serialVersionUID = -6929144752135029652L;
	
	private final Token token;
	
	public RuntimeError(Token token, String message) {
		super(message);
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
}
