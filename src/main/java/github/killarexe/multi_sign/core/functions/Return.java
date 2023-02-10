package github.killarexe.multi_sign.core.functions;

public class Return extends RuntimeException{

	private static final long serialVersionUID = -170144605811376500L;

	private final Object value;
	
	public Return(Object value) {
		super(null, null, false, false);
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
}
