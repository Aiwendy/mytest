package util;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import http.Constant;

import static util.PhoneUtil.getPhoneImei;

/**
 * Function:
 * Created by zhang di on 2017-06-30.
 */

public class AESUtils {
    /**
     * 加密(结果为16进制字符串)
     **/
    public static String encrypt(String content, String key, String iv) {
        byte[] data = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = encrypt(data, key, iv);
        String result = byte2hex(data);//16进制转码
        return result;
    }

    /**
     * 解密(输出结果为字符串)
     **/
    public static String decrypt(String content, String key, String iv) {
        byte[] data = null;
        try {
            data = hex2byte(content);//16进制解码
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt(data, key, iv);
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 算法/模式/填充
     **/
    private static final String CipherMode = "AES/CBC/PKCS5Padding";
    /**
     * 密码
     */
    public final static String KEY = "weitaixincw12345";
    /**
     * 向量
     */
    public final static String IV = "1234567812345678";

    public static final String DEVICE_TYPE = "weitaixincw12345";

    /**
     * 创建密钥
     **/
    private static SecretKeySpec createKey(String key) {
        byte[] data = null;
        if (key == null) {
            key = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(key);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }

    /**
     * 创建iv向量
     **/
    private static IvParameterSpec createIV(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(16);
        sb.append(password);
        while (sb.length() < 16) {
            sb.append("0");
        }
        if (sb.length() > 16) {
            sb.setLength(16);
        }

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }

    /**
     * 加密字节数据
     **/
    private static byte[] encrypt(byte[] content, String password, String iv) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, key, createIV(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解密字节数组
     **/
    private static byte[] decrypt(byte[] content, String password, String iv) {
        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key, createIV(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 2进制转成16进制字符串
     **/
    private static String byte2hex(byte[] b) { // 一个字节的数，
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp = "";
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            tmp = (Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 16进制转2进制
     **/
    private static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

    /**
     * 网络请求的时候获取http请求的头信息Auth
     */
    public static String getWTXAuth(LinkedHashMap<String, String> map, Context context) {
        // 拼接
        String wtxAuth = "";
        try {
            String deviceId = getPhoneImei(context);//设备imei码
            String timestamp = getTimes();//时间戳
            sortMap(map);// 排序
            String sign = getSign(map);// 生成签名
            wtxAuth = "deviceId=" + deviceId + "&timestamp=" + timestamp + "&sign=" + sign;
        } catch (Exception e) {
            e.printStackTrace();
            wtxAuth = "WTXAuth";
        }
        return wtxAuth;
    }


    /**
     * 获取当前系统时间戳的前10位
     *
     * @return
     */
    public static String getTimes() {
        String s = "";
        String cm = System.currentTimeMillis() + "";
        s = cm.substring(0, 10);
        return s;
    }

    /**
     * 对LinkHashMap集合进行排序并输出
     **/
    public static LinkedHashMap<String, String> sortMap(LinkedHashMap<String, String> map) {
        if (map != null && map.size() != 0) {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            try {
                // 排序
                Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                        return (o1.getKey()).toString().compareTo(o2.getKey());
                    }
                });
                map.clear();
                // 排序后
                for (int i = 0; i < infoIds.size(); i++) {
                    String key = infoIds.get(i).getKey();
                    String value = infoIds.get(i).getValue();
                    map.put(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 获取接口校验签名
     *
     * @param map
     * @return
     */
    public static String getSign(LinkedHashMap<String, String> map) {
        String s = "";
        if (map != null && map.size() > 0) {
            String replace = transMapValueTostr(map) + "&deviceType=" + DEVICE_TYPE;
            s = Md5Utils.MD5(replace);
        } else {
            String replace = "deviceType=" + DEVICE_TYPE;
            s = Md5Utils.MD5(replace);
        }
        return s;
    }

    /**
     * @param map 取出map集合中的value值 ，用@拼接
     * @return
     */
    public static String transMapValueTostr(LinkedHashMap<String, String> map) {
        String s = "";
        StringBuilder sb = new StringBuilder();
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> en : map.entrySet()) {
                sb.append(en.getKey() + "=" + en.getValue()).append("&");
            }
            s = sb.toString();
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
