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
    private float lineHight;
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
        lineHight = (float)Integer.parseInt(lines[1].split("lineHeight=")[1].split(" ")[0])
        /(float)tex.height();

        for (int i = 5; i < lines.length; i++) {
            String[] parmet = lines[i].split("=");
            char c =(char)Integer.parseInt(parmet[1].split(" ")[0]);
            int x = Integer.parseInt(parmet[2].split(" ")[0]);
            int y = Integer.parseInt(parmet[3].split(" ")[0]);
            int w = Integer.parseInt(parmet[4].split(" ")[0]);
            int h = Integer.parseInt(parmet[5].split(" ")[0]);
            int Xoffset = Integer.parseInt(parmet[6].split(" ")[0]);
            int Yoffset = Integer.parseInt(parmet[7].split(" ")[0]);
            int Xadvance = Integer.parseInt(parmet[8].split(" ")[0]);
            
            charToInfo.put(c,new CharInfo(c, (float)x/(float)tex.width()
            ,1 - (float)y/(float)tex.height() - (float)h/(float)tex.height()
            , (float)w/(float)tex.width(), (float)h/(float)tex.height()
            ,(float)Xoffset/(float)tex.width(),
            (float)(h)/(float)tex.height() - (float)Yoffset/(float)tex.height(),
            (float)Xadvance/(float)tex.width()));
        }
    }

    public float[] charCoreds(char c){
        CharInfo ci = charToInfo.get(c);
        return new float[]{ci.x(),ci.y(),ci.width(),ci.height(),ci.Xoffset(),ci.Yoffset(),ci.Xadvance()};
    }

    public float getLineHight(){
        return lineHight;
    }
    
    public Texture getTexture(){
        return tex;
    } 
}