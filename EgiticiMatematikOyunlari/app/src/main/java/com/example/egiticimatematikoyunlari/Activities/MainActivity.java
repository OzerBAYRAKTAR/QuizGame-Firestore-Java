package com.example.egiticimatematikoyunlari.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;


import androidx.fragment.app.FragmentTransaction;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.egiticimatematikoyunlari.Fragments.AnasayfaFragment;
import com.example.egiticimatematikoyunlari.Fragments.ProfilFragment;
import com.example.egiticimatematikoyunlari.Models.KategoriModelClass;
import com.example.egiticimatematikoyunlari.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<KategoriModelClass> kategoriList;
    private Toolbar toolbar;
    private BottomNavigationView bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        toolbar=findViewById(R.id.toolbar);
        bottomBar=findViewById(R.id.bottomBar);

        //toolbar.setTitle("Anasayfa");
        setSupportActionBar(toolbar);

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new AnasayfaFragment());
        transaction.commit();




        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.nav_anasayfa:
                        transaction.replace(R.id.content, new AnasayfaFragment());
                        transaction.commit();
                        break;
                    case R.id.nav_profil:
                        transaction.replace(R.id.content, new ProfilFragment());
                        transaction.commit();
                        break;

                }
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Çıkış yapmak istiyor musnuz?");
        builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //EVETE TIKLARSA UYGULAMA KAPANIR
                finish();
            }
        });
        builder.setNegativeButton("HAYIR",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //HAYIR DERSE ÇIKIŞ İPTAL OLUR
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }



}