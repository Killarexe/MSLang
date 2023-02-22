package github.killarexe.multi_sign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import github.killarexe.multi_sign.core.Parser;
import github.killarexe.multi_sign.core.scanner.Scanner;
import github.killarexe.multi_sign.core.statements.Statement;
import github.killarexe.multi_sign.core.visitors.Interpreter;

public class Launch {
	
	private static final Interpreter INTERPRETER = new Interpreter();
	
	public static void main(String[] args) {
		if(args.length > 0) {
			try {
				byte[] source = Files.readAllBytes(Paths.get(args[0]));
				execute(new String(source, Charset.defaultCharset()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			InputStreamReader input  = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader(input);
			while(true) {
				System.out.print("Multi Sign > ");
				String code = null;
				try {
					code = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(code == null || code.isEmpty()) {
					break;
				}
				execute(code);
			}
		}
	}
	
	public static void execute(String code) {
		Scanner scanner = new Scanner(code);
		Parser parser = new Parser(scanner.scanTokens());
		List<Statement> statements = parser.parse();
		INTERPRETER.interpret(statements);
	}
}
