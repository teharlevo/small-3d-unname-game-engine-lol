package modeling;

import java.io.File;

import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMaterialProperty;

import main.Assets;
import modeling.light.Material;
import render.Texture;

public class ModelLoader {

    private float vertices[];
    private Texture texture;

    private Vector3f materialAmbient;
    private Vector3f materialDiffuse;
    private Vector3f materialSpecular;
    private    float materialShininess;
    
    public ModelLoader(String path){
        AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate);

        PointerBuffer buffer = scene.mMeshes();

        for (int i = 0; i < 1; i++) { //buffer.limit(); i++) {
            AIMesh mesh = AIMesh.create(buffer.get(i));
            vertices = processMesh(mesh);
        }
        materialImporting(scene,0);
        String texName = new File(path).getName();
        texName = texName.substring(0, texName.indexOf("."));
        texture = Assets.getTexture(texName);
         
    }

    private void materialImporting(AIScene scene,int pointer){

        //לתקן מתישהו
        PointerBuffer materials = scene.mMaterials();
        AIMaterial material = AIMaterial.create(materials.get(pointer));
        PointerBuffer properties = material.mProperties();
        for ( int j = 0; j < properties.remaining(); j++ ) {
            AIMaterialProperty prop = AIMaterialProperty.create(properties.get(j));
        }
        material.close();
        materialAmbient = new Vector3f(0.1f);
        materialDiffuse = new Vector3f(0.5f);
        materialSpecular = new Vector3f(0.5f);
        materialShininess = 256;

    }

    private float[] processMesh(AIMesh mesh){
        AIVector3D.Buffer vectors = mesh.mVertices();
        AIVector3D.Buffer cordes = mesh.mTextureCoords(0);
        AIVector3D.Buffer Normals = mesh.mNormals();

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
        mesh.close();
        return vertices;
    }

    public Mash getMash(){
        return new Mash(vertices,texture,new Material(materialAmbient, materialDiffuse
        , materialSpecular, materialShininess));
    }

}