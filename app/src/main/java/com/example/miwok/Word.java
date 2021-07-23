package com.example.miwok;

/** @Link Word represents a vocabulary word that the user wants to learn .
 * It contains default translation and miwok translation for that word.
 */
public class Word {
    // Default translation for word
    private  String mDefaultTranslation;
    // Miwok translation for the word
    private String mMiwokTranslation;
    // image resource id
    private int mImageResourceId=NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED=-1;
    // audio resource id for word
    private int mAudioResourceId;

    public Word(String DefaultTranslation,String MiwokTranslation,int AudioResourceId){
        mDefaultTranslation=DefaultTranslation;
        mMiwokTranslation=MiwokTranslation;
        mAudioResourceId=AudioResourceId;
    }
    public Word(String DefaultTranslation,String MiwokTranslation,int ImageResourceId,int AudioResourceId){
        mDefaultTranslation=DefaultTranslation;
        mMiwokTranslation=MiwokTranslation;
        mImageResourceId=ImageResourceId;
        mAudioResourceId=AudioResourceId;
    }

    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }
    public String getmMiwokTranslation(){
        return mMiwokTranslation;
    }
    public int getmImageResourceId(){return mImageResourceId;
    }
    public boolean hasImage(){
        return mImageResourceId!=NO_IMAGE_PROVIDED;
    }
// return audio resource id for the word
    public int getmAudioResourceId(){
        return mAudioResourceId;
    }


}
