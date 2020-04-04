package com.example.pushnotification.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import androidx.appcompat.widget.AppCompatEditText;

public class CleanableEditText extends AppCompatEditText {
    private Drawable mRightDrawable;
    private boolean isHasFocus;

    public CleanableEditText(Context context) {
        super(context);
        init();
    }
    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        //getCompoundDrawables:
        //Returns drawables for the left, top, right, and bottom borders.
        Drawable[] drawables=this.getCompoundDrawables();
        //Obtain right Drawable
        //We arranged in a layout file in android:drawableRight
        mRightDrawable=drawables[2];

        //Set the focus change monitoring
        this.setOnFocusChangeListener(new FocusChangeListenerImpl());
        //Set the EditText text change monitoring
        this.addTextChangedListener(new TextWatcherImpl());
        //Initialization of clean icon is not visible to the right
        setClearDrawableVisible(false);
    }
    /**
     * When the finger is lifted position in the clean icon in the region
     * We see this as the removal operation
     * getWidth():Get the width of the control
     * event.getX():Coordinate when lifting (change the coordinates are relative to the control itself.)
     * getTotalPaddingRight():The clean icon to the right edge of the left edge of the control distance
     * getPaddingRight():The clean icon to the right edge of the right edge of the control distance
     * Therefore:
     * getWidth() - getTotalPaddingRight()Express:
     * The left edge of the control to the clean icon on the left of the area
     * getWidth() - getPaddingRight()Express:
     * The icon on the left to the right edge of the control region of the clean
     * So between the two regions is just the clean icon in the region
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:

                boolean isClean =(event.getX() > (getWidth() - getTotalPaddingRight()))&&
                        (event.getX() <(getWidth() - getPaddingRight()));
                if (isClean) {
                    setText("");
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private class FocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            isHasFocus=hasFocus;
            if (isHasFocus) {
                boolean isVisible=getText().toString().length()>=1;
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }
    }
    private class TextWatcherImpl implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            boolean isVisible=getText().toString().length()>=1;
            setClearDrawableVisible(isVisible);

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    //Show or hide the clean Icon
    protected void setClearDrawableVisible(boolean isVisible) {
        Drawable rightDrawable;
        if (isVisible) {
            rightDrawable = mRightDrawable;
        } else {
            rightDrawable = null;
        }
        //Use the code sets the control left, top, right, and bottom Icon
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],
                rightDrawable,getCompoundDrawables()[3]);
    }
}
