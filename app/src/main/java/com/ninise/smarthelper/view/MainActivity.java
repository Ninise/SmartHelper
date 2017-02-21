package com.ninise.smarthelper.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.base.BaseActivity;
import com.ninise.smarthelper.view.apps.AppsFragment;
import com.ninise.smarthelper.view.draw.DrawFragment;

import static com.ninise.smarthelper.view.MainActivity.IDrawListener.*;

/**
 * @author Nikitin Nikita
 */

public class MainActivity extends BaseActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    public interface IDrawListener {
        int DRAW_APPS  = 1;
        int DRAW_SAVE  = 2;
        int DRAW_DRAWS = 3;

        void onActionListener(int action);
    }

    private IDrawListener mDrawListener = action -> {
        switch (action) {
            case DRAW_APPS: Log.d(TAG, "DRAW_APPS"); switchFragment(AppsFragment.newInstance()); break;
            case DRAW_SAVE: break;
            case DRAW_DRAWS: break;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        switchFragment(DrawFragment.newInstance(mDrawListener));
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        ifFragType(AppsFragment.class, f -> {
            switchFragment(DrawFragment.newInstance(mDrawListener));
        }, () -> {
            super.onBackPressed();
        });
    }
}
