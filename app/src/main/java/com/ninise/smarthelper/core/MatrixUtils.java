package com.ninise.smarthelper.core;

import android.support.v4.util.Pair;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class MatrixUtils {

    private static MatrixUtils mInstance = null;

    public static MatrixUtils getInstance() {
        if (mInstance == null) {
            mInstance = new MatrixUtils();
        }

        return mInstance;
    }

    public MatrixUtils() {}

    public Pair<int[], int[]> getArr(int[][] matrix, int width, int height) {
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

        Pair<Integer, Integer> yy = getPoints(yArr);
        y1 = yy.first;
        y2 = yy.second;

        System.out.println();

        Pair<Integer, Integer> xx = getPoints(xArr);
        x1 = xx.first;
        x2 = xx.second;

        System.out.println();


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

    private Pair<Integer, Integer> getPoints(int[] arr) {
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
