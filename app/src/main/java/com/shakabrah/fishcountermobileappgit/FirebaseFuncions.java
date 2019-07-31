package com.shakabrah.fishcountermobileappgit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shakabrah.fishcountermobileappgit.Activities.LoginActivity;
import com.shakabrah.fishcountermobileappgit.Activities.MachinesActivity;

import static android.widget.Toast.LENGTH_SHORT;

public class FirebaseFuncions {
    FirebaseDatabase Ref = FirebaseDatabase.getInstance("https://countera-mobile-app.firebaseio.com/"); // Yongatek  DB
    //FirebaseDatabase Ref = FirebaseDatabase.getInstance("https://users-5d30c.firebaseio.com/");

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Context mContext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public FirebaseFuncions(Context mContext) {
        this.mContext = mContext;
    }

    public DatabaseReference FirebaseRef() {
        DatabaseReference Ref1 = Ref.getReference();
        return Ref1;
    }

    public DatabaseReference FirebaseRef(String path) {
        DatabaseReference Ref1 = Ref.getReference(path);
        return Ref1;
    }

    public void Navigator( Class b) {
        Intent myIntent = new Intent(mContext, b);
        mContext.startActivity(myIntent);
    }
    public void Navigator( Context m,Class b) {
        Intent myIntent = new Intent(m, b);
        m.startActivity(myIntent);
    }
    public void NavigateWhileNotLoged() {
       if(InternetCheck()){
            if(firebaseAuth.getCurrentUser() == null) {
                Navigator(LoginActivity.class);
            }
        }
    }
    public void NavigateWhileLoged() {
        if(InternetCheck()){
            if(firebaseAuth.getCurrentUser() != null){
                Navigator(MachinesActivity.class);
            }
        }
    }

    public boolean InternetCheck() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isAvailable() && manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.There_is_no_internet_connection), Toast.LENGTH_SHORT).show();
            return false;

        }
    }
    public void signOut(){
        if(InternetCheck()){
            firebaseAuth.signOut();
            if(firebaseAuth.getCurrentUser() == null){
                Toast.makeText(mContext, mContext.getResources().getString(R.string.Logout_Successful), Toast.LENGTH_SHORT).show();
                Navigator(LoginActivity.class);
            }
        }
    }

    public void login(String email, String password){
        final ProgressDialog progressDialog = new ProgressDialog(mContext);

        if(InternetCheck()){
            progressDialog.setMessage(mContext.getResources().getString(R.string.Login_Progress));
            progressDialog.show();
            preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            editor = preferences.edit();

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Navigator(MachinesActivity.class);

                            }else if(InternetCheck() == false){
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.There_is_no_internet_connection) , LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(mContext, mContext.getResources().getString(R.string.Invalid_User) , LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public String getUserMail(){
        if(InternetCheck() && firebaseAuth.getCurrentUser() !=null){
            return firebaseAuth.getCurrentUser().getEmail();
        }
        return null;
    }
}
