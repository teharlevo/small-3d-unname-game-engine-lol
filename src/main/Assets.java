package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Spring;

import modeling.Mash;
import modeling.ModelLoader;
import render.*;
import Sound.*;
import fontPancking.FontLoader;

public class Assets {

    private static Map<String, String> vertexShader = new HashMap<>();
    private static Map<String, String> fragmentShader = new HashMap<>();
    private static Map<String, Shader> shaders = new HashMap<>();

    private static Map<String,Texture> textures = new HashMap<>();
    private static Map<String,ModelLoader> modelsMashes = new HashMap<>();
    private static Map<String,FontLoader> fonts = new HashMap<>();
    

    public static Shader getShader(String key) {
        return shaders.get(key);
    }

    public static String getVertexShader(String key) {
        return vertexShader.get(key);
    }

    public static String getFragmentShader(String key) {
        return fragmentShader.get(key);
    }

    public static Texture getTexture(String key) {
        return textures.get(key);
    }

    public static Mash getModelMesh(String key) {
        return modelsMashes.get(key).getMash();
    }

    public static FontLoader getFontLoader(String key){
        return fonts.get(key);
    }

    public static void newShader(String name,String vertex,String fragment){
        Shader shader = new Shader(vertexShader.get(vertex),fragmentShader.get(fragment));
        shaders.put(name, shader);
    }

    public static void newShader(String path){
        Shader shader = new Shader(path);
        String name = new File(path).getName();
        name = name.substring(0, name.indexOf("."));
        shaders.put(name, shader);
    }

    public static void newShaderSrc(String path){
        File file = new File(path);
        String name = file.getName();
        String data;
        try {
            data = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            return;
        }
        String key = name.substring(name.indexOf(".") + 1);
        name = name.substring(0, name.indexOf("."));
        if(key.equals("fs")){
            fragmentShader.put(name,data);
        }
        else if(key.equals("vs")){
            vertexShader.put(name,data );
        }
    }

    public static String newTexture(String path){
        String name = new File(path).getName();
        name = name.substring(0, name.indexOf("."));
        if(textures.get(name) == null){
            Texture texture = new Texture(path);
            textures.put(name, texture);
        }
        return name;
    }

    public static String newModelMash(String path){
        String name = new File(path).getName();
        name = name.substring(0, name.indexOf("."));
        if(modelsMashes.get(name) == null){
            ModelLoader ml = new ModelLoader(path);
            modelsMashes.put(name, ml);
        }
        return name;
    }

    public static String newFont(String path){
        String name = new File(path).getName();
        name = name.substring(0, name.indexOf("."));
        if(fonts.get(name) == null){
            FontLoader f = new FontLoader(path);
            fonts.put(name, f);
        }
        return name;
    }

    public static void folderShaders(String path){
        File folder = new File(path);
        String shaderList = new String();
        if(folder.listFiles() == null){
            return;
        }
        for (File fileEntry : folder.listFiles()) {
            if(!fileEntry.isDirectory()){
                String name = fileEntry.getName();
                if(name.substring(name.indexOf(".") + 1).equals("glsl")){
                    newShader(fileEntry.getAbsolutePath());
                }
                else if(name.substring(name.indexOf(".") + 1).equals("shli")){
                    try {
                        shaderList = new String(Files.readAllBytes(Paths.get(fileEntry.getAbsolutePath())));
                    } catch (IOException e) {}
                }
                else{
                    newShaderSrc(fileEntry.getAbsolutePath());
                }
            }
            else{
                folderShaders(fileEntry.getAbsolutePath());
            }
        }
        String[] shaders = shaderList.split("\n");
        for (int i = 0; i < shaders.length; i++) {
            String[] shaderInfo = shaders[i].split(",");
            newShader(shaderInfo[0],shaderInfo[1],shaderInfo[2]);
        }
    }
    
    public static void folderImages(String path){
        File folder = new File(path);
        if( folder.listFiles() == null){
            return;
        }
        for (File fileEntry : folder.listFiles()) {
            if(!fileEntry.isDirectory()){
                newTexture(fileEntry.getAbsolutePath());
            }
            else{
                folderImages(fileEntry.getAbsolutePath());
            }
        }
    }

    public static void folderModels(String path){
        File folder = new File(path);
        if( folder.listFiles() == null){
            return;
        }
        for (File fileEntry : folder.listFiles()) {
            if(!fileEntry.isDirectory()){
                newModelMash(fileEntry.getAbsolutePath());
            }
            else{
                folderModels(fileEntry.getAbsolutePath());
            }
        }
    }

    public static void folderFonts(String path){
        File folder = new File(path);
        if( folder.listFiles() == null){
            return;
        }
        for (File fileEntry : folder.listFiles()) {
            if(!fileEntry.isDirectory()){
                newFont(fileEntry.getAbsolutePath());
            }
            else{
                folderFonts(fileEntry.getAbsolutePath());
            }
        }
    }
    

    public static void newSound(String path){
        String name = new File(path).getName();
        name = name.substring(0, name.indexOf("."));
        int soundID = Sound.importSound(path);
        SoundMaster.addSound(soundID,name);
    }

    public static void folderSounds(String path){
        File folder = new File(path);
        for (File fileEntry : folder.listFiles()) {
            newSound(fileEntry.getAbsolutePath());
        }
    }

    public static void totalImport(String path){
        folderImages(path + "/images"  );
        folderShaders(path +"/shaders");
        folderModels(path + "/models");
        folderSounds(path + "/sounds");
        folderFonts(path  + "/fonts");
    }
}