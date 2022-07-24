package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubmittedAssignment extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    Adapter Ad;
    ArrayList<Teacher1> List;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_assignment);


        recyclerView=findViewById(R.id.TeacherList);
        databaseReference= FirebaseDatabase.getInstance().getReference("Submitted Assignments");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List = new ArrayList<>();
        Ad= new Adapter(this,List);
        recyclerView.setAdapter(Ad);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){


                    Teacher1 Teacher =dataSnapshot.getValue(Teacher1.class);
                    List.add(Teacher);

                }
                Ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}