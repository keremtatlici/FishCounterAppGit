package com.shakabrah.fishcountermobileappgit.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shakabrah.fishcountermobileappgit.Database_Items;
import com.shakabrah.fishcountermobileappgit.FirebaseFuncions;
import com.shakabrah.fishcountermobileappgit.GridAdapter;
import com.shakabrah.fishcountermobileappgit.R;

import java.util.ArrayList;
import java.util.Collections;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseFuncions firebaseFuncions = new FirebaseFuncions(this);
    String SpecificDate;
    com.shakabrah.fishcountermobileappgit.Database_Items Sayimy;
    Button Go2MainPage,btn_ListData,tarih_btn;
    ArrayList<Database_Items> Database_Items;
    ArrayList<Database_Items> Database_Items_SpecificDate;
    GridAdapter gridAdapter,gridAdapter_Specific;
    GridView gridView;
    EditText tarih_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        final  ProgressDialog progressDialog = new ProgressDialog(this);
        Database_Items = new ArrayList<>();
        Database_Items_SpecificDate = new ArrayList<>();

        gridAdapter = new GridAdapter(this,Database_Items);
        gridAdapter_Specific = new GridAdapter(this,Database_Items_SpecificDate);

        gridView = findViewById(R.id.gridView);
        tarih_btn = findViewById(R.id.tarih_btn);
        tarih_editText = findViewById(R.id.tarih_editText);
        Go2MainPage = findViewById(R.id.MainPage);
        btn_ListData = findViewById(R.id.btn_ListData);

        tarih_btn.setEnabled(false);
        Go2MainPage.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        String  value = ""; // or other values
        if(b != null)
            value = b.getString("key");

        tarih_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tarih_btn.setEnabled(!tarih_editText.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        final ValueEventListener SpecificDate_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Database_Items_SpecificDate.clear();
                if(dataSnapshot.hasChild("1"))
                {
                    for(DataSnapshot SonSayimSnapshot_ustfor: dataSnapshot.getChildren()){

                        Sayimy.setSayimKey(SonSayimSnapshot_ustfor.getKey());
                        String sayimKey_max = SonSayimSnapshot_ustfor.getKey();

                        Sayimy = dataSnapshot.child(Sayimy.getSayimKey()).getValue(Database_Items.class);
                        Database_Items_SpecificDate.add(new Database_Items(Sayimy.getCount(), Sayimy.getStart(), Sayimy.getEnd(),sayimKey_max,SpecificDate,Sayimy.getMachineKey(),""));

                    }
                }else{
                    Toast.makeText(LogActivity.this, getResources().getString(R.string.Cant_Find_Record), Toast.LENGTH_SHORT).show();
                }
                Collections.reverse(Database_Items_SpecificDate); // ADD THIS LINE TO REVERSE ORDER!

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        firebaseFuncions.FirebaseRef().child("Machines").child(value).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot RefdataSnapshot) {

                Database_Items.clear();
                for(DataSnapshot ds : RefdataSnapshot.getChildren()) {
                    String name = ds.getKey();

                    for(DataSnapshot postSnapShot : RefdataSnapshot.child(name).getChildren()) {
                        Sayimy = postSnapShot.getValue(Database_Items.class);
                        Sayimy.setSayimKey(postSnapShot.getKey());
                        Database_Items.add(new Database_Items(Sayimy.getCount(), Sayimy.getStart(), Sayimy.getEnd(),Sayimy.getSayimKey(),name,"",""));
                    }
                }
                Collections.reverse(Database_Items); // ADD THIS LINE TO REVERSE ORDER!

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.onCanceled), Toast.LENGTH_SHORT).show();
            }

        });

        btn_ListData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(firebaseFuncions.InternetCheck()) {
                    for (int i = 0; i < Database_Items.size() ; i++) {
                        gridView.setAdapter(gridAdapter);
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//Butona basıldığında klavyeyi kapatır
                    imm.hideSoftInputFromWindow(tarih_editText.getWindowToken(), 0);// Butona basıldığında klavyeyi kapatır.
                }else{
                    Toast.makeText(LogActivity.this, getResources().getString(R.string.There_is_no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        final String finalValue = value;
        tarih_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SpecificDate = tarih_editText.getText().toString();
                if(firebaseFuncions.InternetCheck()){
                    firebaseFuncions.FirebaseRef().child("Machines").child(finalValue).child(SpecificDate).addListenerForSingleValueEvent(SpecificDate_Listener);
                    for (int i = 0; i < Database_Items_SpecificDate.size() ; i++) {
                        gridView.setAdapter(gridAdapter_Specific);
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);//Butona basıldığında klavyeyi kapatır
                    imm.hideSoftInputFromWindow(tarih_editText.getWindowToken(), 0);// Butona basıldığında klavyeyi kapatır.
                }

            }
        });


        Database_Items.clear();
        Database_Items_SpecificDate.clear();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    @Override
    public void onClick(View view) {
        if(view == Go2MainPage){
            this.finish();
        }
    }
}
