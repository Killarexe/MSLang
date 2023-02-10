package github.killarexe.multi_sign.core.statements;

import github.killarexe.multi_sign.core.expressions.Expression;
import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public class IfStatement extends Statement{

	private final Expression condition;
	private final Statement thenBranch, elseBranch;
	
	
	public IfStatement(Expression condition, Statement thenBranch, Statement elseBranch) {
		super();
		this.condition = condition;
		this.thenBranch = thenBranch;
		this.elseBranch = elseBranch;
	}


	@Override
	public <R> R accept(StatementVisitor<R> visitor) {
		return visitor.visitIfStatement(this);
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	public Statement getElseBranch() {
		return elseBranch;
	}
	
	public Statement getThenBranch() {
		return thenBranch;
	}
}
