package com.ninise.smarthelper.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.Toast;

import com.ninise.smarthelper.R;
import com.ninise.smarthelper.base.BaseActivity;
import com.ninise.smarthelper.model.BitmapMatrix;
import com.ninise.smarthelper.utils.Utils;
import com.ninise.smarthelper.view.apps.AppsFragment;
import com.ninise.smarthelper.view.draw.DrawFragment;

import java.util.Arrays;
import java.util.List;

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

    DrawFragment drawFragment;

    private IDrawListener mDrawListener = action -> {
        switch (action) {
            case DRAW_APPS:
                switchFragment(AppsFragment.newInstance());
                break;
            case DRAW_SAVE:
                BitmapMatrix bitmapMatrix = Utils.arrayFromBitmap(drawFragment.getImage());
                int[][] matrix = bitmapMatrix.getMatrix();

                Pair<int[], int[]> pair = getArr(matrix, bitmapMatrix.getWidth(), bitmapMatrix.getHeight());
                int[] xArr = pair.first;
                int[] yArr = pair.second;

                int[][] sub = getSubMatrix(matrix, xArr, yArr);

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

                System.out.println();
                for (int i = 0; i < sub.length; i++) {
                    System.out.print(" " + i);
                }

//                Log.d("das", "COMPRESS...");
//                int[][] compress = compress(matrix, width, height);
//
//                System.out.println("COMPRESSED START\n");
//                for (int i = 0; i < compress.length; i++) {
//                    for (int j = 0; j < compress[i].length; j++) {
//                        System.out.print(" " + compress[i][j]);
//                    }
//                    System.out.println();
//                }
//
//                System.out.println("COMPRESSED FINISH\n");

                break;
            case DRAW_DRAWS:
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

    private Pair<int[], int[]> getArr(int[][] matrix, int width, int height) {
        int[] yArr = new int[width];
        int[] xArr = new int[height];

        System.out.println();
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (matrix[x][y] > 0) {
                    xArr[x] = 1 + xArr[x];
                    yArr[y] = 1 + yArr[y];
                }
            }
        }

        return new Pair<>(xArr, yArr);
    }

    private int[] getPoints(int[] xArr, int[] yArr) {
        int x1 = 0;
        int y1 = 0;

        int x2 = 0;
        int y2 = 0;

        Pair<Integer, Integer> yy = getP(yArr);
        y1 = yy.first;
        y2 = yy.second;

        System.out.println();

        Pair<Integer, Integer> xx = getP(xArr);
        x1 = xx.first;
        x2 = xx.second;

        System.out.println();


        Log.d("das", "x1: " + x1 + "; x2: " + x2 + "; y1: " + y1 + "; y2: " + y2);

        return new int[]{x1, x2, y1, y2};
    }

    public int[][] getSubMatrix(int[][] matrix, int[] xr, int[] yr) {
        int[] ab = getPoints(xr, yr);

        int x1 = ab[0];
        int x2 = ab[1];
        int y1 = ab[2];
        int y2 = ab[3];

        int[][] a = new int[x2 - x1][y2 - y1];

        for (int x = x1, xB = 0; x < x2; x++, xB++) {
            for (int y = y1, yB = 0; y < y2; y++, yB++) {
                a[xB][yB] = matrix[x][y];
            }
        }

        return a;
    }

    private Pair<Integer, Integer> getP(int[] arr) {
        int first = 0, last = 0;

        while (arr[first] == 0 && first < arr.length - 1) {
            first++;
        }

        last = first;

        int end = last;
        while (last < arr.length - 1) {
            last++;
            if (arr[last] > 0) {
                end = last;
            }
        }

        return new Pair<>(first, end);
    }

}
