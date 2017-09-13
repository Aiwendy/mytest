package bluetoothtest.cw.com.testapplication.base;

import android.os.Bundle;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.LinkedHashMap;

import bean.CourseInfoEntity;
import bluetoothtest.cw.com.testapplication.R;
import cn.cwkj.bluetooth.utils.ToastUtils;
import http.Http;
import inter.OnLoadDataListener;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoordinateActivity extends BaseActivity implements OnLoadDataListener {

    /*    @BindView(R.id.home_listview)
        RecyclerView homeListview;
        @BindView(R.id.collapsing_toolbar)
        CollapsingToolbarLayout collapsingToolbar;
        @BindView(R.id.appbar)
        AppBarLayout appbar;
        @BindView(R.id.activity_coor_app_bar)*/

    Observer courseInfoEntityObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);
//        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        request(true);
    }

    @Override
    public void request(boolean isRefresh) {
        if (isRefresh) {
            ToastUtils.showShort(this, "加载中。。。");
        }
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        new Http(this).getCourseInfoApi(map).getCourseInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CourseInfoEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    Logger.e(String.valueOf(d.isDisposed()));
                    }

                    @Override
                    public void onNext(@NonNull CourseInfoEntity courseInfoEntity) {

                        if (1 == courseInfoEntity.getStatus()) {
//                            Logger.e(courseInfoEntity.toString());
                            Logger.e("*****************请求成功*******************");
                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("String", String.valueOf(e.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                    }
                });

    }
}
