package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class Round extends BuiltinFunction{

	public Round() {
		super("round");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return (double)Math.round((Double)args.get(0));
	}

	@Override
	public int argsSize() {
		return 1;
	}
}
