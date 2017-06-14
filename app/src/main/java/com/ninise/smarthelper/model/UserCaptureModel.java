package com.ninise.smarthelper.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Nikitin Nikita
 */

@RealmClass
public class UserCaptureModel extends RealmObject {

    @PrimaryKey
    @Required
    private String appName;

    @Required
    private String packag;

    @Required
    private byte[] imgVector;
    private String mPathToFile;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public byte[] getImgVector() {
        return imgVector;
    }

    public void setImgVector(byte[] imgVector) {
        this.imgVector = imgVector;
    }

    public String getPackag() {
        return packag;
    }

    public void setPackag(String packag) {
        this.packag = packag;
    }

    @Override
    public String toString() {
        return "UserCaptureModel{" +
                "appName='" + appName + '\'' +
                ", packag='" + packag + '\'' +
                ", mPathToFile='" + mPathToFile + '\'' +
                '}';
    }

    public void setPathToFile(String pathToFile) {
        mPathToFile = pathToFile;
    }

    public String getPathToFile() {
        return mPathToFile;
    }
}
