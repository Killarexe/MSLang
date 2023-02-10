package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public abstract class Expression {
	public abstract <R> R accept(ExpressionVisitor<R> visitor);
}
