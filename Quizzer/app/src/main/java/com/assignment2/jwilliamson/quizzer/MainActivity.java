package com.assignment2.jwilliamson.quizzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText name_ET;
    private Button startQuiz_btn;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name_ET = (EditText) findViewById(R.id.name_ET);
        startQuiz_btn = (Button) findViewById(R.id.startQuiz_btn);

        if((extras = getIntent().getExtras()) != null) {
            if(extras.containsKey("name")) {
                name_ET.setText((String) extras.get("name"));
            }
        }

        startQuiz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name_ET.getText().toString().matches("^\\s+$") || name_ET.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), getString(R.string.please_enter_your_name), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(view.getContext(), Quiz.class);
                i.putExtra("name", name_ET.getText().toString().trim());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

}
