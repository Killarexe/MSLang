package github.killarexe.multi_sign.core.functions.builtins;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class ReadFile extends BuiltinFunction{
	
	public ReadFile() {
		super("read_file");
	}
	
	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		if(args.get(0) instanceof File file) {
			try {
				return Files.readString(file.toPath());
			} catch (IOException e) {
				return null;
			}
		}
		try {
			byte[] source = Files.readAllBytes(Paths.get(args.get(0).toString()));
			return new String(source, Charset.defaultCharset());
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	public int argsSize() {
		return 1;
	}
}