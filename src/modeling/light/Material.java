package modeling.light;

import org.joml.Vector3f;

import render.Shader;

public class Material {
    
    public Vector3f ambient;
    public Vector3f diffuse;
    public Vector3f specular;
    public float shininess;

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
}
