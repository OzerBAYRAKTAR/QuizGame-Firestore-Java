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

import com.example.egiticimatematikoyunlari.Models.UserModelClass;
import com.example.egiticimatematikoyunlari.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private EditText sgnupName,sgnupEmail,sgnupPassword;
    private Button btnKayit,btnZaten;
    private FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sgnupName=findViewById(R.id.sgnupName);
        sgnupEmail=findViewById(R.id.sgnupEmail);
        sgnupPassword=findViewById(R.id.sgnupPassword);
        btnKayit=findViewById(R.id.btnKayit);
        btnZaten=findViewById(R.id.btnZaten);


        auth=FirebaseAuth.getInstance();

        dialog=new ProgressDialog(this);
        dialog.setMessage("Hesap Oluşturuluyor...");


        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass,name;

                email=sgnupEmail.getText().toString();
                pass=sgnupPassword.getText().toString();
                name=sgnupName.getText().toString();

                UserModelClass user=new UserModelClass(name,email,pass);

                dialog.show();
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String uid=task.getResult().getUser().getUid();

                            db.collection("users").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                dialog.dismiss();
                                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                                finish();
                                            }else{
                                                Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage()
                                                        , Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } else {
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage()
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnZaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));

            }
        });
    }
}