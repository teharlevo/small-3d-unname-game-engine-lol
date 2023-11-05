package render;

import static org.lwjgl.opengl.GL20.*;

import main.Assets;
import main.Entity;
import main.Window;
import modeling.Mash;
import modeling.Model;

public class SkyBox {
    
    private Shader skyBoxShader;
    private Renderer skyBoxRenderer;

    public SkyBox(){
        skyBoxShader = Assets.getShader("default");
        skyBoxRenderer = new Renderer(skyBoxShader, Window.getCurrentScene().cam);

        float[] cubeVertex = new float[]{
            -0.5f, -0.5f,  0.5f, 1.0f,1.0f,1.0f,1.0f,   0,     0.0f,-1.0f,
             0.5f, -0.5f,  0.5f, 1.0f,1.0f,1.0f,1.0f,   1,     0.0f,-1.0f,
             0.5f,  0.5f,  0.5f, 1.0f,1.0f,1.0f,1.0f,   1,     1.0f,-1.0f,
             0.5f,  0.5f,  0.5f, 1.0f,1.0f,1.0f,1.0f,   1,     1.0f,-1.0f,
            -0.5f,  0.5f,  0.5f, 1.0f,1.0f,1.0f,1.0f,   0,     1.0f,-1.0f,
            -0.5f, -0.5f,  0.5f, 1.0f,1.0f,1.0f,1.0f,   0,     0.0f,-1.0f,
    
            -0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,-1.0f,
             0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,-1.0f,
             0.5f,  0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,-1.0f,
             0.5f,  0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,-1.0f,
            -0.5f,  0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,-1.0f,
            -0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,-1.0f,
    
            -0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,-1.0f,
             0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,-1.0f,
             0.5f, -0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,-1.0f,
             0.5f, -0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,-1.0f,
            -0.5f, -0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,-1.0f,
            -0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,-1.0f,
    
            -0.5f, 0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,      0.0f,-1.0f,
             0.5f, 0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  1,      0.0f,-1.0f,
             0.5f, 0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f,  1,      0.0f,-1.0f,
             0.5f, 0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f,  1,      0.0f,-1.0f,
            -0.5f, 0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f,  0,      0.0f,-1.0f,
            -0.5f, 0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,      0.0f,-1.0f,
             
            0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f, 0,       0.0f,-1.0f,
            0.5f, -0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f, 1,       0.0f,-1.0f,
            0.5f,  0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f, 1,       0.0f,-1.0f,
            0.5f,  0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f, 1,       0.0f,-1.0f,
            0.5f,  0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f, 0,       0.0f,-1.0f,
            0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f, 0,       0.0f,-1.0f,
    
            -0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,-1.0f,
            -0.5f, -0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,-1.0f,
            -0.5f,  0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,-1.0f,
            -0.5f,  0.5f,   0.5f, 1.0f,1.0f,1.0f,1.0f,  1,     0.0f,-1.0f,
            -0.5f,  0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,-1.0f,
            -0.5f, -0.5f,  -0.5f, 1.0f,1.0f,1.0f,1.0f,  0,     0.0f,-1.0f,
        };

        Mash m =  new Mash(cubeVertex, "bob");
        new Entity().addComponent(new Model( m, 0.0f,0.0f,0.0f,skyBoxRenderer));
    }

    public void render(){
        skyBoxRenderer.render();
    }

}
