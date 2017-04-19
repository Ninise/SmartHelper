package com.ninise.smarthelper.core;

import com.ninise.smarthelper.core.interpolations.BicubicFilter;
import com.ninise.smarthelper.core.interpolations.BilinearFilter;
import com.ninise.smarthelper.core.interpolations.NeighborProcessor;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class ImageProccesorFactory {

    public static final int BICUBIC = 0;
    public static final int NEIGHBOR = 1;
    public static final int BILINEAR = 2;

    public static Processor getFactory(int processorType) {
        Processor processor;
        switch (processorType) {

            case BICUBIC:
                processor = new BicubicFilter();
                break;

            case NEIGHBOR:
                processor = new NeighborProcessor();
                break;

            case BILINEAR:
                processor = new BilinearFilter();
                break;

            default:
                processor = new NeighborProcessor();
                break;

        }

        return processor;
    }

}
