package com.idogfooding.photopicker;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.yalantis.ucrop.UCrop;

import me.iwf.photopicker.R;

/**
 * UCropUtils
 *
 * @author Charles
 */
public class UCropUtils {

    /**
     * get common options with color
     * 
     * @param context
     * @param isCircle
     * @return
     */
    public static UCrop.Options getOptions(Context context, boolean isCircle) {
        UCrop.Options options = new UCrop.Options();
        options.setCircleDimmedLayer(isCircle);
        options.setStatusBarColor(ContextCompat.getColor(context, R.color.__picker_actionbar_background_color));
        options.setToolbarColor(ContextCompat.getColor(context, R.color.__picker_actionbar_background_dark_color));
        options.setActiveWidgetColor(ContextCompat.getColor(context, R.color.__picker_actionbar_background_color));
        return options;
    }

    public static void initUCrop(Activity activity, @NonNull Uri source, @NonNull Uri destination, boolean isCircle) {
        initUCrop(activity, UCrop.REQUEST_CROP, source, destination, isCircle);
    }

    public static void initUCrop(Activity activity, int requestCode, @NonNull Uri source, @NonNull Uri destination, boolean isCircle) {
        UCrop.of(source, destination)
                .withOptions(getOptions(activity, isCircle))
                .withAspectRatio(1, 1)
                .withMaxResultSize(800, 800)
                .start(activity, requestCode);
    }
}
