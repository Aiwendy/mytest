package http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import bean.CourseInfoEntity;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import util.AESUtils;

/**
 * Function:
 * Created by zhang di on 2017-09-06.
 */

public class AESResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;//gson对象
    private final TypeAdapter<T> adapter;
    private final Type mType;

    /**
     * 构造器
     */
    public AESResponseBodyConverter(Gson gson, Type  type,TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.mType=type;
        this.adapter = adapter;
    }

    /**
     * 转换
     * @param responseBody
     * @return
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String  strResult= new String(responseBody.string().getBytes("UTF-8"));
        String result = AESUtils.decrypt(strResult, AESUtils.KEY,AESUtils.IV);//解密
        return  mGson.fromJson(result, mType);
    }

}