package com.ninise.smarthelper.core;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class NeighborProcessor extends Processor {

    @Override
    public int[] process(int[] pixels, int w1, int h1, int w2, int h2) {
        int[] temp = new int[w2*h2] ;
        double x_ratio = w1/(double)w2 ;
        double y_ratio = h1/(double)h2 ;
        double px, py ;
        for (int i=0;i<h2;i++) {
            for (int j=0;j<w2;j++) {
                px = Math.floor(j*x_ratio) ;
                py = Math.floor(i*y_ratio) ;
                temp[(i*w2)+j] = pixels[(int)((py*w1)+px)] ;
            }
        }
        return temp ;
    }
}
