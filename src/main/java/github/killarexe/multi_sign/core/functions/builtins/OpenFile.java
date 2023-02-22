package github.killarexe.multi_sign.core.functions.builtins;

import java.io.File;
import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class OpenFile extends BuiltinFunction{
	
	public OpenFile() {
		super("open_file");
	}
	
	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return new File(args.get(0).toString());
	}
	
	@Override
	public int argsSize() {
		return 1;
	}
}