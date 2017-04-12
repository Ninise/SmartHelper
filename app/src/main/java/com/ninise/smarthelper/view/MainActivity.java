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
import com.ninise.smarthelper.core.ImageProccesorFactory;
import com.ninise.smarthelper.core.Processor;
import com.ninise.smarthelper.core.XORRunner;
import com.ninise.smarthelper.model.BitmapMatrix;
import com.ninise.smarthelper.core.MatrixUtils;
import com.ninise.smarthelper.utils.Utils;
import com.ninise.smarthelper.view.apps.AppsFragment;
import com.ninise.smarthelper.view.draw.DrawFragment;

import static com.ninise.smarthelper.view.MainActivity.IDrawListener.DRAG_FINISH;
import static com.ninise.smarthelper.view.MainActivity.IDrawListener.DRAW_APPS;
import static com.ninise.smarthelper.view.MainActivity.IDrawListener.DRAW_DRAWS;
import static com.ninise.smarthelper.view.MainActivity.IDrawListener.DRAW_SAVE;

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

        void onActionListener(int action);
    }

    DrawFragment drawFragment;

    private IDrawListener mDrawListener = action -> {
        switch (action) {
            case DRAW_APPS:
                switchFragment(AppsFragment.newInstance());
                break;
            case DRAW_SAVE:
                BitmapMatrix bitmapMatrix = Utils.arrayFromBitmap(drawFragment.getImage());
                int[][] matrix = bitmapMatrix.getMatrix();

                Pair<int[], int[]> pair = MatrixUtils.getInstance().getArr(matrix, bitmapMatrix.getWidth(), bitmapMatrix.getHeight());
                int[] xArr = pair.first;
                int[] yArr = pair.second;

                int[][] sub = MatrixUtils.getInstance().getSubMatrix(matrix, xArr, yArr);

                Log.d("das", "SUB MATRIX");
                System.out.println("SUB MATRIX\n");

                int width = 0;
                int height = 0;

                for (int i = 0; i < sub.length; i++) {
                    for (int j = 0; j < sub[i].length; j++) {
                        System.out.print(" " + sub[i][j]);
                        width = j;
                    }
                    System.out.println();
                    height = i;
                }

                int[] vector = Utils.getInstance().convert2DtoVector(sub);

                System.out.println("\nCOMPRESS\n");

                Processor processor = ImageProccesorFactory.getFactory(ImageProccesorFactory.NEIGHBOR);
                int[] comVector = processor.process(vector, width, height, 24, 24);

                int[][] newSub = Utils.getInstance().convert1Dto2D(comVector, 24);

                for (int i = 0; i < newSub.length; i++) {
                    for (int j = 0; j < newSub[i].length; j++) {
                        System.out.print(" " + newSub[i][j]);
                        width = j;
                    }
                    System.out.println();
                    height = i;
                }

                System.out.println();
                for (int i = 0; i < sub.length; i++) {
                    System.out.print(" " + i);
                }

                break;
            case DRAW_DRAWS:
                break;

            case DRAG_FINISH:

                break;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        drawFragment = DrawFragment.newInstance(mDrawListener);
        switchFragment(drawFragment);
        Utils.getInstance().checkPermissions(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);



        int[][] a = {   {0,0,0,0,0},
                        {0,1,0,1,0},
                        {0,1,1,1,0},
                        {0,1,0,1,0},
                        {0,0,0,0,0}
        };

        int[][] b = {   {1,0,0,0,1},
                        {0,1,0,1,0},
                        {0,1,1,1,0},
                        {0,1,0,1,0},
                        {1,1,1,1,1}
        };


        int ma = XORRunner
                .build()
                .setOriginalPixels(a)
                .setSamplePixels(b)
                .getMatchAccuracy();

        Log.d("das", "ma = " + ma + "%");


    }

    @Override
    public void onBackPressed() {
        ifFragType(AppsFragment.class, f -> switchFragment(DrawFragment.newInstance(mDrawListener)), super::onBackPressed);
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
