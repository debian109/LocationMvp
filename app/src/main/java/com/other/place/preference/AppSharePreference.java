package com.other.place.preference;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by thinh.vo on 16/11/2016.
 */

public class AppSharePreference {
    Context context;
    SharedPreferences mSharedPreferences;

    public AppSharePreference(Context context) {
        this.context = context;
        mSharedPreferences=context.getSharedPreferences("AppSharePreference",Context.MODE_PRIVATE);
    }

    public void setString(String key, String value)
    {
        SharedPreferences.Editor editor =mSharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();

    }

    public String getString(String key)
    {
        return mSharedPreferences.getString(key,"");
    }


    public void setInt(String key, int value)
    {
        SharedPreferences.Editor editor =mSharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();

    }

    public int getInt(String key)
    {
        return mSharedPreferences.getInt(key,0);
    }

}
