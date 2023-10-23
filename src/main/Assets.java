package main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import modeling.Mash;
import modeling.ModelLoader;
import render.*;
import Sound.*;
import fontPancking.FontLoader;

public class Assets {

    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String,Texture> textures = new HashMap<>();
    private static Map<String,ModelLoader> modelsMashes = new HashMap<>();
    private static Map<String,FontLoader> fonts = new HashMap<>();
    

    public static Shader getShader(String key) {
        return shaders.get(key);
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

    public static void newShader(String path){
        Shader shader = new Shader(path);
        shader.compile();
        String name = new File(path).getName();
        name = name.substring(0, name.indexOf("."));
        shaders.put(name, shader);
        shader.compile();
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
        if(folder.listFiles() == null){
            return;
        }
        for (File fileEntry : folder.listFiles()) {
            if(!fileEntry.isDirectory()){
                newShader(fileEntry.getAbsolutePath());
            }
            else{
                folderShaders(fileEntry.getAbsolutePath());
            }
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