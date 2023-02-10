package github.killarexe.multi_sign.core.statements;

import java.util.List;

import github.killarexe.multi_sign.core.expressions.VariableExpression;
import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.visitors.StatementVisitor;

public class ClassStatement extends Statement{

	private final Token name;
	private final VariableExpression superclass;
	private List<FunctionStatement> methods;
	
	
	public ClassStatement(Token name, VariableExpression superclass, List<FunctionStatement> methods) {
		super();
		this.name = name;
		this.superclass = superclass;
		this.methods = methods;
	}

	@Override
	public <R> R accept(StatementVisitor<R> visitor) {
		return visitor.visitClassStatement(this);
	}

	public VariableExpression getSuperclass() {
		return superclass;
	}
	
	public List<FunctionStatement> getMethods() {
		return methods;
	}
	
	public Token getName() {
		return name;
	}
}
