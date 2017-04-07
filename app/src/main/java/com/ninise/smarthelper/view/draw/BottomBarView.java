package com.ninise.smarthelper.view.draw;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninise.smarthelper.R;

public class BottomBarView extends RelativeLayout {

    private View mFirstView;
    private View mSecondView;
    private View mThirdView;

    private ImageView mFirstImageView;
    private TextView mFirstTextView;

    private ImageView mSecondImageView;
    private TextView mSecondTextView;

    private ImageView mThirdImageView;
    private TextView mThirdTextView;

    private final OnClickListener DEFAULT_CLICK_LISTENER = view -> {

    };

    private OnClickListener mFirstClickListener = DEFAULT_CLICK_LISTENER;
    private OnClickListener mSecondClickListener = DEFAULT_CLICK_LISTENER;
    private OnClickListener mThirdClickListener = DEFAULT_CLICK_LISTENER;

    public BottomBarView(Context context) {
        super(context);
        build();
    }

    public BottomBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        build();
    }

    public BottomBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        build();
    }

    private void build() {
        View view = inflate(getContext(), R.layout.footer_layout, this);
        mFirstView = view.findViewById(R.id.layout_panic);
        mSecondView = view.findViewById(R.id.layout_action);
        mThirdView = view.findViewById(R.id.layout_lock);

        mFirstImageView = (ImageView) mFirstView.findViewById(R.id.image);
        mSecondImageView = (ImageView) mSecondView.findViewById(R.id.image);
        mThirdImageView = (ImageView) mThirdView.findViewById(R.id.image);

        mFirstTextView = (TextView) mFirstView.findViewById(R.id.title);
        mSecondTextView = (TextView) mSecondView.findViewById(R.id.title);
        mThirdTextView = (TextView) mThirdView.findViewById(R.id.title);

    }

    public BottomBarView setOnFirstClickListener(OnClickListener listener) {
        mFirstClickListener = listener;
        mFirstView.setOnClickListener(mFirstClickListener);
        return this;
    }

    public BottomBarView setSecondClickListener(OnClickListener listener) {
        mSecondClickListener = listener;
        mSecondView.setOnClickListener(mSecondClickListener);

        return this;
    }

    public BottomBarView setThirdChangeListener(OnClickListener listener) {
        mThirdClickListener = listener;
        mThirdView.setOnClickListener(mThirdClickListener);

        return this;
    }

    public BottomBarView setFirstImage(int resId) {
        mFirstImageView.setImageResource(resId);
        return this;
    }

    public BottomBarView setFirstText(int id) {
        mFirstTextView.setText(id);
        return this;
    }

    public BottomBarView setSecondImage(int resId) {
        mSecondImageView.setImageResource(resId);
        return this;
    }

    public BottomBarView setSecondText(int id) {
        mSecondTextView.setText(id);
        return this;
    }

    public BottomBarView setThirdImage(int resId) {
        mThirdImageView.setImageResource(resId);
        return this;
    }

    public BottomBarView setThirdText(int id) {
        mThirdTextView.setText(id);
        return this;
    }

}
