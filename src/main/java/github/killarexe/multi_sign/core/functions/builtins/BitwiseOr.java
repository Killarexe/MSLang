package github.killarexe.multi_sign.core.functions.builtins;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class BitwiseOr extends BuiltinFunction{

	public BitwiseOr() {
		super("bit_xor");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return ((Double)args.get(0)).intValue() | ((Double)args.get(1)).intValue();
	}

	@Override
	public int argsSize() {
		return 2;
	}

}
