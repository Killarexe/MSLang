package github.killarexe.multi_sign.core.expressions;

import java.util.List;

import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.ExpressionVisitor;

public class CallExpression extends Expression{

	private final Token paren;
	private final Expression call;
	private final List<Expression> args;
	
	public CallExpression(Token paren, Expression call, List<Expression> args) {
		this.paren = paren;
		this.call = call;
		this.args = args;
	}
	
	@Override
	public <R> R accept(ExpressionVisitor<R> visitor) {
		return visitor.visitCallExpression(this);
	}
	
	public Token getParen() {
		return paren;
	}
	
	public List<Expression> getArgs() {
		return args;
	}
	
	public Expression getCall() {
		return call;
	}
}
