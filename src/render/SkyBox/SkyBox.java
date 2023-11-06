package render.SkyBox;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LESS;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL20.*;

import main.Assets;
import main.Entity;
import main.Input;
import main.Window;
import modeling.Mash;
import modeling.Model;
import render.Renderer;
import render.Shader;
import render.Texture;

public class SkyBox {
    
    private Shader skyBoxShader;
    private Renderer skyBoxRenderer;

    public SkyBox(){
        skyBoxShader = Assets.getShader("SkyBox");
        skyBoxRenderer = new Renderer(skyBoxShader, Window.getCurrentScene().cam);

        float[] cubeVertex = new float[]{
            -1f, -1f,  1f, 1.0f,1.0f,1.0f,1.0f,   0,     0.0f,0.0f,
             1f, -1f,  1f, 1.0f,1.0f,1.0f,1.0f,   1,     0.0f,0.0f,
             1f,  1f,  1f, 1.0f,1.0f,1.0f,1.0f,   1,     1.0f,0.0f,
             1f,  1f,  1f, 1.0f,1.0f,1.0f,1.0f,   1,     1.0f,0.0f,
            -1f,  1f,  1f, 1.0f,1.0f,1.0f,1.0f,   0,     1.0f,0.0f,
            -1f, -1f,  1f, 1.0f,1.0f,1.0f,1.0f,   0,     0.0f,0.0f,
    
            -1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,0.0f,
             1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,0.0f,
             1f,  1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,0.0f,
             1f,  1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,0.0f,
            -1f,  1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,0.0f,
            -1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,0.0f,
    
            -1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,0.0f,
             1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,0.0f,
             1f, -1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,0.0f,
             1f, -1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,0.0f,
            -1f, -1f,   1f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,0.0f,
            -1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,0.0f,
    
            -1f, 1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,      0.0f,0.0f,
             1f, 1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  1,      0.0f,0.0f,
             1f, 1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,      0.0f,0.0f,
             1f, 1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,      0.0f,0.0f,
            -1f, 1f,   1f, 1.0f,1.0f,1.0f,1.0f,  0,      0.0f,0.0f,
            -1f, 1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,      0.0f,0.0f,
             
            1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,      0.0f,0.0f,
            1f, -1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,      0.0f,0.0f,
            1f,  1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,      0.0f,0.0f,
            1f,  1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,      0.0f,0.0f,
            1f,  1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,      0.0f,0.0f,
            1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,      0.0f,0.0f,
    
            -1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,0.0f,
            -1f, -1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,0.0f,
            -1f,  1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,0.0f,
            -1f,  1f,   1f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,0.0f,
            -1f,  1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,0.0f,
            -1f, -1f,  -1f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,0.0f,
        };

        Texture t = new Texture("E:\\projects\\opengl\\game engine\\assets\\images\\4.jpg");
        //,"E:\\projects\\opengl\\game engine\\assets\\images\\4.jpg","E:\\projects\\opengl\\game engine\\assets\\images\\4.jpg",
        //"E:\\projects\\opengl\\game engine\\assets\\images\\4.jpg","E:\\projects\\opengl\\game engine\\assets\\images\\4.jpg","E:\\projects\\opengl\\game engine\\assets\\images\\4.jpg"});
        Mash m =  new Mash(cubeVertex, t);
        new Entity().addComponent(new Model( m, 0.0f,0.0f,0.0f,skyBoxRenderer));
    }

    public void render(){
        if(!Input.getKeyPress("j")){return;}
        glDepthMask(false); // change depth function so depth test passes when values are equal to depth buffer's content

        skyBoxRenderer.render();
        glDepthMask(true);
    }

}
