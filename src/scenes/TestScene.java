

import main.Entity;

import org.jbox2d.dynamics.BodyType;

import _2DPhysics.BoxCollider;
import _2DPhysics.DistanceJointComponent;
import _2DPhysics.Physics2D;
import _2DPhysics.RevoluteJointComponent;
import _2DPhysics.RigidBody;

import main.Input;
import main.Scene;
import main.Window;
import modeling.Mash;
import modeling.Model;

public class TestScene extends Scene{
    RigidBody bady,rightBox,LeftBox;
    
    public static void main(String[] args){
        Window.scenes = new Scene[1];
        Physics2D.setGrvity(0, 0);
        Window.scenes[0] = new TestScene();
        new Window(900,600,"test");
    }
    
    public void init() {
        Entity g = new Entity();
        g.addComponent(new Model("bob", 0, 0, -15));
        g.addComponent(new BoxCollider(1.5f *2, 2f*2));
        g.addComponent(bady = new RigidBody(0,0,0,BodyType.DYNAMIC));
        Entity r = new Entity();
        r.addComponent(new BoxCollider(0.25f*2, 2f*2));
        r.addComponent(LeftBox = new RigidBody(1.625f,0,0,BodyType.DYNAMIC));
        r.addComponent(new Model(new Mash("4"), 1.625f, 0, -15));
        Entity j = new Entity();
        j.addComponent(new BoxCollider(0.25f*2, 2f*2));
        j.addComponent(rightBox = new RigidBody(-1.625f,0,0,BodyType.DYNAMIC));
        j.addComponent(new Model(new Mash("4"), -1.625f, 0, -15));
        DistanceJointComponent k = new DistanceJointComponent(bady.getBody(),1.625f);
        k.setLocalAnchorA(0, 1);
        k.setLocalAnchorB(0, 1);
        DistanceJointComponent k2 = new DistanceJointComponent(bady.getBody(),1.625f);
        k2.setLocalAnchorA(0, -1);
        k2.setLocalAnchorB(0, -1);

        j.addComponent(k);
        j.addComponent(k2);
        g.addComponent(new RevoluteJointComponent(LeftBox.getBody()));
        g.addComponent(new RevoluteJointComponent(rightBox.getBody()));

        DistanceJointComponent w = new DistanceJointComponent(bady.getBody(),1.625f);
        w.setLocalAnchorA(0, 1);
        w.setLocalAnchorB(0, 1);
        DistanceJointComponent w2 = new DistanceJointComponent(bady.getBody(),1.625f);
        w2.setLocalAnchorA(0, -1);
        w2.setLocalAnchorB(0, -1);
        r.addComponent(w);
        r.addComponent(w2);
    }
    public void update(float dt) {
        float x = 0;
        float y = 0;
        
        if(Input.getKeyPress("a")){
            x += 5.0f;
        }
        if(Input.getKeyPress("s")){
            x -= 5.0f;
        }
        if(Input.getKeyPress("d")){
            y += 5.0f;
        }
        if(Input.getKeyPress("f")){
            y -= 5.0f;
        }
        float sin = (float)Math.sin(Math.toRadians((double)rightBox.getAngle()));
        float cos = (float)Math.cos(Math.toRadians((double)rightBox.getAngle()));
        rightBox.addForce(-1000 * sin * x, -1000 * cos * x);
        sin = (float)Math.sin(Math.toRadians((double)LeftBox.getAngle()));
        cos = (float)Math.cos(Math.toRadians((double)LeftBox.getAngle()));
        LeftBox.addForce( -1000 * sin  * y, -1000 * cos * y);
    }
}