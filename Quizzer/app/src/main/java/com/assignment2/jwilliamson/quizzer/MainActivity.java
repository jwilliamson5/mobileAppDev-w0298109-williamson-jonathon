package com.assignment2.jwilliamson.quizzer;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText name_ET;
    Button startQuiz_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name_ET = (EditText) findViewById(R.id.name_ET);
        startQuiz_btn = (Button) findViewById(R.id.startQuiz_btn);

        startQuiz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name_ET.getText().toString().matches("^\\s+$") || name_ET.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(view.getContext(), Quiz.class);
                i.putExtra("name", name_ET.getText().toString().trim());
                startActivity(i);
            }
        });
    }

}
