package com.ninise.smarthelper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ninise.smarthelper.R;

/**
 * @author Nikitin Nikita
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface DoIfFragment<E extends Fragment>{
        void process(E fragment);
    }

    public interface DoIfNoFragment{
        void process();
    }

    public <E extends Fragment> void ifFragType(Class<E> clazz, DoIfFragment<E> thenCallback, DoIfNoFragment elseCallback){
        runOnUiThread(()-> {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
            if(f == null)
                return;
            if (clazz.isAssignableFrom(f.getClass())) {
                E fragment = (E) f;
                if(thenCallback != null)
                    thenCallback.process(fragment);
            }else{
                if(elseCallback != null){
                    elseCallback.process();
                }
            }
        });
    }

    protected void switchFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

}
