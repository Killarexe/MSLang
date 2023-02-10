package github.killarexe.multi_sign.core.statements;

import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public abstract class Statement {
	public abstract <R> R accept(StatementVisitor<R> visitor);
}
