package com.example.egiticimatematikoyunlari.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.egiticimatematikoyunlari.Activities.QuizActivity;
import com.example.egiticimatematikoyunlari.Models.KategoriModelClass;
import com.example.egiticimatematikoyunlari.R;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.kategoriCardTutucu>{

    private List<KategoriModelClass> kategoriList;
    private Context mContext;

    public KategoriAdapter(List<KategoriModelClass> kategoriList, Context mContext) {
        this.kategoriList = kategoriList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public KategoriAdapter.kategoriCardTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_card_tasarim,parent,false);

        return new kategoriCardTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull kategoriCardTutucu holder, int position) {

        final KategoriModelClass kmc=kategoriList.get(position);

        holder.textKategori.setText(kmc.getKategori_ad());
        Glide.with(mContext).load(kmc.getKategori_resim()).into(holder.imageKategori);

        holder.cardviewKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, QuizActivity.class);
                intent.putExtra("categoriId",kmc.getKategoriId());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }


    public class kategoriCardTutucu extends RecyclerView.ViewHolder{
        private ImageView imageKategori;
        private TextView textKategori;
        private CardView cardviewKategori;

        public kategoriCardTutucu(@NonNull View itemView) {
            super(itemView);

            imageKategori=itemView.findViewById(R.id.imageKategori);
            textKategori=itemView.findViewById(R.id.textKategori);
            cardviewKategori=itemView.findViewById(R.id.kategori_rv);



        }
    }
}
