package scenes;

import java.util.Random;

import main.Assets;
import main.Entity;

import org.jbox2d.dynamics.BodyType;
import org.joml.Vector3f;

import _2DPhysics.BoxCollider;
import _2DPhysics.Physics2D;
import _2DPhysics.RigidBody;
import main.Input;
import main.Scene;
import main.Window;
import modeling.Model;

public class TestScene extends Scene{
    
    public static void main(String[] args){
        Window.scenes = new Scene[1];
        Physics2D.setGrvity(0, 10);
        Window.scenes[0] = new TestScene();
        new Window(600,600,"test");
    }
    
    public void init() {
        Random r = new Random();
        float dis = 20;
        String[] modelName = new String[]{"bob","bus","mrkrab","pat","sandy"};
        for (int i = 0; i < 1000; i++) {
            Entity entt = new Entity();
            entt.pos = new Vector3f(
                r.nextFloat(-dis, dis), r.nextFloat(-dis, dis),r.nextFloat(-dis, dis));
            entt.angleX = r.nextFloat(-180, 180);
            entt.angleY = r.nextFloat(-180, 180);
            entt.angleZ = r.nextFloat(-180, 180);
            entt.addComponent(new Model(modelName[r.nextInt(modelName.length)],0,0,0));
        }
    }
    
    public void update(float dt) {
        
        float x = 0;
        float y = 0;
        
        if(Input.getKeyPress("w")){
            x += 5.0f;
        }
        if(Input.getKeyPress("s")){
            x -= 5.0f;
        }
        if(Input.getKeyPress("a")){
            y += 5.0f;
        }
        if(Input.getKeyPress("d")){
            y -= 5.0f;
        }
        cam.getPos().add(cam.getLookDir().mul(x * dt,new Vector3f()));
        cam.setAngle(cam.getAngleX(), cam.getAngleY() + 90, cam.getAngleZ());
        cam.getPos().add(cam.getLookDir().mul(y * dt,new Vector3f()));
        cam.setAngle(cam.getAngleX(), cam.getAngleY() - 90, cam.getAngleZ());
        

        float angleX = 0;
        float angleY = 0;
        float angleZ = 0;

        if(Input.getKeyPress("q")){
            angleX += 90.0f;
        }
        if(Input.getKeyPress("e")){
            angleX -= 90.0f;
        }
        if(Input.getKeyPress("c")){
            angleY -= 90.0f;
        }
        if(Input.getKeyPress("v")){
            angleY += 90.0f;
        }
        if(Input.getKeyPress("k")){
            angleZ += 90.0f;
        }
        if(Input.getKeyPress("j")){
            angleZ -= 90.0f;
        }

        cam.setAngle(cam.getAngleX() + angleX * dt, cam.getAngleY() + angleY * dt,cam.getAngleZ() + angleZ * dt);

    }   
}