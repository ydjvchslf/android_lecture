package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //우리가 만들어준 recyclerView 선언
    RecyclerView recyclerView;

    String s1[], s2[];
    int images[] = {R.drawable.c, R.drawable.c_hashtag, R.drawable.java, R.drawable.js, R.drawable.kotlin,
           R.drawable.python, R.drawable.ruby, R.drawable.swift, R.drawable.typescript, R.drawable.vs };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, " : onCreate 실행"); //1번 실행

        //선언한 변수에 id로 찾아와서 변수에 할당!
        recyclerView = findViewById(R.id.recyclerView);

        //string.xml에 있는 string값들 가져오기
        s1 = getResources().getStringArray(R.array.programming_languages);
        s2 = getResources().getStringArray(R.array.description);

        //만든  MyAdapter Class를 쓰기 위해 new 생성
        MyAdapter myAdapter = new MyAdapter(this, s1, s2, images); //2번 생성자 실행
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // list 모양으로
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 격자 모양으로
    }
}