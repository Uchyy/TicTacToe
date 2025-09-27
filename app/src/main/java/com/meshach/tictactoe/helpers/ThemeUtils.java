package com.meshach.tictactoe.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeUtils {
    private static final String PREFS = "theme_prefs";
    private static final String KEY_IS_DARK = "is_dark";

    public static boolean isDark(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_IS_DARK, true); // default = dark
    }

    public static void setDark(Context context, boolean isDark) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_IS_DARK, isDark).apply();
    }
}

