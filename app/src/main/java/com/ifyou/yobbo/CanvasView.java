package com.ifyou.yobbo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class CanvasView extends View {

    private Bitmap mBitmap;
    private Bitmap sBitmap;
    private final Context context;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private String textOn;
    private TextPaint fontPaint;
    private TextPaint strokePaint;
    private String typeFont;
    private int fontSize;
    private int fontColor;
    private float scale;
    private StaticLayout sl;
    private StaticLayout s2;
    private boolean stroke;
    private int textHeight;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        textOn = "";
        this.setDrawingCacheEnabled(true);

        fontSize = 100;
        fontColor = Color.BLACK;
        typeFont = "a_SimplerClg.TTF";

        if (Global.img == null) {
            Glide.with(context)
                    .load(Global.path)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.place)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            mBitmap = resource; // Possibly runOnUiThread()
                            Up();
                        }
                    });
            mBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.place);
            Up();
        } else
            clearCanvas();
    }

    public void SetConfig() {
        Preferences mPrefs;
        mPrefs = new Preferences(context);
        stroke = mPrefs.getStrokeEnabled();
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
        fontColor = mPrefs.getColor();
        Resources resources = context.getResources();
        scale = resources.getDisplayMetrics().density;
        fontPaint = new TextPaint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        fontPaint.setAntiAlias(true);
        fontPaint.setColor(Color.TRANSPARENT);
        Typeface chops;
        if (newFont != 6)
            chops = Typeface.createFromAsset(context.getAssets(),
                    typeFont);
        else
            chops = Typeface.DEFAULT;
        fontPaint.setTypeface(chops);
        if (stroke) {
            strokePaint = new TextPaint(fontPaint);
            strokePaint.setStyle(Paint.Style.STROKE);
            if (newSize >= 3)
                strokePaint.setStrokeWidth(4);
            else
                strokePaint.setStrokeWidth(2);
            strokePaint.setStrokeJoin(Paint.Join.ROUND);
            strokePaint.setStrokeMiter(10);
            strokePaint.setStrokeCap(Paint.Cap.ROUND);
            strokePaint.setColor(Color.TRANSPARENT);
            strokePaint.setTextSize(fontSize);
            strokePaint.setTypeface(chops);
        }
        fontPaint.setTextSize(fontSize);
        if (textOn.length() > 0)
            addText(textOn);
        invalidate();
    }

    public void clearCanvas() {
        SetConfig();
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        int s_height = metrics.heightPixels - getStatusBarHeight();
        int s_wight = metrics.widthPixels;
        sBitmap = resize(Global.img, s_wight, s_height);
        sl = null;
        textOn = "";
        mX = s_wight / 2;
        mY = s_height / 2;
        invalidate();
    }

    private void Up() {
        SetConfig();
        mBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        int s_height = metrics.heightPixels - getStatusBarHeight();
        int s_wight = metrics.widthPixels;
        sBitmap = resize(mBitmap, s_wight, s_height);
        mX = s_wight / 2;
        mY = s_height / 2;
        invalidate();
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = result + getResources().getDimensionPixelSize(resourceId);
        }
        result = result + Global.seek;
        return result;
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth;
        int mHeight;
        if (sBitmap != null) {
            mWidth = sBitmap.getWidth();
            mHeight = sBitmap.getHeight();
        } else {
            mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
            mHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textWidth = canvas.getWidth() - (int) (scale);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(sBitmap, 0, 0, null);
        int x = (canvas.getWidth() - textWidth) / 2;
        canvas.save();
        int mmX;
        mmX = x + (textWidth / 2);

        canvas.translate(mX - mmX, mY - (textHeight / 2));
        if (sl != null) {
            sl.draw(canvas);
            if (stroke) {
                s2.draw(canvas);
            }
        }
        canvas.restore();
    }

    public Bitmap get() {
        invalidate();
        Bitmap b = this.getDrawingCache();
        int mWidth = b.getWidth();
        int mHeight = b.getHeight();
        b = resize(b, mWidth / 2, mHeight / 2);
        return b;
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        mX = x;
        mY = y;
    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public void addText(String g) {
        textOn = g;
        fontPaint.setColor(fontColor);
        if (stroke) {
            if (fontColor != Color.parseColor("#ffffff") && fontColor != Color.parseColor("#FFEB3B"))
                strokePaint.setColor(Color.WHITE);
            else
                strokePaint.setColor(Color.BLACK);
        }
        sl = new StaticLayout(textOn, fontPaint, sBitmap.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        if (stroke) {
            s2 = new StaticLayout(textOn, strokePaint, sBitmap.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        }
        textHeight = sl.getHeight();
        invalidate();
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mX = x;
            mY = y;
        }
        invalidate();
    }


    private void upTouch() {
        invalidate();
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}
