package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;
import github.killarexe.multi_sign.graphics.Window;

public class WindowUpdate extends BuiltinFunction{

	public WindowUpdate() {
		super("window_update");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		((Window)args.get(0)).update();
		((Window)args.get(0)).render();
		return null;
	}

	@Override
	public int argsSize() {
		return 1;
	}

}
