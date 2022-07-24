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

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewholder>{

    /*
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {

        holder.name.setText(model.getStudentName());
        holder.stuclass.setText(model.getStudentClass());
        holder.stuemail.setText(model.getEmail());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus;
                dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();


                View view1 =dialogPlus.getHolderView();
                EditText stuname=view1.findViewById(R.id.studentName);
                EditText studentclass=view1.findViewById(R.id.studentClass);
                EditText studentemail=view1.findViewById(R.id.email);

                Button btnUpdate=view1.findViewById(R.id.btnUpdate);

                stuname.setText(model.getStudentName());
                studentclass.setText(model.getStudentClass());
                studentemail.setText(model.getEmail());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("studentName",stuname.getText().toString());
                        map.put("studentClass",studentclass.getText().toString());
                        map.put("email",studentemail.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Students")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(),"error",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                });
                    }
                });

                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                        builder.setTitle("Are You Sure U want to delete?");
                        builder.setMessage("Deleted data cannot be regained");

                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Students")
                                        .child(getRef(position).getKey()).removeValue();


                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(holder.name.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();

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
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);

        return new myViewholder(view);
    }

    class myViewholder extends RecyclerView.ViewHolder{
        TextView stuclass,name,stuemail;

        Button btnEdit,btnDelete;

        public myViewholder(@NonNull View itemView) {
            super(itemView);

            name=(TextView) itemView.findViewById(R.id.studentName);
            stuclass=(TextView) itemView.findViewById(R.id.studentClass);
            stuemail=(TextView) itemView.findViewById(R.id.email);


            btnEdit=(Button) itemView.findViewById(R.id.btnEdit);
            btnDelete=(Button) itemView.findViewById(R.id.btnDelete);



        }
    }




}
