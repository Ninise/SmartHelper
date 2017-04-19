package com.ninise.smarthelper.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.Log;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.base.BaseActivity;
import com.ninise.smarthelper.core.CoreProcessor;
import com.ninise.smarthelper.core.ImageProccesorFactory;
import com.ninise.smarthelper.core.MatrixUtils;
import com.ninise.smarthelper.core.Processor;
import com.ninise.smarthelper.core.XORRunner;
import com.ninise.smarthelper.db.RealmWorker;
import com.ninise.smarthelper.model.BitmapMatrix;
import com.ninise.smarthelper.model.UserCaptureModel;
import com.ninise.smarthelper.utils.Utils;
import com.ninise.smarthelper.view.apps.AppsFragment;
import com.ninise.smarthelper.view.draw.DrawFragment;
import com.ninise.smarthelper.view.draws.DrawsFragment;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;

import static com.ninise.smarthelper.view.MainActivity.IDrawListener.DRAG_FINISH;
import static com.ninise.smarthelper.view.MainActivity.IDrawListener.DRAW_APPS;
import static com.ninise.smarthelper.view.MainActivity.IDrawListener.DRAW_DRAWS;
import static com.ninise.smarthelper.view.MainActivity.IDrawListener.DRAW_SAVE;
import static com.ninise.smarthelper.view.MainActivity.IDrawListener.DRAW_UP;

/**
 * @author Nikitin Nikita
 */

public class MainActivity extends BaseActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    public interface IDrawListener {
        int DRAW_APPS = 1;
        int DRAW_SAVE = 2;
        int DRAW_DRAWS = 3;
        int DRAG_FINISH = 4;
        int DRAW_UP = 5;

        void onActionListener(int action);
    }

    private DrawFragment drawFragment;
    private byte[] imgBytes;

    private AppsFragment.IAppsListener appsListener = (info -> {
        UserCaptureModel captureModel = new UserCaptureModel();
        captureModel.setAppName(Utils.getInstance().parseResolveInfoLabel(info));
        captureModel.setImgVector(imgBytes);

        if (imgBytes != null) {
            RealmWorker.getInstance().createItem(captureModel);
        }
    });

    private IDrawListener mDrawListener = action -> {
        switch (action) {
            case DRAW_APPS:
                switchFragment(AppsFragment.newInstance(appsListener));
                break;
            case DRAW_SAVE:
                CoreProcessor processor = new CoreProcessor()
                        .addBitmap(drawFragment.getImage());

                try {
                    processor.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("BEFORE SAVE");
                    imgBytes = processor.getCompressedSubByteVector();
                    switchFragment(AppsFragment.newInstance(appsListener));
                }

                break;
            case DRAW_DRAWS:
                switchFragment(DrawsFragment.newInstance());
                break;

            case DRAW_UP:
                CoreProcessor proc = new CoreProcessor()
                        .addBitmap(drawFragment.getImage());
                int[] compressedMatrix;
                try {
                    proc.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("BEFORE SAVE");

                    List<UserCaptureModel> list = RealmWorker.getInstance().<UserCaptureModel>readAllItems();

                    compressedMatrix = proc.getCompressedSubVector();
                    if (list.isEmpty()) return;
                    for (UserCaptureModel captureModel : list) {

                        try {
                            int[] matrix = proc.toIntArray(captureModel.getImgVector());
                            System.out.println("SAVED");
                            Utils.getInstance().printMatrix(proc.convert1Dto2D(matrix, proc.getCompressValue()));
                            System.out.println("DRAWED");
                            Utils.getInstance().printMatrix(proc.convert1Dto2D(compressedMatrix, proc.getCompressValue()));
                            int ma = XORRunner
                                    .vector()
                                    .setOriginalPixels(compressedMatrix)
                                    .setSamplePixels(matrix)
                                    .getMatchAccuracy();

                            Log.d("das", "ma = " + ma + " - for " + captureModel.getAppName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                break;

            case DRAG_FINISH: break;
        }
    };




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        RealmWorker.getInstance().init(Realm.getDefaultInstance(), UserCaptureModel.class);


        drawFragment = DrawFragment.newInstance(mDrawListener);
        switchFragment(drawFragment);
        Utils.getInstance().checkPermissions(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    protected void onDestroy() {
        RealmWorker.getInstance().deinit();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        ifFragType(AppsFragment.class, f -> {
            drawFragment = DrawFragment.newInstance(mDrawListener);
            switchFragment(drawFragment);
        });
        ifFragType(DrawsFragment.class, f -> {
            drawFragment = DrawFragment.newInstance(mDrawListener);
            switchFragment(drawFragment);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        } else {
            finishAffinity();
        }
    }



}
