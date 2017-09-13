package bluetoothtest.cw.com.testapplication.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.android.ActivityEvent;

import bluetoothtest.cw.com.testapplication.R;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;


/**
 * Function:
 * Created by zhang di on 2017-06-30.
 */

public  abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        initStatusBar();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initStatusBar() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                /*顶部状态栏*/
                window.setStatusBarColor(getResources().getColor(R.color.green));
                /*底部导航栏*/
                window.setNavigationBarColor(getResources().getColor(R.color.green));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void intent(Class<?> cla) {
        startActivity(new Intent(this, cla));
    }

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();


}