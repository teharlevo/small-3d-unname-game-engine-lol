package render;

import java.util.ArrayList;
import java.util.List;

import main.Assets;
import modeling.Model;

public class Renderer {
    private RenderrInformationHolder RIH;
    public List<RenderBatch> batchers = new ArrayList<>();
    private Shader s;
    private Camrea c;
    private int[] arrayStrcher = null;
    
    public Renderer(Shader shader,Camrea camrea){
        s = shader;
        c = camrea;
        RIH = new RenderrInformationHolder();
    }

    public Renderer(String shaderName,Camrea camrea){
        s = Assets.getShader(shaderName);
        c = camrea;
        RIH = new RenderrInformationHolder();
    }

    public Renderer(Shader shader,Camrea camrea,int[] _arrayStrcher){
        s = shader;
        c = camrea;
        RIH = new RenderrInformationHolder();
        arrayStrcher = _arrayStrcher;
    }

    public Renderer(String shaderName,Camrea camrea,int[] _arrayStrcher){
        s = Assets.getShader(shaderName);
        c = camrea;
        RIH = new RenderrInformationHolder();
        arrayStrcher = _arrayStrcher;
    }

    public void addModel(Model m){
        batchers.add(new RenderBatch(s, m,RIH,arrayStrcher));
    }

    public void render(Camrea c){
        for (int i = 0; i < batchers.size(); i++) {
            batchers.get(i).update(c);
        }
    }

    public void render(){
        render(c);
    }

    public Shader getShader(){
        return s;
    }

    public void setShader(Shader newShader){
        s = newShader;
        for (int i = 0; i < batchers.size(); i++) {
            batchers.get(i).setShader(newShader);
        }
    }

    public RenderrInformationHolder getRIH(){
        return RIH;
    }

}