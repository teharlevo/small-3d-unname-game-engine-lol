package modeling;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import main.Assets;
import main.Window;
import render.Texture;

public class Mash {

    private float[] vertices;
    private Texture textuere;
    private boolean dartyFlag;
    private ModelShape mp = ModelShape.triangles;

    public Mash(float[] mashVertices,Texture tex){
        vertices = mashVertices; 
        textuere = tex;
    }

    public Mash(float[] mashVertices,String str){
        vertices = mashVertices; 
        textuere = Assets.getTexture(str);
    }

    public Mash(Texture tex){
        float width =  (float)tex.width() /(float)Window.height();
        float height = (float)tex.height()/(float)Window.height();
        vertices =new float[]{
        -width, -height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  0.0f, 0.0f,0.0f,
         width, -height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  1.0f, 0.0f,0.0f,
         width,  height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  1.0f, 1.0f,0.0f,
         width,  height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  1.0f, 1.0f,0.0f,
        -width,  height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  0.0f, 1.0f,0.0f,
        -width, -height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  0.0f, 0.0f,0.0f,}; 
        textuere = tex;
    }

    public Mash(String str){
        textuere = Assets.getTexture(str);
        float width =  (float)textuere.width() /(float)Window.height();
        float height = (float)textuere.height()/(float)Window.height();
        vertices =new float[]{
        -width, -height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  0.0f, 0.0f,0.0f,
         width, -height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  1.0f, 0.0f,0.0f,
         width,  height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  1.0f, 1.0f,0.0f,
         width,  height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  1.0f, 1.0f,0.0f,
        -width,  height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  0.0f, 1.0f,0.0f,
        -width, -height,  0.0f, 1.0f,1.0f,1.0f,1.0f,  0.0f, 0.0f,0.0f,}; 
    }
   
    public void setVertices(float[] newVertices){
        vertices = newVertices;
        dartyFlag = true;
    }

    public float[] getVertices(){
        return vertices;
    }

    public Texture getTexture(){
        return textuere;
    }

    public boolean needBufferVertex(){
        boolean df = dartyFlag;
        dartyFlag = false;
        return df;
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
}