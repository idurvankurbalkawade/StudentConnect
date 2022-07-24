package com.example.studentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestion extends AppCompatActivity {
    EditText qid,q,a,b,c,d,answer;
    Button button;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_add_question);

        button=(Button)findViewById(R.id.addq);
        qid=(EditText)findViewById(R.id.qid);
        q=(EditText)findViewById(R.id.ques);
        a=(EditText)findViewById(R.id.A);
        b=(EditText)findViewById(R.id.B);
        c=(EditText)findViewById(R.id.C);
        d=(EditText)findViewById(R.id.D);
        answer=(EditText)findViewById(R.id.Ans);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qid1= qid.getText().toString();
                String q1=q.getText().toString();
                String a1=a.getText().toString();
                String b1=b.getText().toString();
                String c1=c.getText().toString();
                String d1=d.getText().toString();
                String answer1=answer.getText().toString();



                ModelClass model=new ModelClass(a1,b1,c1,d1,q1,answer1);


                myRef.child(qid1).setValue(model);
                Toast.makeText(getApplicationContext(), "Value Inserted", Toast.LENGTH_SHORT).show();

                qid.setText("");
                q.setText("");
                a.setText("");
                b.setText("");
                c.setText("");
                d.setText("");
                answer.setText("");
            }
        });


    }
}