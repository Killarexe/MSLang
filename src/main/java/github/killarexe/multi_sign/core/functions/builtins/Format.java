package github.killarexe.multi_sign.core.functions.builtins;

import java.util.ArrayList;
import java.util.List;

import github.killarexe.multi_sign.core.error.RuntimeError;
import github.killarexe.multi_sign.core.visitors.Interpreter;

public class Format extends BuiltinFunction{

	public Format() {
		super("format");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		if(args.size() < 2) {
			throw new RuntimeError(null, "'format' needs more than 2 arguments!");
		}
		List<Object> formatArgs = new ArrayList<>();
		for(int i = 1; i < args.size(); i++) {
			formatArgs.add(args.get(i));
		}
		return String.format((String)args.get(0), formatArgs);
	}

	@Override
	public int argsSize() {
		return -1;
	}

}
