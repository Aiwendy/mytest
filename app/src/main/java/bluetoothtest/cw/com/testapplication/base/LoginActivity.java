package bluetoothtest.cw.com.testapplication.base;

import android.os.Bundle;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.LinkedHashMap;

import bean.InformationEntitiy;
import bluetoothtest.cw.com.testapplication.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.cwkj.bluetooth.utils.ToastUtils;
import http.DisposableManager;
import http.Http;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import material.MaterialEditText;

public class LoginActivity extends BaseActivity{

    @BindView(R.id.name)
    MaterialEditText name;
    @BindView(R.id.pwd)
    MaterialEditText pwd;
    Observer observer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
    }
    private void initData() {
        request(true);
    }

    public void request(boolean isRefresh) {
        if (isRefresh) {
            ToastUtils.showShort(this, "加载中。。。");
        }

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("userId", "9527");
        map.put("categoryId", "-1");
        map.put("pageNo", "1");
        map.put("pageSize", "10");

/*
        Http.getInstance(this).getApi(map).getInfo(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<InformationEntitiy>() {
                    @Override
                    public void accept(InformationEntitiy informationEntitiy) throws Exception {
                        if (1 == informationEntitiy.getStatus()) {
                            Logger.e(informationEntitiy.toString());
                            Logger.e("*************************accept1成功返回**************************************");
                        }
                    }
                })
                .subscribe(new Consumer<InformationEntitiy>() {
                    @Override
                    public void accept(InformationEntitiy informationEntitiy) throws Exception {
                        if (1 == informationEntitiy.getStatus()) {
                            Logger.e(informationEntitiy.toString());
                            Logger.e("*************************accept2成功返回**************************************");
                        }
                    }
                });*/


        Http.getInstance(this).getApi(map).getInfo(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<InformationEntitiy>() {
                    @Override
                    public void accept(InformationEntitiy informationEntitiy) throws Exception {
                        if (1 == informationEntitiy.getStatus()) {
                            Logger.e("*************************accept1成功返回**************************************");
                        }
                    }
                })
                .subscribe(new Observer<InformationEntitiy>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        DisposableManager.instance().add(d);
                    }

                    @Override
                    public void onNext(@NonNull InformationEntitiy informationEntitiy) {
                        Logger.e("*************************accept2成功返回**************************************");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }





    @Override
    protected void onStop() {
        super.onStop();
        DisposableManager.instance().dispose();
        finish();
    }
}
