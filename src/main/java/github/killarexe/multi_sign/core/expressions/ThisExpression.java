package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class ThisExpression extends Expression{

	private final Token keyword;
	
	public ThisExpression(Token keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitThisExpression(this);
	}

	public Token getKeyword() {
		return keyword;
	}
}
