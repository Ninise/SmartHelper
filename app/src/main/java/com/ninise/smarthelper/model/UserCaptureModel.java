package com.ninise.smarthelper.model;

import java.util.Arrays;

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
    private byte[] imgVector;

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


    @Override
    public String toString() {
        return "UserCaptureModel{" +
                "appName='" + appName + '\'' +
                ", imgVector=" + Arrays.toString(imgVector) +
                '}';
    }
}
