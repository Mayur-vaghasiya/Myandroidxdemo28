package com.example.pushnotification.util;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class RecyclerItemDoubleClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemDoubleClickListener doubleClickListener;

    public interface OnItemDoubleClickListener {
        public void onItemDoubleClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemDoubleClickListener(Context context, OnItemDoubleClickListener listener) {
        doubleClickListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && doubleClickListener != null && mGestureDetector.onTouchEvent(e)) {
            doubleClickListener.onItemDoubleClick(childView, view.getChildPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}