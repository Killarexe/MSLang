package github.killarexe.multi_sign.core.statements;

import github.killarexe.multi_sign.core.expressions.Expression;
import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public class VariableStatement extends Statement{

	private final Token name;
	private final Expression initializer;
	
	public VariableStatement(Token name, Expression initializer) {
		super();
		this.name = name;
		this.initializer = initializer;
	}

	@Override
	public <R> R accept(StatementVisitor<R> visitor) {
		return visitor.visitVariableStatement(this);
	}
	
	public Expression getInitializer() {
		return initializer;
	}
	
	public Token getName() {
		return name;
	}
}
