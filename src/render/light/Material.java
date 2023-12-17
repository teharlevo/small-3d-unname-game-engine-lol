package render.light;

import render.Texture;

public class Material {

    private Texture specularTexture;
    private float shininess;

    public Material(Texture _specularTexture,float _shininess){
        specularTexture = _specularTexture;
        shininess = _shininess;
    }

    public float getShininess(){
        return shininess;
    }

    public void setShininess(float x){
        shininess = x;
    }

    public Texture getSpecularTexture(){
        return specularTexture;
    }

    public void setSpecularTexture(Texture newSpecularTexture){
        specularTexture = newSpecularTexture;
    }
}
