package render.light;

import org.joml.Vector3f;

import main.Component;
import render.Shader;

public class LightSource extends Component{

    private Vector3f pos;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;

    public LightSource(Vector3f _pos,Vector3f _ambient,Vector3f _diffuse,Vector3f _specular,float _shininess){
        pos = _pos;
        ambient = _ambient;
        diffuse = _diffuse;
        specular = _specular;
    }

    public void sandToGPU(Shader shader){
        shader.uploadVec3f("light.pos", new Vector3f(pos.x + object().getPos().x
        ,pos.y + object().getPos().y,pos.z + object().getPos().z));
        //System.out.println(ambient.toString());
        shader.uploadVec3f("light.ambient", ambient);
        shader.uploadVec3f("light.diffuse", diffuse);
        shader.uploadVec3f("light.specular", specular);
    }

    public Vector3f getPos(){
        return pos;
    }

    public void setPos(Vector3f newPos){
        pos = newPos;
    }

    public void setPos(float x,float y,float z){
        pos = new Vector3f(x, y, z);
    }

    public Vector3f getAmbient(){
        return ambient;
    }

    public void setAmbient(Vector3f newAmbient){
        ambient = newAmbient;
    }

    public void setAmbient(float x,float y,float z){
        ambient = new Vector3f(x, y, z);
    }

    public Vector3f getDiffuse(){
        return diffuse;
    }

    public void setDiffuse(Vector3f newDiffuse){
        diffuse = newDiffuse;
    }

    public void setDiffuse(float x,float y,float z){
        diffuse = new Vector3f(x, y, z);
    }

    public Vector3f getSpecular(){
        return specular;
    }

    public void setSpecular(Vector3f newSpecular){
        specular = newSpecular;
    }

    public void setSpecular(float x,float y,float z){
        specular = new Vector3f(x, y, z);
    }

    public void start() {}
    
    public void update(float dt) {}
}
