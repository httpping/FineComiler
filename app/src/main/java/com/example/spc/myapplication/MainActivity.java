package com.example.spc.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import tp.fine.layout.FineLayout;

@FineLayout(layout = "activity_main")
//@FineLayout(layout = "demo")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        InjectActivity.inject(this);//调用build生成的类
//        MainActivity$ViewHolder asdf = new  MainActivity$ViewHolder();
//        adb.tvTitle.set
        TextView textView;

        FineLayout$ActivityMain main;
//        MainActivity$$InjectActivity injectActivity = new MainActivity$$InjectActivity();

//        injectActivity.inject(this);
//        Object a = injectActivity.adbd ;
//        injectActivity.hellow();
//        injectActivity.
    }

    @Override
    public void onClick(View v) {

    }
}
