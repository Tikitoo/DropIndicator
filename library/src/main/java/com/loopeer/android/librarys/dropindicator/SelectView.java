package com.loopeer.android.librarys.dropindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

public class SelectView extends View {
    private Path mPath;
    private int position;
    private int mWidth;
    private int mHeight;
    private int radius;
    private Paint mPaint;
    private float leftCircleX;

    public SelectView(Context context) {
        this(context, null);
    }

    public SelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.select_color));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOwnPath(canvas);
    }

    private void drawOwnPath(Canvas canvas) {
        leftCircleX = (position + 1) * mWidth / 4;
        float leftCircleNextX = leftCircleX + mWidth / 4;
        int leftCircleY = mHeight / 2;

        mPath.reset();
        mPath.moveTo(leftCircleX, leftCircleY + radius);
        mPath.addArc(new RectF(leftCircleX - radius, leftCircleY - radius, leftCircleX + radius, leftCircleY + radius), 90, 180);
        mPath.lineTo(leftCircleNextX, leftCircleY - radius);

        mPath.addArc(new RectF(leftCircleNextX - radius, leftCircleY - radius, leftCircleNextX + radius, leftCircleY + radius), -90, 180);
        mPath.lineTo(leftCircleX, leftCircleY + radius);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getLeftCircleX() {
        return leftCircleX;
    }

    public void setLeftCircleX(float leftCircleX) {
        this.leftCircleX = leftCircleX;
    }

    public static final Property<SelectView, Float> LEFT_CIRCLE_X =
            new Property<SelectView, Float>(Float.class, "leftCircleX") {
                @Override
                public Float get(SelectView object) {
                    return object.getLeftCircleX();
                }

                @Override
                public void set(SelectView object, Float value) {
                    object.setLeftCircleX(value);
                }
            };
}
