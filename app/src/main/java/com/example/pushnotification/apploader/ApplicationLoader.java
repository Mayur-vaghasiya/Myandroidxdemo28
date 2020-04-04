package com.example.pushnotification.apploader;

import android.app.Application;


import com.example.pushnotification.SharedPreferenceUtility;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.pushnotification.util.Staticdatautility.BASE_URL;


/**
 * Created by peacock on 21/1/17.
 */

public class ApplicationLoader extends Application {

    public static final String TAG = ApplicationLoader.class.getSimpleName();
    private static ApplicationLoader applicationLoader;
    private SharedPreferenceUtility preferencesUtility;
    private  Retrofit retrofit;

    public static synchronized ApplicationLoader getAppLoader() {

        return applicationLoader;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationLoader = this;

        preferencesUtility = new SharedPreferenceUtility(this);

    }

    public SharedPreferenceUtility getPreferencesUtility() {

        if (preferencesUtility == null) {

            preferencesUtility = new SharedPreferenceUtility(this);

        }

        return preferencesUtility;

    }

    public Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
