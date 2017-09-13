package util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Function:
 * Created by zhang di on 2017-06-30.
 */

public class PhoneUtil {
    private static String imei;

    /**
     * 获取手机的IMEI串号
     */
    public static String getPhoneImei(Context ctx) {
        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
        return imei;
    }

    public static void setPhoneImei(String imeii) {
        imei = imeii;
    }

    public static String getPhoneImei() {
        return imei;
    }

}
