package fontPancking;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import main.Assets;
import render.Texture;

public class FontLoader {

    private Map<Character,CharInfo> charToInfo = new HashMap<>();
    private Texture tex;
    
    public FontLoader(String path){
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            System.out.println("didnt import atf file good");
            e.printStackTrace();
        }
        String texName = new File(path).getName();
        texName = texName.substring(0, texName.indexOf("."));
        tex = Assets.getTexture(texName);
        String[] lines = text.split("\n");
        System.out.println(lines.length);
        for (int i = 5; i < lines.length; i++) {
            String[] parmet = lines[i].split("=");
            char c =(char)Integer.parseInt(parmet[1].split(" ")[0]);
            int x = Integer.parseInt(parmet[2].split(" ")[0]);
            int y = Integer.parseInt(parmet[3].split(" ")[0]);
            int w = Integer.parseInt(parmet[4].split(" ")[0]);
            int h = Integer.parseInt(parmet[5].split(" ")[0]);
            charToInfo.put(c,new CharInfo(c, x/tex.width(), y/tex.height(), w/tex.width(), h/tex.height()));
        }
    }

    public float[] charCoreds(char c){
        CharInfo ci = charToInfo.get(c);
        return new float[]{ci.x(),ci.y(),ci.width(),ci.height()};
    }
    
    public Texture getTexture(){
        return tex;
    } 
}