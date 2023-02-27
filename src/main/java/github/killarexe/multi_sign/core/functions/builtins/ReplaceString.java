package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class ReplaceString extends BuiltinFunction{

	public ReplaceString() {
		super("replace_string");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return args.get(0).toString().replace(args.get(1).toString(), args.get(2).toString());
	}
	
	@Override
	public int argsSize() {
		return 3;
	}
}
