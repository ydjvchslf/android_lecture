package com.example.volly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //라이브러리 Volly를 씀으로써 내부적으로 쓰레드, 핸들러까지 다 처리해줌

    EditText editText;
    TextView textView;

    static RequestQueue requestQueue;

    //recyclerView 추가부분
    RecyclerView recyclerView;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        //recyclerView 추가부분
        recyclerView = findViewById(R.id.recylerView);
        //레이아웃 어떻게 할지, list형 or grid형
//        LinearLayoutManager layoutManager = new LinearLayoutManager
//                (this, LinearLayoutManager.VERTICAL, true);
//        recyclerView.setLayoutManager(layoutManager);


        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlStr = editText.getText().toString();
                request(urlStr);
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());


    }

    public void request(String urlStr){
        //Volly는 요청객체를 만들고 Que안에 넣으면 ui에서 처리할 수 있도록 응답을 return 해줌!
        StringRequest request = new StringRequest(
                Request.Method.GET,
                urlStr, // urlStr을 GET방식으로 보냈을 때 정상적으로 Respose가 오면 받아주는 메서드
                new Response.Listener<String>() {
                    @Override //정상 응답
                    public void onResponse(String response) {
                        println("응답 -> " + response); //응답값을 확인해 보면 JSON 포맷으로 되어 있음!

                        //이 JSON 응답값을 넣어줄 class를 정의해줬고 (Movie, MovieList, MovieListResult)
                        //처리해줄거야
                        processRespons(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override //에러 응답
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> " + error.toString());
                    }
                }
        ){
            @Override //만들어진 요청 객체
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<String, String >();

               return params; //이 요청객체를 que안에 넣으러 출발 (위로)
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
        println("요청 보냄");
    }

    //응답 문자열을 처리하기 위한 메서드
    public void processRespons(String response){
        Gson gson = new Gson();

        //JSON객체를 받아오는 함수를 이용해서 우리가 class로 정의해준 객체로 담아준다
        MovieList movieList = gson.fromJson(response, MovieList.class);
        println("영화 정보의 수 : " + movieList.boxOfficeResult.dailyBoxOfficeList.size());

        //recyclerView에 리스트로 보여줄 포문 추가~!
        for (int i = 0; i < movieList.boxOfficeResult.dailyBoxOfficeList.size(); i++){
            Movie movie = movieList.boxOfficeResult.dailyBoxOfficeList.get(i);
            adapter.addItem(movie);
        }

        adapter.notifyDataSetChanged();

    }

    //응답이나 에러가 오면 출력해주는 메서드 정의
    public void println(String data){
        textView.append(data + "\n");
    }


}