package main;

//import org.jbox2d.common.Vec2;
//import org.jbox2d.dynamics.World;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;

import Sound.SoundMaster;
import _2DPhysics.Physics2D;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.ALC10.*;

public class Window {


    public static Scene[] scenes;
    private static Scene currentScene;
    private static List<Entity> gameObjects;
    

    private int FPS = 0;
    static float dt = 0;
    private float FPS_timer = System.nanoTime();

    private static long window;

    private static int width = 300;
    private static int height = 500; 
    private static String title;

    private static float time = 0;
    

    public Window(String _title){
        title = _title;
        init(true,1920,1080);
        gameLoop();
        freeMemory();
    }

    public Window(int _width,int _height,String _title){
        title = _title;
        init(false,_width,_height);
        gameLoop();
        freeMemory();
    }

    private void init(boolean fullScreen,int _width,int _height){
        
        width = _width;
        height = _height;
        GLFWErrorCallback.createPrint(System.err).set();
        
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }
        
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        if(fullScreen){
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE);
            window = glfwCreateWindow(width, height, title,  glfwGetPrimaryMonitor(), NULL);
        }
        else{
            window = glfwCreateWindow(width, height, title,  NULL, NULL);
        }

        if (window == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }
        
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        long audioDevice = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        long audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (!alCapabilities.OpenAL10) {
            assert false : "Audio library not supported.";
        }

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);  
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        Assets.totalImport("assets");
        Input.init();

        changeScene(0);

    }

    private void gameLoop(){
        float endframe;
        float stertframe = (float)glfwGetTime();
        while (!glfwWindowShouldClose(window)){

            glfwPollEvents();
            glfwSwapBuffers(window);

            glClearColor(0.0f, 0.0f, 0.3f, 1.0f );
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            update();
            FPS ++;
            if(System.nanoTime() - FPS_timer > 1000000000.0){
                FPS_timer = System.nanoTime();              
                System.out.println("FPS:" + FPS);
                FPS = 0;
            }
            endframe = (float)glfwGetTime();
            dt = (endframe - stertframe);
            stertframe = (float)glfwGetTime();
            time += dt;
        }
    }

    private void freeMemory(){
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        
        glfwTerminate();
        glfwSetErrorCallback(null).free();

        glfwTerminate();
    }

    private void update(){
        
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update(dt);
        }
        
        Physics2D.update(dt);
        SoundMaster.update();

        currentScene.update(dt);
        Input.update(window);
    }

    public static void changeScene(int newScene){
        Physics2D.reset();
        gameObjects = new ArrayList<>();
        if(newScene > -1 && newScene < scenes.length){
            try {
                currentScene = (Scene)scenes[newScene].getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("change scene dont work");
                e.printStackTrace();
            }
            currentScene.init();
        }
        else{
            assert false : "Unknown scene '" + newScene + "'";
        }
    }

    public static Scene getCurrentScene(){
        return currentScene;
    }

    public static int width(){
        return width;
    }

    public static int height(){
        return height;
    }

    public static float time(){
        return time;
    }
    
    public static void addObjecttoScene(Entity e){
        gameObjects.add(e);
    }
}