package bluetoothtest.cw.com.testapplication.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Random;

import bean.Bean;
import bean.UserInfoEntity;
import bluetoothtest.cw.com.testapplication.R;
import butterknife.ButterKnife;
import cn.cwkj.bluetooth.utils.ToastUtils;
import http.Http;
import inter.CallBack;
import inter.OnLoadDataListener;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import util.JsonUtils;
import util.PhoneUtil;

public class MainActivity extends BaseActivity implements OnLoadDataListener,CallBack {


    ValueActivity valueActivity = new ValueActivity();


    private Observer<UserInfoEntity> userInfoEntityObserver;
    private int REQUEST_PHONE_STATE = 10000;
    private String json = "{[{\"a\": 0,\"o\": 0,\"t\": 0,\"y\": 0},{\"a\": 0,\"o\": 0,\"t\": 0,\"y\": 0m,{\"aw22: 140,\"o\": 0,\"t\": 0,\"y\": 0},{\"a\": 0,\"o\": 0,\"t\": 0,\"y\": 0},{\"a\": 0,\"o\": 0,\"t\": 0,\"y\": 0}]}";
    private String json1 = "{[{\"a\": 100,\"o\": 101,\"t\": 102,\"y\": 103},{\"a\": 201,\"o\": 202,\"t\": 203,\"y\": 204},{\"a\":301,\"o\":302,\"t\": 303,\"y\": 304}]}";
    private Bean bean;
    private String tag = "MainActivity";
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initAuth();
        initData();
       valueActivity.setValueCallBack(this,"1+1=");


    }

    @Override
    public void answer(String tran) {
        Log.e("传回来后地址", bean + "bean.toString()=" + bean.toString()+"答案是="+tran);
    }

    private void initAuth() {
        //Android6.0需要动态获取权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            toast("需要动态获取权限");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
        } else {
//            toast("不需要动态获取权限");
            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            PhoneUtil.setPhoneImei(TelephonyMgr.getDeviceId());
        }

    }

    private void initData() {
        userInfoEntityObserver = new Observer<UserInfoEntity>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
//                    Logger.e(String.valueOf(d));
            }

            @Override
            public void onNext(@NonNull UserInfoEntity userInfoEntity) {
                if (userInfoEntity == null) return;
                if (userInfoEntity.userinfo != null)
                    Logger.json(new Gson().toJson(userInfoEntity.userinfo));
                if (userInfoEntity.babyInfo != null)
                    Logger.json(new Gson().toJson(userInfoEntity.babyInfo));
                Logger.d(userInfoEntity.unableBook);
                Logger.d(userInfoEntity.state);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.e(String.valueOf(e.getMessage()));
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        };
        request(true);

        findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag, "onViewClicked");
                if (new JsonUtils().validate(json1)) {
                    Log.e(tag, "onViewClicked: 合法");
                } else {
                    Log.e(tag, "onViewClicked: 不合法");
                }
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag, "onViewClicked");
                intent(LoginActivity.class);
            }
        });
        findViewById(R.id.video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag, "onViewClicked");
                intent(Main2Activity.class);
            }
        });
        findViewById(R.id.coordinate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent(CoordinateActivity.class);
            }
        });
        findViewById(R.id.flowlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent(FlowlayoutActivity.class);
            }
        });
        findViewById(R.id.v_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent(VLayoutActivity.class);
            }
        });
        findViewById(R.id.value_layout).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ValueActivity.class);

                bean = new Bean("aaa", 1);
                Log.e("原地址", bean + "bean.toString()=" + bean.toString());
                intent.putExtra("value", bean);
                startActivity(intent);
            }
        });
    }

    @Override
    public void request(boolean isRefresh) {
        if (isRefresh) {
            ToastUtils.showShort(this, "加载中。。。");
        }

        new Http(this).getUserInfoApi()
                .getUserInfo("1333161")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfoEntityObserver);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_STATE && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            PhoneUtil.setPhoneImei(TelephonyMgr.getDeviceId());
        }

    }

    @Subscribe
    public void onEventMainThread(Bean a) {
        Log.e("传回的地址", a + "bean.toString()=" + a.toString());
        Log.e("传回的地址", bean + "bean.toString()=" + bean.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
}
