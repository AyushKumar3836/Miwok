package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                // AUDIOFOCUS_LOSS_TRANSIENT case means that we'have lost audio focus
                //sort amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK  case means
                //our app is allowed to continue playing sound but at lower volume.
                // both cases the same way because our app is playing sort sound files
                //pause playback and reset player to the start of the file.That way we play the word from begininng when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                //The AUDIOFOCUS_GAIN cause means we have regained focus and can resume playback
                mMediaPlayer.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                //The AUDIOFOCUS_LOSS case means we've lost audio focus and stop playback and cleanup resource
                releaseMediaPlayer();

            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        // create and setup the {@ Link AudioManager } to request Audio Focus
        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);


     final    ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("Red","Wetetti",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("Green","Chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("Brown","Takaakki",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("Gray","Topoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("Black","Kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("White","Kelelli",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("Dusty yellow","Topiise",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("Mustard yellow","Chiwiite",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        WordAdapter adapter=new WordAdapter(this,words,R.color.category_colors);
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);
                //release the media player if it currently exist because we are about to play a song
                releaseMediaPlayer();
                // request audio focus for playback
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,// use the music stream
                        AudioManager.STREAM_MUSIC,//Request permament focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    // we have audio focus now
                    mMediaPlayer= MediaPlayer.create(ColorsActivity.this,word.getmAudioResourceId());
                    mMediaPlayer.start();;
                }


            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        // when this activity is stopped , release the media player resource because we don't be
        // playing sounds any more
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}