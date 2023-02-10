package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class UnaryExpression extends Expression{
	
	private final Token operator;
	private final Expression right;
	
	public UnaryExpression(Token operator, Expression right) {
		this.operator = operator;
		this.right = right;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitUnaryExpression(this);
	}
	
	public Token getOperator() {
		return operator;
	}
	
	public Expression getRight() {
		return right;
	}
}
