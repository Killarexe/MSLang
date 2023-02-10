package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class AssignExpression extends Expression{

	private final Token name;
	private final Expression value;
	
	public AssignExpression(Token name, Expression value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitAssignExpression(this);
	}
	
	public Token getName() {
		return name;
	}
	
	public Expression getValue() {
		return value;
	}
}
