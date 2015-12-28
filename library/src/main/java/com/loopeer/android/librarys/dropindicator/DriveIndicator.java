package com.loopeer.android.librarys.dropindicator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangyifa on 2015/12/25.
 */
public class DriveIndicator extends View {
    private ArrayList<ValueAnimator> mAnimators;
    private List<PointF> mPoints;
    private Path mPath = new Path();

    private int mWidth;
    private int mHeight;
    private int mCount = 3;
    private Paint mPaint, mSelectPaint;
    private float radius;

    private int position;
    private float offset;

    private float leftCircleX;
    private float selectCircleX;
    private float rightCircleX;

    public DriveIndicator(Context context) {
        this(context, null);
    }

    public DriveIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DriveIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPoints = new ArrayList<>();
        mAnimators = new ArrayList<>();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff00ffff);

        mSelectPaint = new Paint();
        mSelectPaint.setColor(0xff00ffff);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        selectCircleX = mWidth / 4;
        leftCircleX = mWidth / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOwnPath(canvas);

        for (int i = 0; i < mCount; i++) {
            canvas.drawCircle(getWidth() / (mCount + 1) * (i + 1), getHeight() / 2, radius, mPaint);
        }


        canvas.drawCircle(selectCircleX, mHeight / 2, radius, mSelectPaint);

//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle();
        /*canvas.drawCircle(leftCircleX, mHeight / 2, radius, mPaint);
        canvas.drawCircle(rightCircleX, mHeight / 2, radius, mPaint);*/

    }

    private void drawOwnPath(Canvas canvas) {
        int leftCircleX = (position + 1) * mWidth / 4;
        int leftCircleNextX = leftCircleX + mWidth / 4;
        int leftCircleY = mHeight / 2;
        mPath.reset();
        mPath.moveTo(leftCircleX, leftCircleY + radius);
        mPath.addArc(new RectF(leftCircleX - radius, leftCircleY - radius, leftCircleX + radius, leftCircleY + radius), 90, 180);
        mPath.lineTo(leftCircleNextX, leftCircleY - radius);

        mPath.addArc(new RectF(leftCircleNextX - radius, leftCircleY - radius, leftCircleNextX + radius, leftCircleY + radius), -90, 180);
//        mPath.addArc(new RectF(0, 0, 100, 100), 90, 180);
        mPath.lineTo(leftCircleX, leftCircleY + radius);


        mPath.close();
//        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath, mPaint);
    }



    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getSelectCircleX() {
        return selectCircleX;
    }

    public void setSelectCircleX(float selectCircleX) {
        this.selectCircleX = selectCircleX;
    }

    public float getLeftCircleX() {
        return leftCircleX;
    }

    public void setLeftCircleX(float leftCircleX) {
        this.leftCircleX = leftCircleX;
    }

    public static final Property<DriveIndicator, Float>  SELECT_CIRCLE_X =
            new Property<DriveIndicator, Float>(Float.class, "selectCircleX") {
                @Override
                public Float get(DriveIndicator object) {
                    return object.getLeftCircleX();
                }

                @Override
                public void set(DriveIndicator object, Float value) {
                    object.setSelectCircleX(value);
                }
            };
}
