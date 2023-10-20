package _2DPhysics;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import main.Component;

public class DistanceJointComponent extends Component{

    private DistanceJoint joint;

    private Body bodyB;
    private float length;
    private float frequencyHz;
    private float dampingRatio;

    public DistanceJointComponent(Body _bodyB,float _length,float _frequencyHz,float _dampingRatio){
        setJoint(_bodyB,_length,_frequencyHz,_dampingRatio);
    }

    public DistanceJointComponent(Body _bodyB,float _length){// יוצר גוינט מוחלט שלא ספריגי כזה
        setJoint(_bodyB,_length,0,1);
    }

    private void setJoint(Body _bodyB,float _length,float _frequencyHz,float _dampingRatio){
        bodyB = _bodyB;
        length = _length;
        frequencyHz = _frequencyHz;
        dampingRatio = _dampingRatio;
    }

    public void start() {
        DistanceJointDef djd = new DistanceJointDef();

        djd.bodyA = object().getComponent(RigidBody.class).getBody();
        djd.bodyB = bodyB;

        djd.length = length;

        djd.frequencyHz = frequencyHz;
        djd.dampingRatio = dampingRatio;

        joint = (DistanceJoint)Physics2D.getWorld().createJoint(djd);
    }
    
    public void update(float dt) {
        
    }
    
}