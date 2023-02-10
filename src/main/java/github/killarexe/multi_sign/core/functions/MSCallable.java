package github.killarexe.multi_sign.core.functions;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public interface MSCallable {
	public Object call(Interpreter interpreter, List<Object> args);
	public int argsSize();
}
