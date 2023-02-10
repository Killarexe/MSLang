package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class LiteralExpression extends Expression{
	
	private final Object value;
	
	public LiteralExpression(Object value) {
		this.value = value;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitLiteralExpression(this);
	}
	
	public Object getValue() {
		return value;
	}
}
