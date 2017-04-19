package com.ninise.smarthelper.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninise.smarthelper.R2;
import com.ninise.smarthelper.core.CoreProcessor;
import com.ninise.smarthelper.model.UserCaptureModel;
import com.ninise.smarthelper.utils.IRecyclerItemClickListener;
import com.ninise.smarthelper.utils.Utils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Nikita <nikita.nikitin@computoolsglobal.com>
 */

public class DrawsRecyclerViewHolder extends BaseViewHolder<UserCaptureModel> {

    @BindView(R2.id.appsItemView) View mView;
    @BindView(R2.id.appsItemImageView) ImageView mImageView;
    @BindView(R2.id.appsItemTextView) TextView mTextView;

    public DrawsRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    CoreProcessor processor = new CoreProcessor();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void bind(UserCaptureModel userCaptureModel, int position, IRecyclerItemClickListener<UserCaptureModel> listener) {
        BitmapFactory.Options options = new BitmapFactory.Options();

//        Bitmap bmp = null;
        try {
            int [] vector = processor.toIntArray(userCaptureModel.getImgVector());
            int[][] matrix = processor.convert1Dto2D(vector, processor.getCompressValue());
            System.out.println("ELEMENT - " + userCaptureModel.getAppName());
            Utils.getInstance().printMatrix(matrix);

//            bmp = Bitmap.createBitmap(vector, 128, 128, Bitmap.Config.ARGB_8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        Bitmap scale = Bitmap.createScaledBitmap(bmp, 128 * 4, 128 * 4, true);
//
//        mImageView.setImageBitmap(scale);

        mTextView.setText(userCaptureModel.getAppName());
        mView.setOnClickListener(v -> listener.onClick(userCaptureModel, position));
    }
}
