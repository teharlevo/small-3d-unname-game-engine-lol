package _2DPhysics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJointDef;

public class Physics2D {

    private static Vec2 gravity;
    private static World world;
    private static float physicsTime = 0.0f;

    public static float timeStep = 0.016666f;
    public static boolean isPlaying = true;

    public static void update(float dt) {
        if (!isPlaying) return;

        physicsTime += dt;
        if (physicsTime >= 0.0f) {
            physicsTime -= timeStep;
            world.step(timeStep,  8, 2);
        }
    }

    public static World getWorld(){
        return world;
    }

    public static void reset(){
        if(gravity == null){
            gravity = new Vec2(0, 0);
        }
        world = new World(gravity);
    }

    public static float getGrvityX(){
        return gravity.x;
    }

    public static float getGrvityY(){
        return gravity.y;
    }

    public static void setGrvity(float grvityX, float grvityY) {
        gravity = new Vec2(grvityX,grvityY);
        if(world == null){return;}
        world.setGravity(gravity);
        new DistanceJointDef();
    }
}