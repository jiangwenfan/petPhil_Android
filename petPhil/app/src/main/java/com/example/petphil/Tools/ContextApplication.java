package com.example.petphil.Tools;

import android.app.Application;
import android.content.Context;

/**
 * @author wunu
 */
public class ContextApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ContextApplication.context = getApplicationContext();
    }

    public static Context getAppContext(){
        return ContextApplication.context;
    }
}
