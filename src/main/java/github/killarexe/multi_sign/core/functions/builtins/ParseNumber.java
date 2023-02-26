package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class ParseNumber extends BuiltinFunction{

	public ParseNumber() {
		super("parse_number");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		Object object = args.get(0);
		if(object instanceof String) {
			return Double.parseDouble((String)args.get(0));
		}
		if(object instanceof Character ch) {
			return (double)ch;
		}
		return 0;
	}

	@Override
	public int argsSize() {
		return 1;
	}

}
