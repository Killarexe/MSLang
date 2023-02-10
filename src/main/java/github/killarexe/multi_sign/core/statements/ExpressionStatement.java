package github.killarexe.multi_sign.core.statements;

import github.killarexe.multi_sign.core.expressions.Expression;
import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public class ExpressionStatement extends Statement{

	private final Expression expression;
	
	public ExpressionStatement(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public <R> R accept(StatementVisitor<R> visitor) {
		return visitor.visitExpressionStatement(this);
	}
	
	public Expression getExpression() {
		return expression;
	}
}
