package com.ifyou.yobbo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.yalantis.ucrop.UCrop;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class EditorActivity extends AppCompatActivity {

    private CanvasView customCanvas;
    private com.github.clans.fab.FloatingActionButton fabE;
    private com.github.clans.fab.FloatingActionButton fabC;
    private DiscreteSeekBar discrete;
    private FloatingActionMenu fam;
    private Toolbar toolbar;
    private TextView mTitle;
    private CircleImageView setcolor;
    private String textG;
    int numberData, enableCE;
    private TextView txt;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "crop";
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    private ExitActivityTransition exitTransition;
    boolean anim;
    private Preferences mPrefs;
    private SimpleTooltip tooltip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primary));
        }

        mPrefs = new Preferences(this);
        anim = mPrefs.getAnimEnabled();

        enableCE = 0;

        //ActivityTransition.with(getIntent()).to(findViewById(R.id.fab_menu)).start(savedInstanceState);

        numberData = 0;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            mTitle.setText(getResources().getString(R.string.fo_fab));
        }

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        if (anim && Global.img == null)
            exitTransition = ActivityTransition
                    .with(getIntent())
                    .to(findViewById(R.id.signature_canvas))
                    .duration(700)
                    .interpolator(new OvershootInterpolator())
                    .start(savedInstanceState);

        setcolor = (CircleImageView) findViewById(R.id.color_image);
        setcolor.setColorFilter(mPrefs.getColor());
        setcolor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SetColor();
            }
        });

        discrete = (DiscreteSeekBar) findViewById(R.id.discret);
        if (discrete != null) {
            discrete.measure(0, 0);
            Global.seek = discrete.getMeasuredHeight();
            discrete.setProgress(mPrefs.getSize());
            discrete.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
                @Override
                public int transform(int value) {
                    mPrefs.setSize(value);
                    customCanvas.SetConfig();
                    return value;
                }
            });
        }

        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);
        if (fam != null) {
            fam.setClosedOnTouchOutside(true);
            //fam.setBackgroundColor(Color.parseColor("#ccffffff"));
            fam.setMenuButtonColorNormalResId(R.color.colorAccent);
            fam.setMenuButtonColorPressedResId(R.color.colorAccent2);
            fam.setMenuButtonColorRippleResId(R.color.colorAccent3);
        }

        textG = "";

        com.github.clans.fab.FloatingActionButton fabS = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabS);
        fabE = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabE);
        fabC = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabC);
        fabS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b;
                if (numberData == 0)
                    b = customCanvas.get();
                else
                    b = Global.img;
                if (b != null) {
                    sHare(b);
                }
                fam.toggle(true);
            }
        });
        fabE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(textG);
                fam.toggle(true);
            }
        });
        fabC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File cache = getApplicationContext().getExternalCacheDir();
                File sharefile = new File(cache, "crop.png");
                //sharefile.delete();
                try {
                    FileOutputStream out = new FileOutputStream(sharefile);
                    customCanvas.get().compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (IOException e) {

                }
                String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
                destinationFileName += ".png";
                UCrop uCrop = UCrop.of(Uri.parse("file://" + sharefile), Uri.fromFile(new File(getApplicationContext().getExternalCacheDir(), destinationFileName)));
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                options.setToolbarColor(ContextCompat.getColor(EditorActivity.this, R.color.colorPrimary));
                options.setToolbarTitle(getString(R.string.app_name));
                options.setStatusBarColor(ContextCompat.getColor(EditorActivity.this, R.color.colorPrimaryDark));
                options.setActiveWidgetColor(ContextCompat.getColor(EditorActivity.this, R.color.colorAccent));
                uCrop.withOptions(options);
                uCrop.withAspectRatio(1, 1);
                uCrop.start(EditorActivity.this);
                //beginCrop(Uri.parse("file://" + sharefile));
                fam.toggle(true);
            }
        });
    }

    private void SetColor() {
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
                customCanvas.SetConfig();
                setcolor.setColorFilter(color);
            }
        });
        dialog.show(getFragmentManager(), "color_dialog_test");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Bitmap b;
                    if (numberData == 0)
                        b = customCanvas.get();
                    else
                        b = Global.img;
                    if (b != null) {
                        sHare(b);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            txt.setText(String.format("%s/32", String.valueOf(s.length())));
            if (s.length() <= 30)
                txt.setTextColor(Color.GREEN);
            else
                txt.setTextColor(Color.RED);
        }

        public void afterTextChanged(Editable s) {
        }
    };

    public void showInputDialog(String ourText) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit);
        txt = (TextView) dialogView.findViewById(R.id.textView);
        edt.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        edt.setSingleLine(false);

        int fontColor = mPrefs.getColor();
        if (fontColor != Color.parseColor("#ffffff"))
            edt.setTextColor(fontColor);
        String typeFont = "a_SimplerClg.TTF";
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
            chops = Typeface.createFromAsset(getAssets(), typeFont);
        else
            chops = Typeface.DEFAULT;
        edt.setTypeface(chops);

        edt.addTextChangedListener(mTextEditorWatcher);

        edt.setText(ourText);
        dialogBuilder.setTitle(R.string.app_name);
        dialogBuilder.setMessage(R.string.input_dialog);
        dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
                textG = edt.getText().toString().trim();
                customCanvas.addText(textG);
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
        edt.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        AlertDialog alertDialog = builder.show();
    }

    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(EditorActivity.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(android.R.string.ok), null, getString(android.R.string.cancel));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    public void sHare(Bitmap bitmap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            //File cache = getApplicationContext().getExternalCacheDir();
            File downloadsFolder;
            downloadsFolder = new File(getApplicationContext().getString(R.string.save_location,
                    Environment.getExternalStorageDirectory().getAbsolutePath()));
            downloadsFolder.mkdirs();

            Calendar c = Calendar.getInstance();
            long offset = c.get(Calendar.ZONE_OFFSET) + c.get(Calendar.DST_OFFSET);
            String tmpStr10 = Long.toString((c.getTimeInMillis() + offset) % (24 * 60 * 60 * 1000));

            File sharefile = new File(downloadsFolder, tmpStr10 + ".png");
            try {
                FileOutputStream out = new FileOutputStream(sharefile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {

            }

            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("image/*");
            share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + sharefile));
            try {
                startActivity(Intent.createChooser(share, "Share photo"));
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
        //customCanvas.Fresh();
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            //Global.path = resultUri.toString();
            try {
                Global.img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
            } catch (Exception e) {
                //handle exception
            }
            numberData = 1;
            customCanvas.clearCanvas();
            fabC.setEnabled(false);
            fabE.setEnabled(false);
            discrete.setVisibility(View.INVISIBLE);
            setcolor.setVisibility(View.INVISIBLE);
            enableCE = 1;
            /*Intent intent = new Intent(MainActivity.this, EditorActivity.class);
            MainActivity.this.startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);*/
        } else {
            Toast.makeText(EditorActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(EditorActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(EditorActivity.this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            if (tooltip != null) {
                if (tooltip.isShowing())
                    tooltip.dismiss();
            }
            if (anim && Global.img == null)
                exitTransition.interpolator(new OvershootInterpolator()).exit(this);
            else
                super.onBackPressed();
        }
        if (item.getItemId() == R.id.miProfile) {
            tooltip = new SimpleTooltip.Builder(this)
                    .anchorView(setcolor)
                    .text(getResources().getString(R.string.color_select))
                    .gravity(Gravity.START)
                    .dismissOnOutsideTouch(true)
                    .dismissOnInsideTouch(false)
                    .animated(true)
                    .transparentOverlay(false)
                    .contentView(R.layout.tooltip_custom, R.id.tv_text)
                    .build();
            tooltip.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    if (tooltip.isShowing())
                        tooltip.dismiss();
                    tooltip = new SimpleTooltip.Builder(EditorActivity.this)
                            .anchorView(discrete)
                            .text(getString(R.string.text_select))
                            .gravity(Gravity.BOTTOM)
                            .animated(true)
                            .transparentOverlay(false)
                            .contentView(R.layout.tootlip_next, R.id.tv_text)
                            .build();
                    tooltip.show();
                }
            });
            tooltip.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("color", setcolor.getVisibility());
        outState.putInt("size", discrete.getVisibility());
        outState.putInt("enableCE", enableCE);
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("ResourceType")
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setcolor.setVisibility(savedInstanceState.getInt("color"));
        discrete.setVisibility(savedInstanceState.getInt("size"));
        if (savedInstanceState.getInt("enableCE") == 1) {
            enableCE = savedInstanceState.getInt("enableCE");
            fabC.setEnabled(false);
            fabE.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (tooltip != null) {
            if (tooltip.isShowing())
                tooltip.dismiss();
        }
        if (anim && Global.img == null)
            exitTransition.interpolator(new OvershootInterpolator()).exit(this);
        else
            super.onBackPressed();
    }
}
