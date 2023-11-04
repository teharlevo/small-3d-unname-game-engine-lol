package main;

import render.Camrea;
import render.Renderer;

public abstract class Scene{

    private Renderer renderer,UI;
    public  Camrea cam;
    
    public Scene(){
        cam = new Camrea(0, 0, 0);
        renderer = new Renderer("default",cam);
        UI = new Renderer("UI",new Camrea(0, 0, 1));
    }

    public Renderer getRenderer(){
        return renderer;
    }

    public Renderer getUIRenderer(){
        return UI;
    }

    public abstract void init();

    public abstract void update(float dt);

}