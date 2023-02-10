package github.killarexe.multi_sign.core.functions.builtins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class Input extends BuiltinFunction{

	public Input() {
		super("input");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		System.out.print(args.get(0));
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			return reader.readLine();
		} catch (IOException e) {
			return "";
		}
	}

	@Override
	public int argsSize() {
		return 1;
	}
	
}
