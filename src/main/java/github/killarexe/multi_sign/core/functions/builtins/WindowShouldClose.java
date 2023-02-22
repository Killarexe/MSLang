package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;
import github.killarexe.multi_sign.graphics.Window;

public class WindowShouldClose extends BuiltinFunction{

	public WindowShouldClose() {
		super("window_should_close");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return ((Window)args.get(0)).shouldClose();
	}

	@Override
	public int argsSize() {
		return 1;
	}

	
}
