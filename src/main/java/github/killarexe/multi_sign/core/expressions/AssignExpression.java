package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class AssignExpression extends Expression{

	private final Token name, operator;
	private final Expression value;
	
	public AssignExpression(Token name, Token operator, Expression value) {
		this.name = name;
		this.value = value;
		this.operator = operator;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitAssignExpression(this);
	}
	
	public Token getName() {
		return name;
	}
	
	public Token getOperator() {
		return operator;
	}
	
	public Expression getValue() {
		return value;
	}
}
