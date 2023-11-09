package render;

public class RenderrInformationHolder {
    
    private Texture[] texs = new Texture[0];
    private float[] floats  = new float[0];
    private String[] floatsNames = new String[0];
    private int[] ints = new int[0];
    private String[] intsNames = new String[0];

    public void setTexs(Texture[] newTexs){
        texs = newTexs;
    }

    public Texture[] getTexs(){
        return texs;
    }

    public void setFloats(float[] newFloats){
        floats = newFloats;
    }

    public float[] getFloats(){
        return floats;
    }

    public void setFloatsNames(String[] newFloatsNames){
        floatsNames = newFloatsNames;
    }

    public String[] getFloatsNames(){
        return floatsNames;
    }

    public void setInts(int[] newInts){
        ints = newInts;
    }

    public int[] getInts(){
        return ints;
    }

    public void setIntsNames(String[] newIntsNames){
        intsNames = newIntsNames;
    }

    public String[] getIntsNames(){
        return intsNames;
    }
}
