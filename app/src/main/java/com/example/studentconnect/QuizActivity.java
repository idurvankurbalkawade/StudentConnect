package com.example.studentconnect;
import static com.example.studentconnect.R.id;
import static com.example.studentconnect.R.layout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class QuizActivity extends AppCompatActivity {
    CountDownTimer countdowntimer;
    int timerValue = 20;
    ProgressBar pb;
    //Collections.shuffle(qList);
    ModelClass md;
    int index = 0;
    ArrayList<ModelClass> listOfQ;
    TextView q, a, b, c, d;
    ImageView i1;
    CardView cq, ca, cb, cc, cd;
    int correctCount = 0;
    int wrongCount = 0;
    Button btn1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_quiz);

        Hooks();
        listOfQ = new ArrayList<>();

        Log.d("Message", "Children are : ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                int n = (int) dataSnapshot.getChildrenCount();
                Log.d("Message1", "Children are : " + n);
                Log.d("Happy","Look, I an in control!!!");



           /*     for(int i=1;i<=n;i++){
                    String A=dataSnapshot.child(Integer.toString(i)).child("oA").getValue().toString();
                    Log.d("oA",A);
                    String B=dataSnapshot.child(Integer.toString(i)).child("oB").getValue().toString();
                    Log.d("oB",B);
                    String C=dataSnapshot.child(Integer.toString(i)).child("oC").getValue().toString();
                    Log.d("oC",C);
                    String D=dataSnapshot.child(Integer.toString(i)).child("oD").getValue().toString();
                    Log.d("oD",D);
                    String Q=dataSnapshot.child(Integer.toString(i)).child("question").getValue().toString();
                    Log.d("question",Q);
                    String A1=dataSnapshot.child(Integer.toString(i)).child("ans").getValue().toString();
                    Log.d("ans",A1);
                    listOfQ.add(new Modelclass(A,B,C,D,Q,A1));

                }*/
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ModelClass model=snapshot.getValue(ModelClass.class);
                    listOfQ.add(model);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("Message2", "Failed to read value.", error.toException());
            }
        });



        listOfQ.add(new ModelClass("Guido van Rossum", "James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Who invented Java Programming?", "James Gosling"));
        //   listOfQ.add(new Modelclass("JVM", "JIT", "JGT", "JDK", "Which component is used to compile, debug and execute the java programs?", "JDK"));
        //   listOfQ.add(new Modelclass("Object-oriented", "Use of Pointers", "Portable", "Dynamic and Extensible", "Which one of the following is not a Java feature?", "Use of Pointers"));
        md = listOfQ.get(index);


        setAllData();
        pb = (ProgressBar) findViewById(id.pgbar);
        countdowntimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                timerValue -= 1;
                pb.setProgress(timerValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(QuizActivity.this);
                dialog.setContentView(layout.timeout_dialog);
                dialog.show();

                dialog.findViewById(id.btn_tryagain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });

            }
        }.start();
    }

    private void setAllData() {
        q.setText(md.getQuestion());
        a.setText(md.getoA());
        b.setText(md.getoB());
        c.setText(md.getoC());
        d.setText(md.getoD());
    }

    private void Hooks() {
        q = (TextView) findViewById(id.card_question);
        a = (TextView) findViewById(id.card_questionA);
        b = (TextView) findViewById(id.card_questionB);
        c = (TextView) findViewById(id.card_questionC);
        d = (TextView) findViewById(id.card_questionD);

        cq = (CardView) findViewById(id.cQ);
        ca = (CardView) findViewById(id.cA);
        cb = (CardView) findViewById(id.cB);
        cc = (CardView) findViewById(id.cC);
        cd = (CardView) findViewById(id.cD);
        //btn1.setClickable(false);
        btn1 = (Button) findViewById(id.next);
        i1=(ImageView) findViewById(id.ic_back);

    }
    public void back(View view){
        Intent intent=new Intent(QuizActivity.this,QuizList.class);
        startActivity(intent);

    }

    public void Correct(CardView c) {
        //ca.setCardBackgroundColor(getResources().getColor(R.color.green));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c.setBackgroundColor(ContextCompat.getColor(QuizActivity.this,
                        R.color.white));
                //c.setBackgroundColor(getResources().getColor(R.color.white));
                enableButton();
                correctCount++;
                index++;
                md = listOfQ.get(index);
                setAllData();
                //resetColor();

            }
        });


    }

    public void Wrong(CardView c) {
        //c.setCardBackgroundColor(getResources().getColor(R.color.red));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setBackgroundColor(ContextCompat.getColor(QuizActivity.this, R.color.white));
                //c.setBackgroundColor(getResources().getColor(R.color.white));
                enableButton();
                wrongCount++;
                if (index < listOfQ.size() - 1) {
                    index++;
                    md = listOfQ.get(index);
                    setAllData();
                    // resetColor();


                } else {
                    gameWon();
                }
            }
        });


    }

    private void gameWon() {
        Intent intent = new Intent(QuizActivity.this, WonActivity.class);
        intent.putExtra("correct", correctCount);
        intent.putExtra("wrong", wrongCount);
        startActivity(intent);
        //finishAndRemoveTask();
    }


    public void enableButton() {
        ca.setClickable(true);
        cb.setClickable(true);
        cc.setClickable(true);
        cd.setClickable(true);


    }

    public void disableButton() {
        ca.setClickable(false);
        cb.setClickable(false);
        cc.setClickable(false);
        cd.setClickable(false);


    }

    public void optionAclick(View view) {
        disableButton();
        //Toast.makeText(getApplicationContext(),"Selected A",Toast.LENGTH_SHORT).show();
        btn1.setClickable(true);
        if (md.getoA().equals(md.getAns())) {
            ca.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            //ca.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < listOfQ.size() - 1) {
                Correct(ca);
            } else {
                correctCount++;
                gameWon();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Woahhhhh", Toast.LENGTH_SHORT).show();
            ca.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            //ca.setCardBackgroundColor(getResources().getColor(R.color.red));
            Wrong(ca);
        }
    }

    public void optionBclick(View view) {
        disableButton();
        Toast.makeText(getApplicationContext(), "Selected B", Toast.LENGTH_SHORT).show();
        btn1.setClickable(true);
        if (md.getoB().equals(md.getAns())) {
            //cb.setCardBackgroundColor(getResources().getColor(R.color.green));
            cb.setBackgroundColor(ContextCompat.getColor(this, R.color.green));

            if (index < listOfQ.size() - 1) {

                Correct(cb);
            } else {
                correctCount++;
                gameWon();
            }
        } else {
            cb.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            //cb.setCardBackgroundColor(getResources().getColor(R.color.red));
            Wrong(cb);
        }
    }

    public void optionCclick(View view) {
        disableButton();
        Toast.makeText(getApplicationContext(), "Selected C", Toast.LENGTH_SHORT).show();
        btn1.setClickable(true);
        if (md.getoC().equals(md.getAns())) {
            cc.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            //cc.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < listOfQ.size() - 1) {
                Correct(cc
                );
            } else {
                correctCount++;
                gameWon();
            }
        } else {
            cc.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            //cc.setCardBackgroundColor(getResources().getColor(R.color.red));
            Wrong(cc);
        }
    }


    public void optionDclick(View view) {
        disableButton();
        Toast.makeText(getApplicationContext(), "Selected D", Toast.LENGTH_SHORT).show();
        btn1.setClickable(true);
        if (md.getoD().equals(md.getAns())) {
            cd.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            //cd.setCardBackgroundColor(getResources().getColor(R.color.green));
            if (index < listOfQ.size() - 1) {
                Correct(cd);
            } else {
                correctCount++;
                gameWon();
            }
        } else {
            cd.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            //cd.setCardBackgroundColor(getResources().getColor(R.color.red));
            Wrong(cd);
        }
    }

}