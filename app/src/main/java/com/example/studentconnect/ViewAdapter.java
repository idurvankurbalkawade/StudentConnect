package com.example.studentconnect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private Context context;
    private List<putPDF> pdfList;

    public ViewAdapter(Context context, List<putPDF> pdfList) {
        this.context = context;
        this.pdfList = pdfList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.pdf_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.pdfTitle.setText(pdfList.get(position).getTitle());

        holder.pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked!!!", Toast.LENGTH_SHORT).show();
                //Intent intent=new Intent(context,ViewPDF.class);
                //intent.putExtra("pdfURL",Uri.parse(pdfList.get(position).getUrl()));
                //context.startActivity(intent);

            }
        });

        holder.pdfDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(pdfList.get(position).getUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pdfTitle;
        private ImageView pdfDownload,pdfView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pdfTitle=(TextView) itemView.findViewById(R.id.pdfTitle);
            pdfDownload=(ImageView) itemView.findViewById(R.id.pdfIcon);
            pdfView=(ImageView) itemView.findViewById(R.id.pdfView);


        }
    }
}
