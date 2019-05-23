package com.pesquisa.censo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TelaInicialActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        Button buttonStartQuiz = (Button) findViewById(R.id.button_start_pesquisa);
        buttonStartQuiz.setOnClickListener((View.OnClickListener) this);

        Button sobre = (Button) findViewById(R.id.sobreCensu);
        sobre.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sobreCensu:

                Intent intent2 = new Intent(TelaInicialActivity.this, SobreCensu.class);
                startActivity(intent2);
                break;

            case R.id.button_start_pesquisa:

                Intent intent = new Intent(TelaInicialActivity.this, PesquisaActivity.class);
                startActivity(intent);
                break;
        }
    }
}
