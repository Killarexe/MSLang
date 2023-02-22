package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;
import github.killarexe.multi_sign.graphics.Window;

public class WindowDelta extends BuiltinFunction{

	public WindowDelta() {
		super("window_delta");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return (double)((Window)args.get(0)).getDelta();
	}

	@Override
	public int argsSize() {
		return 1;
	}

}
