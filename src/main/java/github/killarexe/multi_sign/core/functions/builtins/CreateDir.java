package github.killarexe.multi_sign.core.functions.builtins;

import java.io.File;
import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class CreateDir extends BuiltinFunction{
	
	public CreateDir() {
		super("create_dir");
	}
	
	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		return new File((String)args.get(0)).mkdir();
	}

	@Override
	public int argsSize() {
		return 1;
	}
}