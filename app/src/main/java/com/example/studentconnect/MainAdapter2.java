package com.example.studentconnect;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class MainAdapter2 extends FirebaseRecyclerAdapter<MainModel2,MainAdapter2.myViewHolder2> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter2(@NonNull FirebaseRecyclerOptions<MainModel2> options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder2 holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel2 model) {
        holder.tname.setText(model.getFullname());
        holder.subject.setText(model.getSubject());
        holder.email.setText(model.getEmail());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus;
                dialogPlus = DialogPlus.newDialog(holder.tname.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup2))
                        .setExpanded(true,1200)
                        .create();


                View view1 =dialogPlus.getHolderView();
                EditText tname=view1.findViewById(R.id.txtName);
                EditText tsubject=view1.findViewById(R.id.txtSubject);
                EditText temail=view1.findViewById(R.id.txtEmail);

                Button btnUpdate=view1.findViewById(R.id.btnUpdate);

                tname.setText(model.getFullname());
                tsubject.setText(model.getSubject());
                temail.setText(model.getEmail());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("fullName",tname.getText().toString());
                        map.put("subject",tsubject.getText().toString());
                        map.put("email",temail.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Teachers")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.tname.getContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.tname.getContext(),"error",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                });
                    }
                });

                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.tname.getContext());
                        builder.setTitle("Are You Sure U want to delete?");
                        builder.setMessage("Deleted data cannot be regained");

                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Teachers")
                                        .child(getRef(position).getKey()).removeValue();


                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(holder.tname.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();

                            }
                        });
                        builder.show();
                    }
                });


            }
        });






    }

    @NonNull
    @Override
    public myViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item2,parent,false);
        return new myViewHolder2(view);
    }

    class myViewHolder2 extends RecyclerView.ViewHolder{
        TextView tname, subject,email;
        Button btnEdit,btnDelete;

        public myViewHolder2(@NonNull View itemView) {
            super(itemView);
            tname=(TextView) itemView.findViewById(R.id.nametext);
            subject=(TextView) itemView.findViewById(R.id.subjecttext);
            email=(TextView) itemView.findViewById(R.id.emailtext);

            btnEdit=(Button) itemView.findViewById(R.id.btnEdit);
            btnDelete=(Button) itemView.findViewById(R.id.btnDelete);

        }
    }
}
