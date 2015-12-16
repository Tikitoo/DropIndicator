package com.shadev.pierrebeziercircle.me;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class DropIndicator extends View {

    private ArrayList<ValueAnimator> mAnimators;


    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    private float leftCircleX;
    private float leftCircleRadius;

    private float rightCircleX;
    private float rightCircleRadius;

    private List<PointF> mPoints;
    private int mPagerCount;


    private float mMaxCircleRadius;
    private float mMinCircleRadius;

    private List<Integer> mColors;


    private int mMode = MODE_NORMAL;

    public static final int MODE_NORMAL = 1;
    public static final int MODE_BEND = 2;

    public DropIndicator(Context context) {
        this(context, null);
    }

    public DropIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mAnimators = new ArrayList<ValueAnimator>();
        mPoints = new ArrayList<PointF>();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(leftCircleX, mHeight / 2, leftCircleRadius, mPaint);
        canvas.drawCircle(rightCircleX, mHeight / 2, rightCircleRadius, mPaint);
//        canvas.drawCircle(10, 10, 10, mPaint);
//        canvas.drawCircle(50, 50, 10, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mMaxCircleRadius = 0.45F * mHeight;
        mMinCircleRadius = 0.15F * mHeight;

        resetPoint();
    }

    public void setPositionAndOffset(int position, float offSet) {
        createAnimator(position);
        seekAnimator(offSet);

    }

    private void createAnimator(int paramInt) {
        if (mPoints.isEmpty()) {
            return;
        }

        mAnimators.clear();

        int i = Math.min(mPagerCount - 1, paramInt + 1);
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

        ObjectAnimator leftCircleRadiusAnimator = ObjectAnimator.ofFloat(this, "rightCircleRadius", mMinCircleRadius, mMaxCircleRadius);
        leftCircleRadiusAnimator.setDuration(5000L);
        leftCircleRadiusAnimator.setInterpolator(new AccelerateInterpolator(1.5F));

        mAnimators.add(leftCircleRadiusAnimator);

        ObjectAnimator rightCircleRadiusAnimator = ObjectAnimator.ofFloat(this, "leftCircleRadius", mMaxCircleRadius, mMinCircleRadius);
        rightCircleRadiusAnimator.setDuration(5000L);
        rightCircleRadiusAnimator.setInterpolator(new DecelerateInterpolator(0.8F));
        mAnimators.add(rightCircleRadiusAnimator);

    }

    private void seekAnimator(float offset) {
        for (ValueAnimator animator : mAnimators) {
            animator.setCurrentPlayTime((long) (5000.0F * offset));
        }
        postInvalidate();
    }

        private void resetPoint() {
        mPoints.clear();
        for (int i = 0; i < mPagerCount; i++) {
            int x = mWidth / (mPagerCount + 1) * (i + 1);
            mPoints.add(new PointF(x, 0));
        }

        if (!mPoints.isEmpty()) {
            leftCircleX = mPoints.get(0).x;
            leftCircleRadius = mMaxCircleRadius;

            rightCircleX = mPoints.get(0).x;
            rightCircleRadius = mMinCircleRadius;
            postInvalidate();
        }
    }

    public void setPagerCount(int pagerCount) {
        mPagerCount = pagerCount;
    }

    public void setColors(List<Integer> colors) {
        mColors = colors;

        if (!colors.isEmpty()) {
            mPaint.setColor(colors.get(0));
        }
    }
}
