package github.killarexe.multi_sign.core.statements;

import github.killarexe.multi_sign.core.expressions.Expression;
import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public class ReturnStatement extends Statement{

	private final Token keyword;
	private final Expression value;
	
	public ReturnStatement(Token keyword, Expression value) {
		super();
		this.keyword = keyword;
		this.value = value;
	}

	@Override
	public <R> R accept(StatementVisitor<R> visitor) {
		return visitor.visitReturnStatement(this);
	}
	
	public Token getKeyword() {
		return keyword;
	}
	
	public Expression getValue() {
		return value;
	}
}
