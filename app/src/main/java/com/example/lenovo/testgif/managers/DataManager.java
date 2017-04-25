package com.example.lenovo.testgif.managers;

/**
 * Created by Lenovo on 05.04.2017.
 */

public class DataManager {
    private static DataManager INSTANCE = null;
    private PreferencesManager mPreferencesManager;
    private DataManager(){
    this.mPreferencesManager = new PreferencesManager();
    }

    public static DataManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }
    public PreferencesManager getPreferencesManager(){
        return mPreferencesManager;
    }
}
