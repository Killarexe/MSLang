package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class BinaryExpression extends Expression{
	
	private final Token operator;
	private final Expression left, right;
	
	public BinaryExpression(Expression left, Token operator, Expression right) {
		this.left = left;
		this.right = right;
		this.operator = operator;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitBinaryExpression(this);
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
