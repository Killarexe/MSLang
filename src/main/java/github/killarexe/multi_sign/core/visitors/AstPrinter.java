package github.killarexe.multi_sign.core.visitors;

import github.killarexe.multi_sign.core.expressions.*;

public class AstPrinter implements ExpressionVisitor<String>{

	public String print(Expression expression) {
		return expression.accept(this);
	}
	
	private String parenthesize(String name, Expression... expressions) {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(name);
		for(Expression expression: expressions) {
			builder.append(" ");
			builder.append(expression.accept(this));
		}
		builder.append(")");
		return builder.toString();
	}
	
	@Override
	public String visitBinaryExpression(BinaryExpression expression) {
		return parenthesize(expression.getOperator().getValue(), expression.getLeft(), expression.getRight());
	}

	@Override
	public String visitGroupingExpression(GroupingExpression expression) {
		return parenthesize("group", expression.getExpression());
	}

	@Override
	public String visitLiteralExpression(LiteralExpression expression) {
		return expression.getValue() == null ? "null" : expression.getValue().toString();
	}

	@Override
	public String visitUnaryExpression(UnaryExpression expression) {
		return parenthesize(expression.getOperator().getValue(), expression.getRight());
	}

	@Override
	public String visitCallExpression(CallExpression expression) {
		return null;
	}

	@Override
	public String visitGetExpression(GetExpression expression) {
		return null;
	}

	@Override
	public String visitLogicalExpression(LogicalExpression expression) {
		return null;
	}

	@Override
	public String visitSetExpression(SetExpression expression) {
		return null;
	}

	@Override
	public String visitSuperExpression(SuperExpression expression) {
		return null;
	}

	@Override
	public String visitThisExpression(ThisExpression expression) {
		return null;
	}

	@Override
	public String visitVariableExpression(VariableExpression expression) {
		return null;
	}
	
	@Override
	public String visitAssignExpression(AssignExpression expression) {
		return null;
	}
}
