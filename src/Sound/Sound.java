package Sound;


import static org.lwjgl.openal.AL10.*;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class Sound {
    
    private int sourceID;

    private boolean isPlaying = false;

    private float volume = 0.3f;
    private boolean looping = false;

    public static int importSound(String path){
        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);

        ShortBuffer rawAudioBuffer =  stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer);
        
        if (rawAudioBuffer == null) {
            System.out.println("Could not load sound '" + path + "'");
            stackPop();
            stackPop();
            return -1;
        }
        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();

        stackPop();
        stackPop();

        int format = -1;
        if(channels == 1){
            format = AL_FORMAT_MONO16;
        }
        else if(channels == 2){
            format = AL_FORMAT_STEREO16;
        }

        int bufferID = alGenBuffers();
        alBufferData(bufferID, format, rawAudioBuffer,sampleRate);
        free(rawAudioBuffer);
        return bufferID;
    }

    public Sound(int soundID,float _volume,boolean _looping){
        volume = _volume;
        looping = _looping;
        sourceID = alGenSources();
        makeSttings(soundID);
    }

    private void makeSttings(int soundID){
        alSourcei(sourceID, AL_BUFFER, soundID);
        alSourcei(sourceID, AL_LOOPING, looping ? 1 : 0);
        alSourcei(sourceID, AL_POSITION, 0);
        alSourcef(sourceID, AL_GAIN, volume);
    } 

    public void play() {
        int state = alGetSourcei(sourceID, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
            alSourcei(sourceID, AL_POSITION, 0);
            System.out.println(Pos());
        }
        
        if (!isPlaying) {
            alSourcePlay(sourceID);
            isPlaying = true;
        }
    }

    public void stop() {
        if (isPlaying) {
            alSourceStop(sourceID);
            isPlaying = false;
        }
    }

    public boolean isPlaying() {
        int state = alGetSourcei(sourceID, AL_SOURCE_STATE);
        if (state == AL_STOPPED) {
            isPlaying = false;
        }
        return isPlaying;
    }

    public void setVolume(float newVolume){
        volume = newVolume;
        alSourcef(sourceID, AL_GAIN, volume);
    }

    public void setLooping(boolean newLooping){
        looping = newLooping;
        alSourcei(sourceID, AL_LOOPING, looping ? 1 : 0);
    }

    public float getVolume(){
        return alGetSourcef(sourceID,AL_GAIN);
    }

    public boolean getLooping(){
        return alGetSourcei(sourceID,AL_LOOPING) == 1;
    }

    public int Pos(){
        return alGetSourcei(sourceID, AL_POSITION);
    }

}