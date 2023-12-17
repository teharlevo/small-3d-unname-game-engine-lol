package render;

import java.util.ArrayList;
import java.util.List;

import main.Assets;
import modeling.Model;
import render.light.LightSource;

public class Renderer {
    private RenderrInformationHolder RIH;
    public List<RenderBatch> batchers = new ArrayList<>();
    private Shader s;
    private Camrea c;
    private int[] arrayStrcher = null;
    private List<LightSource> lightSources = new ArrayList<>();
    
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
        for (int i = 0; i < batchers.size(); i++) {
            if(m.getMash().getMashID() == batchers.get(i).getMash().getMashID()){
                batchers.get(i).addModel(m);
                return;
            }
        }
        batchers.add(new RenderBatch(s, m,this,arrayStrcher));
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

    public void addLightSource(LightSource newLightSource){
        lightSources.add(newLightSource);
    }

    public LightSource[] getLightSources(){
        LightSource lightSourcesArray[] = new LightSource[lightSources.size()];
        lightSources.toArray(lightSourcesArray);
        return lightSourcesArray;
    }

    public void activetedLightSources(){
        for (int i = 0; i < lightSources.size(); i++) {
            lightSources.get(i).sandToGPU(s);
        }
    }

}