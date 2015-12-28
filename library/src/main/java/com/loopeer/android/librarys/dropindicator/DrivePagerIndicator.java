package com.loopeer.android.librarys.dropindicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class DrivePagerIndicator extends FrameLayout {

    private SelectView mSelectView;
    private DriveIndicator mDriveIndicator;

    private List<ValueAnimator> mAnimators;
    private int position;
    private int mWidth;
    private int mHeight;
    private int mCount = 3;
    private float radius;
    private float circleX;

    public DrivePagerIndicator(Context context) {
        this(context, null);
    }

    public DrivePagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrivePagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.drive_pager_indicator, null);
        mDriveIndicator = (DriveIndicator) rootView.findViewById(R.id.drive_indicator);
        mSelectView = (SelectView) rootView.findViewById(R.id.select_view);

        mAnimators = new ArrayList<>();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    public void setPositionAndOffset(int position, float offset, int pixels) {
        this.position = position;
        mSelectAnim(position, offset);
        seekAnimator(offset);
        postInvalidate();
    }

    private void seekAnimator(float offset) {
        for (ValueAnimator animator : mAnimators) {
            animator.setCurrentPlayTime((long) (1000.0F * offset));
        }
    }

    private void mSelectAnim(int position, float offset) {
//        ObjectAnimator selectAnim = ObjectAnimator.ofFloat(this, "position", );

        float leftX = mWidth / (mCount + 1) * (position + 1);
        float rightX = mWidth / (mCount + 1) * (position + 2);

        ObjectAnimator selectAnimator = ObjectAnimator.ofFloat(mDriveIndicator, DriveIndicator.SELECT_CIRCLE_X, leftX, rightX);
        selectAnimator.setDuration(900L);
//        selectAnimator.setInterpolator(new DecelerateInterpolator());
        selectAnimator.setCurrentPlayTime((long) (900F * offset));
        selectAnimator.setStartDelay((long) 100F);
//        animator.setCurrentPlayTime((long) (5000.0F * offset));
        mAnimators.add(selectAnimator);


        ObjectAnimator bgAnimator = ObjectAnimator.ofFloat(mSelectView, SelectView.LEFT_CIRCLE_X, leftX, rightX);
        bgAnimator.setDuration(900L);
        bgAnimator.setInterpolator(new AccelerateInterpolator());
        bgAnimator.setCurrentPlayTime((long) (500F * offset));
//        animator.setCurrentPlayTime((long) (5000.0F * offset));
        mAnimators.add(bgAnimator);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mSelectView.setVisibility(View.VISIBLE);
                break;
            case MotionEvent.ACTION_MOVE:
                mSelectView.setVisibility(View.VISIBLE);
                break;
            case MotionEvent.ACTION_UP:
                mSelectView.setVisibility(View.INVISIBLE);
                break;
        }
        return true;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getCircleX() {
        return circleX;
    }

    public void setCircleX(float circleX) {
        this.circleX = circleX;
    }
}
