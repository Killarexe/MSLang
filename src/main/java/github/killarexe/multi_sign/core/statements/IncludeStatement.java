package github.killarexe.multi_sign.core.statements;

import github.killarexe.multi_sign.core.expressions.Expression;
import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public class IncludeStatement extends Statement{
	
	private final Expression file;
	
	public IncludeStatement(Expression file) {
		this.file = file;
	}

	@Override
	public <R> R accept(StatementVisitor<R> visitor) {
		return visitor.visitIncludeStatement(this);
	}
	
	public Expression getFile() {
		return file;
	}

}
