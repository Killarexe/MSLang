package github.killarexe.multi_sign.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import github.killarexe.multi_sign.core.error.ErrorHandler;
import github.killarexe.multi_sign.core.error.ParseError;
import github.killarexe.multi_sign.core.error.RuntimeError;
import github.killarexe.multi_sign.core.expressions.AssignExpression;
import github.killarexe.multi_sign.core.expressions.BinaryExpression;
import github.killarexe.multi_sign.core.expressions.CallExpression;
import github.killarexe.multi_sign.core.expressions.Expression;
import github.killarexe.multi_sign.core.expressions.GroupingExpression;
import github.killarexe.multi_sign.core.expressions.LiteralExpression;
import github.killarexe.multi_sign.core.expressions.LogicalExpression;
import github.killarexe.multi_sign.core.expressions.UnaryExpression;
import github.killarexe.multi_sign.core.expressions.VariableExpression;
import github.killarexe.multi_sign.core.scanner.Token;
import github.killarexe.multi_sign.core.scanner.Token.TokenType;
import github.killarexe.multi_sign.core.statements.BlockStatement;
import github.killarexe.multi_sign.core.statements.ExpressionStatement;
import github.killarexe.multi_sign.core.statements.FunctionStatement;
import github.killarexe.multi_sign.core.statements.IfStatement;
import github.killarexe.multi_sign.core.statements.IncludeStatement;
import github.killarexe.multi_sign.core.statements.PrintStatement;
import github.killarexe.multi_sign.core.statements.ReturnStatement;
import github.killarexe.multi_sign.core.statements.Statement;
import github.killarexe.multi_sign.core.statements.VariableStatement;
import github.killarexe.multi_sign.core.statements.WhileStatement;

public class Parser {
	private final List<Token> tokens;
	private int current;
	
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
		this.current = 0;
	}
	
	private void synchronize() {
		advance();
		while(!isEndOfFile()) {
			if(previous().getType() == TokenType.SEMICOLON) return;
			switch(peek().getType()) {
		        case CLASS:
		        case FN:
		        case VAR:
		        case FOR:
		        case IF:
		        case WHILE:
		        case PRINT:
		        case RETURN:
		        case DEFINE:
		        case INCLUDE:
		        	return;
		        default:
		        	break;
			}
			advance();
		}
	}
	
	public List<Statement> parse() {
		List<Statement> statements = new ArrayList<>();
		while(!isEndOfFile()) {
			statements.add(declaration());
		}
		return statements;
	}
	
	private Expression assignement() throws RuntimeError{
		Expression expression = or();
		
		if(match(TokenType.ASSIGN, TokenType.PLUS_EQUAL, TokenType.MINUS_EQUAL, TokenType.STAR_EQUAL,
				TokenType.SLASH_EQUAL, TokenType.AND_EQUAL, TokenType.OR_EQUAL, TokenType.XOR_EQUAL, TokenType.MODULO_EQUAL)) {
			Token equal = previous();
			Expression value = assignement();
			
			if(expression instanceof VariableExpression) {
				Token name = ((VariableExpression)expression).getName();
				return new AssignExpression(name, equal, value);
			}
			throw error(equal, "Invalid assignement...");
		}
		return expression;
	}
	
	private Expression or() {
		Expression expression = and();
		while(match(TokenType.OR)) {
			Token operator = previous();
			Expression right = and();
			expression = new LogicalExpression(expression, operator, right);
		}
		return expression;
	}
	
	private Expression and() {
		Expression expression = equality();
		while(match(TokenType.AND)) {
			Token operator = previous();
			Expression right = equality();
			expression = new LogicalExpression(expression, operator, right);
		}
		return expression;
	}
	
	private Statement declaration() {
		try {
			while(match(TokenType.DEFINE)) {
				String key = advance().getValue();
				Token type = advance();
				for(int i = current - 1; i < tokens.size(); i++) {
					if(tokens.get(i).getValue().equals(key)) {
						tokens.set(
							i,
							new Token(
								type.getType(),
								type.getLiteral(), type.getType() == TokenType.IDENTIFIER ? type.getValue() : key,
								tokens.get(i).getLine()
							)
						);
					}
				}
				consume(TokenType.SEMICOLON, "Expected ';' at the end of the define declaration...");
			}
			if(match(TokenType.INCLUDE)) {
				Statement statement = new IncludeStatement(primary());
				consume(TokenType.SEMICOLON, "Expected ';' at the end of include...");
				return statement;
			}
			if(match(TokenType.FN)) {
				return function("function");
			}
			if(match(TokenType.VAR)) {
				return varDeclaration();
			}
			return statement();
		}catch(ParseError error) {
			synchronize();
			return null;
		}
	}
	
	private FunctionStatement function(String kind) throws ParseError{
		Token name = consume(TokenType.IDENTIFIER, "Expected " + kind + " name...");
		consume(TokenType.LEFT_PAREN, "Expected '(' after " + kind + " name...");
		List<Token> parameters = new ArrayList<>();
		if(!check(TokenType.RIGHT_PAREN)) {
			do {
				if(parameters.size() >= 255) {
					throw error(peek(), "Function parameters can't have more than 255...");
				}
				parameters.add(consume(TokenType.IDENTIFIER, "Expected parameter name..."));
			}while(match(TokenType.COMMA));
		}
		consume(TokenType.RIGHT_PAREN, "Expected ')' after parameters...");
		consume(TokenType.LEFT_BRACE, "Expected '{' before " + kind + " body...");
		List<Statement> statements = block();
		return new FunctionStatement(name, parameters, statements);
	}
	
	private Statement varDeclaration() {
		Token name = consume(TokenType.IDENTIFIER, "Expected variable name...");
		Expression initializer = null;
		if(match(TokenType.ASSIGN)) {
			initializer = expression();
		}
		consume(TokenType.SEMICOLON, "Expected ';' at the end of the variable declaration...");
		return new VariableStatement(name, initializer);
	}
	
	private Statement statement() {
		if(match(TokenType.RETURN)) {
			return returnStatement();
		}
		if(match(TokenType.PRINT)) {
			return printStatement();
		}
		if(match(TokenType.WHILE)) {
			return whileStatement();
		}
		if(match(TokenType.LEFT_BRACE)) {
			return new BlockStatement(block());
		}
		if(match(TokenType.FOR)) {
			return forStatement();
		}
		if(match(TokenType.IF)) {
			return ifStatement();
		}
		return expressionStatement();
	}
	
	private Statement returnStatement() {
		Token keyword = previous();
		Expression value = null;
		if(!check(TokenType.SEMICOLON)) {
			value = expression();
		}
		consume(TokenType.SEMICOLON, "Expected ';' for the return statement...");
		return new ReturnStatement(keyword, value);
	}
	
	private Statement forStatement() {
		consume(TokenType.LEFT_PAREN, "Expected '(' after the for keyword...");
		Statement initializer = match(TokenType.SEMICOLON) ? null : match(TokenType.VAR) ? varDeclaration() : expressionStatement();
		Expression condition = !check(TokenType.SEMICOLON) ? expression() : new LiteralExpression(true);
		consume(TokenType.SEMICOLON, "Expected ';' after the loop condition...");
		Expression increment = !check(TokenType.RIGHT_PAREN) ? expression() : null;
		consume(TokenType.RIGHT_PAREN, "Expected ')' after the increment...");
		Statement body = statement();
		if(increment != null) {
			body = new BlockStatement(Arrays.asList(body, new ExpressionStatement(increment)));
		}
		body = new WhileStatement(condition, body);
		if(initializer != null) {
			body = new BlockStatement(Arrays.asList(initializer, body));
		}
		return body;
	}
	
	private Statement whileStatement() {
		consume(TokenType.LEFT_PAREN, "Expected '(' after the while keyword...");
		Expression condition = expression();
		consume(TokenType.RIGHT_PAREN, "Expected ')' after the condition...");
		Statement body = statement();
		return new WhileStatement(condition, body);
	}
	
	private Statement ifStatement() {
		consume(TokenType.LEFT_PAREN, "Expect '(' after 'if'...");
		Expression condition = expression();
		consume(TokenType.RIGHT_PAREN, "Expect ')' after the if condition");
		Statement thenBranch = statement();
		Statement elseBranch = match(TokenType.ELSE) ? statement() : null;
		return new IfStatement(condition, thenBranch, elseBranch);
	}
	
	private List<Statement> block(){
		List<Statement> statements = new ArrayList<>();
		
		while(!check(TokenType.RIGHT_BRACE) && !isEndOfFile()) {
			statements.add(declaration());
		}
		
		consume(TokenType.RIGHT_BRACE, "Expected '}' after the block...");
		return statements;
	}
	
	private Statement printStatement() {
		Expression value = expression();
		consume(TokenType.SEMICOLON, "Expected ';' at the end...");
		return new PrintStatement(value);
	}
	
	private Statement expressionStatement() {
		Expression value = expression();
		consume(TokenType.SEMICOLON, "Expected ';' at the end...");
		return new ExpressionStatement(value);
	}
	
	private Expression expression() {
		return assignement();
	}
	
	private Expression equality() {
		Expression expression = comparison();
		while(match(TokenType.NOT_EQUAL, TokenType.EQUALS)){
			Token operator = previous();
			Expression right = comparison();
			expression = new BinaryExpression(expression, operator, right);
		}
		return expression;
	}
	
	private Expression comparison() {
		Expression expression = term();
		while(match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
			Token operator = previous();
			Expression right = term();
			expression = new BinaryExpression(expression, operator, right);
		}
		return expression;
	}
	
	private Expression term() {
		Expression expression = factor();
		while(match(TokenType.PLUS, TokenType.MINUS)) {
			Token operator = previous();
			Expression right = factor();
			expression = new BinaryExpression(expression, operator, right);
		}
		return expression;
	}
	
	private Expression factor() {
		Expression expression = unary();
		while(match(TokenType.SLASH, TokenType.STAR, TokenType.MODULO, TokenType.BIN_AND,
				TokenType.BIN_OR,TokenType.BIN_XOR, TokenType.LEFT_SHIFT, TokenType.RIGHT_SHIFT)) {
			Token operator = previous();
			Expression right = unary();
			expression = new BinaryExpression(expression, operator, right);
		}
		return expression;
	}
	
	private Expression unary() {
		if(match(TokenType.NOT, TokenType.MINUS, TokenType.DECREASE, TokenType.INCREASE)) {
			Token operator = previous();
			Expression right = unary();
			return new UnaryExpression(operator, right);
		}
		return call();
	}
	
	private Expression call() {
		Expression expression = primary();
		while(true) {
			if(match(TokenType.LEFT_PAREN)) {
				expression = finishCall(expression);
			}else {
				break;
			}
		}
		return expression;
	}
	
	private Expression finishCall(Expression expression) throws ParseError{
		List<Expression> args = new ArrayList<>();
		if(!check(TokenType.RIGHT_PAREN)) {
			do {
				if(args.size() >= 255) {
					throw error(peek(), "A function can't have more than 255 arguments");
				}
				args.add(expression());
			} while (match(TokenType.COMMA));
		}
		Token paren = consume(TokenType.RIGHT_PAREN, "Expect ')' after arguments");
		return new CallExpression(paren, expression, args);
	}
	
	private Expression primary() throws ParseError{
		if(match(TokenType.FALSE)) {
			return new LiteralExpression(false);
		}
		if(match(TokenType.TRUE)) {
			return new LiteralExpression(true);
		}
		if(match(TokenType.NULL)) {
			return new LiteralExpression(null);
		}
		if(match(TokenType.NUMBER, TokenType.STRING)){
			return new LiteralExpression(previous().getLiteral());
		}
		if(match(TokenType.IDENTIFIER)) {
			return new VariableExpression(previous());
		}
		if(match(TokenType.LEFT_PAREN)) {
			Expression expression = expression();
			consume(TokenType.RIGHT_PAREN, "Expect ')' after expression");
			return new GroupingExpression(expression);
		}
		throw error(peek(), "Unexpected token / Expected an expression...");
	}
	
	private Token consume(TokenType type, String errorMessage) throws ParseError{
		if(check(type)) {
			return advance();
		}
		throw error(peek(), errorMessage);
	}
	
	private ParseError error(Token token, String message) {
	    ErrorHandler.error(token, message);
	    return new ParseError();
	}
	
	private boolean match(TokenType... types) {
		for(TokenType type: types) {
			if(check(type)) {
				advance();
				return true;
			}
		}
		return false;
	}

	private Token advance() {
		if(!isEndOfFile()) {
			current++;
		}
		return previous();
	}
	
	private boolean check(TokenType type) {
		return !isEndOfFile() && peek().getType() == type;
	}
	
	private boolean isEndOfFile() {
		return peek().getType() == TokenType.EOF;
	}
	
	private Token peek() {
		return tokens.get(current);
	}
	
	private Token previous() {
		return tokens.get(current - 1);
	}
}
