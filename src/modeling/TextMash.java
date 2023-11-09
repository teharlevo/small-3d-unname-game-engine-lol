package modeling;

import fontPancking.FontLoader;
import main.Assets;

public class TextMash {
    private Mash mash;
    private FontLoader font;
    private String text;

    public TextMash(String _text,FontLoader _font){
        text = _text;
        font = _font;
        mash = new Mash(makeVerties(text), font.getTexture());
    }

    public TextMash(String _text,String fontName){
        text = _text;
        font = Assets.getFontLoader(fontName);
        mash = new Mash(makeVerties(text), font.getTexture());
    }

    public void cangeText(String _text){
        text = _text;
        mash.setVertices(makeVerties(text));
    }

    public float[] makeVerties(String text){
        float[] verties = new float[1000 * 6 * 10];
        float[] poscords = new float[]{0.0f,0.0f,1.0f,0.0f,1.0f,1.0f,1.0f,1.0f,0.0f,1.0f,0.0f,0.0f};
        int  [] uvcords  = new int[]{0,0,1,0,1,1,1,1,0,1,0,0};
        float addx = 0;
        float addy = 0;
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) != '\n'){
                float[] charCords = font.charCoreds(text.charAt(i));
                for (int j = 0; j < 6; j++) {
                int offset = i * 9 * 6 + j * 9;
                float Xoffset = charCords[4];
                float Yoffset = charCords[5] * 0.5f;
                verties[offset    ] = poscords[j*2] *     charCords[2] + addx;
                verties[offset + 1] = poscords[j*2 + 1] * charCords[3] + addy;
                //verties[offset + 2] =  0;
                verties[offset + 2] =  1.0f;
                verties[offset + 3] =  1.0f;
                verties[offset + 4] =  1.0f;
                verties[offset + 5] =  1.0f;
                verties[offset + 6] = charCords[0] +  uvcords[j*2] *     charCords[2];
                verties[offset + 7] = charCords[1] +  uvcords[j*2 + 1] * charCords[3];
                verties[offset + 8] = 0;
                }
                addx += charCords[6];
            }
            else{
                addy -= font.getLineHight();
                addx = 0;
            }
        }
        return verties;
    }

    public Mash getMash(){
        return mash;
    }

    public String getText(){
        return text;
    }
}