package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    ArrayList<Game> gameArrayList;
    MainActivity mainActivity;

    public GameAdapter(ArrayList<Game> gameArrayList, Context context) {
        this.gameArrayList = gameArrayList;
        mainActivity=(MainActivity)context;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        GameViewHolder viewHolder=new GameViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GameViewHolder holder, int position) {
        final Game game = gameArrayList.get(position);
        holder.tvCity.setText(game.getCity());
        holder.tvDate.setText(game.getDate());
        holder.tvGroupA.setText(game.getGroupA());
        holder.tvGroupB.setText(game.getGroupB());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setGameData(game);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameArrayList.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity, tvDate, tvGroupA, tvGroupB;
        View view;

        public GameViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            tvCity=(TextView)itemView.findViewById(R.id.tvCity);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvGroupA=(TextView)itemView.findViewById(R.id.tvGroupA);
            tvGroupB=(TextView)itemView.findViewById(R.id.tvGroupB);
        }
    }
}
