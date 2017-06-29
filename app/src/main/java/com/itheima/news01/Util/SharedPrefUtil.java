package com.itheima.news01.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/6/26 0026.
 * Shared Preference数据保存工具类
 */

public class SharedPrefUtil {
    private static String FILE="news";
    public static void saveBoolean(Context context,String key,boolean value){
        SharedPreferences sp=context.getSharedPreferences(FILE,context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
    public static boolean getBoolean(Context context, String key, boolean def){
        SharedPreferences sp=context.getSharedPreferences(FILE,context.MODE_PRIVATE);
        return sp.getBoolean(key, def);
    }
}
