package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;
import github.killarexe.multi_sign.graphics.Window;

public class WindowStop extends BuiltinFunction{

	public WindowStop() {
		super("window_stop");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		((Window)args.get(0)).free();
		return null;
	}

	@Override
	public int argsSize() {
		return 1;
	}
}
