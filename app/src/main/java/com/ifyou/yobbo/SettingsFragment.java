package com.ifyou.yobbo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;


public class SettingsFragment extends PreferenceFragment {

    private Preferences mPrefs;
    private Preference sSetting;
    private Preference cSetting;
    private Preference fSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = new Preferences(getActivity());
        mPrefs.setSettingsModified(false);
        addPreferencesFromResource(R.xml.preferences);

        sSetting = findPreference("sizeoftext");
        sSetting.setSummary(getSize());
        sSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                final int currentInterval = mPrefs.getSize();
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.list_title)
                        .content(R.string.list_summary)
                        .items(R.array.entries)
                        .itemsCallbackSingleChoice(currentInterval - 1,
                                new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView,
                                                               int which, CharSequence text) {
                                        int newInterval = which + 1;
                                        if (newInterval != currentInterval) {
                                            mPrefs.setSize(newInterval);
                                            changeValues();
                                        }
                                        return true;
                                    }
                                })
                        .positiveText(android.R.string.ok)
                        .negativeText(android.R.string.cancel)
                        .show();
                return true;
            }
        });

        cSetting = findPreference("coloroftext");
        cSetting.setSummary(getColor());
        cSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                int mSelectedColor;
                mSelectedColor = mPrefs.getColor();
                int[] mColors = getResources().getIntArray(R.array.material_colors);
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_summary,
                        mColors,
                        mSelectedColor,
                        4, // Number of columns
                        ColorPickerDialog.SIZE_SMALL);
                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

                    @Override
                    public void onColorSelected(int color) {
                        mPrefs.setColor(color);
                        changeValues();
                    }
                });
                dialog.show(getActivity().getFragmentManager(), "color_dialog_test");
                return true;
            }
        });

        fSetting = findPreference("fontoftext");
        fSetting.setSummary(getFont());
        fSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                final int currentFont = mPrefs.getFont();
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.font_title)
                        .content(R.string.font_summary)
                        .items(R.array.entries_font)
                        .itemsCallbackSingleChoice(currentFont - 1,
                                new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView,
                                                               int which, CharSequence text) {
                                        int newInterval = which + 1;
                                        if (newInterval != currentFont) {
                                            mPrefs.setFont(newInterval);
                                            changeValues();
                                        }
                                        return true;
                                    }
                                })
                        .positiveText(android.R.string.ok)
                        .negativeText(android.R.string.cancel)
                        .show();
                return true;
            }
        });

        SwitchPreference stroke = (SwitchPreference) findPreference("strokeoftext");
        stroke.setChecked(mPrefs.getStrokeEnabled());
        stroke.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mPrefs.setStrokeEnabled(newValue.toString().equals("true"));
                changeValues();
                return true;
            }
        });

        SwitchPreference anim = (SwitchPreference) findPreference("setanim");
        anim.setChecked(mPrefs.getAnimEnabled());
        anim.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mPrefs.setAnimEnabled(newValue.toString().equals("true"));
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_example, container, false);
        StrokedTextView ex = (StrokedTextView) v.findViewById(R.id.ex);
        ex.setTextColor(mPrefs.getColor());
        int fontSize = 40;
        int newSize = mPrefs.getSize();
        switch (newSize) {
            case 1:
                fontSize = 40;
                break;
            case 2:
                fontSize = 65;
                break;
            case 3:
                fontSize = 90;
                break;
            case 4:
                fontSize = 115;
                break;
            case 5:
                fontSize = 140;
                break;
        }
        ex.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        String typeFont = "impact.ttf";
        int newFont = mPrefs.getFont();
        switch (newFont) {
            case 1:
                typeFont = "IRINA_C.TTF";
                break;
            case 2:
                typeFont = "RoundsBlack.ttf";
                break;
            case 3:
                typeFont = "a_SimplerClg.TTF";
                break;
            case 4:
                typeFont = "impact.ttf";
                break;
            case 5:
                typeFont = "Intro_Inline.otf";
                break;
        }
        Typeface chops;
        if (newFont != 6)
            chops = Typeface.createFromAsset(getActivity().getAssets(),
                    typeFont);
        else
            chops = Typeface.DEFAULT;
        ex.setTypeface(chops);
        boolean stroke = mPrefs.getStrokeEnabled();
        if (stroke) {
            ex.setStrokeColor(Color.BLACK);
            ex.setStrokeWidth(2);
        }
        return v;
    }

    private void changeValues() {
        sSetting.setSummary(getSize());
        cSetting.setSummary(getColor());
        fSetting.setSummary(getFont());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    private String getFont() {
        String sFont = "";
        int font = mPrefs.getFont();
        switch (font) {
            case 1:
                sFont = getResources().getString(R.string.irina);
                break;
            case 2:
                sFont = getResources().getString(R.string.roundblack);
                break;
            case 3:
                sFont = getResources().getString(R.string.clg);
                break;
            case 4:
                sFont = getResources().getString(R.string.impact);
                break;
            case 5:
                sFont = getResources().getString(R.string.Intro_Inline);
                break;
            case 6:
                sFont = getResources().getString(R.string.font_system);
                break;
        }
        return sFont;
    }

    private String getSize() {
        String sSize = "";
        int size = mPrefs.getSize();
        switch (size) {
            case 1:
                sSize = getResources().getString(R.string.vsmall);
                break;
            case 2:
                sSize = getResources().getString(R.string.small);
                break;
            case 3:
                sSize = getResources().getString(R.string.normal);
                break;
            case 4:
                sSize = getResources().getString(R.string.large);
                break;
            case 5:
                sSize = getResources().getString(R.string.vlarge);
                break;
        }
        return sSize;
    }

    private String getColor() {
        String sColor = "";
        int color = mPrefs.getColor();
        //sColor = Integer.toString(color);
        switch (color){
            case -769226:
                sColor = getResources().getString(R.string.red);
                break;
            case -6543440:
                sColor = getResources().getString(R.string.purple);
                break;
            case -12627531:
                sColor = getResources().getString(R.string.dblue);
                break;
            case -16537100:
                sColor = getResources().getString(R.string.blue);
                break;
            case -5317:
                sColor = getResources().getString(R.string.yellow);
                break;
            case -1:
                sColor = getResources().getString(R.string.white);
                break;
            case -7617718:
                sColor = getResources().getString(R.string.green);
                break;
            case -16738680:
                sColor = getResources().getString(R.string.dgreen);
                break;
            case -26624:
                sColor = getResources().getString(R.string.orange);
                break;
            case -8825528:
                sColor = getResources().getString(R.string.brown);
                break;
            case -10453621:
                sColor = getResources().getString(R.string.gray);
                break;
            case -16777216:
                sColor = getResources().getString(R.string.black);
                break;
        }
        return sColor;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
