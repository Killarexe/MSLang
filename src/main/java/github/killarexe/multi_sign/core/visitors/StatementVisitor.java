package github.killarexe.multi_sign.core.visitors;

import github.killarexe.multi_sign.core.statements.*;

public interface StatementVisitor<R> {
    R visitBlockStatement(BlockStatement statement);
    R visitExpressionStatement(ExpressionStatement statement);
    R visitFunctionStatement(FunctionStatement statement);
    R visitIfStatement(IfStatement statement);
    R visitPrintStatement(PrintStatement statement);
    R visitReturnStatement(ReturnStatement statement);
    R visitVariableStatement(VariableStatement statement);
    R visitWhileStatement(WhileStatement statement);
    R visitIncludeStatement(IncludeStatement statement);
}
