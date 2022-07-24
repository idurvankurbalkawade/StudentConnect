package com.example.studentconnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;



    ArrayList<User> list;

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        User user=list.get(position);
        holder.endTime.setText(user.getEndTime());
        holder.instructions.setText(user.getInstructions());
        holder.name.setText(user.getName());
        holder.startTime.setText(user.getStartTime());
        holder.title.setText(user.getTitle());
        holder.url.setText(user.getUrl());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView endTime;
        TextView instructions;
        TextView name;
        TextView startTime;
        TextView title;
        TextView url;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            endTime=itemView.findViewById(R.id.endtime);
            instructions=itemView.findViewById(R.id.instructions);
            name=itemView.findViewById(R.id.pdf);
            startTime=itemView.findViewById(R.id.startTime);
            title=itemView.findViewById(R.id.title1);
            url=itemView.findViewById(R.id.url);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent =new Intent(view.getContext(),Display.class);
                    intent.putExtra("e",endTime.getText());
                    intent.putExtra("i",instructions.getText());
                    intent.putExtra("n",name.getText());
                    intent.putExtra("s",startTime.getText());
                    intent.putExtra("t",title.getText());
                    intent.putExtra("u",url.getText());






                    view.getContext().startActivity(intent);
                }
            });
        }

    }

}