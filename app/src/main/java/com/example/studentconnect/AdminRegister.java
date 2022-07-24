package com.example.studentconnect;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegister extends AppCompatActivity {
    EditText afullname, aemail, apassword;
    Button aRegister;
    TextView aLoginbtn;
    FirebaseAuth fAuth;
    ProgressBar progress;

    FirebaseDatabase rootNode;
    DatabaseReference refernce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        afullname = findViewById(R.id.fullname);
        aemail = findViewById(R.id.adminemail);
        apassword = findViewById(R.id.adminpassword);
        aRegister = findViewById(R.id.registerbutton);
        aLoginbtn = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        progress = findViewById(R.id.adminprogress);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            finish();

        }

        aRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode=FirebaseDatabase.getInstance();
                refernce=rootNode.getReference("Admins");

                //get all values
                String name=afullname.getText().toString();
                String emailid=aemail.getText().toString();
                String adpassword=apassword.getText().toString();

                UserHelper userHelper=new UserHelper(name,emailid,adpassword);
                refernce.child(name).setValue(userHelper);



                String email = aemail.getText().toString().trim();
                String pass = apassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    aemail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    apassword.setError("Password is required");
                    return;
                }

                if (pass.length() < 6) {
                    apassword.setError("logged in to wrond panel");
                    return;
                }

                progress.setVisibility(View.GONE);

                //register the user
                fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(AdminRegister.this, "Admin Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),AdminLogin.class));

                        } else {
                            Toast.makeText(AdminRegister.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);

                        }

                    }
                });

                aLoginbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), AdminLogin.class));

                    }
                });

            }
        });


    }

//for menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_SHORT).show();

                return true;
            case R.id.logout:

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LauncherPage.class));
                finish();
                Toast.makeText(getApplicationContext(), "Bye!!!", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}