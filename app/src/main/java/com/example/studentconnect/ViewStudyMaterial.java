package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudyMaterial extends AppCompatActivity {

    RecyclerView pdfView;
    DatabaseReference reference;
    ImageButton back;
    List<putPDF> pdfList;
    ViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_view_study_material);

        pdfView=(RecyclerView) findViewById(R.id.viewPdf);
        back=(ImageButton) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(getApplicationContext(),StudentDashboard.class);
                finishAffinity();
                startActivity(backIntent);
            }
        });


        retrievePDFFiles();

    }

    private void retrievePDFFiles() {
        reference=FirebaseDatabase.getInstance().getReference("uploadPDF");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfList=new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren())
                {
                    putPDF putPDF=ds.getValue(com.example.studentconnect.putPDF.class);
                    pdfList.add(putPDF);
                }

                adapter=new ViewAdapter(ViewStudyMaterial.this,pdfList);
                pdfView.setLayoutManager(new LinearLayoutManager(ViewStudyMaterial.this));
                pdfView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}