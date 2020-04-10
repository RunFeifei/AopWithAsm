package com.run.learnaopwithasm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fei.asmdepend.TimeCache;

/**
 * Created by PengFeifei on 2020/4/7.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A();
            }
        });
    }

    public void A() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        B();
    }

    public void B() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        C();
    }

    public void C() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
