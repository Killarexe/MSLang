package github.killarexe.multi_sign.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageWrite;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private float delta;
    private Input input;
    private String title, icon;
    private Vector3f backgroundColor;
    private Matrix4f projectionMatrix;
    private int width, height, frames;
    private int[] windowPosX, windowPosY;
    private boolean isResized, isFullscreen;
    private GLFWWindowSizeCallback windowSizeCallback;
    private long window, lastFrameTime, time, audioContext, audioDevice;

    public Window(String title, String icon, int width, int height){
        this.time = System.currentTimeMillis();
        this.icon = icon;
        this.input = new Input();
        this.title = title;
        this.width = width;
        this.height = height;
        this.frames = 0;
        this.windowPosX = new int[1];
        this.windowPosY = new int[1];
        this.lastFrameTime = System.currentTimeMillis();
        this.backgroundColor = new Vector3f(0, 0, 0);
        this.projectionMatrix = new Matrix4f();
    }

    public void init(){
        if(!glfwInit()){
            throw new IllegalStateException("Unable to init GLFW!");
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        window = glfwCreateWindow(width, height, title, isFullscreen ? glfwGetPrimaryMonitor() : 0, 0);
        if(window == 0){
        	throw new IllegalStateException("Failed to create GLFW Window!");
        }


        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        windowPosX[0] = (vidMode.width() - width)/2;
        windowPosY[0] = (vidMode.height() - height)/2;
        glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
        try {
            this.setIcon(this.icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        glfwMakeContextCurrent(window);
        createCapabilities();
        createCallbacks();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        //glEnable(GL_CULL_FACE);
        //glCullFace(GL_BACK);
        glfwShowWindow(window);
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);
        int[] attributes = {0};
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);
        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
        if(!alCapabilities.OpenAL10){
            System.err.println("Audio Lib Not Supported!");
        }
        glfwSwapInterval(1);
    }

    private void createCallbacks(){
        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };
        glfwSetKeyCallback(window, input.getKeybordCallback());
        glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
        glfwSetCursorPosCallback(window, input.getMousePosCallback());
        glfwSetScrollCallback(window, input.getScrollCallback());
        glfwSetWindowSizeCallback(window, windowSizeCallback);
    }

    public void update(){
        if(isResized) {
            glViewport(0, 0, width, height);
            isResized = false;
        }
        if(Input.isKeyJustPressed(GLFW_KEY_F11)){
            setFullscreen(!isFullscreen);
        }else if(Input.isKeyJustPressed(GLFW_KEY_F2)){
            screenshot();
        }
        projectionMatrix = getProjectionMatrix(70, 0.01f, 1000f);
        frames++;
        if(System.currentTimeMillis() > time + 1000){
            glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
        delta = (System.currentTimeMillis() - lastFrameTime) / 1000f;
        lastFrameTime = System.currentTimeMillis();
    }

    public void render(){
        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwPollEvents();
    }

    public void swapBuffers(){
        glfwSwapBuffers(window);
    }

    public void screenshot(){
        ByteBuffer image = BufferUtils.createByteBuffer(width * height * 4);
        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, image);
        STBImageWrite.stbi_flip_vertically_on_write(true);
        new File("screenshot").mkdir();
        STBImageWrite.stbi_write_png(
                "screenshot/screenshot-" + DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").format(LocalDateTime.now()) + ".png",
                width, height, 4, image, width * 4
        );
    }

    public void free(){
        windowSizeCallback.free();
        input.free();
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }
    
    public float getDelta() {
		return delta;
	}

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isResized() {
        return isResized;
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public Matrix4f getProjectionMatrix(int fov, float near, float far) {
        return projectionMatrix.setPerspective((float) Math.toRadians(fov), (float)width/(float)height, near, far);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public String getTitle() {
        return title;
    }

    public long getWindowPtr() {
        return window;
    }

    public int getFrames() {
        return frames;
    }

    public void setBackgroundColor(Vector3f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
        isResized = true;
        if(fullscreen){
            glfwGetWindowPos(window, windowPosX, windowPosY);
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        }else{
            glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }
    }

    public void setMouseState(boolean lock){
        glfwSetInputMode(window, GLFW_CURSOR, lock ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(String path) throws Exception{
        if(path == null || path.isEmpty()){
            return;
        }
        this.icon = path;
        IntBuffer w = memAllocInt(1);
        IntBuffer h = memAllocInt(1);
        IntBuffer comp = memAllocInt(1);

        // Icons
        {
            ByteBuffer icon16;
            ByteBuffer icon32;
            try {
                icon16 = ioResourceToByteBuffer(path, 2048);
                icon32 = ioResourceToByteBuffer(path, 4096);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try ( GLFWImage.Buffer icons = GLFWImage.malloc(2) ) {
                ByteBuffer pixels16 = STBImage.stbi_load_from_memory(icon16, w, h, comp, 4);
                icons
                        .position(0)
                        .width(w.get(0))
                        .height(h.get(0))
                        .pixels(pixels16);

                ByteBuffer pixels32 = STBImage.stbi_load_from_memory(icon32, w, h, comp, 4);
                icons
                        .position(1)
                        .width(w.get(0))
                        .height(h.get(0))
                        .pixels(pixels32);

                icons.position(0);
                glfwSetWindowIcon(window, icons);

                STBImage.stbi_image_free(pixels32);
                STBImage.stbi_image_free(pixels16);
            }
        }

        memFree(comp);
        memFree(h);
        memFree(w);

    }
    
    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if(Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while ( fc.read(buffer) != -1 ) ;
            }
        }else{
            try(
                    InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ){
                buffer = BufferUtils.createByteBuffer(bufferSize);

                while(true) {
                    int bytes = rbc.read(buffer);
                    if(bytes == -1)
                        break;
                    if(buffer.remaining() == 0)
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                }
            }
        }

        buffer.flip();
        return buffer;
    }
}