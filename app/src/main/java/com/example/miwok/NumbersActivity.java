package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteController;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
MediaPlayer mMediaPlayer;
// Handles audio focus when playing a sound file
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
//This listener gets triggered when media player  has completed playing the audio file
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
        //Create and setup the {@ Link AudioManager} to request Audio Focus
        mAudioManager =(AudioManager) getSystemService(Context.AUDIO_SERVICE);



        // Create an array of words
      final  ArrayList<Word> words=new ArrayList<Word>();
       words.add(new Word("One","Lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("Two","Otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("Three","tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("Four","Oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("Five","Massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("Six","Temmokka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("Seven","Kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("Eight","Kauinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("Nine","Wo'e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("Ten","Na'aacha",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter adapter=new WordAdapter(this,words,R.color.category_numbers);
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);
                //release the media player if it currently exists because we are about to play a compltely different sound
                releaseMediaPlayer();
                // request audio focus for playback
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,//use the music stream
                        AudioManager.STREAM_MUSIC,//Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // we have audio focus now



                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

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

    /**
     * Clean up the media player by releasing its resources.
     */
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
            //Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}