package com.example.volly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //라이브러리 Volly를 씀으로써 내부적으로 쓰레드, 핸들러까지 다 사용하게끔 만들어줌

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}