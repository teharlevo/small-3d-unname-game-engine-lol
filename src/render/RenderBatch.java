package render;

import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;


import org.lwjgl.BufferUtils;

import modeling.Model;

public class RenderBatch {

    private Model model;
        

    private final int posSize = 3;
    private final int colorSize = 4;
    private final int UVSize = 2;
    private final int texIDSize = 1;

    private final int posOffset = 0;
    private final int colorOffset = posOffset + posSize * Float.BYTES;
    private final int UVOffset = colorOffset + colorSize * Float.BYTES;
    private final int texOffset = UVOffset + UVSize * Float.BYTES;
    private final int vertexSize = 10;
    private final int vertexSizeBytes = vertexSize * Float.BYTES;

    public RenderBatch(Shader shader,Model _model){
        s = shader;
        model = _model;
        init();
    }

    private Shader s;

    private int vaoID, vboID;//, eboID;

    public void init() {

        float[] vertexArray = model.getMash().getVertices();
        //int[] elementArray =  m.getMash().getElements();
        

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        //IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        //elementBuffer.put(elementArray).flip();
//
        //eboID = glGenBuffers();
        //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        //glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        glVertexAttribPointer(0, posSize, GL_FLOAT, false, vertexSizeBytes, posOffset);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, colorOffset);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, UVSize, GL_FLOAT, false, vertexSizeBytes, UVOffset);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3, texIDSize, GL_FLOAT, false, vertexSizeBytes, texOffset);
        glEnableVertexAttribArray(3);
        reBufferVertex();
    }

    private void reBufferVertex(){
        float[] vertexArray = model.getMash().getVertices();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);
        System.out.println("lol");
    }
    
    public void update(Camrea c) {

        
        // Bind shader program
        s.use();

        sandInfrmasihnToGPU(c,model);

        draw();

        glBindVertexArray(0);
        if(model.getTexture() != null){model.getTexture().unbind();}
        glBindTexture(GL_TEXTURE_2D, 0);

        s.detach();
    }

    private void draw(){
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        //glDrawElements(GL_TRIANGLES, m.getMash().getVertices().length/10, GL_UNSIGNED_INT, 0);
        glDrawArrays(GL_TRIANGLES,0,model.getMash().getVertices().length/10);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    private void sandInfrmasihnToGPU(Camrea c,Model m){
        
        if(model.getTexture() != null){model.getTexture().bind();}
        s.uploadMat4f("uView",c.getViewMatrix());
        s.uploadMat4f("uModel",m.getMatrix() );
        s.uploadMat4f("uProjection",
        c.getProjectionMarix());
        if(model.getMash().needBufferVertex()){
            reBufferVertex();
        }

    }

    private void g(Texture[] tex,float[] floats,String[] floatsNames,
        int[] ints,String[] intsNames){

        int texsLength = tex.length;
        if(texsLength > 7){
            texsLength = 7;
        }
        for (int i = 0; i < tex.length; i++) {
            glActiveTexture(GL_TEXTURE0 + 1 + i);
            tex[i].bind();
        }
        
        if(floats != null){
            for (int i = 0; i < floats.length; i++) {
                s.uploadfloat(floatsNames[i], floats[i]);
            }
        }

        if(ints != null){
            for (int i = 0; i < ints.length; i++) {
                s.uploadInt(intsNames[i], ints[i]);
            }
        }
    }
}