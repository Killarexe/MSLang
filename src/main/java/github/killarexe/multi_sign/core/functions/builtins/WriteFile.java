package github.killarexe.multi_sign.core.functions.builtins;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class WriteFile extends BuiltinFunction{

	public WriteFile() {
		super("write_file");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		File file = null;
		if(args.get(0) instanceof File f) {
			file = f;
		}else {
			file = new File(args.get(0).toString());
		}
		if(file.exists() && Files.isWritable(file.toPath())) {
			try {
				Files.writeString(file.toPath(), args.get(1).toString(), StandardOpenOption.valueOf(args.get(2).toString()));
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public int argsSize() {
		return 3;
	}

}
