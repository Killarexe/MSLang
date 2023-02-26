package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class ToChar extends BuiltinFunction{

	public ToChar() {
		super("to_char");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		if(args.get(0) instanceof Number number)
			return (char)(int)number;
		return '\0';
	}

	@Override
	public int argsSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
