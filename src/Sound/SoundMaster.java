package Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundMaster {

    private static float volume = 0.3f;
    private static Map<String, Integer> soundIDs = new HashMap<>();
    private static Sound[] sounds = new Sound[32];
    private static boolean[] volumeIndefndece = new boolean[32];

    public static void addSound(int soundID,String soundName){
        soundIDs.put(soundName, soundID);
    }

    public static void update(){
        for (int i = 0; i < sounds.length; i++) {
            if(sounds[i] != null){
                if(sounds[i].isPlaying() == false){
                    sounds[i] = null;
                    volumeIndefndece[i] = false;
                }
            }
        }
    }

    public static int playSound(String soundName){
        for (int i = 0; i < sounds.length; i++) {
            if(sounds[i] == null){
                sounds[i] = new Sound(soundIDs.get(soundName),volume,false);
                sounds[i].play();
                return i;
            }
        }
        System.out.println("cant play so match sounds at once(more then 32)");
        return -1;
    }

    public static int playSound(String soundName,float _volume){
        for (int i = 0; i < sounds.length; i++) {
            if(sounds[i] == null){
                sounds[i] = new Sound(soundIDs.get(soundName),_volume,false);
                sounds[i].play();
                volumeIndefndece[i] = true;
                return i;
            }
        }
        System.out.println("cant play so match sounds at once(more then 32)");
        return -1;
    }

    public static int playSound(String soundName,float _volume,boolean looping){
        for (int i = 0; i < sounds.length; i++) {
            if(sounds[i] == null){
                sounds[i] = new Sound(soundIDs.get(soundName),_volume,looping);
                sounds[i].play();
                volumeIndefndece[i] = true;
                return i;
            }
        }
        System.out.println("cant play so match sounds at once(more then 32)");
        return -1;
    }

    public static int playSound(String soundName,boolean looping){
        for (int i = 0; i < sounds.length; i++) {
            if(sounds[i] == null){
                sounds[i] = new Sound(soundIDs.get(soundName),volume,looping);
                sounds[i].play();
                return i;
            }
        }
        System.out.println("cant play so match sounds at once(more then 32)");
        return -1;
    }

    public static void stopSound(int soundPos){
        sounds[soundPos].stop();
        sounds[soundPos] = null;
        volumeIndefndece[soundPos] = false;
    }

    public static void setVolume(int soundPos,float newVolume){
        sounds[soundPos].setVolume(newVolume);
    }
    
    public static void setVolume(float newVolume){
        volume = newVolume;
        for (int i = 0; i < sounds.length; i++) {
            if(sounds[i] != null && volumeIndefndece[i] == false){
                sounds[i].setVolume(newVolume);
            }   
        }
    }


    public static boolean getLooping(int soundPos){
        return sounds[soundPos].getLooping();
    }

    public static float getVolume(){
        return volume;
    }

    public static float getVolume(int soundPos){
        return sounds[soundPos].getVolume();
    }

    public static void setLooping(int soundPos,boolean newLooping){
        sounds[soundPos].setLooping(newLooping);;
    }

    public static int Pos(int soundPos){
        return sounds[soundPos].Pos();
    }
}