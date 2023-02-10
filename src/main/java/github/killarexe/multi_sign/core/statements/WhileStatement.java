package github.killarexe.multi_sign.core.statements;

import github.killarexe.multi_sign.core.expressions.Expression;
import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public class WhileStatement extends Statement{

	private final Expression condition;
	private final Statement body;
	
	
	public WhileStatement(Expression condition, Statement body) {
		super();
		this.condition = condition;
		this.body = body;
	}

	@Override
	public <R> R accept(StatementVisitor<R> visitor) {
		return visitor.visitWhileStatement(this);
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	public Statement getBody() {
		return body;
	}
}
