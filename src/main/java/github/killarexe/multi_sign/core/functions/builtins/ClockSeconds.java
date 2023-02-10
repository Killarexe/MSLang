package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class ClockSeconds extends BuiltinFunction{

	public ClockSeconds() {
		super("clock_s");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return (double)System.currentTimeMillis() / 1000.0;
	}

	@Override
	public int argsSize() {
		return 0;
	}

}
