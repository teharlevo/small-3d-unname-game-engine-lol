package modeling;

public class TextMash {
    private Mash mash;

    public TextMash(String text){
        mash = new Mash(makeVerties(text), "4");
    }

    public void cangeText(String text){
        mash.setVertices(makeVerties(text));
    }

    public float[] makeVerties(String text){
        float[] verties = new float[1000 * 6 * 10];
        float[] po = new float[]{-1,-1,1,-1,1,1,1,1,-1,1,-1,-1};
        int  [] k  = new int[]{0,0,1,0,1,1,1,1,0,1,0,0};
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < 6; j++) {
                int offset = i * 60 + j * 10;
                verties[offset    ] = po[j*2]    + i * 2;
                verties[offset + 1] = po[j*2 + 1];
                verties[offset + 2] =  0;
                verties[offset + 3] =  1.0f;
                verties[offset + 4] =  1.0f;
                verties[offset + 5] =  1.0f;
                verties[offset + 6] =  1.0f;
                verties[offset + 7] = k[j*2];
                verties[offset + 8] = k[j*2 + 1];
                verties[offset + 9] = 0;
            }
        }
        return verties;
    }

    public Mash getMash(){
        return mash;
    }
}