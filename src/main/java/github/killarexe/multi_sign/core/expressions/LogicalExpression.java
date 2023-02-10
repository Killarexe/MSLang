package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class LogicalExpression extends Expression{

	private final Expression left, right;
	private final Token operator;
	
	public LogicalExpression(Expression left, Token operator, Expression right) {
		this.operator = operator;
		this.right = right;
		this.left = left;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitLogicalExpression(this);
	}

	public Token getOperator() {
		return operator;
	}
	
	public Expression getRight() {
		return right;
	}
	
	public Expression getLeft() {
		return left;
	}
}
