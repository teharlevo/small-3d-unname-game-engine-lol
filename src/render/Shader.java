package render;

import static org.lwjgl.opengl.GL43.*;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

public class Shader {

    private String vertexsrc;
    
    private String frgmntsrc;
    
    private int vertexID,frgmntID, SP;

    private boolean isuse = false;

    public  Shader(String path){
        try{
            String sourse = new String(Files.readAllBytes(Paths.get(path)));
            String[] splitString = sourse.split("(#type)( )+([a-zA-Z]+)");
            
            int index = sourse.indexOf("#type") + 6;
            int eol = sourse.indexOf("\r\n",index);
            String firstpetern = sourse.substring(index, eol).trim();

            index = sourse.indexOf("#type",eol) + 6;
            eol = sourse.indexOf("\r\n",index);
            String srcendpetern = sourse.substring(index, eol).trim();

            if (firstpetern.equals("vertex")) {
                vertexsrc = splitString[1];
            } else if (firstpetern.equals("fragment")) {
                frgmntsrc = splitString[1];
            }
            else{
                throw new IOException("Unexpected token '" + firstpetern + "'");
            }

            if (srcendpetern.equals("vertex")) {
                vertexsrc = splitString[2];
            } else if (srcendpetern.equals("fragment")) {
                frgmntsrc = splitString[2];
            } else {
                throw new IOException("Unexpected token '" + srcendpetern + "'");
            }


        }catch(IOException e){
            e.printStackTrace();
            System.out.println("import file dont work " + " PATH " + path);
        }
        compile();
    }

    public  Shader(String _vertexsrc,String _frgmntsrc){
        vertexsrc = _vertexsrc;
        frgmntsrc = _frgmntsrc;
        compile();
    }

    public void compile(){
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        
        glShaderSource(vertexID, vertexsrc);
        glCompileShader(vertexID);

        // בודק בעיות
        int success = glGetShaderi(vertexID,GL_COMPILE_STATUS);

        if(success == GL_FALSE){
            int len = glGetShaderi(vertexID,GL_INFO_LOG_LENGTH);
            System.out.println("Vertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }
        

        frgmntID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(frgmntID, frgmntsrc);
        glCompileShader(frgmntID);

        // בודק בעיות
        success = glGetShaderi(frgmntID,GL_COMPILE_STATUS);

        if(success == GL_FALSE){
            int len = glGetShaderi(frgmntID,GL_INFO_LOG_LENGTH);
            System.out.println("Fragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(frgmntID, len));
            assert false : "";
        }

        //לחבר בין שתי השידרים
        SP = glCreateProgram();
        glAttachShader(SP, vertexID);
        glAttachShader(SP, frgmntID);
        glLinkProgram(SP);
    
        success = glGetProgrami(SP, GL_LINK_STATUS);

        if(success == GL_FALSE){
            int len = glGetProgrami(SP,GL_INFO_LOG_LENGTH);
            System.out.println("Linking of shaders failed.");
            System.out.println(glGetProgramInfoLog(SP, len));
            assert false : "";
        }
    }

    public void use() {
        if(!isuse){
            glUseProgram(SP);
            isuse = true;
        }
    }

    public void detach() {
        glUseProgram(0);
        isuse = false;
    }

    public void uploadVec2f(String varName, Vector2f vec) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform2f(varLocation, vec.x, vec.y);
    }

    public void uploadVec3f(String varName, Vector3f vec) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform3f(varLocation, vec.x, vec.y, vec.z);
    }

    public void uploadVec4f(String varName, Vector4f vec) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
    }

    public void uploadVec2f(String varName,float x,float y) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform2f(varLocation, x,y);
    }

    public void uploadVec3f(String varName,float x,float y,float z) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform3f(varLocation, x,y,z);
    }

    public void uploadVec4f(String varName,float x,float y,float z,float w) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform4f(varLocation, x,y,z,w);
    }
    
    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadfloat(String varName, float f) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform1f(varLocation, f);
    }

    public void uploadInt(String varName, int i) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform1i(varLocation, i);
    }

    public void uploadTexture(String varName, int slot) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform1i(varLocation, slot);
    }

    public void uploadIntArray(String varName, int[] array) {
        int varLocation = glGetUniformLocation(SP, varName);
        use();
        glUniform1iv(varLocation, array);
    }
}