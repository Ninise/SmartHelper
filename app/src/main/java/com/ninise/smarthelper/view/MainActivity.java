package com.ninise.smarthelper.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.base.BaseActivity;
import com.ninise.smarthelper.utils.Utils;
import com.ninise.smarthelper.view.apps.AppsFragment;
import com.ninise.smarthelper.view.draw.DrawFragment;

import static com.ninise.smarthelper.view.MainActivity.IDrawListener.*;

/**
 * @author Nikitin Nikita
 */

public class MainActivity extends BaseActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    public interface IDrawListener {
        int DRAW_APPS = 1;
        int DRAW_SAVE = 2;
        int DRAW_DRAWS = 3;

        void onActionListener(int action);
    }

    private IDrawListener mDrawListener = action -> {
        switch (action) {
            case DRAW_APPS:
                switchFragment(AppsFragment.newInstance());
                break;
            case DRAW_SAVE:
                break;
            case DRAW_DRAWS:
                break;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        switchFragment(DrawFragment.newInstance(mDrawListener));
        Utils.getInstance().checkPermissions(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onBackPressed() {
        ifFragType(AppsFragment.class, f -> switchFragment(DrawFragment.newInstance(mDrawListener)), super::onBackPressed);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {} else {
            finishAffinity();
        }
    }
}
