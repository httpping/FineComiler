package com.example.spc.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import tp.fine.layout.FineLayout;

@FineLayout(layout = "activity_main",className = "Abc",ignoreId = {"iv_ssx"},ignoreView = {"ImageView"})
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    FineLayout$ItemCart7 root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        InjectActivity.inject(this);//调用build生成的类
//        MainActivity$ViewHolder asdf = new  MainActivity$ViewHolder();
//        adb.tvTitle.set
        TextView textView;
//        MainActivity$$InjectActivity injectActivity = new MainActivity$$InjectActivity();

//        injectActivity.inject(this);
//        Object a = injectActivity.adbd ;
//        injectActivity.hellow();
//        injectActivity.
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
//        FineLayout$ItemCart7 root = FineLayout$ItemCart7.init(rootView);
//        FineLayout$ActivityMain main = FineLayout$ActivityMain.init(rootView);

//        Demo$A abc = Demo$A.init(null);
//        abc.abcview = null;

//        FineLayout$ActivityMain layout = FineLayout$ActivityMain.init(null);
//        layout.recy = null;

        int va;
//        va = getAd();
        //        Toast.makeText(this,"ad :" + va,Toast.LENGTH_LONG).show();
//        Toast.makeText(this,"ad :" + getHello(),Toast.LENGTH_LONG).show();

       /* getAd();

        getHello();*/

       Abc abc = Abc.init(rootView);
       String text = abc.tvTest.getText().toString();
        Toast.makeText(this,"ad :" + text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

    }


    public String hello = "helllo world";

    private int ad =2323;
}
