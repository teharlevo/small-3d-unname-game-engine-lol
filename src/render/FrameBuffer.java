package render;

import static org.lwjgl.opengl.GL30.*;


public class FrameBuffer {
    private int fboID = 0;
    private Texture tex = null;

    public FrameBuffer(int width,int height){

        fboID = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);
        
        tex = new Texture(width, height);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width,height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D,
        tex.getID(), 0);
        int rboID = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rboID);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);  

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            System.out.println("Error: Framebuffer is not complete");
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public Texture getTextureex(){
        return tex;
    }

    public int getFboID() {
        return fboID;
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0); 
    }
}