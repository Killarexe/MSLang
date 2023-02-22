package github.killarexe.multi_sign.core.functions.builtins;

import java.io.File;
import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class FileExists extends BuiltinFunction{
	
	public FileExists() {
		super("file_exists");
	}
	
	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		if(args.get(0) instanceof File file) {
			return file.exists();
		}
		return new File(args.get(0).toString()).exists();
	}
	
	@Override
	public int argsSize() {
		return 1;
	}
}