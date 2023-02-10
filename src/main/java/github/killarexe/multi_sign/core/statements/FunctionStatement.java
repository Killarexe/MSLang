package github.killarexe.multi_sign.core.statements;

import java.util.List;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public class FunctionStatement extends Statement{

	private final Token name;
	private final List<Token> params;
	private final List<Statement> body;
	
	public FunctionStatement(Token name, List<Token> params, List<Statement> body) {
		super();
		this.name = name;
		this.params = params;
		this.body = body;
	}

	@Override
	public <R> R accept(StatementVisitor<R> visitor) {
		return visitor.visitFunctionStatement(this);
	}

	public List<Token> getParams() {
		return params;
	}
	
	public List<Statement> getBody() {
		return body;
	}
	
	public Token getName() {
		return name;
	}
}
