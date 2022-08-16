package com.example.egiticimatematikoyunlari.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.egiticimatematikoyunlari.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText emailGiris,passGiris;
    private Button btnGiris,btnOlustur;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailGiris=findViewById(R.id.emailGiris);
        passGiris=findViewById(R.id.passGiris);
        btnGiris=findViewById(R.id.btnGiris);
        btnOlustur=findViewById(R.id.btnOlustur);

        auth=FirebaseAuth.getInstance();

        dialog=new ProgressDialog(this);
        dialog.setMessage("Giriş Yapılıyor...");

        if(auth.getCurrentUser() !=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }




        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass;
                email=emailGiris.getText().toString().trim();
                pass=passGiris.getText().toString().trim();
                dialog.show();

                if (email.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Lütfen Gerekli Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{



                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                            Toast.makeText(LoginActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();


                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            );}



            }
        });
        btnOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });


    }
}