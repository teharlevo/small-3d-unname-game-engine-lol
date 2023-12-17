package modeling;

import java.io.File;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMaterialProperty;

import main.Assets;
import render.Texture;
import render.light.Material;

public class ModelLoader {

    private float vertices[] = new float[0];
    private Texture texture;

    private Texture specularTexture;
    private    float materialShininess;
    
    public ModelLoader(String path){
        AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate);

        PointerBuffer buffer = scene.mMeshes();

        for (int i = 0; i < buffer.limit(); i++) {
            AIMesh mash = AIMesh.create(buffer.get(i));
            float[] oldVertices = vertices;
            float[] addedVertices =processMash(mash);

            int fal = oldVertices.length;
            int sal = addedVertices.length; 
            vertices = new float[fal + sal];
            System.arraycopy(oldVertices, 0, vertices, 0, fal);  
            System.arraycopy(addedVertices, 0, vertices, fal, sal);  
        }
        materialImporting(scene,0);
        String texName = new File(path).getName();
        texName = texName.substring(0, texName.indexOf("."));
        texture = Assets.getTexture(texName);
        String s = texName + "_specular";
        if(Assets.getTexture(s) == null){
            specularTexture = texture;
        }
        else{
            specularTexture = Assets.getTexture(s);
        }
         
    }

    private void materialImporting(AIScene scene,int pointer){

        //לתקן מתישהו
        //PointerBuffer materials = scene.mMaterials();
        //AIMaterial material = AIMaterial.create(materials.get(pointer));
        //PointerBuffer properties = material.mProperties();
        //for ( int j = 0; j < properties.remaining(); j++ ) {
        //    AIMaterialProperty prop = AIMaterialProperty.create(properties.get(j));
        //    if(prop.mKey().dataString().equals("$mat.shininess")){
        //        materialShininess = prop.mData().get();
        //        System.out.println(prop.mData().get());
        //        break;
        //    }
        //}
        //material.close();
        materialShininess = 32;
    }

    private float[] processMash(AIMesh mash){
        AIVector3D.Buffer vectors = mash.mVertices();
        AIVector3D.Buffer cordes = mash.mTextureCoords(0);
        AIVector3D.Buffer Normals = mash.mNormals();

        float[] vertices = new float[vectors.limit() * 8];

        for (int i = 0; i < vectors.limit(); i++) {
            int offset = i * 8;
            AIVector3D vector = vectors.get(i);
            AIVector3D corde  = cordes.get(i);
            AIVector3D normal  = Normals.get(i);
            vertices[offset]    = vector.x();
            vertices[offset+1]  = vector.y();
            vertices[offset+2]  = vector.z();
            vertices[offset+3]  = normal.x();
            vertices[offset+4]  = normal.y();
            vertices[offset+5]  = normal.z();
            vertices[offset+6]  = corde.x();
            vertices[offset+7]  = corde.y();
        }
        mash.close();
        return vertices;
    }

    public Mash getMash(){
        return new Mash(vertices,texture,new Material(specularTexture,materialShininess));
    }

}