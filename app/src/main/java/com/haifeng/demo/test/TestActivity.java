package com.haifeng.demo.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.haifeng.demo.lib.Function;
import com.haifeng.demo.lib.Observable;
import com.haifeng.demo.lib.ObservableCreate;
import com.haifeng.demo.lib.ObservableOnSubscribe;
import com.haifeng.demo.lib.Observer;
import com.haifeng.demo.lib.Schedulers;
import com.joy.littlerx.R;

import java.util.Queue;


public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "test";

    private Button haifengTest;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClickListener();
    }

    private void initView() {

        haifengTest = findViewById(R.id.haifeng_test_rxjava);
    }

    private void initClickListener() {

        haifengTest.setOnClickListener(this);
        final TestZip testZip =new TestZip();

        findViewById(R.id.send_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testZip.zipObservers[0].queue.offer(1);
                testZip.test();
                testZip.zipObservers[0].queue.offer(2);
                testZip.test();
                testZip.zipObservers[0].queue.offer(5);
                testZip.test();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testZip.zipObservers[1].queue.offer("3");
                        testZip.test();
                        testZip.zipObservers[1].queue.offer("4");
                        testZip.test();
                    }
                },3000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {


            case R.id.haifeng_test_rxjava:
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableCreate.Emitter<Integer> emitter) throws Exception {

                        emitter.next(1);
                        emitter.next(2);
                    }
                }).map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer r) {
                        return r+"";
                    }
                }).subscribeOn(Schedulers.IO)
                        .observerOn(Schedulers.MAIN)
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe() {


                                Log.i(TAG, "onSubscribe");
                            }

                            @Override
                            public void onNext(String value) {
                                Log.i(TAG, "onNext："+value);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "onError");
                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAG, "onComplete");
                            }
                        });

                break;
            default:
                break;

        }
    }


}
