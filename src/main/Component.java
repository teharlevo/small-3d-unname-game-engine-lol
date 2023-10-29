package main;

public abstract class Component {
    private Entity object;

    public void objectAttch(Entity obj){
        object = obj;
    }

    public Entity object(){
        return object;
    }   

    public abstract void start();

    public abstract void update(float dt);
}