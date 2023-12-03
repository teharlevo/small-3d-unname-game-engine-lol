package render;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;
import static org.lwjgl.opengl.GL43.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import modeling.Mash;
import modeling.Model;

public class RenderBatch {

    private Model[] models;
    private RenderrInformationHolder RIH;
    private Texture[] texUseThisFrame = new Texture[8];

    private Mash theMash;

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
        RIH = _RIH;
        models = new Model[1];
        models[0] = _model;
        theMash = _model.getMash();
        init(arrayStrcher);
    }

    private Shader s;

    private int vaoID, vertexVboID,instancVboID,eboID;

    public void init(int[] arrayStrcher) {

        float[] vertexArray = theMash.getVertices();
        //int[] elementArray =  m.getMash().getElements();
        

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vertexVboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexVboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        glBindVertexArray(vertexVboID);


        float[] instancArray = new float[]{4,2,3,4};
        FloatBuffer instancBuffer = BufferUtils.createFloatBuffer(instancArray.length);
        instancBuffer.put(instancArray).flip();

        instancVboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, instancVboID);
        glBufferData(GL_ARRAY_BUFFER, instancBuffer, GL_STATIC_DRAW);
        glBindVertexArray(vertexVboID);

        // Create the indices and upload

        // Add the vertex attribute pointers

        if(arrayStrcher == null){
            glBindVertexArray(vaoID);
            glBindBuffer(GL_ARRAY_BUFFER, vertexVboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);
            glVertexAttribPointer(0, posSize, GL_FLOAT, false, vertexSizeBytes, posOffset);
            glEnableVertexAttribArray(0);

            glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, colorOffset);
            glEnableVertexAttribArray(1);

            glVertexAttribPointer(2, UVSize, GL_FLOAT, false, vertexSizeBytes, UVOffset);
            glEnableVertexAttribArray(2);

            glVertexAttribPointer(3, texIDSize, GL_FLOAT, false, vertexSizeBytes, texOffset);
            glEnableVertexAttribArray(3);
            
            //glBindVertexArray(vboID);
            //glVertexAttribPointer(0,UVSize, GL_FLOAT, false,UVSize * Float.BYTES, 0);
            //glEnableVertexAttribArray(0);
            glBindBuffer(GL_ARRAY_BUFFER,instancVboID);
            glVertexAttribPointer(4, texIDSize, GL_FLOAT, false, Float.SIZE, 0);
            glEnableVertexAttribArray(4);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glVertexAttribDivisor(4, 1);
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
         int[] elementArray = new int[(int)(vertexArray.length/vertexSize)];
        for (int i = 0; i < elementArray.length; i++) {
            elementArray[i] = i;
        }//זמני ביתר יש להשמיד
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
        reBufferVertex();
    }

    private void reBufferVertex(){
        float[] vertexArray = theMash.getVertices();
        glBindBuffer(GL_ARRAY_BUFFER, vertexVboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);
    }
    
    public void update(Camrea c) {

        texsUseCount = 0;
        // Bind shader program
        s.use();

        sandInformationToGPU(c);

        draw();
        
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

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
        

        //glDrawElementsInstanced(theMash.getModelShapeNum(), 
        //theMash.getVertices().length/vertexSize, GL_UNSIGNED_INT, 0,models.length);
        glDrawElementsInstanced(theMash.getModelShapeNum(), 
        theMash.getVertices().length/vertexSize, GL_UNSIGNED_INT, 0,4);

        //glDrawArrays(model.getModelShapeNum()
        //,0,theMash.getVertices().length/vertexSize);
        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    private void sandInformationToGPU(Camrea c){
        
        if(theMash.getTexture() != null){
            texUseThisFrame[texsUseCount] =  theMash.getTexture();
            texsUseCount ++;
        }
        RIHInformationToGPU();
        s.uploadMat4f("uView",c.getViewMatrix());
        for (int index = 0; index < models.length; index++) {
            s.uploadMat4f("uModel[" + index + "]",models[index].getMatrix() );
        }
        s.uploadMat4f("uProjection",
        c.getProjectionMarix());
        for (int i = 0; i < texsUseCount; i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            texUseThisFrame[i].bind();
        }
        s.uploadIntArray("uTex_Sampler",texSlat);

    }

    public void addModel(Model m){
        Model newModels[] = new Model[models.length + 1];
        for (int i = 0; i < models.length; i++) {
            newModels[i] = models[i];
        }
        newModels[newModels.length - 1] = m;
        models = newModels;
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

    public Mash getMash(){
        return theMash;
    }
}