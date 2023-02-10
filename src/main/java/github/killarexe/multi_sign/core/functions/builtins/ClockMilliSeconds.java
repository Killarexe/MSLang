package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class ClockMilliSeconds extends BuiltinFunction{

	public ClockMilliSeconds() {
		super("clock_ms");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return (double)System.currentTimeMillis();
	}

	@Override
	public int argsSize() {
		return 0;
	}

}
