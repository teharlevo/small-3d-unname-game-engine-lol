package _2DPhysics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import main.Component;

public class RevoluteJointComponent extends Component{

    private RevoluteJoint joint;
    
    private Body bodyB;

    private boolean angleLimit;
    private float minAngle;
    private float maxAngle;  

    private Vec2 meetPaint;

    public RevoluteJointComponent(Body _bodyB,float meetPaintX,float meetPaintY){
        setJoint(_bodyB,meetPaintX,meetPaintY,false,0,0);
    }

    public RevoluteJointComponent(Body _bodyB,float meetPaintX,float meetPaintY
    ,boolean _angleLimit,float _minAngle,float _maxAngle){
        setJoint(_bodyB,meetPaintX,meetPaintY,_angleLimit,_minAngle,_maxAngle);
    }

    public RevoluteJointComponent(Body _bodyB){
        setJoint(_bodyB,0,0,false,0,0);
    }

    public RevoluteJointComponent(Body _bodyB
    ,boolean _angleLimit,float _minAngle,float _maxAngle){
        setJoint(_bodyB,0,0,_angleLimit,_minAngle,_maxAngle);
    }

    //add Motor when need
    private void setJoint(Body _bodyB,float meetPaintX
    ,float meetPaintY,boolean _angleLimit,float _minAngle,float _maxAngle){
        bodyB = _bodyB;
        angleLimit = _angleLimit;
        minAngle = _minAngle;
        maxAngle = _maxAngle;
        meetPaint = new Vec2(meetPaintX, meetPaintY);
    }
    
    public void start() {
        RevoluteJointDef rjd = new RevoluteJointDef();
        Body bodyA = object().getComponent(RigidBody.class).getBody();
        rjd.initialize(bodyB,bodyA, new Vec2(meetPaint.x + bodyA.getPosition().x,meetPaint.y + bodyA.getPosition().y));

        rjd.enableLimit = angleLimit;
        System.out.println(angleLimit);
        rjd.lowerAngle = (float)Math.toRadians(minAngle);
        rjd.upperAngle = (float)Math.toRadians(maxAngle);

        joint = (RevoluteJoint)Physics2D.getWorld().createJoint(rjd);
    }
    
    public void update(float dt) {
        
    }
    
}