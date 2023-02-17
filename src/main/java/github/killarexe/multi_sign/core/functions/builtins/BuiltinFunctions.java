package github.killarexe.multi_sign.core.functions.builtins;

import java.util.ArrayList;
import java.util.List;

import github.killarexe.multi_sign.core.Environement;
import github.killarexe.multi_sign.core.functions.MSCallable;

public class BuiltinFunctions {
	
	private static final List<BuiltinFunction> FUNCTIONS = new ArrayList<>();
	
	public static final BuiltinFunction CLOCK_MILLISECONDS = register(new ClockMilliSeconds());
	public static final BuiltinFunction CLOCK_SECONDS = register(new ClockSeconds());
	public static final BuiltinFunction PARSE_NUMBER = register(new ParseNumber());
	public static final BuiltinFunction RAND_INT = register(new RandInt());
	public static final BuiltinFunction FORMAT = register(new Format());
	public static final BuiltinFunction INPUT = register(new Input());
	public static final BuiltinFunction ROUND = register(new Round());
	public static final BuiltinFunction EXIT = register(new Exit());
	public static final BuiltinFunction WAIT = register(new Wait());
	
	public static BuiltinFunction register(BuiltinFunction builtinFunction) {
		FUNCTIONS.add(builtinFunction);
		return builtinFunction;
	}
	
	public static void registerAll(Environement global) {
		for(BuiltinFunction function: FUNCTIONS) {
			global.define(function.getName(), (MSCallable)function);
		}
	}
}
