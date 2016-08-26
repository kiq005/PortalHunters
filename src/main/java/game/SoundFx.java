package game;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;
import android.util.Log;

import com.example.kaique.portalhunters.R;

import java.util.HashMap;

/**
 * Created by kaique on 24/08/16.
 */
public class SoundFx {

    public static Context context;
    public static SoundPool soundPool;
    public static HashMap<Integer, Integer> soundPoolMap;
    public static MediaPlayer mediaPlayer;

    public static int SOUND_BG = 105;

    public static AssetFileDescriptor FD_BG;


    public static boolean playing;
    public static int readyToPlay;

    public static void initialize(Context _context){
        playing = false;
        readyToPlay = 0;
        context = _context;

        AssetManager assetManager = context.getAssets();

        try{

            //mediaPlayer = MediaPlayer.create(context, R.raw.gametheme);
            //mediaPlayer.start();
            //Log.d("SoundFX","Background Music loaded!");


            soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
            soundPoolMap = new HashMap<Integer, Integer>();
            soundPoolMap.put(SOUND_BG, soundPool.load(context, R.raw.battletheme, 1));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void play(){
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float leftVolume = curVolume/maxVolume;
        float rightVolume = curVolume/maxVolume;
        int priority = 1;
        int no_loop = 0;
        float normal_playback_rate = 1f;
        soundPool.play(SOUND_BG, leftVolume, rightVolume, priority, no_loop, normal_playback_rate);
    }



}
