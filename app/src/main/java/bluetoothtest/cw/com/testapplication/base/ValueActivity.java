package bluetoothtest.cw.com.testapplication.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import bean.Bean;
import bluetoothtest.cw.com.testapplication.R;
import inter.CallBack;

public class ValueActivity extends BaseActivity {

    private TextView tv;
   public CallBack valueCallBack;

    public ValueActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value);
        tv = (TextView) findViewById(R.id.textview);

       final  Bean bean = (Bean) getIntent().getSerializableExtra("value");
        Log.e("传过来的地址", bean+"bean.toString()="+bean.toString());

        bean.setValue("bbb");
        Log.e("bean.setValue()后的地址", bean+"bean.toString()="+bean.toString());

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueCallBack.answer("答案2");
//                EventBus.getDefault().post(bean);
            }
        });
    }

    public void setValueCallBack(CallBack callBack,String ques) {
        this.valueCallBack = callBack;
        Log.e("问题是", ques);
    }

}
