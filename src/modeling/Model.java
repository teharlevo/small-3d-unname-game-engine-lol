package modeling;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL30.*;

import main.Assets;
import main.Component;
import main.Window;
import render.Renderer;
import render.Texture;

public class Model extends Component{

    private Vector3f pos;
    private Vector3f scale;
    private Mash mash;
    private Texture tex;
    
    private float[] color = new float[]{1,1,1,1};

    private float[] inctanceList = null;

    private float angleX;
    private float angleY;
    private float angleZ;

    public Model(String mashName,float x,float y,float z,Renderer renderer){
        createFanc(Assets.getModelMesh(mashName),x,y,z,renderer);
    } 

    public Model(String mashName,float x,float y,float z){
        createFanc(Assets.getModelMesh(mashName),x,y,z,Window.getCurrentScene().getRenderer());
    } 

    public Model(Mash _mash,float x,float y,float z){
        createFanc(_mash,x,y,z,Window.getCurrentScene().getRenderer());
    } 

    public Model(String mashName,float x,float y){
        createFanc(Assets.getModelMesh(mashName),x,y,0,Window.getCurrentScene().getRenderer());
    } 

    public Model(Mash _mash,float x,float y){
        createFanc(_mash,x,y,0,Window.getCurrentScene().getUIRenderer());
    } 

    public Model(Mash _mash,float x,float y,float z,Renderer renderer){
        createFanc(_mash,x,y,z,renderer);
    } 

    public Model(Mash _mash,float x,float y,float z,Renderer renderer,float[] _inctanceList){
        inctanceList = _inctanceList;
        createFanc(_mash,x,y,z,renderer);
    } 

    public Model(Texture tex,float x,float y,float z){
        createFanc(new Mash(tex),x,y,z,Window.getCurrentScene().getRenderer());
    } 

    public Model(Texture tex,float x,float y,float z,Renderer renderer){
        createFanc(new Mash(tex),x,y,z,renderer);
    } 

    private void createFanc(Mash _mash,float x,float y,float z,Renderer renderer){
        pos = new Vector3f(x, y, z);
        scale = new Vector3f(1,1,1);
        mash = _mash;
        tex = mash.getTexture();
        renderer.addModel(this);
    } 

    public void start(){}

    public void update(float dt){}


    public Vector3f getPos(){
        return pos;
    }

    public void setPos(float x,float y, float z){
        pos.x = x;
        pos.y = y;
        pos.z = z;
    }

    public Texture getTexture(){
        return tex;
    }

    public void setTexture(Texture newTex){
        tex = newTex;
    }

    public Vector3f getScale(){
        return scale;
    }

    public void setScale(float x,float y, float z){
        scale.x = x;
        scale.y = y;
        scale.z = z;
    }

    public float getAngleX(){
        return angleX;
    }

    public float getAngleY(){
        return angleY;
    }

    public float getAngleZ(){
        return angleZ;
    }

    public void setAngle(float x,float y,float z){
        angleX = x;
        angleY = y;
        angleZ = z;
    }

    public Mash getMash(){
        return mash;
    }

    //public static Matrix4f createTransformationMatrix(
    //    float x,float y,float z, float rx, float ry,float rz, float scaleX,float scaleY,float scaleZ) {
	//}

    public Matrix4f getMatrix(){
        Matrix4f matrix = new Matrix4f();
		matrix.identity();
        matrix =   matrix.translate(pos.x + object().pos.x ,pos.y + object().pos.y,pos.z + object().pos.z, matrix);
        matrix = matrix.rotate((float) Math.toRadians(angleX + object().angleX), new Vector3f(1,0,0), matrix);
        matrix = matrix.rotate((float) Math.toRadians(angleY + object().angleY), new Vector3f(0,1,0),matrix);
        matrix = matrix.rotate((float) Math.toRadians(angleZ + object().angleZ), new Vector3f(0,0,1),matrix);
        matrix = matrix.scale(scale.x * object().scale.x ,scale.y * object().scale.y,scale.z * object().scale.z, matrix);
		return matrix;
        //return createTransformationMatrix(pos.x,pos.y,pos.z,angleX,angleY,0,1,1,1);
    }

    public void setColor(float r,float g,float b,float a){
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;
    }

    public float[] getColor(){
        return color;
    }

    public void setInctanceList(float[] list){
        inctanceList = list;
    }

    public float[] getInctanceList(){
        return inctanceList;
    }
}