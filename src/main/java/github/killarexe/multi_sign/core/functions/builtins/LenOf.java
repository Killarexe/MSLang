package github.killarexe.multi_sign.core.functions.builtins;

import java.lang.reflect.Array;
import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class LenOf extends BuiltinFunction{

	public LenOf() {
		super("len_of");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		Object object = args.get(0);
		if(object instanceof String string) {
			return (double)string.length();
		}
		if(object instanceof Array array) {
			return (double)Array.getLength(array);
		}
		if(object instanceof List<?> list) {
			return (double)list.size();
		}
		return 0;
	}

	@Override
	public int argsSize() {
		return 1;
	}

}
