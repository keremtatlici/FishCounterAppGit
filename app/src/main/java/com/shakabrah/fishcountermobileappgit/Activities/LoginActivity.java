package com.shakabrah.fishcountermobileappgit.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shakabrah.fishcountermobileappgit.FirebaseFuncions;
import com.shakabrah.fishcountermobileappgit.R;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener {
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//--------------------------------------DEĞİŞKEN TANIMLARI------------------------------------------------------------------------//
    CheckBox saveLoginCheckBox;
    String email,password;
    Button girisbtn;
    TextView editTextEmail,editTextPassword;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


    FirebaseFuncions firebaseFuncions = new FirebaseFuncions(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseFuncions.NavigateWhileLoged();

        girisbtn = findViewById(R.id.girisbtn);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        girisbtn.setEnabled(!editTextEmail.getText().toString().trim().isEmpty() && !editTextPassword.getText().toString().trim().isEmpty());
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextEmail.setText(loginPreferences.getString("username", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
            girisbtn.setEnabled(!editTextEmail.getText().toString().trim().isEmpty() && !editTextPassword.getText().toString().trim().isEmpty());
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//--------------------------------------ONCREATE LİSTENERSS! ------------------------------------------------------------------------//
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                girisbtn.setEnabled(!editTextEmail.getText().toString().trim().isEmpty() && !editTextPassword.getText().toString().trim().isEmpty());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                girisbtn.setEnabled(!editTextEmail.getText().toString().trim().isEmpty() && !editTextPassword.getText().toString().trim().isEmpty());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        girisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", email);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }
                firebaseFuncions.login(email,password);
            }
        });
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//-------------------------------------- OVERRİDE Functions ------------------------------------------------------------------------//
    @Override
    public void onClick(View view) {

    }
}
