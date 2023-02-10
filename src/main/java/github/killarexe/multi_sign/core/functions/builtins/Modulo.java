package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class Modulo extends BuiltinFunction{

	public Modulo() {
		super("mod");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return (Double)args.get(0) % (Double)args.get(1);
	}

	@Override
	public int argsSize() {
		return 2;
	}

}
