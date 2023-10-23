package fontPancking;

public class CharInfo {
    
    private char theChar;
    private float x,y,w,h;

    public CharInfo(char c,float _x,float _y,float width,float height){
        theChar = c;
        x = _x;
        y = _y;
        w = width;
        h = height;
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

}
