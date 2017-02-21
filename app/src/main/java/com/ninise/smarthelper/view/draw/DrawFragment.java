package com.ninise.smarthelper.view.draw;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.R2;
import com.ninise.smarthelper.base.BaseFragment;
import com.ninise.smarthelper.view.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Nikitin Nikita
 */

public class DrawFragment extends BaseFragment {

    private final String TAG = DrawFragment.class.getSimpleName();

    @BindView(R2.id.mainToolbar) Toolbar mToolbar;
    @BindView(R2.id.mainDrawingView) DrawingView mDrawingView;
    @BindView(R2.id.mainBottomBarView) BottomBarView mBottomBarView;

    private static final int REQUEST_READ_STORAGE = 112;
    MainActivity.IDrawListener mListener = action -> {};

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

        tuneToolbar(mToolbar, 0, R.string.app_name, null);
        tuneBottomBar(mBottomBarView);

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
            case R.id.menuClear: mDrawingView.clear();
            default: return true;
        }
    }

    private void tuneBottomBar(BottomBarView bottomBarView) {
        bottomBarView.setFirstText(R.string.apps);
        bottomBarView.setSecondText(R.string.save);
        bottomBarView.setThirdText(R.string.draws);

        bottomBarView.setOnFirstClickListener(v -> mListener.onActionListener(MainActivity.IDrawListener.DRAW_APPS));
        bottomBarView.setSecondClickListener(v -> mListener.onActionListener(MainActivity.IDrawListener.DRAW_SAVE));
        bottomBarView.setThirdChangeListener(v -> mListener.onActionListener(MainActivity.IDrawListener.DRAW_DRAWS));
    }
}
