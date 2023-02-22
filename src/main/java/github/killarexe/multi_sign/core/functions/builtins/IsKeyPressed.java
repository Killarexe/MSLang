package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;
import github.killarexe.multi_sign.graphics.Input;

public class IsKeyPressed extends BuiltinFunction{

	public IsKeyPressed() {
		super("is_key_pressed");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return Input.isKeyDown((int)(double)args.get(0));
	}

	@Override
	public int argsSize() {
		return 1;
	}

}
