package bluetoothtest.cw.com.testapplication.base;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.LinkedHashMap;

import bean.InformationEntitiy;
import bluetoothtest.cw.com.testapplication.R;
import http.Http;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Main2Activity extends BaseActivity implements MediaPlayer.OnPreparedListener {
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private int index;
    private boolean isPlayed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initData();
        initAction();
    }

    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
    }

    private void initData() {
        isPlayed = false;
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });
    }

    private void initAction() {
request(true);
    }

    private void request(boolean b) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("userId", "9527");
        map.put("categoryId", "-1");
        map.put("pageNo", "1");
        map.put("pageSize", "10");
        new Http(this).getInfoApi(map).getInfo(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InformationEntitiy>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Logger.e(String.valueOf(d.isDisposed()));
                    }

                    @Override
                    public void onNext(@NonNull InformationEntitiy courseInfoEntity) {

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


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        index = mediaPlayer.getCurrentPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(isPlayed){
            mediaPlayer.seekTo(index);
            mediaPlayer.start();
        }else{
            mediaPlayer.start();
            isPlayed = true;
        }

    }

    private void start(){
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(surfaceView.getHolder());
            AssetManager assetManager = getAssets();
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("test1.mp4");
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0,0);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
