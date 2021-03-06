package com.ninise.smarthelper.view.draw;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.R2;
import com.ninise.smarthelper.base.BaseFragment;
import com.ninise.smarthelper.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

/**
 * @author Nikitin Nikita
 */

public class DrawFragment extends BaseFragment {

    private final String TAG = DrawFragment.class.getSimpleName();

    @BindView(R2.id.mainToolbar) Toolbar mToolbar;
    @BindView(R2.id.mainDrawingView) FrameLayout frameLayout;
    @BindView(R2.id.mainBottomBarView) BottomBarView mBottomBarView;

    MainActivity.IDrawListener mListener = action -> {};

    private DrawingView mDrawingView;

    private Runnable runnable = () -> mListener.onActionListener(MainActivity.IDrawListener.DRAW_UP);
    private Handler handler = new Handler();


    DrawingView.IDragListener mDragListener = () -> {
        mListener.onActionListener(MainActivity.IDrawListener.DRAG_FINISH);
    };

    public static DrawFragment newInstance(MainActivity.IDrawListener listener) {
        DrawFragment fragment = new DrawFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.draw_fragment, container, false);

        ButterKnife.bind(this, view);

        tuneToolbar(mToolbar, R.drawable.ic_spy, R.string.app_name, null);
        tuneBottomBar(mBottomBarView);

        addView();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.draw_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuClear: addView();
            default: return true;
        }
    }

    private void addView() {
        if (mDrawingView != null) {
            frameLayout.removeView(mDrawingView);
            mDrawingView = null;
        }

        mDrawingView = new DrawingView(getContext());
        mDrawingView.setDragListener(mDragListener);
        frameLayout.addView(mDrawingView);

        mDrawingView.setOnTouchListener((v, event) -> {

            switch (event.getAction()) {
                case ACTION_UP :
                    handler.postDelayed(runnable, 500);
                    break;
                case ACTION_DOWN :
                    handler.removeCallbacks(runnable);
                    break;

                case ACTION_MOVE :
                    break;
            }

            return false;
        });
    }

    private void tuneBottomBar(BottomBarView bottomBarView) {
        bottomBarView.setFirstText(R.string.apps);
        bottomBarView.setSecondText(R.string.save);
        bottomBarView.setThirdText(R.string.draws);
        bottomBarView.setFirstImage(R.drawable.ic_app);
        bottomBarView.setSecondImage(R.drawable.ic_floppy_disk);
        bottomBarView.setThirdImage(R.drawable.ic_learning);

        bottomBarView.setOnFirstClickListener(v -> mListener.onActionListener(MainActivity.IDrawListener.DRAW_APPS));
        bottomBarView.setSecondClickListener(v -> mListener.onActionListener(MainActivity.IDrawListener.DRAW_SAVE));
        bottomBarView.setThirdChangeListener(v -> mListener.onActionListener(MainActivity.IDrawListener.DRAW_DRAWS));
    }

    public Bitmap getImage() {
        return mDrawingView.getImage();
    }
}
