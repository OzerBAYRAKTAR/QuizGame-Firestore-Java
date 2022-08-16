package com.example.egiticimatematikoyunlari.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.egiticimatematikoyunlari.Activities.MainActivity;
import com.example.egiticimatematikoyunlari.Adapters.KategoriAdapter;
import com.example.egiticimatematikoyunlari.Models.KategoriModelClass;
import com.example.egiticimatematikoyunlari.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AnasayfaFragment extends Fragment {
    private ArrayList<KategoriModelClass> kategoriList;
    private KategoriAdapter adapter;
    private RecyclerView RecyclerKategori;
    private FirebaseFirestore database;
    private Context mContext;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view=inflater.inflate(R.layout.fragment_anasayfa,container,false);
        RecyclerKategori=view.findViewById(R.id.RecyclerKategori);
        RecyclerKategori.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        RecyclerKategori.setHasFixedSize(true);

        database=FirebaseFirestore.getInstance();

        kategoriList=new ArrayList<>();

        database.collection("categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                kategoriList.clear();
                for (DocumentSnapshot snapshot:value.getDocuments()){

                    KategoriModelClass model=snapshot.toObject(KategoriModelClass.class);

                    model.setKategoriId(snapshot.getId());
                    kategoriList.add(model);
                }
                adapter=new KategoriAdapter(kategoriList,getContext());
                RecyclerKategori.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        });


        return view.getRootView();
    }

 }