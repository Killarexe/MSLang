package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class GroupingExpression extends Expression{
	private final Expression expression;
	
	public GroupingExpression(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitGroupingExpression(this);
	}
	
	public Expression getExpression() {
		return expression;
	}
}
