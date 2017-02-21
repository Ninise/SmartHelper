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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPathToDraw() {
        return pathToDraw;
    }

    public void setPathToDraw(String pathToDraw) {
        this.pathToDraw = pathToDraw;
    }
}
