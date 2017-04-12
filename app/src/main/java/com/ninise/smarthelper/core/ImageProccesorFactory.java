package com.ninise.smarthelper.core;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class ImageProccesorFactory {

    public static final int BICUBIC = 0;
    public static final int NEIGHBOR = 1;

    public static Processor getFactory(int processorType) {
        Processor processor;
        switch (processorType) {

            case BICUBIC:
                processor = new BicubicFilter();
                break;

            case NEIGHBOR:
                processor = new NeighborProcessor();
                break;

            default:
                processor = new NeighborProcessor();
                break;

        }

        return processor;
    }

}
