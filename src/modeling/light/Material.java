package modeling.light;

import org.joml.Vector3f;

import render.Shader;

public class Material {
    
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private float shininess;

    public Material(Vector3f _ambient,Vector3f _diffuse,Vector3f _specular,float _shininess){
        ambient = _ambient;
        diffuse = _diffuse;
        specular = _specular;
        shininess = _shininess;
    }

    public void sandToGPU(Shader shader){
        shader.uploadVec3f("material.ambient", ambient);
        shader.uploadVec3f("material.diffuse", diffuse);
        shader.uploadVec3f("material.specular", specular);
        shader.uploadfloat("material.shininess",shininess );
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

    public float getShininess(){
        return shininess;
    }

    public void setShininess(float x){
        shininess = x;
    }
}
