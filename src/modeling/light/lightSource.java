package modeling.light;

import org.joml.Vector3f;

import render.Shader;

public class lightSource {

    public Vector3f pos;
    public Vector3f ambient;
    public Vector3f diffuse;
    public Vector3f specular;

    public lightSource(Vector3f _ambient,Vector3f _diffuse,Vector3f _specular,float _shininess){
        ambient = _ambient;
        diffuse = _diffuse;
        specular = _specular;
    }

    public void sandToGPU(Shader shader){
        shader.uploadVec3f("light.ambient", ambient);
        shader.uploadVec3f("light.diffuse", diffuse);
        shader.uploadVec3f("light.specular", specular);
        shader.uploadfloat("light.shininess",shininess );
    }
}
