package main;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

public class Entity {
    private Vector3f  pos;
    private Vector3f scale;
    private Vector3f angle;

    private List<Component> components;

    public Entity() {
        components = new ArrayList<>();
        Window.addObjecttoScene(this);
        pos = new Vector3f(0,0,0);
        scale = new Vector3f(1,1,1);
        angle = new Vector3f(0);
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        components.add(c);
        c.objectAttch(this);
        c.start();
    }

    public void update(float dt) {
        for (int i=0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    public Vector3f getPos(){
        return pos;
    }

    public void setPos(Vector3f newPos){
        pos = newPos;
    }

    public void setPos(float x,float y,float z){
        pos = new Vector3f(x, y, z);
    }

    public Vector3f getScale(){
        return scale;
    }

    public void setScale(Vector3f newScale){
        scale = newScale;
    }

    public void setScale(float x,float y,float z){
        scale = new Vector3f(x, y, z);
    }

    public Vector3f getAngle(){
        return angle;
    }

    public void setAngle(Vector3f newAngle){
        angle = newAngle;
    }

    public void setAngle(float x,float y,float z){
        angle = new Vector3f(x, y, z);
    }
}