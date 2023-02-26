package github.killarexe.multi_sign.core.functions.builtins;

//import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import github.killarexe.multi_sign.core.visitors.Interpreter;

public class DrawRecangle extends BuiltinFunction{

	public DrawRecangle() {
		super("draw_rectangle");
	}

	@Override
	public Object call(Interpreter interpreter, List<Object> args) {
		/*float x = (float)(double)args.get(0);
		float y = (float)(double)args.get(1);
		float width = (float)(double)args.get(2);
		float height = (float)(double)args.get(3);
		float red = (float)(double)args.get(4);
		float green = (float)(double)args.get(5);
		float blue = (float)(double)args.get(6);
		glBegin(GL_QUADS);
		glColor3f(red, green, blue);
		glVertex2f(-0.5f, 0.5f);
		glVertex2f(0.5f, 0.5f);
		glVertex2f(0.5f, -0.5f);
		glVertex2f(-0.5f, -0.5f);
		glEnd();*/
		return null;
	}

	@Override
	public int argsSize() {
		return 7;
	}

}
