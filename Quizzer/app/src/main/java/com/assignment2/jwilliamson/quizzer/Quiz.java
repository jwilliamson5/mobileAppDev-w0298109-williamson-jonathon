package com.assignment2.jwilliamson.quizzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Map;

public class Quiz extends AppCompatActivity {

    private Bundle extras;
    private TextView definition_TV;
    private RadioGroup rg;
    private ArrayList<RadioButton> radioButtons = new ArrayList<>();
    private Map<String, String> termMap = new HashMap<>();
    private ArrayList<String> terms = new ArrayList<>();
    private ArrayList<String> usedTerms = new ArrayList<>();
    private final String delimiter = "<\\*SPLIT\\*>";
    private String answer;
    private RadioButton answerButton;
    private Button nextQuestion_btn;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        nextQuestion_btn = (Button) findViewById(R.id.nextQuestion_btn);
        nextQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nextQuestion()) {
                    Intent i = new Intent(view.getContext(), Finish.class);
                    i.putExtra("name", extras.get("name").toString());
                    i.putExtra("score", score);
                    i.putExtra("total", terms.size());
                    startActivity(i);
                    return;
                }
                nextQuestion_btn.setClickable(false);
                for(RadioButton rb : radioButtons) {
                    rb.setClickable(true);
                }
            }
        });
        extras = getIntent().getExtras();
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
            terms.addAll(termMap.keySet());
        } catch (Exception e) {
            Log.d("File Reader Try/Catch", "Error reading file: ", e);
        }
        nextQuestion();
    }

    public boolean checkSelection() {
        RadioButton selected = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        boolean isCorrect = selected.getText().toString().equals(answer);
        if(isCorrect) {
            Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show();
            score += 1;
        } else {
            Toast.makeText(this, R.string.incorrect, Toast.LENGTH_SHORT).show();
        }
        answerButton.setTextColor(getResources().getColor(R.color.colorCorrect));
        for(RadioButton rb : radioButtons) {
            rb.setClickable(false);
        }
        nextQuestion_btn.setClickable(true);
        return isCorrect;
    }

    public boolean nextQuestion() {
        rg.clearCheck();
        if(usedTerms.containsAll(terms)) {
            return false;
        }
        if(usedTerms.size() == terms.size() - 1) {
            nextQuestion_btn.setText(R.string.finish);
        }
        Collections.shuffle(terms);
        Collections.shuffle(radioButtons);
        int counter = -1;
        do {
            counter++;
            answer = terms.get(counter);
            Log.d("Quiz", answer);
        } while(usedTerms.contains(answer));

        for(int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setTextColor(getResources().getColor(R.color.colorDefault));
            int index = counter + i;
            if(index >= terms.size()) {
                index -= terms.size();
            }
            radioButtons.get(i).setText(terms.get(index));
        }
        answerButton = radioButtons.get(0);
        definition_TV.setText(termMap.get(answer));
        usedTerms.add(answer);

        return true;
    }
}
