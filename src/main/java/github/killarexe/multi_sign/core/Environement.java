package github.killarexe.multi_sign.core;

import java.util.HashMap;
import java.util.Map;

import github.killarexe.multi_sign.core.error.RuntimeError;
import github.killarexe.multi_sign.core.scanner.Token;

public class Environement {
	private final Map<String, Object> values = new HashMap<>();
	private final Environement enclosing;
	
	public Environement() {
		this(null);
	}
	
	public Environement(Environement enclosing) {
		this.enclosing = enclosing;
	}
	
	public void define(String name, Object value) {
		values.put(name, value);
	}
	
	public void assign(Token name, Object value) {
		if(values.containsKey(name.getValue())) {
			values.put(name.getValue(), value);
			return;
		}
		if(enclosing != null) {
			enclosing.assign(name, value);
			return;
		}
		throw new RuntimeError(name, "Can not assign. undefined variable '" + name.getValue() + "'");
	}
	
	public Object get(Token name) throws RuntimeError{
		if(values.containsKey(name.getValue())) {
			return values.get(name.getValue());
		}
		if(enclosing != null) {
			return enclosing.get(name);
		}
		throw new RuntimeError(name, "Undefined variable '" + name.getValue() + "'");
	}
	
	@Override
	public String toString() {
		return values.toString();
	}
	
	public Environement getEnclosing() {
		return enclosing;
	}
	
	public Map<String, Object> getValues() {
		return values;
	}
}
