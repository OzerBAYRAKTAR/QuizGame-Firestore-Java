package com.example.egiticimatematikoyunlari.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egiticimatematikoyunlari.Models.QuestionModelClass;
import com.example.egiticimatematikoyunlari.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private TextView soru,option_1,option_2,option_3,option_4,questionCounter,timer;
    private Button nextBtn,quitBtn;
    private CountDownTimer countDownTimer;
    private QuestionModelClass questionNesne;
    private QuestionModelClass questionModelClass;
    private ArrayList<QuestionModelClass> sorularList;
    private FirebaseFirestore database;
    int index=0;
    int correctAnswer= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        soru =findViewById(R.id.soru);
        option_1=findViewById(R.id.option_1);
        option_2=findViewById(R.id.option_3);
        option_3=findViewById(R.id.option_4);
        option_4=findViewById(R.id.option_2);
        questionCounter=findViewById(R.id.questionCounter);
        timer=findViewById(R.id.timer);
        nextBtn=findViewById(R.id.nextBtn);
        quitBtn=findViewById(R.id.quitBtn);




        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                startActivity(new Intent(QuizActivity.this,MainActivity.class));
                finish();


            }
        });

        final String categoriId=getIntent().getStringExtra("categoriId");

        Random random=new Random();
        final int rand=random.nextInt(12);


        sorularList=new ArrayList<>();
        database=FirebaseFirestore.getInstance();

        database.collection("categories")
                        .document(categoriId)
                                .collection("sorularDataBase")
                                        .whereGreaterThanOrEqualTo("index",rand)
                                                .orderBy("index")
                                                        .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.getDocuments().size() < 5){

                            database.collection("categories")
                                    .document(categoriId)
                                    .collection("sorularDataBase")
                                    .whereLessThanOrEqualTo("index",rand)
                                    .orderBy("index")
                                    .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (DocumentSnapshot snapshot: queryDocumentSnapshots){
                                                QuestionModelClass questionModelClass=snapshot.toObject(QuestionModelClass.class);
                                                sorularList.add(questionModelClass);
                                                }
                                            setNextQuestion();
                                        }
                                    });
                        }else{
                            for (DocumentSnapshot snapshot: queryDocumentSnapshots){
                                QuestionModelClass questionModelClass=snapshot.toObject(QuestionModelClass.class);
                                sorularList.add(questionModelClass);
                            }
                            setNextQuestion();
                        }
                    }
                });

        countTimeReset();



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();

                if(index < sorularList.size()){
                    questionCounter.setText(String.format("%d/%d",(index+1),sorularList.size()));
                    questionModelClass=sorularList.get(index);
                    soru.setText(questionModelClass.getSoru());
                    option_1.setText(questionModelClass.getSecenek1());
                    option_2.setText(questionModelClass.getSecenek2());
                    option_3.setText(questionModelClass.getSecenek3());
                    option_4.setText(questionModelClass.getSecenek4());
                }
                else{
                    countDownTimer.cancel();
                    Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
                    intent.putExtra("correct",correctAnswer);
                    intent.putExtra("toplam",sorularList.size());
                    startActivity(intent);
                    QuizActivity.this.finish();
                }
                index++;

            }
        });
    }
    public void countTimeReset(){

        countDownTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                countDownTimer.cancel();
                Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
                intent.putExtra("correct",correctAnswer);
                intent.putExtra("toplam",sorularList.size());
                startActivity(intent);
                QuizActivity.this.finish();

            }
        }.start();
    }

    public void setNextQuestion(){

        if(index < sorularList.size()){
            questionCounter.setText(String.format("%d/%d",(index+1),sorularList.size()));
            questionModelClass=sorularList.get(index);
            soru.setText(questionModelClass.getSoru());
            option_1.setText(questionModelClass.getSecenek1());
            option_2.setText(questionModelClass.getSecenek2());
            option_3.setText(questionModelClass.getSecenek3());
            option_4.setText(questionModelClass.getSecenek4());

            option_1.setEnabled(true);
            option_2.setEnabled(true);
            option_3.setEnabled(true);
            option_4.setEnabled(true);
            nextBtn.setEnabled(true);
            quitBtn.setEnabled(true);



        }
        else{
            countDownTimer.cancel();
            Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
            intent.putExtra("correct",correctAnswer);
            intent.putExtra("toplam",sorularList.size());
            startActivity(intent);
            QuizActivity.this.finish();
        }
        index++;
    }
    public void showAnswer(){
        if (questionModelClass.getCevap().equals(option_1.getText().toString()))
            option_1.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(questionModelClass.getCevap().equals(option_2.getText().toString()))
            option_2.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(questionModelClass.getCevap().equals(option_3.getText().toString()))
            option_3.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(questionModelClass.getCevap().equals(option_4.getText().toString()))
            option_4.setBackground(getResources().getDrawable(R.drawable.option_right));



    }
    public void checkAnswer(TextView textView){
        String selectedAnswer=textView.getText().toString();
        if(selectedAnswer.equals(questionModelClass.getCevap())){
            correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else{
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }
    public void reset(){
        option_1.setBackground(getResources().getDrawable(R.drawable.options_unselected));
        option_2.setBackground(getResources().getDrawable(R.drawable.options_unselected));
        option_3.setBackground(getResources().getDrawable(R.drawable.options_unselected));
        option_4.setBackground(getResources().getDrawable(R.drawable.options_unselected));
    }





    public void onClick(View view) {

        Handler mHandler=new Handler();
        Runnable mLaunchTask=new Runnable() {
            @Override
            public void run() {

                reset();
                setNextQuestion();
            }
        };
        mHandler.postDelayed(mLaunchTask,600);

        switch (view.getId()) {
            case R.id.option_1:
                case R.id.option_2:
                    case R.id.option_3:
                        case R.id.option_4:
                            TextView selected=(TextView) view;
                            checkAnswer(selected);
                            if (selected !=null){
                                option_1.setEnabled(false);
                                option_2.setEnabled(false);
                                option_3.setEnabled(false);
                                option_4.setEnabled(false);
                                nextBtn.setEnabled(false);
                                quitBtn.setEnabled(false);
                            }
                            break;
        }
    }

}