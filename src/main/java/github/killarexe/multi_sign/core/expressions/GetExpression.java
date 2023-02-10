package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class GetExpression extends Expression{

	private final Expression object;
	private final Token name;
	
	public GetExpression(Expression object, Token name) {
		this.object = object;
		this.name = name;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitGetExpression(this);
	}
	
	public Expression getObject() {
		return object;
	}
	
	public Token getName() {
		return name;
	}
}
