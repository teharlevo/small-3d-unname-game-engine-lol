package modeling;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import main.Assets;
import main.Window;
import render.Texture;
import render.light.Material;

public class Mash {

    private float[] vertices;
    private Texture textuere;
    private ModelShape mp = ModelShape.triangles;
    private int mashID;
    static private int mashNum;
    private Material material;

    public Mash(float[] mashVertices,Texture tex,Material _material){
        vertices = mashVertices; 
        textuere = tex;
        mashID = mashNum;
        mashNum ++;
        material = _material;
    }

    public Mash(float[] mashVertices,String str,Material _material){
        vertices = mashVertices; 
        textuere = Assets.getTexture(str);
        mashID = mashNum;
        mashNum ++;
        material = _material;
    }

    public Mash(Texture tex){
        float width =  (float)tex.width() /(float)Window.height();
        float height = (float)tex.height()/(float)Window.height();
        vertices =new float[]{
        -width, -height,  0.0f, 0.0f,0.0f,1.0f,  0.0f, 0.0f,
         width, -height,  0.0f, 0.0f,0.0f,1.0f,  1.0f, 0.0f,
         width,  height,  0.0f, 0.0f,0.0f,1.0f,  1.0f, 1.0f,
         width,  height,  0.0f, 0.0f,0.0f,1.0f,  1.0f, 1.0f,
        -width,  height,  0.0f, 0.0f,0.0f,1.0f,  0.0f, 1.0f,
        -width, -height,  0.0f, 0.0f,0.0f,1.0f,  0.0f, 0.0f,}; 
        textuere = tex;
        mashID = mashNum;
        mashNum ++;
    }

    public Mash(String str){
        textuere = Assets.getTexture(str);
        float width =  (float)textuere.width() /(float)Window.height();
        float height = (float)textuere.height()/(float)Window.height();
        vertices =new float[]{
        -width, -height,  0.0f, 0.0f,0.0f,1.0f,  0.0f, 0.0f,
         width, -height,  0.0f, 0.0f,0.0f,1.0f,  1.0f, 0.0f,
         width,  height,  0.0f, 0.0f,0.0f,1.0f,  1.0f, 1.0f,
         width,  height,  0.0f, 0.0f,0.0f,1.0f,  1.0f, 1.0f,
        -width,  height,  0.0f, 0.0f,0.0f,1.0f,  0.0f, 1.0f,
        -width, -height,  0.0f, 0.0f,0.0f,1.0f,  0.0f, 0.0f,}; 
        mashID = mashNum;
        mashNum ++;
    }
   
    public void setVertices(float[] newVertices){
        vertices = newVertices;
    }

    public float[] getVertices(){
        return vertices;
    }

    public Texture getTexture(){
        return textuere;
    }

    public void setModelShape(ModelShape newNp){
        mp = newNp;
    }

    public ModelShape getModelShape(){
        return mp;
    }

    public int getModelShapeNum(){
        if(mp == ModelShape.Lines){
            return GL_LINES;
        }
        else if(mp == ModelShape.triangles){
            return GL_TRIANGLES;
        }
        return 0;
    }

    public Material getMaterial(){
        return material;
    }

    public void setMaterial(Material m){
        material = m;
    }

    public int getMashID() {
        return mashID;
    }
}