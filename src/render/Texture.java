package render;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL20.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.stb.STBImage.*;

import org.lwjgl.BufferUtils;

public class Texture {
    private int texID;
    private int width = 0;
    private int height = 0;

    private int kindOfBind = 0;

    public Texture(String path){

        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);
        kindOfBind = GL_TEXTURE_2D;

        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_R,GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_NEAREST);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,GL_NEAREST);

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer imge = stbi_load(path, w, h,channels,0);

        if(imge != null){
            if (channels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w.get(0), h.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, imge);
            } else if (channels.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(0), h.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, imge);
            } else {
                assert false : "Error: (Texture) Unknown number of channesl '" + channels.get(0) + "'";
            }
        
        }else{
            System.out.println("dont work imge " + path);
        }
        
        stbi_image_free(imge);

        width  = w.get(0);
        height = h.get(0);
        unbind();
    }

    //public Texture(String[] facesNameFile){//cube map// ירחם השם עליי
    //    texID = glGenTextures();
    //    glBindTexture(GL_TEXTURE_CUBE_MAP, texID);
    //    kindOfBind = GL_TEXTURE_CUBE_MAP;
    //    //glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_REPEAT);
    //    //glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER,GL_REPEAT);
    //    //glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_NEAREST);
    //    //glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_NEAREST);
    //    //glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_NEAREST); //רצוי
//
    //    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    //    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    //    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    //    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
    //    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE); //מצוי
    //    
    //    IntBuffer w = BufferUtils.createIntBuffer(1);
    //    IntBuffer h = BufferUtils.createIntBuffer(1);
    //    IntBuffer channels = BufferUtils.createIntBuffer(1);
    //    for (int i = 0; i < facesNameFile.length; i++)
    //    {
    //        
    //        ByteBuffer data = stbi_load(facesNameFile[i], w,h,channels,0);
    //        if (data != null)
    //        {
    //            System.out.println(w.get(0));
    //            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 
    //                0, GL_RGB,  w.get(0),h.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE
    //                , data
    //            );
    //            stbi_image_free(data);
    //        }
    //        else
    //        {
    //            System.out.print("gg");
    //            stbi_image_free(data);
    //        }
    //    }
//
//
    //    width  = w.get(0);
    //    height = h.get(0);
    //    unbind();
    //}

    public Texture(int _width,int _height){
        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, _width, _height,
                0, GL_RGB, GL_UNSIGNED_BYTE, 0);

        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_R,GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_NEAREST);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,GL_NEAREST);

        width = _width;
        height = _height;
        unbind();
    }

    public void bind(){
        glBindTexture(kindOfBind, texID);
    }

    public void unbind(){
        glBindTexture(kindOfBind, 0);
    }

    public int getKindOfBind(){
        return kindOfBind;
    }

    public int getID(){
        return texID;
    }

    public int width(){
        return width;
    }

    public int height(){
        return height;
    }
}