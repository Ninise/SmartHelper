package com.ninise.smarthelper.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private boolean mShowFirstDivider = false;
    private boolean mShowLastDivider = false;
    private int colorSeparator = android.R.color.background_light;
    private Context mContext;
    private int margin;

    private DividerItemDecoration() {

    }

    public class Builder {

        private Builder() {

        }

        public Builder setContext(Context context) {
            DividerItemDecoration.this.mContext = context;

            return this;
        }

        public Builder setAttrsDivider(AttributeSet attrs) {
            if (mContext != null) {
                final TypedArray a = DividerItemDecoration.this.mContext
                        .obtainStyledAttributes(attrs, new int[]{android.R.attr.listDivider});
                DividerItemDecoration.this.mDivider = a.getDrawable(0);
                a.recycle();
            }

            return this;
        }

        public Builder setFirstLastDividerShows(boolean firstDividerShows, boolean lastDividerShows) {
            DividerItemDecoration.this.mShowFirstDivider = firstDividerShows;
            DividerItemDecoration.this.mShowLastDivider = lastDividerShows;

            return this;
        }

        public Builder setMargin(int margin) {
            DividerItemDecoration.this.margin = margin;

            return this;
        }

        public Builder setColorSeparator(int color) {
            DividerItemDecoration.this.colorSeparator = color;

            return this;
        }

        public Builder setDivider(Drawable divider) {
            DividerItemDecoration.this.mDivider = divider;

            return this;
        }

        public DividerItemDecoration build() {
            return DividerItemDecoration.this;
        }

    }

    public static Builder newBuilder() {
        return new DividerItemDecoration().new Builder();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mDivider == null) {
            return;
        }
        if (parent.getChildPosition(view) < 1) {
            return;
        }

        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
            outRect.top = mDivider.getIntrinsicHeight();
        } else {
            outRect.left = mDivider.getIntrinsicWidth();
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mDivider == null) {
            super.onDrawOver(c, parent, state);
            return;
        }

        // Initialization needed to avoid compiler warning
        int left = 0, right = 0, top = 0, bottom = 0, size;
        int orientation = getOrientation(parent);
        int childCount = parent.getChildCount();

        if (orientation == LinearLayoutManager.VERTICAL) {
            size = mDivider.getIntrinsicHeight();
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
        } else { //horizontal
            size = mDivider.getIntrinsicWidth();
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
        }

        for (int i = mShowFirstDivider ? 0 : 1; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.getTop() - params.topMargin;
                bottom = top + size;
            } else { //horizontal
                left = child.getLeft() - params.leftMargin;
                right = left + size;
            }

            mDivider.setBounds((left + right) / margin, top, right, bottom);
            mDivider.setColorFilter(mContext.getResources().getColor(colorSeparator), PorterDuff.Mode.DARKEN);
            mDivider.draw(c);
        }

        // show last divider
        if (mShowLastDivider && childCount > 0) {
            View child = parent.getChildAt(childCount - 1);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.getBottom() + params.bottomMargin;
                bottom = top + size;
            } else { // horizontal
                left = child.getRight() + params.rightMargin;
                right = left + size;
            }
            mDivider.setBounds((left + right) / margin, top, right, bottom);
            mDivider.setColorFilter(mContext.getResources().getColor(colorSeparator), PorterDuff.Mode.DARKEN);
            mDivider.draw(c);
        }
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else {
            throw new IllegalStateException(
                    "DividerItemDecoration can only be used with a LinearLayoutManager.");
        }
    }
}
