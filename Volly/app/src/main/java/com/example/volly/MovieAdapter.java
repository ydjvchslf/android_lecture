package com.example.volly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

//recyclerView는 선택위젯, 쓰기 위해서는 어댑터가 필요!
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    ArrayList<Movie> items = new ArrayList<Movie>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Movie item){
        items.add(item);
    }

    public void setItems(ArrayList<Movie> items){
        this.items = items;
    }

    public Movie getItem(int position){
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView movieNm;
        TextView audiCnt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //View 안에서 id로 찾아주기
            movieNm = itemView.findViewById(R.id.movieNm);
            audiCnt = itemView.findViewById(R.id.audiCnt);

        }

        public void setItem(Movie item){
            movieNm.setText(item.movieNm);
            audiCnt.setText(item.audiCnt + " 명");
        }
    }
}
