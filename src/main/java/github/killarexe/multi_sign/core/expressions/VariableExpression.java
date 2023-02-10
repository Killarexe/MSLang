package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class VariableExpression extends Expression{
	
	private final Token name;
	
	public VariableExpression(Token name) {
		this.name = name;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitVariableExpression(this);
	}
	
	public Token getName() {
		return name;
	}
}
