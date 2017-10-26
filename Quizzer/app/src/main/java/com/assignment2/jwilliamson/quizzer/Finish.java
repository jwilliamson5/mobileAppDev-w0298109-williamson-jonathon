package com.assignment2.jwilliamson.quizzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Finish extends AppCompatActivity {

    TextView score_TV;
    Button tryAgain_btn;
    Button changeName_btn;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        score_TV = (TextView) findViewById(R.id.score_TV);
        tryAgain_btn = (Button) findViewById(R.id.tryAgain_btn);
        changeName_btn = (Button) findViewById(R.id.changeName_btn);
        extras = getIntent().getExtras();

        score_TV.setText(getResources().getString(R.string.final_score, extras.get("name"), extras.get("score"), extras.get("total")));

        tryAgain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Quiz.class);
                i.putExtra("name",(String) extras.get("name"));
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        changeName_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MainActivity.class);
                i.putExtra("name", (String) extras.get("name"));
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            changeName_btn.performClick();
        }
        return super.onKeyDown(keyCode, event);
    }
}
