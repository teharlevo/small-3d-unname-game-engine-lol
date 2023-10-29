package fontPancking;

public class CharInfo {
    
    private char theChar;
    private float x,y,w,h;
    private float Xoffset,Yoffset,Xadvance;

    public CharInfo(char c,float _x,float _y,float width,float height
    ,float _Xoffset,float _Yoffset,float _Xadvance){
        theChar = c;
        x = _x;
        y = _y;
        w = width;
        h = height;
        Xoffset = _Xoffset;
        Yoffset = _Yoffset;
        Xadvance = _Xadvance;
    }

    public char theChar(){
        return theChar;
    }

    public float x(){
        return x;
    }

    public float y(){
        return y;
    }

    public float width(){
        return w;
    }

    public float height(){
        return h;
    }

    public float Xoffset(){
        return Xoffset;
    }

    public float Yoffset(){
        return Yoffset;
    }

    public float Xadvance(){
        return Xadvance;
    }
}