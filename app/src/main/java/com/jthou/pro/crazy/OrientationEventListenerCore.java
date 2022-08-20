package com.jthou.pro.crazy;

import static com.jthou.pro.crazy.OrientationEventListenerCore.OrientationStatus.ROTATION_90_DEGREES;
import static com.jthou.pro.crazy.OrientationEventListenerCore.OrientationStatus.ROTATION_180_DEGREES;
import static com.jthou.pro.crazy.OrientationEventListenerCore.OrientationStatus.ROTATION_270_DEGREES;

import android.content.Context;
import android.provider.Settings;
import android.view.OrientationEventListener;

import androidx.annotation.IntDef;

import com.blankj.utilcode.util.LogUtils;

/**
 * <pre>
 *     @author : xyk
 *     e-mail  : yaxiaoke@163.com
 *     github  : https://github.com/xuyingke
 *     time    : 2020-02-14
 *     desc    :
 *     version : 1.0
 * </pre>
 */
public class OrientationEventListenerCore {

    private final String TAG = "OrientationEventListenerCore";

    private OrientationEventListener mOrientationEventListener;
    private IRotationDegreesScreenListener mListener;

    public static OrientationEventListenerCore newInstance() {
        return new OrientationEventListenerCore();
    }


    private @OrientationStatus int mCurrentStatus;

    public void register(final Context context) {
        mOrientationEventListener = new OrientationEventListener(context) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    mCurrentStatus = -1;
                    return;
                }
                // 1 表示已开启
                // 0 表示未开启
                try {
                    int screenRotation = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
                    if (screenRotation != 1) {
                        LogUtils.i(TAG, "自动旋转屏幕未开启");
                        return;
                    }
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                    LogUtils.i(TAG, "判断是否自动旋转屏幕出错");
                    return;
                }
                if (orientation > 350 || orientation < 10) {
                    LogUtils.i(TAG, "orientation=" + "90");
                    if (mListener != null &&
                            mCurrentStatus != ROTATION_90_DEGREES) {
                        mListener.call(ROTATION_90_DEGREES);
                        mCurrentStatus = ROTATION_90_DEGREES;
                    }
                } else if (orientation > 80 && orientation < 100) {
                    LogUtils.i(TAG, "orientation=" + "180");
                    if (mListener != null &&
                            mCurrentStatus != ROTATION_180_DEGREES
                            && mCurrentStatus != ROTATION_270_DEGREES) {
                        mListener.call(ROTATION_180_DEGREES);
                        mCurrentStatus = ROTATION_180_DEGREES;
                    }
                } else if (orientation > 170 && orientation < 190) {
                    LogUtils.i(TAG, "orientation=" + "270");
                    if (mListener != null &&
                            mCurrentStatus != ROTATION_180_DEGREES
                            && mCurrentStatus != ROTATION_270_DEGREES) {
                        mListener.call(ROTATION_270_DEGREES);
                        mCurrentStatus = ROTATION_270_DEGREES;
                    }
                } else if (orientation > 260 && orientation < 280) {
                    LogUtils.i(TAG, "orientation=" + "270度");
                    if (mListener != null
                            && mCurrentStatus != ROTATION_180_DEGREES
                            && mCurrentStatus != ROTATION_270_DEGREES) {
                        mListener.call(ROTATION_270_DEGREES);
                        mCurrentStatus = ROTATION_270_DEGREES;
                    }
                }
            }
        };

        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
            LogUtils.i(TAG, "当前设备支持手机旋转");
        } else {
            mOrientationEventListener.disable();
            LogUtils.i(TAG, "当前设备不支持手机旋转");
        }
    }

    public void disable() {
        mListener = null;
        if (mOrientationEventListener != null) {
            mOrientationEventListener.disable();
        }
    }

    public OrientationEventListenerCore setListener(IRotationDegreesScreenListener l) {
        this.mListener = l;
        return this;
    }

    public interface IRotationDegreesScreenListener {

        /**
         * 屏幕旋转的回调
         *
         * @param degrees degrees
         */
        void call(@OrientationStatus int degrees);
    }

    @IntDef({ROTATION_90_DEGREES, ROTATION_180_DEGREES, ROTATION_270_DEGREES})
    public @interface OrientationStatus {
        /**
         * 屏幕旋转 90 度
         */
        int ROTATION_90_DEGREES = 1;

        /**
         * 屏幕旋转 180 度
         */
        int ROTATION_180_DEGREES = 2;

        /**
         * 屏幕旋转 270 度
         */
        int ROTATION_270_DEGREES = 3;
    }

}
