package main;

import render.Camrea;
import render.Renderer;

public abstract class Scene{

    public Renderer renderer;
    public  Camrea cam;
    
    public Scene(){
        cam = new Camrea(0, 0, 0);
        renderer = new Renderer("default",cam);
    }

    public abstract void init();

    public abstract void update(float dt);

}