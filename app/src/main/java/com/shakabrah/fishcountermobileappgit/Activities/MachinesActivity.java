package com.shakabrah.fishcountermobileappgit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shakabrah.fishcountermobileappgit.FirebaseFuncions;
import com.shakabrah.fishcountermobileappgit.GridAdapter4Machines;
import com.shakabrah.fishcountermobileappgit.R;

import java.util.ArrayList;

public class MachinesActivity extends AppCompatActivity implements View.OnClickListener{
    FirebaseFuncions firebaseFuncions = new FirebaseFuncions(this);
    Button Machines_Logout_btn;
    ArrayList<String> userMachines = new ArrayList<>();
    GridAdapter4Machines gridAdapter4Machines;
    GridView gridView;
    String deger;
    Bundle b = new Bundle();


    public void setDeger(String deger) {
        this.deger = deger;
    }

    public String getDeger() {
        return deger;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.Listing));
        progressDialog.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machines);
        firebaseFuncions.NavigateWhileNotLoged();
        Machines_Logout_btn = findViewById(R.id.Machines_Logout_btn);
        Machines_Logout_btn.setOnClickListener(this);

        gridAdapter4Machines = new GridAdapter4Machines(this,userMachines);
        gridView = findViewById(R.id.gridView_Machines);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setDeger(userMachines.get(i));
                Intent intent = new Intent(MachinesActivity.this, MainActivity.class);

                b.putString("key", getDeger());
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        firebaseFuncions.FirebaseRef("Companies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userMachines.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(firebaseFuncions.getUserMail().compareTo((String) ds.child("Mail").getValue()) == 0) {
                        for (DataSnapshot ds1 : ds.child("userMachines").getChildren()){
                            userMachines.add(String.valueOf(ds1.getValue()));
                            gridView.setAdapter(gridAdapter4Machines);
                        }

                        break;
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MachinesActivity.this, getResources().getString(R.string.onCanceled), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view ==  Machines_Logout_btn){
            firebaseFuncions.signOut();
            System.out.println("----------------------------------->>> " + Machines_Logout_btn.getText());
        }
    }
}
