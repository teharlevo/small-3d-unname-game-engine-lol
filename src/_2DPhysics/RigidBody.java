package _2DPhysics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import main.*;

import org.jbox2d.collision.shapes.*;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.joml.Vector3f;

public class RigidBody extends Component{
    private float lestFrameX,lestFrameY,lestFrameAngle;
    private float offsetX,offsetY,offsetAngle;
    private Body body;
    private BodyType type;

    public RigidBody(float x,float y,float _angle,BodyType _type){
        offsetX = x;
        offsetY = y;
        offsetAngle = _angle;
        type = _type;
    }
    
    public void start() {

        World world = Physics2D.getWorld();
        BodyDef j = new BodyDef();
        j.position = new Vec2 (object().pos.x + offsetX,object().pos.y + offsetY);
        j.type = type;
        j.setActive(true);
        body = world.createBody(j);

        FixtureDef fd = new FixtureDef();
        if(object().getComponent(BoxCollider.class) != null){
            PolygonShape ps = new PolygonShape();
            BoxCollider bc = object().getComponent(BoxCollider.class);
            ps.setAsBox(bc.getWidth()/2,bc.getHeight()/2);
            fd.shape = ps;
        }
        else if(object().getComponent(CircleCollider.class) != null){
            CircleShape cs = new CircleShape();
            CircleCollider cc = object().getComponent(CircleCollider.class);
            cs.setRadius(cc.getRadius());
            fd.shape = cs;
        }
        //else if()ליצור ע"י מש

        fd.density = 300;
        fd.friction = 0.9f;
        fd.restitution = 0.5f;
        body.createFixture(fd);

        body.setTransform(new Vec2(object().pos.x + offsetX,object().pos.y + offsetY)
        ,0.0f);
        lestFrameX = object().pos.x;
        lestFrameY = object().pos.y;
        lestFrameAngle = object().angleZ;
    }
    
    public void update(float dt) {
        float offsetObjectX = object().pos.x - lestFrameX;
        float offsetObjectY = object().pos.y - lestFrameY;
        float offsetObjectAngle = object().angleZ - lestFrameAngle;
        body.setTransform(new Vec2(getX() + offsetObjectX
        ,getY() + offsetObjectY),
        getAngle() + (float)Math.toRadians(offsetObjectAngle));
        

        object().pos = new Vector3f(getX() - offsetX,getY() - offsetY,object().pos.z);
        object().angleZ = (float)Math.toDegrees(getAngle() - offsetAngle);
        lestFrameX = object().pos.x;
        lestFrameY = object().pos.y;
        lestFrameAngle = object().angleZ;
    }

    public Body getBody(){
        return body;
    }

    public void setForce(float x,float y,float angle){
        body.setLinearVelocity(new Vec2(x, y));
        body.setAngularVelocity(angle);
    }

    public void addForce(float x,float y){
        body.applyForceToCenter(new Vec2(x,y));
    }

    public void addForceAtPoaint(float ForceX,float ForceY,float x,float y){
        body.applyForce(new Vec2(ForceX,ForceY),new Vec2(x, y));
    }

    public void addForceAtPoaintInObject(float ForceX,float ForceY,float x,float y){
        float sin = (float)Math.sin(Math.toRadians(getAngle()));
        float cos = (float)Math.cos(Math.toRadians(getAngle()));
        body.applyForce(new Vec2(ForceX * sin,ForceY * cos),new Vec2(getX() + x * sin, getY() + y * cos));
    }

    public float getX(){
        return body.getPosition().x;
    }
    public float getY(){
        return body.getPosition().y;
    }

    public float getAngle(){
        return body.getAngle();
    }
}
