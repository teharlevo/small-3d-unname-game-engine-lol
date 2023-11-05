package _2DPhysics;

import main.Component;

public class BoxCollider extends Component{
    private float w,h;

    public BoxCollider(float width,float height){
        w = width;
        h = height;
    }

    //make set fancs

    public float getWidth(){
        return w;
    }

    public float getHeight(){
        return h;
    }

    public void start() {
        // Auto-generated method stub
    }
    
    public void update(float dt) {
        // Auto-generated method stub
    }
}
