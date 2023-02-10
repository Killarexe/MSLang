package github.killarexe.multi_sign.core.statements;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public class BlockStatement extends Statement{

	private final List<Statement> statements;
	
	
	public BlockStatement(List<Statement> statements) {
		super();
		this.statements = statements;
	}

	@Override
	public <R> R accept(StatementVisitor<R> visitor) {
		return visitor.visitBlockStatement(this);
	}
	
	public List<Statement> getStatements() {
		return statements;
	}
}
