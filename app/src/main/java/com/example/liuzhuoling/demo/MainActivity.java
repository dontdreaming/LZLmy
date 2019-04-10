package com.example.liuzhuoling.demo;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    final String PACKAGE_NAME = "com.example.liuzhuoling.demo";
    final String VERSION_KEY = "MydemoPr";
    int currentVersion;
    int lastVersion;
    SharedPreferences prefs;
    TextView textView;
    Button button;
    long currentTime;
    long savetime;
    long s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        currentVersion = info.versionCode;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        lastVersion = prefs.getInt(VERSION_KEY, 0);


        currentTime = System.currentTimeMillis();
        savetime = prefs.getLong("time", currentTime);
        s = (currentTime - savetime) / (1000 * 60 * 60 * 24);
        prefs.edit().putLong("time",currentTime).commit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lzl","currentTime "+currentTime);
                Log.d("lzl","savetime "+savetime);
                Log.d("lzl","s "+s);
                if (currentVersion > lastVersion) {
                    //如果当前版本大于上次版本，该版本属于第一次启动
                    //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
                    prefs.edit().putInt(VERSION_KEY, currentVersion).commit();
                    textView.setText("欢迎初次使用");
                } else if (s <3) {
                    textView.setText("欢迎经常使用");
                }else if(s>=3){
                    textView.setText("好久不见欢迎再次使用");
                }
            }
        });
    }



}
