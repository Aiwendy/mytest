package http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import bean.APIBodyData;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;
import util.AESUtils;

/**
 * Function:
 * Created by zhang di on 2017-09-06.
 */

public class AESRequestBodyConverter<T> implements Converter<T, RequestBody> {


    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */

    public AESRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }


    @Override
    public RequestBody convert(T value) throws IOException {
        //加密
        APIBodyData data = new APIBodyData();
        Log.i("xiaozhang", "request中传递的json数据：" + value.toString());
        data.setData(AESUtils.encrypt(value.toString(), AESUtils.KEY,AESUtils.IV));
        String postBody = gson.toJson(data); //对象转化成json
        Log.i("xiaozhang", "转化后的数据：" + postBody);
        return RequestBody.create(MEDIA_TYPE, postBody);
    }


}
