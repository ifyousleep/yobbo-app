package com.ifyou.yobbo;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class Preferences {

    private static final String
            PREFERENCES_NAME = "YOBBO_PREFERENCES",
            SETTINGS_MODIFIED = "settings_modified",
            COLOR_TEXT = "color_text",
            COLOR_PROGRESS = "color_progress",
            FONT_TEXT = "font_text",
            ENABLE_STROKE = "enable_stroke",
            ENABLE_ANIM = "enable_anim",
            SIZE_TEXT = "size_text";

    private final Context context;

    public Preferences(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setSettingsModified(boolean loaded) {
        getSharedPreferences().edit().putBoolean(SETTINGS_MODIFIED, loaded).apply();
    }

    public boolean getSettingsModified() {
        return getSharedPreferences().getBoolean(SETTINGS_MODIFIED, false);
    }

    public void setSize(int size) {
        getSharedPreferences().edit().putInt(SIZE_TEXT, size).apply();
    }

    public int getSize() {
        return getSharedPreferences().getInt(SIZE_TEXT, 3);
    }

    public void setColor(int color) {
        getSharedPreferences().edit().putInt(COLOR_TEXT, color).apply();
    }

    public int getColor() {
        return getSharedPreferences().getInt(COLOR_TEXT, Color.argb(255, 244, 67, 54));
    }

    public void setFont(int font) {
        getSharedPreferences().edit().putInt(FONT_TEXT, font).apply();
    }

    public int getFont() {
        return getSharedPreferences().getInt(FONT_TEXT, 6);
    }

    public void setStrokeEnabled(boolean strokeEnabled) {
        getSharedPreferences().edit().putBoolean(ENABLE_STROKE, strokeEnabled).apply();
    }

    public boolean getStrokeEnabled() {
        return getSharedPreferences().getBoolean(ENABLE_STROKE, false);
    }

    public void setAnimEnabled(boolean animEnabled) {
        getSharedPreferences().edit().putBoolean(ENABLE_ANIM, animEnabled).apply();
    }

    public boolean getAnimEnabled() {
        return getSharedPreferences().getBoolean(ENABLE_ANIM, false);
    }
}
