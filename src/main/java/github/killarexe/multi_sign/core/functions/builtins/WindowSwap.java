package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;
import github.killarexe.multi_sign.graphics.Window;

public class WindowSwap extends BuiltinFunction{
	public WindowSwap() {
		super("window_swap");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		((Window)args.get(0)).swapBuffers();
		return null;
	}

	@Override
	public int argsSize() {
		return 1;
	}	
}
