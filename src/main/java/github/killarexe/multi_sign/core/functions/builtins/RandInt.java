package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class RandInt extends BuiltinFunction{

	public RandInt() {
		super("randi");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		double min = (double)args.get(0);
		double max = (double)args.get(1);
		return (double)Math.round(Math.random() * (max - min + 1) + min);
	}

	@Override
	public int argsSize() {
		return 2;
	}

}
