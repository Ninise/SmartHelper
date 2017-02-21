package com.ninise.smarthelper.model;

import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Nikitin Nikita
 */

public class UserCaptureModel {

    @PrimaryKey
    @Required
    private String appName;

    @Required
    private String pathToDraw;

}
