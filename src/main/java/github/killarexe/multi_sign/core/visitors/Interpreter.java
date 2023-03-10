package github.killarexe.multi_sign.core.visitors;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import github.killarexe.multi_sign.Launch;
import github.killarexe.multi_sign.core.Environement;
import github.killarexe.multi_sign.core.error.ErrorHandler;
import github.killarexe.multi_sign.core.error.RuntimeError;
import github.killarexe.multi_sign.core.expressions.*;
import github.killarexe.multi_sign.core.functions.MSCallable;
import github.killarexe.multi_sign.core.functions.MSFunction;
import github.killarexe.multi_sign.core.functions.Return;
import github.killarexe.multi_sign.core.functions.builtins.BuiltinFunctions;
import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.scanner.Token.TokenType;
import github.killarexe.multi_sign.core.statements.*;

public class Interpreter implements ExpressionVisitor<Object>, StatementVisitor<Void>{
	
	private final Environement globals;
	private Environement environement;
	
	public Interpreter() {
		globals = new Environement();
		environement = globals;
		BuiltinFunctions.registerAll(globals);
	}
	
	public void interpret(List<Statement> statements) {
		try {
			for(Statement statement: statements) {
				statement.accept(this);
			}
		}catch (RuntimeError e) {
			ErrorHandler.error(e);
			System.exit(64);
		}
	}

	@Override
	public Object visitBinaryExpression(BinaryExpression expression) throws RuntimeError{
		Object left = evaluate(expression.getLeft());
		Object right = evaluate(expression.getRight());
		switch(expression.getOperator().getType()) {
			case NOT_EQUAL:
				return !isEqual(left, right);
			case EQUALS:
				return isEqual(left, right);
			case MINUS:
				checkNumberOperand(expression.getOperator(), right);
				return (double)left - (double)right;
			case SLASH:
				checkNumberOperands(expression.getOperator(), left, right);
				if((double)right == 0) {
					return left;
				}
				return (double)left / (double)right;
			case MODULO:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)left % (double)right;
			case STAR:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)left * (double)right;
			case GREATER:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)left > (double)right;
			case LESS:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)left < (double)right;
			case GREATER_EQUAL:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)left >= (double)right;
			case LESS_EQUAL:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)left <= (double)right;
			case BIN_AND:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)((int)(double)left & (int)(double)right);
			case BIN_OR:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)((int)(double)left | (int)(double)right);
			case BIN_XOR:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)((int)(double)left ^ (int)(double)right);
			case LEFT_SHIFT:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)((int)(double)left << (int)(double)right);
			case RIGHT_SHIFT:
				checkNumberOperands(expression.getOperator(), left, right);
				return (double)((int)(double)left >> (int)(double)right);
			case PLUS:
				if(left instanceof Double && right instanceof Double) {
					return (double)left + (double)right;
				}
				if(left instanceof String && right instanceof CharSequence) {
					return (String)left + (String)right;
				}
				if(left instanceof Double && right instanceof String) {
					return (Double)left + (String)right;
				}
				if(left instanceof String && right instanceof Double) {
					return (String)left + (Double)right;
				}
				throw new RuntimeError(expression.getOperator(), "Incompatible variable types...");
			default:
				break;
		}
		return null;
	}

	@Override
	public Object visitGroupingExpression(GroupingExpression expression) {
		return evaluate(expression.getExpression());
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression expression) {
		return expression.getValue();
	}

	@Override
	public Object visitUnaryExpression(UnaryExpression expression) {
		Object right = evaluate(expression.getRight());
		switch(expression.getOperator().getType()) {
			case NOT:
				if(right instanceof Double number) {
					return (double)((int)(double)number ^ Integer.MAX_VALUE);
				}
				return !isTruthy(right);
			case MINUS:
				return -(double)right;
			case INCREASE:
				if(expression.getRight() instanceof VariableExpression variable) {
					environement.assign(variable.getName(), (double)(right) + 1);
				}
				return (double)(right) + 1;
			case DECREASE:
				if(expression.getRight() instanceof VariableExpression variable) {
					environement.assign(variable.getName(), (double)(right) - 1);
				}
				return (double)(right) - 1;
			default:
				break;
		}
		return null;
	}

	private Object evaluate(Expression expression) {
		return expression.accept(this);
	}

	@Override
	public Object visitCallExpression(CallExpression expression) throws RuntimeError{
		Object call = evaluate(expression.getCall());
		List<Object> args = new ArrayList<>();
		for(Expression arg: expression.getArgs()) {
			args.add(evaluate(arg));
		}
		if(!(call instanceof MSCallable)) {
			throw new RuntimeError(expression.getParen(), "Except a function or a class.");
		}
		MSCallable function = (MSCallable)call;
		if (args.size() != function.argsSize() && function.argsSize() >= 0) {
		      throw new RuntimeError(expression.getParen(), "Expected " + function.argsSize() + " arguments but got " + args.size() + ".");
		}
		return function.call(this, args);
	}

	@Override
	public Object visitLogicalExpression(LogicalExpression expression) {
		Object left = evaluate(expression.getLeft());
		if(expression.getOperator().getType() == TokenType.OR) {
			if(isTruthy(left)) {
				return left;
			}
		}else {
			if(!isTruthy(left)) {
				return left;
			}
		}
		return evaluate(expression.getRight());
	}

	@Override
	public Object visitVariableExpression(VariableExpression expression) {
		return environement.get(expression.getName());
	}
	
	@Override
	public Object visitAssignExpression(AssignExpression expression) {
		Object value = evaluate(expression.getValue());
		switch(expression.getOperator().getType()) {
			case ASSIGN:
				break;
			case PLUS_EQUAL:
				if(environement.get(expression.getName()) instanceof String a && value instanceof CharSequence b){
					value = a + b;
					break;
				}
				if(environement.get(expression.getName()) instanceof String a && value instanceof Double b){
					value = a + b;
					break;
				}
				checkNumberOperands(expression.getOperator(), environement.get(expression.getName()), value);
				value = (double)environement.get(expression.getName()) + (double)value;
				break;
			case MINUS_EQUAL:
				checkNumberOperands(expression.getOperator(), environement.get(expression.getName()), value);
				value = (double)environement.get(expression.getName()) - (double)value;
				break;
			case STAR_EQUAL:
				checkNumberOperands(expression.getOperator(), environement.get(expression.getName()), value);
				value = (double)environement.get(expression.getName()) * (double)value;
				break;
			case SLASH_EQUAL:
				checkNumberOperands(expression.getOperator(), environement.get(expression.getName()), value);
				value = (double)environement.get(expression.getName()) / (double)value;
				break;
			case AND_EQUAL:
				checkNumberOperands(expression.getOperator(), environement.get(expression.getName()), value);
				value = (double)((int)(double)environement.get(expression.getName()) & (int)(double)value);
				break;
			case OR_EQUAL:
				checkNumberOperands(expression.getOperator(), environement.get(expression.getName()), value);
				value = (double)((int)(double)environement.get(expression.getName()) | (int)(double)value);
				break;
			case XOR_EQUAL:
				checkNumberOperands(expression.getOperator(), environement.get(expression.getName()), value);
				value = (double)((int)(double)environement.get(expression.getName()) ^ (int)(double)value);
				break;
			case MODULO_EQUAL:
				checkNumberOperands(expression.getOperator(), environement.get(expression.getName()), value);
				value = (double)((int)(double)environement.get(expression.getName()) % (int)(double)value);
				break;
			default:
				break;
		}
		environement.assign(expression.getName(), value);
		return value;
	}
	
	public String stringify(Object value) {
		if (value == null) return "null";
		
		if (value instanceof Double number) {
			String text = number.toString();
			if (text.endsWith(".0")) {
				text = text.replace(".0", "");
			}
			return text;
		}
		if(value instanceof String string) {
			string = string.replace("\\n", "\n");
			return string;
		}
		
		return value.toString();
	}
	
	public void checkNumberOperands(Token operator, Object left, Object right) throws RuntimeError{
		if (left instanceof Double && right instanceof Double) {
			return;
		}
		throw new RuntimeError(operator, "Operand must be a number.");
	}
	
	private void checkNumberOperand(Token operator, Object operand) throws RuntimeError{
		if (operand instanceof Double) {
			return;
		}
		throw new RuntimeError(operator, "Operand must be a number.");
	}
	
	public boolean isEqual(Object a, Object b) {
		if(a == null && b == null) {
			return true;
		}
		if(a == null) {
			return false;
		}
		return a.equals(b);
	}
	
	public boolean isTruthy(Object object) {
		if(object == null) {
			return false;
		}
		if(object instanceof Boolean) {
			return (Boolean)object;
		}
		return true;
	}
	
	/*
	 *	Statement 
	 */

	@Override
	public Void visitBlockStatement(BlockStatement statement) {
		executeBlock(statement.getStatements(), new Environement(environement));
		return null;
	}
	
	@Override
	public Void visitExpressionStatement(ExpressionStatement statement) {
		evaluate(statement.getExpression());
		return null;
	}

	@Override
	public Void visitFunctionStatement(FunctionStatement statement) {
		MSFunction function = new MSFunction(statement);
		environement.define(statement.getName().getValue(), function);
		return null;
	}

	@Override
	public Void visitIfStatement(IfStatement statement) {
		if(isTruthy(evaluate(statement.getCondition()))) {
			statement.getThenBranch().accept(this);
		}else if(statement.getElseBranch() != null) {
			statement.getElseBranch().accept(this);
		}
		return null;
	}

	@Override
	public Void visitPrintStatement(PrintStatement statement) {
		Object value = evaluate(statement.getExpression());
		System.out.println(stringify(value));
		return null;
	}

	@Override
	public Void visitReturnStatement(ReturnStatement statement) throws Return{
		Object value = null;
		if(statement.getValue() != null) {
			value = evaluate(statement.getValue());
		}
		throw new Return(value);
	}

	@Override
	public Void visitVariableStatement(VariableStatement statement) {
		Object value = null;
		if(statement.getInitializer() != null) {
			value = evaluate(statement.getInitializer());
		}
		environement.define(statement.getName().getValue(), value);
		return null;
	}

	@Override
	public Void visitWhileStatement(WhileStatement statement) {
		while(isTruthy(evaluate(statement.getCondition()))) {
			statement.getBody().accept(this);
		}
		return null;
	}
	
	@Override
	public Void visitIncludeStatement(IncludeStatement statement) {
		String fileName = stringify(evaluate(statement.getFile()));
		try {
			byte[] source = Files.readAllBytes(Paths.get(fileName));
			Launch.execute(new String(source, Charset.defaultCharset()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void executeBlock(List<Statement> statements, Environement environement) {
		Environement previous = this.environement;
		try {
			this.environement = environement;
			for(Statement statement: statements) {
				statement.accept(this);
			}
		} finally {
			this.environement = previous;
		}
	}
	
	public Environement getEnvironement() {
		return environement;
	}
	
	public Environement getGlobals() {
		return globals;
	}
}
