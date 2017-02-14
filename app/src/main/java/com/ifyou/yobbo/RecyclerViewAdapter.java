package com.ifyou.yobbo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;
    OnItemClickListener mItemClickListener;
    private int lastPosition = -1;
    boolean anim;

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            /*Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);*/
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }

    private int getColorWithAplha(int color, float ratio) {
        int transColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        transColor = Color.argb(alpha, r, g, b);
        return transColor;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, int position) {

        holder.countryName.setText(itemList.get(position).getName());
        if (itemList.get(position).getName().length() < 2)
            holder.titleBg.setVisibility(View.INVISIBLE);

        holder.pb.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(itemList.get(position).getPhoto())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //.placeholder(R.drawable.place)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.pb.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(new BitmapImageViewTarget(holder.countryPhoto) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        Palette.Swatch wallSwatch = getProminentSwatch(resource, false);

                        int opaqueDarkVibrantColor = wallSwatch != null ? wallSwatch.getRgb() : 0;
                        int transparentRGBInt = getColorWithAplha(opaqueDarkVibrantColor, 0.8f);

                        holder.titleBg.setBackgroundColor(transparentRGBInt);
                        //holder.titleBg.setBackgroundColor(wallSwatch.getRgb());
                        //holder.countryName.setTypeface(holder.countryName.getTypeface(), Typeface.BOLD);
                        if (wallSwatch != null)
                            holder.countryName.setTextColor(wallSwatch.getTitleTextColor());

                        TransitionDrawable td = new TransitionDrawable(new Drawable[]{new ColorDrawable(Color.TRANSPARENT), new BitmapDrawable(context.getResources(), resource)});
                        holder.countryPhoto.setImageDrawable(td);
                        td.startTransition(250);
                    }
                });
        Preferences mPrefs;
        mPrefs = new Preferences(context);
        anim = mPrefs.getAnimEnabled();
        if (anim)
            setAnimation(holder.container, position);
    }

    private Palette.Swatch getProminentSwatch(Bitmap bitmap, boolean forIcons) {
        Palette palette = Palette.from(bitmap).generate();
        return getProminentSwatch(palette, forIcons);
    }

    private Palette.Swatch getProminentSwatch(Palette palette, boolean forIcons) {
        if (palette == null) return null;
        List<Palette.Swatch> swatches = getSwatchesList(palette, forIcons);
        return Collections.max(swatches,
                new Comparator<Palette.Swatch>() {
                    @Override
                    public int compare(Palette.Swatch opt1, Palette.Swatch opt2) {
                        int a = opt1 == null ? 0 : opt1.getPopulation();
                        int b = opt2 == null ? 0 : opt2.getPopulation();
                        return a - b;
                    }
                });
    }

    private List<Palette.Swatch> getSwatchesList(Palette palette, boolean forIcons) {
        List<Palette.Swatch> swatches = new ArrayList<>();
        Palette.Swatch vib = palette.getVibrantSwatch();
        Palette.Swatch vibLight = palette.getLightVibrantSwatch();
        Palette.Swatch vibDark = palette.getDarkVibrantSwatch();
        Palette.Swatch muted = palette.getMutedSwatch();
        Palette.Swatch mutedLight = palette.getLightMutedSwatch();
        Palette.Swatch mutedDark = palette.getDarkMutedSwatch();
        swatches.add(vib);
        swatches.add(vibLight);
        swatches.add(vibDark);
        swatches.add(muted);
        swatches.add(mutedLight);
        swatches.add(mutedDark);
        return swatches;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerViewHolders holder) {
        super.onViewDetachedFromWindow(holder);
        ((RecyclerViewHolders) holder).clearAnimation();
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView countryName;
        public ImageView countryPhoto;
        public final LinearLayout titleBg;
        public FrameLayout container;
        public ProgressBar pb;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            container = (FrameLayout) itemView.findViewById(R.id.wall_frame_layout);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
            titleBg = (LinearLayout) itemView.findViewById(R.id.titleBg);
            pb = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

        public void clearAnimation() {
            container.clearAnimation();
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
