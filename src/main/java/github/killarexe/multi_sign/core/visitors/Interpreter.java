package github.killarexe.multi_sign.core.visitors;

import java.util.ArrayList;
import java.util.List;

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
				return (int)left & (int)right;
			case BIN_OR:
				checkNumberOperands(expression.getOperator(), left, right);
				return (int)left | (int)right;
			case BIN_XOR:
				checkNumberOperands(expression.getOperator(), left, right);
				return (int)left ^ (int)right;
			case PLUS:
				if(left instanceof Double && right instanceof Double) {
					return (double)left + (double)right;
				}
				if(left instanceof String && right instanceof String) {
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
	public Object visitCallExpression(CallExpression expression) {
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
	public Object visitGetExpression(GetExpression expression) {
		return null;
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
	public Object visitSetExpression(SetExpression expression) {
		return null;
	}

	@Override
	public Object visitSuperExpression(SuperExpression expression) {
		return null;
	}

	@Override
	public Object visitThisExpression(ThisExpression expression) {
		return null;
	}

	@Override
	public Object visitVariableExpression(VariableExpression expression) {
		return environement.get(expression.getName());
	}
	
	@Override
	public Object visitAssignExpression(AssignExpression expression) {
		Object value = evaluate(expression.getValue());
		environement.assign(expression.getName(), value);
		return value;
	}
	
	private String stringify(Object value) {
		if (value == null) return "null";
		
		if (value instanceof Double) {
			String text = value.toString();
			if (text.endsWith(".0")) {
				text = text.substring(0, text.length() - 2);
			}
			return text;
		}
		
		return value.toString();
	}
	
	private void checkNumberOperands(Token operator, Object left, Object right) throws RuntimeError{
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
	
	private boolean isEqual(Object a, Object b) {
		if(a == null && b == null) {
			return true;
		}
		if(a == null) {
			return false;
		}
		return a.equals(b);
	}
	
	private boolean isTruthy(Object object) {
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
	public Void visitClassStatement(ClassStatement statement) {
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
