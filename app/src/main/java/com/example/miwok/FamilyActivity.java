package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
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

        ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("Father","epe",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("Mother","eta",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("Son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("Daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("Old brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("Younger Brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("Older sister","tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("Younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("Grand Mother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("Grand Father ","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter adapter=new WordAdapter(this,words,R.color.category_family);
        ListView listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word=words.get(position);
                // release the media player if  it currently exist because we are about to play a song
                releaseMediaPlayer();
                // request audio focus for playback
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result== AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer= MediaPlayer.create(FamilyActivity.this,word.getmAudioResourceId());
                    mMediaPlayer.start();
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