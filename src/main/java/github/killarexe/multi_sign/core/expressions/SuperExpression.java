package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class SuperExpression extends Expression{

	private final Token keyword, method;
	
	public SuperExpression(Token keyword, Token method) {
		this.keyword = keyword;
		this.method = method;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitSuperExpression(this);
	}

	public Token getKeyword() {
		return keyword;
	}
	
	public Token getMethod() {
		return method;
	}
}
