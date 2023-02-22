package github.killarexe.multi_sign.core.functions.builtins;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class DrawRecangle extends BuiltinFunction{

	public DrawRecangle() {
		super("draw_rectangle");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		float x = (float)(double)args.get(0);
		float y = (float)(double)args.get(1);
		float width = (float)(double)args.get(2);
		float height = (float)(double)args.get(3);
		float red = (float)(double)args.get(4);
		float green = (float)(double)args.get(5);
		float blue = (float)(double)args.get(6);
		glBegin(GL_TRIANGLE_FAN);
		glColor3f(red, green, blue);
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
		return null;
	}

	@Override
	public int argsSize() {
		return 8;
	}

}
