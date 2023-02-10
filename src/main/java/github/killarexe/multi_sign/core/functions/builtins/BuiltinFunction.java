package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.functions.MSCallable;
import github.killarexe.multi_sign.core.visitors.Interpreter;

public abstract class BuiltinFunction implements MSCallable{

	protected final String name;
	
	public BuiltinFunction(String name) {
		this.name = name;
	}
	
	@Override
	public abstract Object call(Interpreter interpreter, List<Object> args);

	@Override
	public abstract int argsSize();
	
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}
}
