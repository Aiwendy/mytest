package http;

import android.content.Context;
import android.webkit.WebView;

import com.dreamer.http.api.BaseApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import api.Api;
import api.InfomationApi;
import api.ProductInfoApi;
import api.UserInfoApi;
import exception.RetryWhenNetworkException;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;
import listener.HttpListener;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import subscribers.ProgressSubscriber;
import util.AESUtils;
import util.PhoneUtil;

/**
 * Function:
 * Created by zhang di on 2017-06-30.
 */

public class Http {
    public static Api api;
    public static InfomationApi infoApi;
    public static UserInfoApi userInfoApi;
    public static ProductInfoApi courceInfoApi;
    private static OkHttpClient client;
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    static Context context;
    // Okhttp默认超时
    private static final int DEFAULT_TIMEOUT = 15;

    public Http(Context ctx) {
        context = ctx;
    }




    public static Http getInstance(Context ctx) {
        context = ctx;
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        private static final Http INSTANCE = new Http(context);
    }


    public static Retrofit create(BaseApi api) {
        BasicParamsInterceptor intercepter = new BasicParamsInterceptor();
        HttpLoggingInterceptor logterceptor = new HttpLoggingInterceptor();
        logterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        intercepter.headerParamsMap = initialHeaderAES(map);
        client = new OkHttpClient.Builder().addInterceptor(intercepter).addInterceptor(logterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.getBaseUrl())
                .client(client)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
/*rx处理*/
        ProgressSubscriber subscriber = new ProgressSubscriber(api);
        Observable observable = api.getObservable(retrofit)
              /*   失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException(api.getRetryCount(),
                        api.getRetryDelay(), api.getRetryIncreaseDelay()))
             /*   生命周期管理*/
                .compose(api.getRxAppCompatActivity().bindToLifecycle())
                .compose(api.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE))
            /*    http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
       /*         回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
             /*   结果判断*/
                .map(api);


    /*    链接式对象返回*/
        SoftReference<HttpListener> httpOnNextListener = api.getListener();
        if (httpOnNextListener != null && httpOnNextListener.get() != null) {
            httpOnNextListener.get().onNext(observable);
        }

   /*     数据回调*/
        observable.subscribe(subscriber);
        return retrofit;
    }

    /**
     * 获取用户信息
     * https://babyheartapi.ihealthbaby.cn/BabyheartServer/user/userinfo/1334328
     *
     * @return
     */
    public static UserInfoApi getUserInfoApi() {
        initOkhttp();

        if (userInfoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.Base_url)
                    .client(client)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            userInfoApi = retrofit.create(UserInfoApi.class);

        }
        return userInfoApi;
    }


    private static void initOkhttp() {
        BasicParamsInterceptor intercepter = new BasicParamsInterceptor();

        intercepter.headerParamsMap = initialHeader();
        client = new OkHttpClient.Builder().addInterceptor(intercepter).build();
    }

    private static void initOkhttpAES(LinkedHashMap<String, String> map) {
        BasicParamsInterceptor intercepter = new BasicParamsInterceptor();
        HttpLoggingInterceptor logterceptor = new HttpLoggingInterceptor();
        logterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        intercepter.headerParamsMap = initialHeaderAES(map);
        client = new OkHttpClient.Builder().addInterceptor(intercepter).addInterceptor(logterceptor).build();
    }

    //"Auth" -> "355180060688521"
    private static Map<String, String> initialHeader() {
        Map<String, String> headerParamsMap = new HashMap<>();
        String user_agent = new WebView(context).getSettings().getUserAgentString();
//        Logger.e("user_agent:"+user_agent);
        String ua = user_agent + "|ANDROID_YUANWAI" + " V" + "2.1.5" + "_" + "105";
        headerParamsMap.put("User-Agent", ua);
        headerParamsMap.put("Auth", PhoneUtil.getPhoneImei(context));
        return headerParamsMap;
    }

    private static Map<String, String> initialHeaderAES(@Nullable LinkedHashMap<String, String> map) {
        Map<String, String> headerParamsMap = new HashMap<>();
        String user_agent = new WebView(context).getSettings().getUserAgentString();
//      Logger.e("user_agent:"+user_agent);
        String ua = user_agent + "|ANDROID_YUANWAI" + " V" + "2.1.5" + "_" + "105";
        headerParamsMap.put("User-Agent", ua);
        headerParamsMap.put("Auth", AESUtils.getWTXAuth(map, context));
        return headerParamsMap;
    }

    /**
     * 获取产品列表
     */
    public static ProductInfoApi getCourseInfoApi(LinkedHashMap<String, String> map) {
        initOkhttpAES(map);
        Gson gson = new GsonBuilder().setLenient().create();
        if (courceInfoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.CLOUDRENT)
                    .client(client)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(StringConverterFactory.create(gson))
                    .build();
//            Logger.e("URL="+retrofit.);
            courceInfoApi = retrofit.create(ProductInfoApi.class);

        }
        return courceInfoApi;
    }


    /**
     * 获取产品列表
     */
    public static InfomationApi getInfoApi(LinkedHashMap<String, String> map) {
        initOkhttpAES(map);
        Gson gson = new GsonBuilder().setLenient().create();
        if (infoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.CLASSROOM)
                    .client(client)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(StringConverterFactory.create(gson))
                    .build();
            infoApi = retrofit.create(InfomationApi.class);
        }
        return infoApi;
    }

    /**
     * 获取产品列表
     */
    public static Object getInfoApi(LinkedHashMap<String, String> map, Object t) {

        initOkhttpAES(map);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.CLASSROOM)
                .client(client)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        t = retrofit.create(t.getClass());

        return t;
    }

    public static Api getApi(LinkedHashMap<String, String> map) {
        initOkhttpAES(map);
        Gson gson = new GsonBuilder().setLenient().create();
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.CLASSROOM)
                    .client(client)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(StringConverterFactory.create(gson))
                    .build();
            api = retrofit.create(Api.class);
        }
        return api;
    }

}
