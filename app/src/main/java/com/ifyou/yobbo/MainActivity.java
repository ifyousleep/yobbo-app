package com.ifyou.yobbo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.github.javiersantos.piracychecker.PiracyChecker;
import com.github.javiersantos.piracychecker.enums.InstallerID;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Drawer result = null;
    public static long currentItem = 1;
    private static final String MARKET_URL = "https://play.google.com/store/apps/details?id=";
    private static final int REQUEST_SELECT_PICTURE = 0x01;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "crop";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primary));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccountHeader headerResult;
        Global.img = null;

        if (!BuildConfig.DEBUG)
            new PiracyChecker(this)
                    .enableGooglePlayLicensing("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk1TbKBSaPN7zlaa+MRN+i+bTqutydUao1QTHqNLJ+ZPmppgbD78TLF57T8bKXlediSJqTBra65Z7tdRZCUHzKAiDhBLoUBZ2lQWtW/v5g6G6C+e7Mxgh6rZf9ATTlW/WxCdHH/1kbvxylUKzyMXiD4McjMbzMUR0fn8xopWuIsWxsnPSEE3jK+/MOWXLMbpex7FJo7Icw4/oPLeNt0QZqyHjragoaVelqC3ionKalL6u+ZMEIV0P/7GgAdrcn/LpzK1wfRLkpJGelfFRGPWYZ9siZMxz1fOmEMdIrlCnJl+IQSxAsQciOQnfUO5W5sVPM35yi41wRZIrOCkghPx5CwIDAQAB")
                    .enableInstallerId(InstallerID.GOOGLE_PLAY)
                    .start();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final IProfile profile = new ProfileDrawerItem().withName(getResources().getString(R.string.app_name)).withIcon(R.drawable.sim8);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.drawer_item_home).withIdentifier(1).withIcon(GoogleMaterial.Icon.gmd_home),
                        new SecondaryDrawerItem().withName(R.string.open).withIdentifier(5).withIcon(GoogleMaterial.Icon.gmd_file_upload).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIdentifier(3).withIcon(GoogleMaterial.Icon.gmd_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_about).withIdentifier(2).withIcon(GoogleMaterial.Icon.gmd_help),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_rate).withIdentifier(4).withIcon(GoogleMaterial.Icon.gmd_rate_review).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            SwitchFragments((int) drawerItem.getIdentifier());
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        if (!Utils.hasNetwork(this)) {
            Toast.makeText(this, R.string.noconnect, Toast.LENGTH_LONG).show();
        }

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new MainFragment())
                    .commit();
    }


    private void SwitchFragments(int pos) {
        currentItem = pos;
        switch (pos) {
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.main, new MainFragment())
                        .commit();
                toolbar.setTitle(R.string.app_name);
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.main, new AboutFragment())
                        .commit();
                toolbar.setTitle(R.string.drawer_item_about);
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.main, new SettingsFragment())
                        .commit();
                toolbar.setTitle(R.string.drawer_item_settings);
                break;
            case 4:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URL + getPackageName()));
                startActivity(browserIntent);
                break;
            case 5:
                pickFromGallery();
                break;
        }
    }

    private void pickFromGallery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "choose?!"), REQUEST_SELECT_PICTURE);
        }
    }

    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getApplicationContext().getExternalCacheDir(), destinationFileName)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setToolbarTitle(getString(R.string.app_name));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorAccent));
        uCrop.withOptions(options);
        uCrop.withAspectRatio(1, 1);
        uCrop.start(MainActivity.this);
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
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(android.R.string.ok), null, getString(android.R.string.cancel));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    protected void showAlertDialog(
            @Nullable String title, @Nullable String message,
            @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
            @NonNull String positiveText,
            @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
            @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            try {
                Global.img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
            } catch (Exception e) {
                //handle exception
            }
            Intent intent = new Intent(MainActivity.this, EditorActivity.class);
            MainActivity.this.startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(MainActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (result != null)
            outState = result.saveInstanceState(outState);
        outState.putInt("currentSection", (int) currentItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getInt("currentSection") != 5 && savedInstanceState.getInt("currentSection") != 4)
            SwitchFragments(savedInstanceState.getInt("currentSection"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else if (result != null && currentItem != 1 && currentItem != 4 && currentItem != 5) {
            result.setSelection(1);
            toolbar.setTitle(R.string.app_name);
        } else if (result != null) {
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
