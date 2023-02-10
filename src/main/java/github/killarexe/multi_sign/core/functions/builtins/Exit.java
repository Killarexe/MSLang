package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class Exit extends BuiltinFunction{

	public Exit() {
		super("exit");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		System.exit(((Double)args.get(0)).intValue());
		return null;
	}

	@Override
	public int argsSize() {
		return 1;
	}

}
