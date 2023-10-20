package main;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

public class Entity {
    public Vector3f  pos;
    public Vector3f scale;
    public  float angleX = 0;
    public  float angleY = 0;
    public  float angleZ = 0;

    private List<Component> components;

    public Entity() {
        components = new ArrayList<>();
        Window.addObjecttoScene(this);
        pos = new Vector3f(0,0,0);
        scale = new Vector3f(1,1,1);
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
}