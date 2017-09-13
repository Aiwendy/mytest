package http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Function:
 * Created by zhang di on 2017-09-06.
 */

public class AESRequestProxyFactory extends Converter.Factory {

    public AESRequestProxyFactory() {
        super();
    }
    public static AESRequestProxyFactory create() {
        return create(new Gson());
    }
    public static AESRequestProxyFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new AESRequestProxyFactory(gson);
    }

    private  Gson gson;

    private AESRequestProxyFactory(Gson gson) {
        this.gson = gson;
    }


    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        return new AESResponseBodyConverter(gson, type,gson.getAdapter(TypeToken.get(type)));
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new AESRequestBodyConverter(gson, gson.getAdapter(TypeToken.get(type)));
    }

    @Nullable
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }
}
