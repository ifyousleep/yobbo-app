package com.ifyou.yobbo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;

import java.util.ArrayList;
import java.util.List;

public class StickActivity extends AppCompatActivity {

    int numberData;
    boolean anim;
    private ExitActivityTransition exitTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primary));
        }

        Preferences mPrefs;
        mPrefs = new Preferences(this);
        anim = mPrefs.getAnimEnabled();

        Global.img = null;
        GridLayoutManager lLayout;
        GridSpacingItemDecoratio gridSpacing;
        RecyclerFastScroller fastScroller;

        Intent intent = getIntent();
        if (null != intent) {
            numberData = intent.getIntExtra("key", 0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            switch (numberData) {
                case 0:
                    mTitle.setText(getResources().getString(R.string.pusheen));
                    break;
                case 1:
                    mTitle.setText(getResources().getString(R.string.rage));
                    break;
                case 2:
                    mTitle.setText(getResources().getString(R.string.ars));
                    break;
                case 3:
                    mTitle.setText(getResources().getString(R.string.roman));
                    break;
                case 4:
                    mTitle.setText(getResources().getString(R.string.simon));
                    break;
                case 5:
                    mTitle.setText(getResources().getString(R.string.animal));
                    break;
                case 6:
                    mTitle.setText(getResources().getString(R.string.senya));
                    break;
                case 7:
                    mTitle.setText(getResources().getString(R.string.manul));
                    break;
                case 8:
                    mTitle.setText(getResources().getString(R.string.dib));
                    break;
                case 9:
                    mTitle.setText(getResources().getString(R.string.whoiam));
                    break;
                case 10:
                    mTitle.setText(getResources().getString(R.string.cyan));
                    break;
                case 11:
                    mTitle.setText(getResources().getString(R.string.face));
                    break;
                case 12:
                    mTitle.setText(getResources().getString(R.string.peppa));
                    break;
                case 13:
                    mTitle.setText(getResources().getString(R.string.simp));
                    break;
                case 14:
                    mTitle.setText(getResources().getString(R.string.futur));
                    break;
                case 15:
                    mTitle.setText(getResources().getString(R.string.pigeons));
                    break;
                case 16:
                    mTitle.setText(getResources().getString(R.string.wow));
                    break;
                case 17:
                    mTitle.setText(getResources().getString(R.string.avc));
                    break;
                case 18:
                    mTitle.setText(getResources().getString(R.string.boroda));
                    break;
                case 19:
                    mTitle.setText(getResources().getString(R.string.wwf));
                    break;
                case 20:
                    mTitle.setText(getResources().getString(R.string.emo));
                    break;
                case 21:
                    mTitle.setText(getResources().getString(R.string.gad));
                    break;
                case 22:
                    mTitle.setText(getResources().getString(R.string.goo));
                    break;
                case 23:
                    mTitle.setText(getResources().getString(R.string.har));
                    break;
                case 24:
                    mTitle.setText(getResources().getString(R.string.rr));
                    break;
                case 25:
                    mTitle.setText(getResources().getString(R.string.be));
                    break;
                case 26:
                    mTitle.setText(getResources().getString(R.string.adv));
                    break;
                case 27:
                    mTitle.setText(getResources().getString(R.string.coy));
                    break;
                case 28:
                    mTitle.setText(getResources().getString(R.string.fox));
                    break;
                case 29:
                    mTitle.setText(getResources().getString(R.string.pea));
                    break;
                case 30:
                    mTitle.setText(getResources().getString(R.string.fro));
                    break;
                case 31:
                    mTitle.setText(getResources().getString(R.string.smi));
                    break;
                case 32:
                    mTitle.setText(getResources().getString(R.string.spa));
                    break;
                case 33:
                    mTitle.setText(getResources().getString(R.string.ice));
                    break;
                case 34:
                    mTitle.setText(getResources().getString(R.string.pool));
                    break;
                case 35:
                    mTitle.setText(getResources().getString(R.string.gar));
                    break;
                case 36:
                    mTitle.setText(getResources().getString(R.string.cut));
                    break;
                case 37:
                    mTitle.setText(getResources().getString(R.string.hick));
                    break;
            }
        }

        final List<ItemObject> rowListItem = getAllItemList();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            lLayout = new GridLayoutManager(StickActivity.this, 3);
        else
            lLayout = new GridLayoutManager(StickActivity.this, 2);

        //ActivityTransition.with(getIntent()).to(findViewById(R.id.recycler_view)).start(savedInstanceState);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        fastScroller = (RecyclerFastScroller) findViewById(R.id.rvFastScroller);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        gridSpacing = new GridSpacingItemDecoratio(2,
                getResources().getDimensionPixelSize(R.dimen.lists_padding),
                true);
        rView.addItemDecoration(gridSpacing);

        final RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(StickActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);
        if (rView.getAdapter() != null) {
            fastScroller.attachRecyclerView(rView);
            if (fastScroller.getVisibility() != View.VISIBLE) {
                fastScroller.setVisibility(View.VISIBLE);
            }
        }
        rView.setItemAnimator(new DefaultItemAnimator());
        rcAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Global.path = getAllItemList().get(position).getPhoto();
                Intent intent = new Intent(StickActivity.this, EditorActivity.class);
                if (anim) {
                    ActivityTransitionLauncher.with(StickActivity.this).from(v).launch(intent);
                    overridePendingTransition(0, 0);
                } else {
                    StickActivity.this.startActivity(intent);
                    //overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                }
            }
        });

        if (anim)
            exitTransition = ActivityTransition
                    .with(getIntent())
                    .to(findViewById(R.id.recycler_view))
                    .duration(700)
                    .interpolator(new OvershootInterpolator())
                    .start(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (anim)
                super.onBackPressed();
                //exitTransition.interpolator(new OvershootInterpolator()).exit(this);
            else
                super.onBackPressed(); // close this activity and return to preview activity (if there is any)
            //exitTransition.interpolator(new OvershootInterpolator()).exit(this);
            //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Global.img = null;
    }

    @Override
    public void onBackPressed() {
        if (anim) {
            super.onBackPressed();
            //exitTransition.interpolator(new OvershootInterpolator()).exit(this);
        } else
            super.onBackPressed();
        //exitTransition.interpolator(new OvershootInterpolator()).exit(this);
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private List<ItemObject> getAllItemList() {

        List<ItemObject> allItems = new ArrayList<>();
        String url = "https://raw.githubusercontent.com/ifyousleep/stick/master/";
        switch (numberData) {
            case 0:
                allItems.add(new ItemObject("", url + "push1.png"));
                allItems.add(new ItemObject("", url + "push2.png"));
                allItems.add(new ItemObject("", url + "push3.png"));
                allItems.add(new ItemObject("", url + "push4.png"));
                allItems.add(new ItemObject("", url + "push5.png"));
                allItems.add(new ItemObject("", url + "push6.png"));
                allItems.add(new ItemObject("", url + "push7.png"));
                allItems.add(new ItemObject("", url + "push8.png"));
                allItems.add(new ItemObject("", url + "push9.png"));
                allItems.add(new ItemObject("", url + "push10.png"));
                allItems.add(new ItemObject("", url + "push11.png"));
                allItems.add(new ItemObject("", url + "push12.png"));
                allItems.add(new ItemObject("", url + "push14.png"));
                allItems.add(new ItemObject("", url + "push15.png"));
                allItems.add(new ItemObject("", url + "push16.png"));
                allItems.add(new ItemObject("", url + "push17.png"));
                allItems.add(new ItemObject("", url + "push18.png"));
                allItems.add(new ItemObject("", url + "push19.png"));
                allItems.add(new ItemObject("", url + "push20.png"));
                allItems.add(new ItemObject("", url + "push23.png"));
                allItems.add(new ItemObject("", url + "push22.png"));
                allItems.add(new ItemObject("", url + "push24.png"));
                allItems.add(new ItemObject("", url + "push25.png"));
                allItems.add(new ItemObject("", url + "push26.png"));
                allItems.add(new ItemObject("", url + "push27.png"));
                allItems.add(new ItemObject("", url + "push28.png"));
                allItems.add(new ItemObject("", url + "push29.png"));
                allItems.add(new ItemObject("", url + "push30.png"));
                break;
            case 1:
                allItems.add(new ItemObject("", url + "rage1.png"));
                allItems.add(new ItemObject("", url + "rage2.png"));
                allItems.add(new ItemObject("", url + "rage3.png"));
                allItems.add(new ItemObject("", url + "rage4.png"));
                allItems.add(new ItemObject("", url + "rage5.png"));
                allItems.add(new ItemObject("", url + "rage6.png"));
                allItems.add(new ItemObject("", url + "rage7.png"));
                allItems.add(new ItemObject("", url + "rage8.png"));
                allItems.add(new ItemObject("", url + "rage9.png"));
                allItems.add(new ItemObject("", url + "rage10.png"));
                allItems.add(new ItemObject("", url + "rage11.png"));
                allItems.add(new ItemObject("", url + "rage12.png"));
                allItems.add(new ItemObject("", url + "rage13.png"));
                allItems.add(new ItemObject("", url + "rage14.png"));
                allItems.add(new ItemObject("", url + "rage15.png"));
                allItems.add(new ItemObject("", url + "rage16.png"));
                allItems.add(new ItemObject("", url + "rage17.png"));
                allItems.add(new ItemObject("", url + "rage18.png"));
                allItems.add(new ItemObject("", url + "rage19.png"));
                allItems.add(new ItemObject("", url + "rage20.png"));
                allItems.add(new ItemObject("", url + "rage21.png"));
                allItems.add(new ItemObject("", url + "rage22.png"));
                allItems.add(new ItemObject("", url + "rage23.png"));
                allItems.add(new ItemObject("", url + "rage24.png"));
                allItems.add(new ItemObject("", url + "rage25.png"));
                allItems.add(new ItemObject("", url + "rage26.png"));
                allItems.add(new ItemObject("", url + "rage27.png"));
                allItems.add(new ItemObject("", url + "rage28.png"));
                break;
            case 2:
                allItems.add(new ItemObject("", url + "ars1.png"));
                allItems.add(new ItemObject("", url + "ars2.png"));
                allItems.add(new ItemObject("", url + "ars3.png"));
                allItems.add(new ItemObject("", url + "ars4.png"));
                allItems.add(new ItemObject("", url + "ars5.png"));
                allItems.add(new ItemObject("", url + "ars6.png"));
                allItems.add(new ItemObject("", url + "ars7.png"));
                allItems.add(new ItemObject("", url + "ars8.png"));
                allItems.add(new ItemObject("", url + "ars9.png"));
                allItems.add(new ItemObject("", url + "ars10.png"));
                allItems.add(new ItemObject("", url + "ars11.png"));
                allItems.add(new ItemObject("", url + "ars12.png"));
                break;
            case 3:
                allItems.add(new ItemObject("", url + "rom1.png"));
                allItems.add(new ItemObject("", url + "rom2.png"));
                allItems.add(new ItemObject("", url + "rom3.png"));
                allItems.add(new ItemObject("", url + "rom4.png"));
                allItems.add(new ItemObject("", url + "rom5.png"));
                allItems.add(new ItemObject("", url + "rom6.png"));
                allItems.add(new ItemObject("", url + "rom7.png"));
                allItems.add(new ItemObject("", url + "rom8.png"));
                allItems.add(new ItemObject("", url + "rom9.png"));
                allItems.add(new ItemObject("", url + "rom10.png"));
                allItems.add(new ItemObject("", url + "rom11.png"));
                allItems.add(new ItemObject("", url + "rom12.png"));
                allItems.add(new ItemObject("", url + "rom13.png"));
                allItems.add(new ItemObject("", url + "rom14.png"));
                allItems.add(new ItemObject("", url + "rom15.png"));
                allItems.add(new ItemObject("", url + "rom16.png"));
                allItems.add(new ItemObject("", url + "rom17.png"));
                allItems.add(new ItemObject("", url + "rom18.png"));
                allItems.add(new ItemObject("", url + "rom19.png"));
                allItems.add(new ItemObject("", url + "rom20.png"));
                break;
            case 4:
                allItems.add(new ItemObject("", url + "sim1.png"));
                allItems.add(new ItemObject("", url + "sim2.png"));
                allItems.add(new ItemObject("", url + "sim3.png"));
                allItems.add(new ItemObject("", url + "sim4.png"));
                allItems.add(new ItemObject("", url + "sim5.png"));
                allItems.add(new ItemObject("", url + "sim6.png"));
                allItems.add(new ItemObject("", url + "sim7.png"));
                allItems.add(new ItemObject("", url + "sim8.png"));
                allItems.add(new ItemObject("", url + "sim9.png"));
                allItems.add(new ItemObject("", url + "sim10.png"));
                allItems.add(new ItemObject("", url + "sim11.png"));
                allItems.add(new ItemObject("", url + "sim12.png"));
                break;
            case 5:
                allItems.add(new ItemObject("", url + "anim1.png"));
                allItems.add(new ItemObject("", url + "anim2.png"));
                allItems.add(new ItemObject("", url + "anim3.png"));
                allItems.add(new ItemObject("", url + "anim4.png"));
                allItems.add(new ItemObject("", url + "anim5.png"));
                allItems.add(new ItemObject("", url + "anim6.png"));
                allItems.add(new ItemObject("", url + "anim7.png"));
                allItems.add(new ItemObject("", url + "anim8.png"));
                allItems.add(new ItemObject("", url + "anim9.png"));
                allItems.add(new ItemObject("", url + "anim10.png"));
                allItems.add(new ItemObject("", url + "anim11.png"));
                allItems.add(new ItemObject("", url + "anim12.png"));
                allItems.add(new ItemObject("", url + "anim13.png"));
                allItems.add(new ItemObject("", url + "anim14.png"));
                allItems.add(new ItemObject("", url + "anim15.png"));
                allItems.add(new ItemObject("", url + "anim16.png"));
                allItems.add(new ItemObject("", url + "anim17.png"));
                allItems.add(new ItemObject("", url + "anim18.png"));
                allItems.add(new ItemObject("", url + "anim19.png"));
                allItems.add(new ItemObject("", url + "anim20.png"));
                allItems.add(new ItemObject("", url + "anim21.png"));
                allItems.add(new ItemObject("", url + "anim22.png"));
                allItems.add(new ItemObject("", url + "anim23.png"));
                allItems.add(new ItemObject("", url + "anim24.png"));
                allItems.add(new ItemObject("", url + "anim25.png"));
                allItems.add(new ItemObject("", url + "anim26.png"));
                allItems.add(new ItemObject("", url + "anim27.png"));
                allItems.add(new ItemObject("", url + "anim28.png"));
                allItems.add(new ItemObject("", url + "anim29.png"));
                allItems.add(new ItemObject("", url + "anim30.png"));
                allItems.add(new ItemObject("", url + "anim31.png"));
                allItems.add(new ItemObject("", url + "anim32.png"));
                allItems.add(new ItemObject("", url + "anim33.png"));
                allItems.add(new ItemObject("", url + "anim34.png"));
                allItems.add(new ItemObject("", url + "anim35.png"));
                break;
            case 6:
                allItems.add(new ItemObject("", url + "sen1.png"));
                allItems.add(new ItemObject("", url + "sen2.png"));
                allItems.add(new ItemObject("", url + "sen3.png"));
                allItems.add(new ItemObject("", url + "sen4.png"));
                allItems.add(new ItemObject("", url + "sen5.png"));
                allItems.add(new ItemObject("", url + "sen6.png"));
                allItems.add(new ItemObject("", url + "sen7.png"));
                allItems.add(new ItemObject("", url + "sen8.png"));
                allItems.add(new ItemObject("", url + "sen9.png"));
                allItems.add(new ItemObject("", url + "sen10.png"));
                allItems.add(new ItemObject("", url + "sen11.png"));
                allItems.add(new ItemObject("", url + "sen12.png"));
                allItems.add(new ItemObject("", url + "sen13.png"));
                allItems.add(new ItemObject("", url + "sen14.png"));
                allItems.add(new ItemObject("", url + "sen15.png"));
                allItems.add(new ItemObject("", url + "sen16.png"));
                allItems.add(new ItemObject("", url + "sen17.png"));
                allItems.add(new ItemObject("", url + "sen18.png"));
                allItems.add(new ItemObject("", url + "sen19.png"));
                allItems.add(new ItemObject("", url + "sen20.png"));
                allItems.add(new ItemObject("", url + "sen21.png"));
                allItems.add(new ItemObject("", url + "sen22.png"));
                allItems.add(new ItemObject("", url + "sen23.png"));
                allItems.add(new ItemObject("", url + "sen24.png"));
                allItems.add(new ItemObject("", url + "sen25.png"));
                allItems.add(new ItemObject("", url + "sen26.png"));
                allItems.add(new ItemObject("", url + "sen27.png"));
                allItems.add(new ItemObject("", url + "sen28.png"));
                allItems.add(new ItemObject("", url + "sen29.png"));
                allItems.add(new ItemObject("", url + "sen30.png"));
                allItems.add(new ItemObject("", url + "sen31.png"));
                allItems.add(new ItemObject("", url + "sen32.png"));
                allItems.add(new ItemObject("", url + "sen33.png"));
                allItems.add(new ItemObject("", url + "sen34.png"));
                allItems.add(new ItemObject("", url + "sen35.png"));
                break;
            case 7:
                allItems.add(new ItemObject("", url + "man1.png"));
                allItems.add(new ItemObject("", url + "man2.png"));
                allItems.add(new ItemObject("", url + "man3.png"));
                allItems.add(new ItemObject("", url + "man4.png"));
                allItems.add(new ItemObject("", url + "man5.png"));
                allItems.add(new ItemObject("", url + "man6.png"));
                allItems.add(new ItemObject("", url + "man7.png"));
                allItems.add(new ItemObject("", url + "man8.png"));
                allItems.add(new ItemObject("", url + "man9.png"));
                allItems.add(new ItemObject("", url + "man10.png"));
                allItems.add(new ItemObject("", url + "man11.png"));
                allItems.add(new ItemObject("", url + "man12.png"));
                allItems.add(new ItemObject("", url + "man13.png"));
                allItems.add(new ItemObject("", url + "man14.png"));
                allItems.add(new ItemObject("", url + "man15.png"));
                allItems.add(new ItemObject("", url + "man16.png"));
                allItems.add(new ItemObject("", url + "man17.png"));
                allItems.add(new ItemObject("", url + "man18.png"));
                allItems.add(new ItemObject("", url + "man19.png"));
                allItems.add(new ItemObject("", url + "man20.png"));
                allItems.add(new ItemObject("", url + "man21.png"));
                allItems.add(new ItemObject("", url + "man22.png"));
                allItems.add(new ItemObject("", url + "man23.png"));
                allItems.add(new ItemObject("", url + "man24.png"));
                allItems.add(new ItemObject("", url + "man25.png"));
                allItems.add(new ItemObject("", url + "man26.png"));
                allItems.add(new ItemObject("", url + "man27.png"));
                allItems.add(new ItemObject("", url + "man28.png"));
                allItems.add(new ItemObject("", url + "man29.png"));
                allItems.add(new ItemObject("", url + "man30.png"));
                break;
            case 8:
                allItems.add(new ItemObject("", url + "d8.png"));
                allItems.add(new ItemObject("", url + "d9.png"));
                allItems.add(new ItemObject("", url + "d10.png"));
                allItems.add(new ItemObject("", url + "d11.png"));
                allItems.add(new ItemObject("", url + "d12.png"));
                allItems.add(new ItemObject("", url + "d13.png"));
                allItems.add(new ItemObject("", url + "d14.png"));
                allItems.add(new ItemObject("", url + "d1.png"));
                allItems.add(new ItemObject("", url + "d2.png"));
                allItems.add(new ItemObject("", url + "d3.png"));
                allItems.add(new ItemObject("", url + "d4.png"));
                allItems.add(new ItemObject("", url + "d5.png"));
                allItems.add(new ItemObject("", url + "d6.png"));
                allItems.add(new ItemObject("", url + "d7.png"));
                allItems.add(new ItemObject("", url + "d15.png"));
                allItems.add(new ItemObject("", url + "d16.png"));
                allItems.add(new ItemObject("", url + "d17.png"));
                allItems.add(new ItemObject("", url + "d18.png"));
                allItems.add(new ItemObject("", url + "d19.png"));
                allItems.add(new ItemObject("", url + "d20.png"));
                allItems.add(new ItemObject("", url + "d21.png"));
                allItems.add(new ItemObject("", url + "d22.png"));
                break;
            case 9:
                allItems.add(new ItemObject("", url + "wh1.png"));
                allItems.add(new ItemObject("", url + "wh2.png"));
                allItems.add(new ItemObject("", url + "wh3.png"));
                allItems.add(new ItemObject("", url + "wh4.png"));
                allItems.add(new ItemObject("", url + "wh5.png"));
                allItems.add(new ItemObject("", url + "wh6.png"));
                allItems.add(new ItemObject("", url + "wh7.png"));
                allItems.add(new ItemObject("", url + "wh8.png"));
                allItems.add(new ItemObject("", url + "wh9.png"));
                allItems.add(new ItemObject("", url + "wh10.png"));
                allItems.add(new ItemObject("", url + "wh11.png"));
                allItems.add(new ItemObject("", url + "wh12.png"));
                allItems.add(new ItemObject("", url + "wh13.png"));
                allItems.add(new ItemObject("", url + "wh14.png"));
                allItems.add(new ItemObject("", url + "wh15.png"));
                break;
            case 10:
                allItems.add(new ItemObject("", url + "cyan1.png"));
                allItems.add(new ItemObject("", url + "cyan2.png"));
                allItems.add(new ItemObject("", url + "cyan3.png"));
                allItems.add(new ItemObject("", url + "cyan4.png"));
                allItems.add(new ItemObject("", url + "cyan5.png"));
                allItems.add(new ItemObject("", url + "cyan6.png"));
                allItems.add(new ItemObject("", url + "cyan7.png"));
                allItems.add(new ItemObject("", url + "cyan8.png"));
                allItems.add(new ItemObject("", url + "cyan9.png"));
                allItems.add(new ItemObject("", url + "cyan10.png"));
                allItems.add(new ItemObject("", url + "cyan11.png"));
                allItems.add(new ItemObject("", url + "cyan12.png"));
                allItems.add(new ItemObject("", url + "cyan13.png"));
                allItems.add(new ItemObject("", url + "cyan14.png"));
                break;
            case 11:
                allItems.add(new ItemObject("", url + "face1.png"));
                allItems.add(new ItemObject("", url + "face2.png"));
                allItems.add(new ItemObject("", url + "face3.png"));
                allItems.add(new ItemObject("", url + "face4.png"));
                allItems.add(new ItemObject("", url + "face5.png"));
                allItems.add(new ItemObject("", url + "face6.png"));
                allItems.add(new ItemObject("", url + "face7.png"));
                allItems.add(new ItemObject("", url + "face8.png"));
                allItems.add(new ItemObject("", url + "face9.png"));
                allItems.add(new ItemObject("", url + "face10.png"));
                break;
            case 12:
                allItems.add(new ItemObject("", url + "pep1.png"));
                allItems.add(new ItemObject("", url + "pep2.png"));
                allItems.add(new ItemObject("", url + "pep3.png"));
                allItems.add(new ItemObject("", url + "pep4.png"));
                allItems.add(new ItemObject("", url + "pep5.png"));
                allItems.add(new ItemObject("", url + "pep6.png"));
                allItems.add(new ItemObject("", url + "pep7.png"));
                allItems.add(new ItemObject("", url + "pep8.png"));
                allItems.add(new ItemObject("", url + "pep9.png"));
                break;
            case 13:
                allItems.add(new ItemObject("", url + "simp1.png"));
                allItems.add(new ItemObject("", url + "simp2.png"));
                allItems.add(new ItemObject("", url + "simp3.png"));
                allItems.add(new ItemObject("", url + "simp4.png"));
                allItems.add(new ItemObject("", url + "simp5.png"));
                allItems.add(new ItemObject("", url + "simp6.png"));
                allItems.add(new ItemObject("", url + "simp7.png"));
                allItems.add(new ItemObject("", url + "simp8.png"));
                allItems.add(new ItemObject("", url + "simp9.png"));
                allItems.add(new ItemObject("", url + "simp10.png"));
                allItems.add(new ItemObject("", url + "simp11.png"));
                allItems.add(new ItemObject("", url + "simp12.png"));
                allItems.add(new ItemObject("", url + "simp13.png"));
                break;
            case 14:
                allItems.add(new ItemObject("", url + "fut1.png"));
                allItems.add(new ItemObject("", url + "fut2.png"));
                allItems.add(new ItemObject("", url + "fut3.png"));
                allItems.add(new ItemObject("", url + "fut4.png"));
                allItems.add(new ItemObject("", url + "fut5.png"));
                allItems.add(new ItemObject("", url + "fut6.png"));
                allItems.add(new ItemObject("", url + "fut7.png"));
                allItems.add(new ItemObject("", url + "fut8.png"));
                allItems.add(new ItemObject("", url + "fut9.png"));
                allItems.add(new ItemObject("", url + "fut10.png"));
                allItems.add(new ItemObject("", url + "fut11.png"));
                allItems.add(new ItemObject("", url + "fut12.png"));
                allItems.add(new ItemObject("", url + "fut13.png"));
                allItems.add(new ItemObject("", url + "fut14.png"));
                break;
            case 15:
                allItems.add(new ItemObject("", url + "pi1.png"));
                allItems.add(new ItemObject("", url + "pi2.png"));
                allItems.add(new ItemObject("", url + "pi3.png"));
                allItems.add(new ItemObject("", url + "pi4.png"));
                allItems.add(new ItemObject("", url + "pi5.png"));
                allItems.add(new ItemObject("", url + "pi6.png"));
                allItems.add(new ItemObject("", url + "pi7.png"));
                allItems.add(new ItemObject("", url + "pi8.png"));
                allItems.add(new ItemObject("", url + "pi9.png"));
                allItems.add(new ItemObject("", url + "pi10.png"));
                break;
            case 16:
                allItems.add(new ItemObject("", url + "wow1.png"));
                allItems.add(new ItemObject("", url + "wow2.png"));
                allItems.add(new ItemObject("", url + "wow3.png"));
                allItems.add(new ItemObject("", url + "wow4.png"));
                allItems.add(new ItemObject("", url + "wow5.png"));
                allItems.add(new ItemObject("", url + "wow6.png"));
                allItems.add(new ItemObject("", url + "wow7.png"));
                allItems.add(new ItemObject("", url + "wow8.png"));
                allItems.add(new ItemObject("", url + "wow9.png"));
                allItems.add(new ItemObject("", url + "wow10.png"));
                break;
            case 17:
                allItems.add(new ItemObject("", url + "avc1.png"));
                allItems.add(new ItemObject("", url + "avc2.png"));
                allItems.add(new ItemObject("", url + "avc3.png"));
                allItems.add(new ItemObject("", url + "avc4.png"));
                allItems.add(new ItemObject("", url + "avc5.png"));
                allItems.add(new ItemObject("", url + "avc6.png"));
                allItems.add(new ItemObject("", url + "avc7.png"));
                allItems.add(new ItemObject("", url + "avc8.png"));
                allItems.add(new ItemObject("", url + "avc9.png"));
                allItems.add(new ItemObject("", url + "avc10.png"));
                allItems.add(new ItemObject("", url + "avc11.png"));
                allItems.add(new ItemObject("", url + "avc12.png"));
                allItems.add(new ItemObject("", url + "avc13.png"));
                allItems.add(new ItemObject("", url + "avc14.png"));
                allItems.add(new ItemObject("", url + "avc15.png"));
                allItems.add(new ItemObject("", url + "avc16.png"));
                allItems.add(new ItemObject("", url + "avc17.png"));
                allItems.add(new ItemObject("", url + "avc18.png"));
                break;
            case 18:
                allItems.add(new ItemObject("", url + "bor1.png"));
                allItems.add(new ItemObject("", url + "bor2.png"));
                allItems.add(new ItemObject("", url + "bor3.png"));
                allItems.add(new ItemObject("", url + "bor6.png"));
                allItems.add(new ItemObject("", url + "bor7.png"));
                allItems.add(new ItemObject("", url + "bor8.png"));
                allItems.add(new ItemObject("", url + "bor9.png"));
                allItems.add(new ItemObject("", url + "bor10.png"));
                allItems.add(new ItemObject("", url + "bor12.png"));
                allItems.add(new ItemObject("", url + "bor13.png"));
                allItems.add(new ItemObject("", url + "bor20.png"));
                break;
            case 19:
                allItems.add(new ItemObject("", url + "wwf1.png"));
                allItems.add(new ItemObject("", url + "wwf2.png"));
                allItems.add(new ItemObject("", url + "wwf3.png"));
                allItems.add(new ItemObject("", url + "wwf4.png"));
                allItems.add(new ItemObject("", url + "wwf5.png"));
                allItems.add(new ItemObject("", url + "wwf6.png"));
                allItems.add(new ItemObject("", url + "wwf7.png"));
                allItems.add(new ItemObject("", url + "wwf8.png"));
                allItems.add(new ItemObject("", url + "wwf9.png"));
                allItems.add(new ItemObject("", url + "wwf10.png"));
                allItems.add(new ItemObject("", url + "wwf11.png"));
                allItems.add(new ItemObject("", url + "wwf12.png"));
                allItems.add(new ItemObject("", url + "wwf13.png"));
                allItems.add(new ItemObject("", url + "wwf14.png"));
                allItems.add(new ItemObject("", url + "wwf15.png"));
                allItems.add(new ItemObject("", url + "wwf16.png"));
                break;
            case 20:
                allItems.add(new ItemObject("", url + "emo1.png"));
                allItems.add(new ItemObject("", url + "emo2.png"));
                allItems.add(new ItemObject("", url + "emo3.png"));
                allItems.add(new ItemObject("", url + "emo4.png"));
                allItems.add(new ItemObject("", url + "emo5.png"));
                allItems.add(new ItemObject("", url + "emo6.png"));
                allItems.add(new ItemObject("", url + "emo7.png"));
                allItems.add(new ItemObject("", url + "emo8.png"));
                allItems.add(new ItemObject("", url + "emo9.png"));
                allItems.add(new ItemObject("", url + "emo10.png"));
                allItems.add(new ItemObject("", url + "emo11.png"));
                allItems.add(new ItemObject("", url + "emo12.png"));
                allItems.add(new ItemObject("", url + "emo13.png"));
                allItems.add(new ItemObject("", url + "emo14.png"));
                allItems.add(new ItemObject("", url + "emo15.png"));
                allItems.add(new ItemObject("", url + "emo16.png"));
                allItems.add(new ItemObject("", url + "emo17.png"));
                allItems.add(new ItemObject("", url + "emo18.png"));
                allItems.add(new ItemObject("", url + "emo19.png"));
                break;
            case 21:
                allItems.add(new ItemObject("", url + "gad1.png"));
                allItems.add(new ItemObject("", url + "gad2.png"));
                allItems.add(new ItemObject("", url + "gad3.png"));
                allItems.add(new ItemObject("", url + "gad4.png"));
                allItems.add(new ItemObject("", url + "gad5.png"));
                allItems.add(new ItemObject("", url + "gad6.png"));
                allItems.add(new ItemObject("", url + "gad7.png"));
                allItems.add(new ItemObject("", url + "gad8.png"));
                allItems.add(new ItemObject("", url + "gad9.png"));
                allItems.add(new ItemObject("", url + "gad10.png"));
                break;
            case 22:
                allItems.add(new ItemObject("", url + "goo1.png"));
                allItems.add(new ItemObject("", url + "goo2.png"));
                allItems.add(new ItemObject("", url + "goo3.png"));
                allItems.add(new ItemObject("", url + "goo4.png"));
                allItems.add(new ItemObject("", url + "goo5.png"));
                allItems.add(new ItemObject("", url + "goo6.png"));
                allItems.add(new ItemObject("", url + "goo7.png"));
                allItems.add(new ItemObject("", url + "goo8.png"));
                allItems.add(new ItemObject("", url + "goo9.png"));
                allItems.add(new ItemObject("", url + "goo10.png"));
                allItems.add(new ItemObject("", url + "goo11.png"));
                allItems.add(new ItemObject("", url + "goo12.png"));
                break;
            case 23:
                allItems.add(new ItemObject("", url + "har1.png"));
                allItems.add(new ItemObject("", url + "har2.png"));
                allItems.add(new ItemObject("", url + "har3.png"));
                allItems.add(new ItemObject("", url + "har4.png"));
                allItems.add(new ItemObject("", url + "har5.png"));
                allItems.add(new ItemObject("", url + "har6.png"));
                allItems.add(new ItemObject("", url + "har7.png"));
                allItems.add(new ItemObject("", url + "har8.png"));
                break;
            case 24:
                allItems.add(new ItemObject("", url + "rr1.png"));
                allItems.add(new ItemObject("", url + "rr2.png"));
                allItems.add(new ItemObject("", url + "rr3.png"));
                allItems.add(new ItemObject("", url + "rr4.png"));
                allItems.add(new ItemObject("", url + "rr5.png"));
                allItems.add(new ItemObject("", url + "rr6.png"));
                allItems.add(new ItemObject("", url + "rr7.png"));
                allItems.add(new ItemObject("", url + "rr8.png"));
                allItems.add(new ItemObject("", url + "rr9.png"));
                allItems.add(new ItemObject("", url + "rr10.png"));
                allItems.add(new ItemObject("", url + "rr11.png"));
                allItems.add(new ItemObject("", url + "rr12.png"));
                allItems.add(new ItemObject("", url + "rr13.png"));
                allItems.add(new ItemObject("", url + "rr14.png"));
                allItems.add(new ItemObject("", url + "rr15.png"));
                allItems.add(new ItemObject("", url + "rr16.png"));
                allItems.add(new ItemObject("", url + "rr17.png"));
                break;
            case 25:
                allItems.add(new ItemObject("", url + "be1.png"));
                allItems.add(new ItemObject("", url + "be2.png"));
                allItems.add(new ItemObject("", url + "be3.png"));
                allItems.add(new ItemObject("", url + "be4.png"));
                allItems.add(new ItemObject("", url + "be5.png"));
                allItems.add(new ItemObject("", url + "be6.png"));
                allItems.add(new ItemObject("", url + "be7.png"));
                allItems.add(new ItemObject("", url + "be8.png"));
                break;
            case 26:
                allItems.add(new ItemObject("", url + "adv1.png"));
                allItems.add(new ItemObject("", url + "adv2.png"));
                allItems.add(new ItemObject("", url + "adv3.png"));
                allItems.add(new ItemObject("", url + "adv4.png"));
                allItems.add(new ItemObject("", url + "adv5.png"));
                allItems.add(new ItemObject("", url + "adv6.png"));
                allItems.add(new ItemObject("", url + "adv7.png"));
                allItems.add(new ItemObject("", url + "adv8.png"));
                allItems.add(new ItemObject("", url + "adv9.png"));
                allItems.add(new ItemObject("", url + "adv10.png"));
                allItems.add(new ItemObject("", url + "adv11.png"));
                allItems.add(new ItemObject("", url + "adv12.png"));
                break;
            case 27:
                allItems.add(new ItemObject("", url + "coy1.png"));
                allItems.add(new ItemObject("", url + "coy2.png"));
                allItems.add(new ItemObject("", url + "coy3.png"));
                allItems.add(new ItemObject("", url + "coy4.png"));
                allItems.add(new ItemObject("", url + "coy5.png"));
                allItems.add(new ItemObject("", url + "coy6.png"));
                allItems.add(new ItemObject("", url + "coy7.png"));
                allItems.add(new ItemObject("", url + "coy8.png"));
                allItems.add(new ItemObject("", url + "coy9.png"));
                allItems.add(new ItemObject("", url + "coy10.png"));
                break;
            case 28:
                allItems.add(new ItemObject("", url + "fox1.png"));
                allItems.add(new ItemObject("", url + "fox2.png"));
                allItems.add(new ItemObject("", url + "fox3.png"));
                allItems.add(new ItemObject("", url + "fox4.png"));
                allItems.add(new ItemObject("", url + "fox5.png"));
                allItems.add(new ItemObject("", url + "fox6.png"));
                allItems.add(new ItemObject("", url + "fox7.png"));
                allItems.add(new ItemObject("", url + "fox8.png"));
                allItems.add(new ItemObject("", url + "fox9.png"));
                allItems.add(new ItemObject("", url + "fox10.png"));
                break;
            case 29:
                allItems.add(new ItemObject("", url + "pea1.png"));
                allItems.add(new ItemObject("", url + "pea2.png"));
                allItems.add(new ItemObject("", url + "pea3.png"));
                allItems.add(new ItemObject("", url + "pea4.png"));
                allItems.add(new ItemObject("", url + "pea5.png"));
                allItems.add(new ItemObject("", url + "pea6.png"));
                allItems.add(new ItemObject("", url + "pea7.png"));
                allItems.add(new ItemObject("", url + "pea8.png"));
                allItems.add(new ItemObject("", url + "pea9.png"));
                allItems.add(new ItemObject("", url + "pea10.png"));
                allItems.add(new ItemObject("", url + "pea11.png"));
                allItems.add(new ItemObject("", url + "pea12.png"));
                allItems.add(new ItemObject("", url + "pea13.png"));
                allItems.add(new ItemObject("", url + "pea14.png"));
                allItems.add(new ItemObject("", url + "pea15.png"));
                allItems.add(new ItemObject("", url + "pea16.png"));
                allItems.add(new ItemObject("", url + "pea17.png"));
                allItems.add(new ItemObject("", url + "pea18.png"));
                allItems.add(new ItemObject("", url + "pea19.png"));
                allItems.add(new ItemObject("", url + "pea20.png"));
                allItems.add(new ItemObject("", url + "pea21.png"));
                allItems.add(new ItemObject("", url + "pea22.png"));
                allItems.add(new ItemObject("", url + "pea23.png"));
                allItems.add(new ItemObject("", url + "pea24.png"));
                allItems.add(new ItemObject("", url + "pea25.png"));
                break;
            case 30:
                allItems.add(new ItemObject("", url + "fro1.png"));
                allItems.add(new ItemObject("", url + "fro2.png"));
                allItems.add(new ItemObject("", url + "fro3.png"));
                allItems.add(new ItemObject("", url + "fro4.png"));
                allItems.add(new ItemObject("", url + "fro5.png"));
                allItems.add(new ItemObject("", url + "fro6.png"));
                allItems.add(new ItemObject("", url + "fro7.png"));
                allItems.add(new ItemObject("", url + "fro8.png"));
                allItems.add(new ItemObject("", url + "fro9.png"));
                allItems.add(new ItemObject("", url + "fro10.png"));
                allItems.add(new ItemObject("", url + "fro11.png"));
                allItems.add(new ItemObject("", url + "fro12.png"));
                break;
            case 31:
                allItems.add(new ItemObject("", url + "smi1.png"));
                allItems.add(new ItemObject("", url + "smi2.png"));
                allItems.add(new ItemObject("", url + "smi3.png"));
                allItems.add(new ItemObject("", url + "smi4.png"));
                allItems.add(new ItemObject("", url + "smi5.png"));
                allItems.add(new ItemObject("", url + "smi6.png"));
                allItems.add(new ItemObject("", url + "smi7.png"));
                allItems.add(new ItemObject("", url + "smi8.png"));
                allItems.add(new ItemObject("", url + "smi9.png"));
                allItems.add(new ItemObject("", url + "smi10.png"));
                allItems.add(new ItemObject("", url + "smi11.png"));
                allItems.add(new ItemObject("", url + "smi12.png"));
                allItems.add(new ItemObject("", url + "smi13.png"));
                allItems.add(new ItemObject("", url + "smi14.png"));
                allItems.add(new ItemObject("", url + "smi15.png"));
                allItems.add(new ItemObject("", url + "smi16.png"));
                allItems.add(new ItemObject("", url + "smi17.png"));
                allItems.add(new ItemObject("", url + "smi18.png"));
                allItems.add(new ItemObject("", url + "smi19.png"));
                allItems.add(new ItemObject("", url + "smi20.png"));
                allItems.add(new ItemObject("", url + "smi21.png"));
                allItems.add(new ItemObject("", url + "smi22.png"));
                allItems.add(new ItemObject("", url + "smi23.png"));
                allItems.add(new ItemObject("", url + "smi24.png"));
                allItems.add(new ItemObject("", url + "smi25.png"));
                allItems.add(new ItemObject("", url + "smi26.png"));
                break;
            case 32:
                allItems.add(new ItemObject("", url + "spa1.png"));
                allItems.add(new ItemObject("", url + "spa2.png"));
                allItems.add(new ItemObject("", url + "spa3.png"));
                allItems.add(new ItemObject("", url + "spa4.png"));
                allItems.add(new ItemObject("", url + "spa5.png"));
                allItems.add(new ItemObject("", url + "spa6.png"));
                allItems.add(new ItemObject("", url + "spa7.png"));
                allItems.add(new ItemObject("", url + "spa8.png"));
                allItems.add(new ItemObject("", url + "spa9.png"));
                allItems.add(new ItemObject("", url + "spa10.png"));
                break;
            case 33:
                allItems.add(new ItemObject("", url + "ice1.png"));
                allItems.add(new ItemObject("", url + "ice2.png"));
                allItems.add(new ItemObject("", url + "ice3.png"));
                allItems.add(new ItemObject("", url + "ice4.png"));
                allItems.add(new ItemObject("", url + "ice5.png"));
                allItems.add(new ItemObject("", url + "ice6.png"));
                allItems.add(new ItemObject("", url + "ice7.png"));
                allItems.add(new ItemObject("", url + "ice8.png"));
                allItems.add(new ItemObject("", url + "ice9.png"));
                allItems.add(new ItemObject("", url + "ice10.png"));
                allItems.add(new ItemObject("", url + "ice11.png"));
                allItems.add(new ItemObject("", url + "ice12.png"));
                allItems.add(new ItemObject("", url + "ice13.png"));
                allItems.add(new ItemObject("", url + "ice14.png"));
                allItems.add(new ItemObject("", url + "ice15.png"));
                allItems.add(new ItemObject("", url + "ice16.png"));
                break;
            case 34:
                allItems.add(new ItemObject("", url + "pool1.png"));
                allItems.add(new ItemObject("", url + "pool2.png"));
                allItems.add(new ItemObject("", url + "pool3.png"));
                allItems.add(new ItemObject("", url + "pool4.png"));
                allItems.add(new ItemObject("", url + "pool5.png"));
                allItems.add(new ItemObject("", url + "pool6.png"));
                allItems.add(new ItemObject("", url + "pool7.png"));
                allItems.add(new ItemObject("", url + "pool8.png"));
                allItems.add(new ItemObject("", url + "pool9.png"));
                allItems.add(new ItemObject("", url + "pool10.png"));
                allItems.add(new ItemObject("", url + "pool11.png"));
                break;
            case 35:
                allItems.add(new ItemObject("", url + "gar1.png"));
                allItems.add(new ItemObject("", url + "gar2.png"));
                allItems.add(new ItemObject("", url + "gar3.png"));
                allItems.add(new ItemObject("", url + "gar4.png"));
                allItems.add(new ItemObject("", url + "gar5.png"));
                allItems.add(new ItemObject("", url + "gar6.png"));
                allItems.add(new ItemObject("", url + "gar7.png"));
                allItems.add(new ItemObject("", url + "gar8.png"));
                allItems.add(new ItemObject("", url + "gar9.png"));
                allItems.add(new ItemObject("", url + "gar10.png"));
                break;
            case 36:
                allItems.add(new ItemObject("", url + "cut1.png"));
                allItems.add(new ItemObject("", url + "cut2.png"));
                allItems.add(new ItemObject("", url + "cut3.png"));
                allItems.add(new ItemObject("", url + "cut4.png"));
                allItems.add(new ItemObject("", url + "cut5.png"));
                allItems.add(new ItemObject("", url + "cut6.png"));
                allItems.add(new ItemObject("", url + "cut7.png"));
                allItems.add(new ItemObject("", url + "cut8.png"));
                allItems.add(new ItemObject("", url + "cut9.png"));
                allItems.add(new ItemObject("", url + "cut10.png"));
                allItems.add(new ItemObject("", url + "cut11.png"));
                allItems.add(new ItemObject("", url + "cut12.png"));
                break;
            case 37:
                allItems.add(new ItemObject("", url + "hick1.png"));
                allItems.add(new ItemObject("", url + "hick2.png"));
                allItems.add(new ItemObject("", url + "hick3.png"));
                allItems.add(new ItemObject("", url + "hick4.png"));
                allItems.add(new ItemObject("", url + "hick5.png"));
                allItems.add(new ItemObject("", url + "hick6.png"));
                allItems.add(new ItemObject("", url + "hick7.png"));
                allItems.add(new ItemObject("", url + "hick8.png"));
                allItems.add(new ItemObject("", url + "hick9.png"));
                allItems.add(new ItemObject("", url + "hick10.png"));
                allItems.add(new ItemObject("", url + "hick11.png"));
                allItems.add(new ItemObject("", url + "hick12.png"));
                allItems.add(new ItemObject("", url + "hick13.png"));
                allItems.add(new ItemObject("", url + "hick14.png"));
                allItems.add(new ItemObject("", url + "hick15.png"));
                allItems.add(new ItemObject("", url + "hick16.png"));
                allItems.add(new ItemObject("", url + "hick17.png"));
                allItems.add(new ItemObject("", url + "hick18.png"));
                allItems.add(new ItemObject("", url + "hick19.png"));
                allItems.add(new ItemObject("", url + "hick20.png"));
                allItems.add(new ItemObject("", url + "hick21.png"));
                allItems.add(new ItemObject("", url + "hick22.png"));
                allItems.add(new ItemObject("", url + "hick23.png"));
                allItems.add(new ItemObject("", url + "hick24.png"));
                break;
        }
        return allItems;
    }

}
