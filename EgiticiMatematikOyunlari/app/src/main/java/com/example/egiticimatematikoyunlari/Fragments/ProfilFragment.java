package com.example.egiticimatematikoyunlari.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egiticimatematikoyunlari.Activities.MainActivity;
import com.example.egiticimatematikoyunlari.R;
import com.example.egiticimatematikoyunlari.SoundService;


public class ProfilFragment extends Fragment{
    private Switch switchMusic;
    private ImageView likeBtn,shareBtn,puanBtn;
    private MediaPlayer player;
    private Context mContext;



    public static final String SHARED_PREFS="sharedPrefs";
    public static final String SWITCHMUSIC="switchMusic";
    private boolean switcMusichOnOff;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
    public void BackgroundPlayer(Context mContext, int res) {
        this.mContext = mContext;
        player = MediaPlayer.create(this.mContext, res);
        player.setLooping(true);
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_profil,container,false);


        switchMusic=view.findViewById(R.id.switchMusic);

        shareBtn=view.findViewById(R.id.shareBtn);
        likeBtn=view.findViewById(R.id.likeBtn);
        puanBtn=view.findViewById(R.id.puanBtn);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareBody="Bu Matematik Uygulamasını Dene";
                String shareText="https://play.google.com/store/apps/details?id=com.abc.tarifkitabim";
                i.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                i.putExtra(Intent.EXTRA_TEXT,shareText);
                startActivity(Intent.createChooser(i,"PAYLAŞ"));

            }
        });
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=com.abc.tarifkitabim";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        puanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //diyalog başlatılır.
                Dialog dialog=new Dialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //view set edildi.
                dialog.setContentView(R.layout.ratingitem);
                //diyalog görüntülendi
                dialog.show();

                //değişken atama
                RatingBar rating_bar=dialog.findViewById(R.id.rating_bar);
                TextView tv_rating=dialog.findViewById(R.id.tv_rating);
                Button btnKaydet=dialog.findViewById(R.id.btnKaydet);

                rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        tv_rating.setText(String.format("%s",rating));
                    }
                });
                btnKaydet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sRating =String.valueOf(rating_bar.getRating());
                        Toast.makeText(getActivity().getApplicationContext(), "Oylamanız İçin Teşekkürler", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });








            }
        });

        player = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.music);


        switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    mContext.startService(new Intent(getActivity().getApplicationContext(), SoundService.class));
                }else{
                    mContext.stopService(new Intent(getActivity().getApplicationContext(), SoundService.class));
                }
                saveData();
            }


        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                player.release();
            }
        });

        loadData();
        updateViews();

        return view.getRootView();
    }
    public void updateViews(){
        switchMusic.setChecked(switcMusichOnOff);
    }
    private void saveData() {
        SharedPreferences sp= getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();

        editor.putBoolean(SWITCHMUSIC,switchMusic.isChecked());
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sp= getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        switcMusichOnOff=sp.getBoolean(SWITCHMUSIC,false);

    }

    @Override
    public void onResume() {
        SharedPreferences sp= getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        switcMusichOnOff=sp.getBoolean(SWITCHMUSIC,switchMusic.isChecked());
        super.onResume();
    }



}