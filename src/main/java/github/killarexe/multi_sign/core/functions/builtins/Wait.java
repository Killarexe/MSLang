package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;
import java.util.concurrent.TimeUnit;

import github.killarexe.multi_sign.core.error.RuntimeError;
import github.killarexe.multi_sign.core.visitors.Interpreter;

public class Wait extends BuiltinFunction{

	public Wait() {
		super("wait");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		try {
			TimeUnit.MILLISECONDS.sleep(((Double)args.get(0)).longValue());
		} catch (InterruptedException e) {
			throw new RuntimeError(null, e.getMessage());
		}
		return null;
	}

	@Override
	public int argsSize() {
		return 1;
	}
}
