package com.example.admin.texttospeech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


    import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private static final int SPEECH_INPUT_ID = 100;
        private TextView tvSpeechOutput;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            tvSpeechOutput = findViewById(R.id.tvSpeechOutput);
            ImageButton btnSpeak = findViewById(R.id.btnSpeak);

            btnSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prompSpeechInput();
                }
            });
        }

        private void prompSpeechInput() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
            try {
                startActivityForResult(intent, SPEECH_INPUT_ID);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, R.string.speech_not_supported, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            tvSpeechOutput = findViewById(R.id.tvSpeechOutput);
            switch (requestCode) {
                case SPEECH_INPUT_ID:
                    if(resultCode == RESULT_OK && data != null) {
                        ArrayList<String> speechInfo;
                        speechInfo = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        tvSpeechOutput.setText(speechInfo.get(0));
                    }
                    else
                        Toast.makeText(this, getString(R.string.say_something), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
