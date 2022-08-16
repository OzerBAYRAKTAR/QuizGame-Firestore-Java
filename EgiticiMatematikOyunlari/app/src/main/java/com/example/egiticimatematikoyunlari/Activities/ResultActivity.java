package com.example.egiticimatematikoyunlari.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.egiticimatematikoyunlari.R;

public class ResultActivity extends AppCompatActivity {
    private TextView score;
    private Button paylasBtn,tekrarBtn;
    private QuizActivity quizActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);




        score=findViewById(R.id.score);
        paylasBtn=findViewById(R.id.paylasBtn);
        tekrarBtn=findViewById(R.id.tekrarBtn);

        int correctAnswer=getIntent().getIntExtra("correct",0);
        int toplam=getIntent().getIntExtra("toplam",0);


        score.setText(String.format("%d/%d", correctAnswer,toplam));

        tekrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,MainActivity.class));
                finish();
            }
        });

        paylasBtn.setOnClickListener(new View.OnClickListener() {
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





    }
}