package modeling;

import java.io.File;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import main.Assets;
import render.Texture;

public class ModelLoader {

    private float vertices[];
    private Texture texture;
    
    public ModelLoader(String path){
        AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate);

        PointerBuffer buffer = scene.mMeshes();

        for (int i = 0; i < 1; i++) { //buffer.limit(); i++) {
            AIMesh mesh = AIMesh.create(buffer.get(i));
            vertices = processMesh(mesh);
        }
        
        String texName = new File(path).getName();
        texName = texName.substring(0, texName.indexOf("."));
        texture = Assets.getTexture(texName);
         
    }

    private float[] processMesh(AIMesh mesh){

        AIVector3D.Buffer vectors = mesh.mVertices();
        AIVector3D.Buffer cordes = mesh.mTextureCoords(0);
        AIColor4D.Buffer colors = mesh.mColors(0);

        float[] vertices = new float[vectors.limit() * 10];

        if(colors != null){
            for (int i = 0; i < vectors.limit(); i++) {
                int offset = i * 10;
                AIVector3D vector = vectors.get(i);
                AIVector3D corde  = cordes.get(i);
                AIColor4D  color  = colors.get(i);
                vertices[offset]    = vector.x();
                vertices[offset+1]  = vector.y();
                vertices[offset+2]  = vector.z();
                vertices[offset+3]  =  color.r();
                vertices[offset+4]  =  color.g();
                vertices[offset+5]  =  color.b();
                vertices[offset+6]  =  color.a();
                vertices[offset+7]  =  corde.x();
                vertices[offset+8]  =  corde.y();
                vertices[offset+9]  =  -1;
            }
        }
        else{
            for (int i = 0; i < vectors.limit(); i++) {
                int offset = i * 10;
                AIVector3D vector = vectors.get(i);
                AIVector3D corde  = cordes.get(i);
                vertices[offset]    = vector.x();
                vertices[offset+1]  = vector.y();
                vertices[offset+2]  = vector.z();
                vertices[offset+3]  =  1.0f;
                vertices[offset+4]  =  1.0f;
                vertices[offset+5]  =  1.0f;
                vertices[offset+6]  =  1.0f;
                vertices[offset+7]  =  corde.x();
                vertices[offset+8]  =  corde.y();
                vertices[offset+9]  =  0.0f;
            }
        }
        
        return vertices;
    }

    public Mash getMash(){
        return new Mash(vertices,texture);
    }

}