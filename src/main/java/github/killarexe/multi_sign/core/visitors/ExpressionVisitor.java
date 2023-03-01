package github.killarexe.multi_sign.core.visitors;

import github.killarexe.multi_sign.core.expressions.*;

public interface ExpressionVisitor<R> {
	R visitBinaryExpression(BinaryExpression expression);
	R visitGroupingExpression(GroupingExpression expression);
	R visitLiteralExpression(LiteralExpression expression);
	R visitUnaryExpression(UnaryExpression expression);
    R visitCallExpression(CallExpression expression);
    R visitLogicalExpression(LogicalExpression expression);
    R visitVariableExpression(VariableExpression expression);
    R visitAssignExpression(AssignExpression expression);
}
