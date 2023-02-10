package github.killarexe.multi_sign.core.functions;

import java.util.List;

import github.killarexe.multi_sign.core.Environement;
import github.killarexe.multi_sign.core.statements.FunctionStatement;
import github.killarexe.multi_sign.core.visitors.Interpreter;

public class MSFunction implements MSCallable{

	private final FunctionStatement statement;
	
	public MSFunction(FunctionStatement statement) {
		this.statement = statement;
	}
	
	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		Environement environement = new Environement(interpreter.getGlobals());
		for(int i = 0; i < statement.getParams().size(); i++) {
			environement.define(statement.getParams().get(i).getValue(), args.get(i));
		}
		try {
			interpreter.executeBlock(statement.getBody(), environement);
		}catch(Return returnValue) {
			return returnValue.getValue();
		}
		return null;
	}

	@Override
	public int argsSize() {
		return statement.getParams().size();
	}

	@Override
	public String toString() {
		return "<fn " + statement.getName().getValue() + ">";
	}
}
