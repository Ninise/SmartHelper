package com.ninise.smarthelper.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikita N.
 */

public class HopfieldNetwork {
    // the number of recognition steps
    private final int RSTEPS = 25;
    // samples
    private List<ArrayList<Integer>> mSamples;
    // training matrix
    private int[][] mW;

    public HopfieldNetwork(List<ArrayList<Integer>> samples) {
        mSamples = samples;
        trainW();
    }

    /**
     * Recognition.
     * @param v input vector.
     * @return index of samples list or -1.
     */
    public int recognize(List<Integer> v) {
        for (int i = 0; i < RSTEPS; i++) {
            v = activation(mulToW(v));
            int found = isFound(v);
            if (found != -1) {
                return found;
            }
        }
        return -1;
    }

    /**
     * Training.
     */
    private void trainW() {
        List<int[][]> samples = new ArrayList<>();
        for (List<Integer> vector : mSamples) {
            samples.add(makeMatrix(vector));
        }

        final int len = mSamples.get(0).size();
        mW = new int[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (i == j) {
                    mW[i][j] = 0;
                    continue;
                }
                int sum = 0;
                for (int k = 0; k < samples.size(); k++) {
                    sum += samples.get(k)[i][j];
                }
                mW[i][j] = sum;
            }
        }
    }

    /**
     * Activation function.
     */
    private List<Integer> activation(List<Integer> v) {
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < v.size(); i++) {
            res.add(v.get(i) < 0 ? -1 : 1);
        }
        return res;
    }

    /**
     * @return multiplication mW * v.
     */
    private List<Integer> mulToW(List<Integer> v) {
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < mW.length; i++) {
            int mul = 0;
            for (int j = 0; j < mW[i].length; j++) {
                mul += mW[i][j] * v.get(j);
            }
            res.add(mul);
        }
        return res;
    }

    /**
     * @return index of sample list if v was found there, else -1.
     */
    private int isFound(List<Integer> v) {
        for (int i = 0; i < mSamples.size(); i++) {
            if (v.equals(mSamples.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return vectorT * vector.
     */
    private int[][] makeMatrix(List<Integer> vector) {
        int[][] m = new int[vector.size()][vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            for (int j = 0; j < vector.size(); j++) {
                m[i][j] = (vector.get(i) == -1) ? -vector.get(j) : vector.get(j);
            }
        }
        return m;
    }
}