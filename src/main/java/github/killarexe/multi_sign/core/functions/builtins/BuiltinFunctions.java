package github.killarexe.multi_sign.core.functions.builtins;

import java.util.ArrayList;
import java.util.List;

import github.killarexe.multi_sign.core.Environement;
import github.killarexe.multi_sign.core.functions.MSCallable;

public class BuiltinFunctions {
	
	private static final List<BuiltinFunction> FUNCTIONS = new ArrayList<>();
	
	public static final BuiltinFunction WINDOW_SHOULD_CLOSE = register(new WindowShouldClose());
	public static final BuiltinFunction CLOCK_MILLISECONDS = register(new ClockMilliSeconds());
	public static final BuiltinFunction WINDOW_BACKGROUND = register(new WindowBackground());
	public static final BuiltinFunction DRAW_RECTANGLE = register(new DrawRecangle());
	public static final BuiltinFunction IS_KEY_PRESSED = register(new IsKeyPressed());
	public static final BuiltinFunction WINDOW_UPDATE = register(new WindowUpdate());
	public static final BuiltinFunction CREATE_WINDOW = register(new CreateWindow());
	public static final BuiltinFunction CLOCK_SECONDS = register(new ClockSeconds());
	public static final BuiltinFunction WINDOW_DELTA = register(new WindowDelta());
	public static final BuiltinFunction PARSE_NUMBER = register(new ParseNumber());
	public static final BuiltinFunction WINDOW_SWAP = register(new WindowSwap());
	public static final BuiltinFunction WINDOW_STOP = register(new WindowStop());
	public static final BuiltinFunction FILE_EXISTS = register(new FileExists());
	public static final BuiltinFunction CREATE_FILE = register(new CreateFile());
	public static final BuiltinFunction GET_CHAR_AT = register(new GetCharAt());
	public static final BuiltinFunction WRITE_FILE = register(new WriteFile());
	public static final BuiltinFunction CREATE_DIR = register(new CreateDir());
	public static final BuiltinFunction OPEN_FILE = register(new OpenFile());
	public static final BuiltinFunction READ_FILE = register(new ReadFile());
	public static final BuiltinFunction RAND_INT = register(new RandInt());
	public static final BuiltinFunction TO_CHAR = register(new ToChar());
	public static final BuiltinFunction FORMAT = register(new Format());
	public static final BuiltinFunction LEN_OF = register(new LenOf());
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
