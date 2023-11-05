package _2DPhysics;

import main.Component;

public class CircleCollider extends Component{
    private float radius;

    public CircleCollider(float _radius){
        radius = _radius;
    }

    //make set fancs

    public float getRadius(){
        System.out.println(radius);
        return radius;
    }
    
    public void start() {
        // Auto-generated method stub
    }
    
    public void update(float dt) {
        // Auto-generated method stub
    }
}
