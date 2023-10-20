package render;

import org.joml.Vector3f;

import main.Window;

import org.joml.Matrix4f;

public class Camrea {
    
    private Vector3f pos;
    private Vector3f lookDir;
    private Vector3f orientation;

    private float angleX = 0;
    private float angleY = 0;
    private float angleZ = 0;
    private float FoV = 90;

    public Camrea(float x,float y,float z){
        pos = new Vector3f(x, y, z);
        lookDir = new Vector3f(0, 0, -1);
        orientation = new Vector3f(0,  1, 0);
    } 

    public Vector3f getPos(){
        return pos;
    }

    public void setPos(float x,float y, float z){
        pos.x = x;
        pos.y = y;
        pos.z = z;
    }

    public void setPos(Vector3f newPos){
        pos = newPos;
    }

    public float getAngleX(){
        return angleX;
    }

    public float getAngleY(){
        return angleY;
    }

    public float getAngleZ(){
        return angleY;
    }

    public Vector3f getLookDir(){
        return lookDir;
    }

    public Vector3f getOrientation(){
        return orientation;
    }

    public void setAngle(float x,float y,float z){
        angleX = x;
        angleY = y;
        angleZ = z;
        Vector3f frant = new Vector3f();
        frant.x = (float)Math.cos(Math.toRadians(angleY - 90)) * (float)Math.cos(Math.toRadians(angleX));
        frant.y = (float)Math.sin(Math.toRadians(angleX));
        frant.z = (float)Math.sin(Math.toRadians(angleY - 90)) * (float)Math.cos(Math.toRadians(angleX));
        lookDir = frant.normalize(lookDir); 
    }

    public Matrix4f getViewMatrix(){
        
        Vector3f lookPoaint = new Vector3f(pos.x + lookDir.x
        ,pos.y + lookDir.y,pos.z + lookDir.z);

        Matrix4f matrix  =new Matrix4f();
        matrix.identity();

        matrix.lookAt(pos,lookPoaint,orientation,matrix);
        return matrix;
    }

    public Matrix4f getProjectionMarix(){
        Matrix4f matrix  = new Matrix4f();
        matrix.identity();
        matrix.perspective((float)Math.toRadians(FoV),Window.width()/Window.height(),0.01f,1000.0f,matrix);
        return  matrix;
    }
}