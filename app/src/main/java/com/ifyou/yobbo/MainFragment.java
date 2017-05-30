package com.ifyou.yobbo;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    ViewGroup layout;
    Activity context;
    boolean anim;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        GridLayoutManager lLayout;
        GridSpacingItemDecoratio gridSpacing;
        RecyclerFastScroller fastScroller;

        setHasOptionsMenu(true);
        context = getActivity();

        Preferences mPrefs;
        mPrefs = new Preferences(context);
        anim = mPrefs.getAnimEnabled();

        if (layout != null) {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        try {
            layout = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        } catch (InflateException e) {
            // Do nothing
        }

        List<ItemObject> rowListItem = getAllItemList();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            lLayout = new GridLayoutManager(context, 3);
        else
            lLayout = new GridLayoutManager(context, 2);

        RecyclerView rView = (RecyclerView) layout.findViewById(R.id.recycler_view);
        fastScroller = (RecyclerFastScroller) layout.findViewById(R.id.rvFastScroller);

        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        gridSpacing = new GridSpacingItemDecoratio(2,
                getResources().getDimensionPixelSize(R.dimen.lists_padding),
                true);
        rView.addItemDecoration(gridSpacing);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(context, rowListItem);
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
                Intent intent = new Intent(context, StickActivity.class);
                intent.putExtra("key", position);
                if (anim) {
                    ActivityTransitionLauncher.with(context).from(v).launch(intent);
                    context.overridePendingTransition(0, 0);
                } else
                    context.startActivity(intent);
                //context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private List<ItemObject> getAllItemList() {
        String url = "https://raw.githubusercontent.com/ifyousleep/stick/master/";
        List<ItemObject> allItems = new ArrayList<>();
        allItems.add(new ItemObject(getResources().getString(R.string.pusheen), url + "push.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.rage), url + "rage.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.ars), url + "ars.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.roman), url + "rom1.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.simon), url + "sim.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.animal), url + "anim.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.senya), url + "sen.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.manul), url + "man.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.dib), url + "dib.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.whoiam), url + "wh.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.cyan), url + "cyan.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.face), url + "face.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.peppa), url + "pep.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.simp), url + "simp.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.futur), url + "fut.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.pigeons), url + "pi.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.wow), url + "wow.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.avc), url + "avc.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.boroda), url + "bor.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.wwf), url + "wwf.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.emo), url + "emo.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.gad), url + "gad.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.goo), url + "goo.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.har), url + "har.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.rr), url + "rr.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.be), url + "be1.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.adv), url + "adv.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.coy), url + "coy.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.fox), url + "fox.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.pea), url + "pea.jpg"));
        allItems.add(new ItemObject(getResources().getString(R.string.fro), url + "fro.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.smi), url + "smi.jpg"));
        allItems.add(new ItemObject(getResources().getString(R.string.spa), url + "spa.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.ice), url + "ice.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.pool), url + "pool.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.gar), url + "gar.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.cut), url + "cut.png"));
        allItems.add(new ItemObject(getResources().getString(R.string.hick), url + "hick.png"));
        return allItems;
    }
}
