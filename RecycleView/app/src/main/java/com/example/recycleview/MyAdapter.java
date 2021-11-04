package com.example.recycleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

//새로운 class 생성, RecyclerView.Adapert<> 상속하는
//innerClass 선언
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static final String TAG = "MyAdapter";

    Context context;
    String data1[], data2[];
    int imgs[];

    //생성자 생성, 4개 parameters를 받는
    public  MyAdapter(Context ct, String s1[], String s2[], int images[] ){

        Log.d(TAG," : 4개 params 생성자"); // 메인에서 넘어와서 2번 생성자 실행

        context = ct;
        data1 = s1;
        data2 = s2;
        imgs = images;
    }

    @NonNull
    @Override //OR 해주어야 함
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, " : onCreateViewHolder 실행"); //3번 실행

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
}

    @Override //어떤 데이터를 넣을지 배정하는? 메서드
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Log.d(TAG, " : onBindViewHolder 실행"); //5번 실행

        holder.myText1.setText(data1[position]); //동적으로 데이터 배정
        holder.myText2.setText(data2[position]);
        holder.myImage.setImageResource(imgs[position]);

        // + 추가
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // SecondActivity에 넣어줄 내용을 intent에 저장해줄거야
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("data1", data1[position]);
                intent.putExtra("data2", data2[position]);
                intent.putExtra("imgs", imgs[position]);

                context.startActivities(new Intent[]{intent});

            }
        });
    }

    @Override
    public int getItemCount() {
        //return images.length; // string도 10개니까 s1의 갯수로 해줘도 되지 않을까? ㅇㅇ 됨~
        Log.d(TAG," : getItemCount 실행");
        return  data1.length; // Q. 호출되는 갯수가 매번 다른데, 왜그럴까?
    }

    // 추가 해주어야 함
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "MyAdapter";

        TextView myText1, myText2;
        ImageView myImage;

        //Layout에 추가한 id를 이곳에 추가
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.d(TAG," : InnerClass - MyViewHolder 실행"); //4번 실행

            myText1 = itemView.findViewById(R.id.myText1);
            myText2 = itemView.findViewById(R.id.myText2);
            myImage = itemView.findViewById(R.id.myImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout); // + 추가
        }
    }
}
