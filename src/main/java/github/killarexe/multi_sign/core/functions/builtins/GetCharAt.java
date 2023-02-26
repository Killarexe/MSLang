package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class GetCharAt extends BuiltinFunction{

	public GetCharAt() {
		super("get_char_at");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		String string = (String)args.get(0);
		int index = (int)(double)args.get(1);
		if(string.length() < index) {
			return string.charAt(index);
		}
		return '\0';
	}

	@Override
	public int argsSize() {
		return 2;
	}
}
