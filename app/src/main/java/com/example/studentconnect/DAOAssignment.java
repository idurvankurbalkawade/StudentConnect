package com.example.studentconnect;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOAssignment {
    private DatabaseReference databaseReference;

    public DAOAssignment() {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        databaseReference= db.getReference(Assignment.class.getSimpleName());
    }

    public Task<Void> add(Assignment assi)
    {
        return databaseReference.push().setValue(assi);
    }
}
