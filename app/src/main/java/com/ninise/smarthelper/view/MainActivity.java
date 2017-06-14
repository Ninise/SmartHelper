package com.ninise.smarthelper.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.base.BaseActivity;
import com.ninise.smarthelper.core.CoreProcessor;
import com.ninise.smarthelper.core.XORRunner;
import com.ninise.smarthelper.db.RealmWorker;
import com.ninise.smarthelper.model.UserCaptureModel;
import com.ninise.smarthelper.utils.Utils;
import com.ninise.smarthelper.view.apps.AppsFragment;
import com.ninise.smarthelper.view.draw.DrawFragment;
import com.ninise.smarthelper.view.draws.DrawsFragment;

import java.io.IOException;
import java.util.ArrayList;
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
    private String pathToFile;

    private AppsFragment.IAppsListener appsListener = (info -> {
        UserCaptureModel captureModel = new UserCaptureModel();
        captureModel.setAppName(Utils.getInstance().parseResolveInfoLabel(info));
        captureModel.setImgVector(imgBytes);
        captureModel.setPathToFile(pathToFile);
        captureModel.setPackag(info.activityInfo.packageName);

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
                    pathToFile = processor.getPathToFile();
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
                    if (list.isEmpty()) return;

                    List<OpenApp> apps = new ArrayList<>();

                    compressedMatrix = proc.getCompressedSubVector();
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


                            apps.add(new OpenApp(ma, captureModel.getPackag()));
                            Log.d("das", "ma = " + ma + " - for " + captureModel.getAppName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    String bestMAPkg = bestMA(apps);

                    Log.d("das", "start package: " + bestMAPkg);

                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(bestMAPkg);
                    if (launchIntent != null) {
                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }

                }

                break;

            case DRAG_FINISH: break;
        }
    };

    class OpenApp {
        int ma;
        String packag;

        public OpenApp(int ma, String packag) {
            this.ma = ma;
            this.packag = packag;
        }

        public String getPackag() {
            return packag;
        }
    }

    private String bestMA(List<OpenApp> list) {
        String pk = null;

        int best = 0;

        for (OpenApp app : list) {
            if (app.ma > best) {
                best = app.ma;
                pk = app.getPackag();
            }
        }

        return pk;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        RealmWorker.getInstance().init(Realm.getDefaultInstance(), UserCaptureModel.class);


        List<UserCaptureModel> list = RealmWorker.getInstance().<UserCaptureModel>readAllItems();

        drawFragment = DrawFragment.newInstance(mDrawListener);
        switchFragment(drawFragment);
        Utils.getInstance().checkPermissions(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public static ArrayList<Integer> bytesToListOfIntegers(byte[] bytes) {
        ArrayList<Integer> longs = new ArrayList<Integer>();
        longs.ensureCapacity(bytes.length);

        for (byte b: bytes) {
            longs.add((int) b);
        }

        return longs;
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
