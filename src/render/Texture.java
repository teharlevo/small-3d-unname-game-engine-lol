package render;

import static org.lwjgl.opengl.GL20.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.stb.STBImage.*;

import org.lwjgl.BufferUtils;

public class Texture {
    private int texID;
    private int width = 0;
    private int height = 0;


    public Texture(String path){

        texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);

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
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
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