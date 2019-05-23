package com.pesquisa.censo;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;


public class PesquisaActivity extends AppCompatActivity {


    private static final String KEY_Pergunta_COUNT = "keyPerguntaCount";

    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_Pergunta_LIST = "keyPerguntaList";

    private TextView textViewPergunta;
    private TextView textViewPerguntaCount;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;


    private ArrayList<Pergunta> PerguntaList;
    private int PerguntaCounter;
    private int PerguntaCountTotal;
    private Pergunta currentPergunta;
    private boolean answered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        textViewPergunta = findViewById(R.id.text_view_pergunta);

        textViewPerguntaCount = findViewById(R.id.text_view_pergunta_count);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();

        Intent intent = getIntent();


        if (savedInstanceState == null) {
            PesquisaDbHelper dbHelper = PesquisaDbHelper.getInstance(this);
            PerguntaList = dbHelper.getPerguntas();
            PerguntaCountTotal = PerguntaList.size();
            Collections.shuffle(PerguntaList);

            showNextPergunta();
        } else {
            PerguntaList = savedInstanceState.getParcelableArrayList(KEY_Pergunta_LIST);
            PerguntaCountTotal = PerguntaList.size();
            PerguntaCounter = savedInstanceState.getInt(KEY_Pergunta_COUNT);
            currentPergunta = PerguntaList.get(PerguntaCounter - 1);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (answered) {
                showSolution();
            }
        }

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PerguntaCountTotal > PerguntaCounter) {
                    if (!rb1.isChecked() && !rb2.isChecked() && !rb3.isChecked()) {
                        Toast.makeText(PesquisaActivity.this, "Por favor, selecione uma resposta", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        showNextPergunta();
                    }

                }else{
                    finalizarPergunta();
                }
            }
        });
    }

    private void showNextPergunta() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (PerguntaCounter < PerguntaCountTotal) {
            currentPergunta = PerguntaList.get(PerguntaCounter);

            textViewPergunta.setText(currentPergunta.getPergunta());
            rb1.setText(currentPergunta.getOpcao1());
            rb2.setText(currentPergunta.getOpcao2());
            rb3.setText(currentPergunta.getOpcao3());

            PerguntaCounter++;
            textViewPerguntaCount.setText("Pergunta: " + PerguntaCounter + "/" + PerguntaCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirmar");


        } else {
            finalizarPergunta();
        }
    }





    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentPergunta.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewPergunta.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewPergunta.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewPergunta.setText("Answer 3 is correct");
                break;
        }

        if (PerguntaCounter < PerguntaCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finalizarPergunta() {
        Intent resultIntent = new Intent();
        finish();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_Pergunta_COUNT, PerguntaCounter);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_Pergunta_LIST, PerguntaList);
    }
}