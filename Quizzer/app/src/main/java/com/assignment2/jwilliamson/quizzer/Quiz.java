package com.assignment2.jwilliamson.quizzer;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Quiz extends AppCompatActivity {

    private Bundle extras;
    private TextView definition_TV;
    private RadioGroup rg;
    private ArrayList<RadioButton> radioButtons = new ArrayList<>();
    private Map<String, String> termMap = new HashMap<>();
    private ArrayList<String> usedTerms = new ArrayList<>();
    private final String delimiter = "<\\*SPLIT\\*>";
    private RadioButton answerButton;
    private Button nextQuestion_btn;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        extras = getIntent().getExtras();
        nextQuestion_btn = (Button) findViewById(R.id.nextQuestion_btn);
        nextQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nextQuestion()) {
                    Intent i = new Intent(view.getContext(), Finish.class);
                    i.putExtra("name", (String) extras.get("name"));
                    i.putExtra("score", score);
                    i.putExtra("total", termMap.keySet().size());
                    startActivity(i);
                    return;
                }
                nextQuestion_btn.setClickable(false);
                for(RadioButton rb : radioButtons) {
                    rb.setClickable(true);
                }
            }
        });
        definition_TV = (TextView) findViewById(R.id.definition_TV);
        rg = (RadioGroup) findViewById(R.id.rg);
        for(int i = 0; i < rg.getChildCount(); i++) {
            radioButtons.add((RadioButton) rg.getChildAt(i));
        }

        for(RadioButton rb : radioButtons) {
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkSelection();
                }
            });
        }

        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.quiz_data)));
            String[] dLine;
            while(bf.ready()) {
                dLine = bf.readLine().split(delimiter);
                termMap.put(dLine[0], dLine[1]);
            }
            usedTerms.addAll(termMap.keySet());
        } catch (Exception e) {
            Log.d("OnCreate File Read", "Error reading file: ", e);
        }
        nextQuestion();
    }

    public void checkSelection() {
        RadioButton selected = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        if(selected == answerButton) {
            Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show();
            score += 1;
        } else {
            Toast.makeText(this, R.string.incorrect, Toast.LENGTH_SHORT).show();
        }
        answerButton.setTextColor(ContextCompat.getColor(this, R.color.colorCorrect));
        for(RadioButton rb : radioButtons) {
            rb.setClickable(false);
        }
        nextQuestion_btn.setClickable(true);
    }

    public boolean nextQuestion() {
        rg.clearCheck();
        if(usedTerms.isEmpty()) {
            return false;
        }
        if(usedTerms.size() == 1) {
            nextQuestion_btn.setText(R.string.finish);
        }
        Collections.shuffle(usedTerms);
        Collections.shuffle(radioButtons);

        answerButton = radioButtons.get(0);
        answerButton.setText(usedTerms.remove(0));

        Iterator<String> termIter = termMap.keySet().iterator();
        String nextTerm;
        for(RadioButton rb : radioButtons) {
            if((nextTerm = termIter.next()).equals(answerButton.getText())) {
                nextTerm = termIter.next();
            }
            rb.setTextColor(ContextCompat.getColor(this, R.color.colorDefault));
            if(rb != answerButton) {
                rb.setText(nextTerm);
            }
        }
        definition_TV.setText(termMap.get(answerButton.getText().toString()));

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("name", (String) extras.get("name"));
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
}
