package github.killarexe.multi_sign.core.expressions;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class SetExpression extends Expression{

	private final Expression object, value;
	private final Token name;
	
	public SetExpression(Expression object, Expression value, Token name) {
		super();
		this.object = object;
		this.value = value;
		this.name = name;
	}

	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitSetExpression(this);
	}

	public Expression getObject() {
		return object;
	}
	
	public Expression getValue() {
		return value;
	}
	
	public Token getName() {
		return name;
	}
}
