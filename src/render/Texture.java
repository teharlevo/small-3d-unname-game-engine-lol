package render;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL43.*;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import static org.lwjgl.stb.STBImage.*;

import org.lwjgl.BufferUtils;


public class Texture {
    private int texID;
    private int width = 0;
    private int height = 0;
    private int channels = 3;

    private int kindOfBind = 0;

    public Texture(String path){

        texID = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texID);
        kindOfBind = GL_TEXTURE_2D;

        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_R,GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_NEAREST);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,GL_NEAREST);

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer c = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer imge = stbi_load(path, w, h,c,0);
        
        
        if(imge != null){
            if (c.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, w.get(0), h.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, imge);
            } else if (c.get(0) == 4) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w.get(0), h.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, imge);
            } else {
                assert false : "Error: (Texture) Unknown number of channesl '" + c.get(0) + "'";
            }
        
        }else{
            System.out.println("dont work imge " + path);
        }

        stbi_image_free(imge);

        width  = w.get(0);
        height = h.get(0);
        channels = c.get(0);
        unbind();
    }

    //public Texture(String[] facesNameFile){//cube map// ירחם השם עליי
    //    texID = glGenTextures();
    //    glActiveTexture(GL_TEXTURE0);
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
    //            System.out.print("טקסטורה תלת מימדית לא עובדת ");
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
        glActiveTexture(GL_TEXTURE0);
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


    public ByteBuffer getTextureData() {
        int bufferSize = width * height * channels;

        // Create a ByteBuffer to hold the texture data
        ByteBuffer buffer = BufferUtils.createByteBuffer(bufferSize);

        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, texID);

        // Get the texture data
        if(channels == 3){
            glGetTexImage(GL_TEXTURE_2D, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);
        }
        else{
            glGetTexImage(GL_TEXTURE_2D, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        }

        // Unbind the texture
        glBindTexture(GL_TEXTURE_2D, 0);

        return buffer;
    }
    
    public void saveImage(String name) {// איטי רצח יש לשפר

        //יצרת באיטבאפר מהאקסטאראידי בלבד

        ByteBuffer buffer = getTextureData();
        //לקיחת באיטבאפרולשמור אותו לקובץ
        String formet ="jpg";
        if(channels == 4){formet ="png";}
        File file = new File(name + "." + formet); // The file to save to.
        BufferedImage image;
        if(channels == 3){
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }
        else{
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
           
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                int i = (x + (width * y)) * channels;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
           
        try {
            ImageIO.write(image, formet, file);
        } catch (IOException e) { e.printStackTrace(); }
    }
}