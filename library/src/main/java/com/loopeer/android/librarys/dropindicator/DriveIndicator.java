package com.loopeer.android.librarys.dropindicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangyifa on 2015/12/25.
 */
public class DriveIndicator extends View {
    private ArrayList<ValueAnimator> mAnimators;
    private List<PointF> mPoints;

    private int mDefaultColor;
    private int mSelectColor;
    private int mWidth;
    private int mHeight;
    private int mCount = 3;
    private Paint mPaint;
    private float mRadius;

    private int position;
    private float offset;

    private float leftCircleX;
    private float rightCircleX;
    private float leftCircleRadius;
    private float rightCircleRadius;

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
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff00ffff);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
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

        for (int i = 0; i < mCount; i++) {
            canvas.drawCircle(getWidth() / (mCount + 1) * (i + 1), getHeight() / 2, mRadius, mPaint);
        }

        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle();
        canvas.drawCircle(leftCircleX, mHeight / 2, mRadius, mPaint);
        canvas.drawCircle(rightCircleX, mHeight / 2, mRadius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getButtonState()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private void createAnimator(final int paramInt) {
        if (mPoints.isEmpty()) {
            return;
        }
        mAnimators.clear();

        int i = Math.min(mCount - 1, paramInt + 1);

        float leftX = mPoints.get(paramInt).x;
        float rightX = mPoints.get(i).x;

        ObjectAnimator leftPointAnimator = ObjectAnimator.ofFloat(this, "rightCircleX", leftX, rightX);
        leftPointAnimator.setDuration(5000L);
        leftPointAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimators.add(leftPointAnimator);

        ObjectAnimator rightPointAnimator = ObjectAnimator.ofFloat(this, "leftCircleX", leftX, rightX);
        rightPointAnimator.setDuration(5000L);
        rightPointAnimator.setInterpolator(new AccelerateInterpolator(1.5F));
        mAnimators.add(rightPointAnimator);


    }

    private void seekAnimator(float offset) {
        for (ValueAnimator animator : mAnimators) {
            animator.setCurrentPlayTime((long) (5000.0F * offset));
        }
        postInvalidate();
    }


    public void setPositionAndOffset(int position, float offset, int pixels) {
        this.position = position;
        this.offset = offset;
        createAnimator(position);
        seekAnimator(offset);
    }

    public int getDefaultColor() {
        return mDefaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        mDefaultColor = defaultColor;
    }

    public int getSelectColor() {
        return mSelectColor;
    }

    public void setSelectColor(int selectColor) {
        mSelectColor = selectColor;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float radius) {
        mRadius = radius;
    }
}
