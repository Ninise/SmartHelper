package com.ninise.smarthelper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ninise.smarthelper.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * @author Nikitin Nikita
 */

public class Utils {

    private final String TAG = Utils.class.getSimpleName();

    private static Utils mInstance = null;

    private static final String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
    private static Random random = new Random();

    private Context mContext;

    public void init(Context context) {
        mContext = context;
    }

    public static Utils getInstance() {
        if (mInstance == null) {
            mInstance = new Utils();
        }

        return mInstance;
    }

    public List<ResolveInfo> getApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = mContext.getPackageManager().queryIntentActivities(mainIntent, 0);

        return pkgAppsList;
    }

    public String string(@StringRes int id) {
        return mContext.getResources().getString(id);
    }

    public DrawableRequestBuilder<String> parseResolveInfoIcon(ResolveInfo ri) {
        return Glide.with(mContext).load("").placeholder(ri.loadIcon(mContext.getPackageManager())).diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    public String parseResolveInfoLabel(ResolveInfo ri) {
        return String.valueOf(ri.loadLabel(mContext.getPackageManager()));
    }


    public void checkPermissions(Activity activity, String permission) {
        boolean hasPermission = (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
        }
    }

    public String saveBitmapToFile(Bitmap bitmap) throws IOException {
        String path = "";
        try {
            String file_path = String.format(
                    Locale.US, "%s/%s",
                    Environment.getExternalStorageDirectory().getAbsolutePath(),
                    string(R.string.app_name));

            File dir = new File(file_path);
            if (!dir.exists())
                dir.mkdirs();

            File file = new File(String.format(Locale.US, "%s/%s.jpg", file_path, generateRandomString(1)));

            FileOutputStream fOut = null;

            try {
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            } finally {
                fOut.flush();
                fOut.close();
            }

            Log.d(TAG, file.getAbsolutePath());
            path = file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    public static String generateRandomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
        return sb.toString();
    }



    public static int R(int c) {
        return (c >> 16) & 0xff;
    }

    public static int G(int c) {
        return (c >> 8) & 0xff;
    }

    public static int B(int c) {
        return c & 0xff;
    }

    public static double r(int c) {
        return R(c) / 255.0;
    }

    public static double g(int c) {
        return G(c) / 255.0;
    }

    public static double b(int c) {
        return B(c) / 255.0;
    }


    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[j][i] == 1 ? "*" : " ");
            }
            System.out.println();
        }
    }
}
