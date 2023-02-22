package github.killarexe.multi_sign.graphics;

import org.joml.Vector2d;
import org.joml.Vector2i;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private static boolean[] keysPressed = new boolean[GLFW_KEY_LAST];
    private static boolean[] keysJustPressed = new boolean[GLFW_KEY_LAST];
    private static boolean[] buttonsPressed = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static Vector2d mousePos, scrool;
    private static Vector2i mouseDelta;

    private GLFWKeyCallback keybordCallback;
    private GLFWScrollCallback scrollCallback;
    private GLFWCursorPosCallback mousePosCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;

    public Input(){
        scrool = new Vector2d();
        mousePos = new Vector2d();
        mouseDelta = new Vector2i();
        keybordCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keysPressed[key] = (action != GLFW_RELEASE);
                if(keysJustPressed[key] && action == GLFW_PRESS){
                    keysJustPressed[key] = false;
                }else{
                    keysJustPressed[key] = (action == GLFW_PRESS);
                }
            }
        };

        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttonsPressed[button] = (action != GLFW_RELEASE);
            }
        };

        mousePosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
            	mouseDelta.set((int) (xpos-mousePos.x), (int) (ypos-mousePos.y)).mul(100);
                mousePos.set(xpos, ypos);
            }
        };
        scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrool.add(xoffset, yoffset);
            }
        };
    }

    public static boolean isKeyDown(int key){
        return keysPressed[key];
    }

    public static boolean isKeyJustPressed(int key){
        return keysJustPressed[key];
    }

    public static boolean isButtonDown(int button){
        return buttonsPressed[button];
    }

    public void free(){
        keybordCallback.free();
        mouseButtonCallback.free();
        mousePosCallback.free();
        scrollCallback.free();
    }

    public GLFWKeyCallback getKeybordCallback() {
        return keybordCallback;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }

    public GLFWCursorPosCallback getMousePosCallback() {
        return mousePosCallback;
    }

    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    public static Vector2i getMouseDirection() {
        Vector2i value = new Vector2i(mouseDelta);
        mouseDelta.set(0, 0);
        return value;
    }

    public static Vector2d getMousePos() {
        if(mousePos == null){
            return new Vector2d(0);
        }
        return mousePos;
    }

    public static float getScrool() {
        float s = (float) scrool.y;
        if(scrool.y < 0){
            scrool.y++;
        }
        if(scrool.y > 0){
            scrool.y--;
        }
        return s;
    }
}