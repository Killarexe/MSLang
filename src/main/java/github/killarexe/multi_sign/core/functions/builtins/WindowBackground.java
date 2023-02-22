package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import org.joml.Vector3f;

import github.killarexe.multi_sign.core.visitors.Interpreter;
import github.killarexe.multi_sign.graphics.Window;

public class WindowBackground extends BuiltinFunction{

	public WindowBackground() {
		super("window_background");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		((Window)args.get(0)).setBackgroundColor(new Vector3f((float)(double)args.get(1), (float)(double)args.get(2), (float)(double)args.get(3)));
		return null;
	}

	@Override
	public int argsSize() {
		return 4;
	}
}
