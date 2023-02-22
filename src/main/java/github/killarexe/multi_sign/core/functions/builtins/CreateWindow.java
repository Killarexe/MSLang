package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;
import github.killarexe.multi_sign.graphics.Window;

public class CreateWindow extends BuiltinFunction{

	public CreateWindow() {
		super("create_window");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		Window window = new Window((String)args.get(0), args.get(1) == null ? null : (String)args.get(1), (int)(double)args.get(2), (int)(double)args.get(3));
		window.init();
		return window;
	}

	@Override
	public int argsSize() {
		return 4;
	}
}
