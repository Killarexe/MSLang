package github.killarexe.multi_sign.core.functions.builtins;

import java.io.File;
import java.io.IOException;
import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class CreateFile extends BuiltinFunction{

	public CreateFile() {
		super("create_file");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		try {
			return new File((String)args.get(0)).createNewFile();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public int argsSize() {
		return 1;
	}
  
}
