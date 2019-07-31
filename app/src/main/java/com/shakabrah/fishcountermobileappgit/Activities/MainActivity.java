package com.shakabrah.fishcountermobileappgit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shakabrah.fishcountermobileappgit.Database_Items;
import com.shakabrah.fishcountermobileappgit.FirebaseFuncions;
import com.shakabrah.fishcountermobileappgit.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//--------------------------------------DEĞİŞKEN TANIMLARI------------------------------------------------------------------------//
    FirebaseFuncions firebaseFuncions = new FirebaseFuncions(this);
    Database_Items Sayimx;
    int sayimKey_max ;

    Bundle b ;
    String  value = "";

    TextView CountView,StartView,EndView,Date_Layout,textViewFlash,MacNumTv;
    Button btn,cikisyapbtn,go2machinebtn;
    MachinesActivity machinesActivity = new MachinesActivity();
    final DateFormat tarih = new SimpleDateFormat("dd-MM-yyyy  EEEE");
    final DateFormat tarih4db = new SimpleDateFormat("dd-MM-yyyy");
    final Date suan = new Date();


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------- OnCreate Methodu // DEĞİŞKEN TANIMLARI ------------------------------------------------------------------------//
        Date_Layout = findViewById(R.id.Date_Layout);
        btn = findViewById(R.id.button5);
        cikisyapbtn = findViewById(R.id.cikisyapbtn);
        textViewFlash = findViewById(R.id.textViewFlash);
        CountView = findViewById(R.id.FishCount);
        StartView = findViewById(R.id.StartTime_Label);
        EndView = findViewById(R.id.EndTime_Label);
        go2machinebtn = findViewById(R.id.go2machinebtn);
        MacNumTv = findViewById(R.id.MacNumTv);

        Date_Layout.setText(tarih.format(suan));

        btn.setOnClickListener(this);
        cikisyapbtn.setOnClickListener(this);
        go2machinebtn.setOnClickListener(this);
        firebaseFuncions.NavigateWhileNotLoged();


        b = getIntent().getExtras();
        if(b != null){
            setValue(b.getString("key"));
        MacNumTv.setText(getResources().getString(R.string.MacNum)+ value);
        }

        firebaseFuncions.FirebaseRef().child("Machines").child(value).child(tarih4db.format(suan)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot SonSayimSnapshot: dataSnapshot.getChildren()){
                    sayimKey_max = Integer.parseInt(SonSayimSnapshot.getKey());
                }

                Sayimx = dataSnapshot.child(String.valueOf(sayimKey_max)).getValue(Database_Items.class);


                if(Sayimx != null)
                {
                    if(Sayimx.getCount() == null) {
                        Sayimx.setCount("0");
                    }
                    if(Sayimx.getEnd() == null || Sayimx.getEnd() == "null" || Sayimx.getEnd().compareTo("Null")==0){
                        Sayimx.setEnd(getResources().getString(R.string.Counting_Not_Done_Yet));
                        textViewFlash.setText(getResources().getString(R.string.Fishes_Are_Counting));
                    }
                    else{
                        textViewFlash.setText(getResources().getString(R.string.There_is_no_count_now));
                    }

                    if(Sayimx.getStart() == null){
                        CountView.setText(getResources().getString(R.string.There_is_no_counting_today));
                        StartView.setText("");
                        EndView.setText("");
                    }else{
                        CountView.setText(getResources().getString(R.string.Fish_Count)+Sayimx.getCount()+"("+sayimKey_max+getResources().getString(R.string.Count_num));
                        StartView.setText(getResources().getString(R.string.Start_Time) + Sayimx.getStart());
                        EndView.setText(getResources().getString(R.string.End_Time) + Sayimx.getEnd());
                    }
                }else {
                    CountView.setText(getResources().getString(R.string.There_is_no_counting_today));
                    StartView.setText("");
                    EndView.setText("");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.onCanceled), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == btn){
            Intent intent = new Intent(MainActivity.this, LogActivity.class);
            Bundle b = new Bundle();
            b.putString("key", getValue());
            intent.putExtras(b);
            startActivity(intent);
        }else if(view == cikisyapbtn){
            this.finish();
            firebaseFuncions.signOut();
        }else if (view == go2machinebtn){
            firebaseFuncions.Navigator(MachinesActivity.class);
        }
    }
}
