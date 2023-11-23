package render;

import static org.lwjgl.opengl.GL43.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import modeling.Model;

public class RenderBatch {

    private Model model;
    private RenderrInformationHolder RIH;
    private Texture[] texUseThisFrame = new Texture[8];

    private final int posSize = 3;
    private final int colorSize = 4;
    private final int UVSize = 2;
    private final int texIDSize = 1;

    private final int posOffset = 0;
    private final int colorOffset = posOffset + posSize * Float.BYTES;
    private final int UVOffset = colorOffset + colorSize * Float.BYTES;
    private final int texOffset = UVOffset + UVSize * Float.BYTES;
    private int vertexSize = 10;
    private final int vertexSizeBytes = vertexSize * Float.BYTES;

    private int texsUseCount = 0;

    private int[] texSlat = new int[]{0,1,2,3,4,5,6,7};

    public RenderBatch(Shader shader,Model _model,RenderrInformationHolder _RIH,int[] arrayStrcher){
        s = shader;
        model = _model;
        RIH = _RIH;
        init(arrayStrcher);
    }

    private Shader s;

    private int vaoID, vboID;//, eboID;

    public void init(int[] arrayStrcher) {

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
        glBindVertexArray(vboID);

        // Create the indices and upload
        //IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        //elementBuffer.put(elementArray).flip();
//
        //eboID = glGenBuffers();
        //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        //glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers

        if(arrayStrcher == null){
            glVertexAttribPointer(0, posSize, GL_FLOAT, false, vertexSizeBytes, posOffset);
            glEnableVertexAttribArray(0);

            glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, colorOffset);
            glEnableVertexAttribArray(1);

            glVertexAttribPointer(2, UVSize, GL_FLOAT, false, vertexSizeBytes, UVOffset);
            glEnableVertexAttribArray(2);

            glVertexAttribPointer(3, texIDSize, GL_FLOAT, false, vertexSizeBytes, texOffset);
            glEnableVertexAttribArray(3);
        }
        else{
            int newVertexSize = 0;
            for (int i = 0; i < arrayStrcher.length; i++) {
                newVertexSize += arrayStrcher[i];
            }
            int newVertexSizeBytes = newVertexSize * Float.BYTES;
            int offset = 0;
            vertexSize = 0;
            for (int i = 0; i < arrayStrcher.length; i++) {
                glVertexAttribPointer(i, arrayStrcher[i] 
                , GL_FLOAT, false,newVertexSizeBytes, offset);
                glEnableVertexAttribArray(i);
                offset +=  arrayStrcher[i] * Float.BYTES;
                vertexSize += arrayStrcher[i];
            }
        }
        reBufferVertex();
    }

    private void reBufferVertex(){
        float[] vertexArray = model.getMash().getVertices();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);
    }
    
    public void update(Camrea c) {

        texsUseCount = 0;
        // Bind shader program
        s.use();

        sandInformationToGPU(c,model);

        draw();
        
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        for (int i = 0; i < texsUseCount; i++) {
            texUseThisFrame[i].unbind();
        }
        glBindTexture(GL_TEXTURE_2D, 0);

        s.detach();
    }

    private void draw(){
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        //glDrawElements(GL_TRIANGLES, m.getMash().getVertices().length/10, GL_UNSIGNED_INT, 0);

        glDrawArrays(model.getModelShapeNum()
        ,0,model.getMash().getVertices().length/vertexSize);
        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    private void sandInformationToGPU(Camrea c,Model m){
        
        if(model.getTexture() != null){
            texUseThisFrame[texsUseCount] =  model.getTexture();
            texsUseCount ++;
        }
        RIHInformationToGPU();
        s.uploadMat4f("uView",c.getViewMatrix());
        s.uploadMat4f("uModel",m.getMatrix() );
        s.uploadMat4f("uProjection",
        c.getProjectionMarix());
        for (int i = 0; i < texsUseCount; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            texUseThisFrame[i].bind();
        }
        s.uploadIntArray("uTex_Sampler",texSlat);
        if(model.getMash().needBufferVertex()){
            reBufferVertex();
        }

    }

    private void RIHInformationToGPU(){

        for (int i = 0; i < RIH.getTexs().length; i++) {

            if(texsUseCount < 8){
                texUseThisFrame[texsUseCount] =  RIH.getTexs()[i];
                texsUseCount ++;
            }
        }
        
        for (int i = 0; i < RIH.getFloats().length; i++) {
            s.uploadfloat(RIH.getFloatsNames()[i],RIH.getFloats()[i]);
        }

        for (int i = 0; i < RIH.getInts().length; i++) {
            s.uploadInt(RIH.getIntsNames()[i], RIH.getInts()[i]);
        }
    }

    public Shader getShader(){
        return s;
    }

    public void setShader(Shader newShader){
        s = newShader;
    }
}